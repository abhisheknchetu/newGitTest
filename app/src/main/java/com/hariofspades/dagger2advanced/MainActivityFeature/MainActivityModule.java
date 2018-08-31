package com.hariofspades.dagger2advanced.MainActivityFeature;

import com.hariofspades.dagger2advanced.HomeActivity;
import com.hariofspades.dagger2advanced.MainActivity;
import com.hariofspades.dagger2advanced.adapter.RandomUserAdapter;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abhishekn on 8/30/2018.
 */
@Module
public class MainActivityModule {

    private final HomeActivity mainActivity;

    public MainActivityModule(HomeActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @MainActivityScope
    public RandomUserAdapter randomUserAdapter(Picasso picasso){
        return new RandomUserAdapter(mainActivity, picasso);
    }
}
