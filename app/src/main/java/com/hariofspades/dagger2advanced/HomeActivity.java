package com.hariofspades.dagger2advanced;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hariofspades.dagger2advanced.MainActivityFeature.DaggerMainActivityComponent;
import com.hariofspades.dagger2advanced.MainActivityFeature.MainActivityComponent;
import com.hariofspades.dagger2advanced.MainActivityFeature.MainActivityModule;
import com.hariofspades.dagger2advanced.adapter.RandomUserAdapter;
import com.hariofspades.dagger2advanced.application.RandomUserApplication;
import com.hariofspades.dagger2advanced.component.DaggerRandomUserComponent;
import com.hariofspades.dagger2advanced.component.RandomUserComponent;
import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;
import com.hariofspades.dagger2advanced.model.RandomUsers;
import com.hariofspades.dagger2advanced.module.ContextModule;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by abhishekn on 8/27/2018.
 */

public class HomeActivity extends AppCompatActivity {

    RandomUserAdapter mRandomUserAdapter;
    RecyclerView mRecyclerView;
    Retrofit mRetrofit;
    Picasso mPicasso;
    RandomUsersApi mRandomUserApi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        beforeDagger2();
//        afterDagger();
        afterActivityLevelComponent();
        populateUsers();
    }

    private void afterActivityLevelComponent(){
        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent
                .builder()
                .mainActivityModule(new MainActivityModule(this))
                .randomUserComponent(RandomUserApplication.get(this).getRandomUserComponent())
                .build();

        mRandomUserAdapter = mainActivityComponent.getRandomUserAdapter();
        mRandomUserApi = mainActivityComponent.getRandomUserService();
    }

    private void afterDagger(){
        RandomUserComponent randomUserComponent = DaggerRandomUserComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();
        mRandomUserApi = randomUserComponent.getRandomUserService();
        mPicasso = randomUserComponent.getPicasso();
    }
    private void beforeDagger2(){
        Timber.plant(new Timber.DebugTree());

        Gson gson = new GsonBuilder().create();

        File cacheFile = new File(this.getCacheDir(), "HttpCache");
        cacheFile.mkdirs();

        Cache cache = new Cache(cacheFile, 10*1000*1000);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){

            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttpClient);

        mPicasso = new Picasso.Builder(this)
                .downloader(okHttp3Downloader)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private void populateUsers(){
        Call<RandomUsers> randomUsersCall = getRandosmUserService().getRandomUsers(10);
        randomUsersCall.enqueue(new Callback<RandomUsers>() {
            @Override
            public void onResponse(Call<RandomUsers> call, Response<RandomUsers> response) {
                if (response.isSuccessful()){
                    mRandomUserAdapter = new RandomUserAdapter(mPicasso);
                    mRandomUserAdapter.setItems(response.body().getResults());
                    mRecyclerView.setAdapter(mRandomUserAdapter);
                }
            }

            @Override
            public void onFailure(Call<RandomUsers> call, Throwable t) {
                t.printStackTrace();
                Timber.i(t.getMessage());
            }
        });
    }

    public RandomUsersApi getRandosmUserService(){
//        return mRetrofit.create(RandomUsersApi.class); // before Dagger
        return mRandomUserApi;  // after dagger
    }
    private void initView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
