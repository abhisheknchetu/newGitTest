package com.hariofspades.dagger2advanced.application;

import android.app.Activity;
import android.app.Application;

//import com.hariofspades.dagger2advanced.component.DaggerRandomUserComponent;
import com.hariofspades.dagger2advanced.component.DaggerRandomUserComponent;
import com.hariofspades.dagger2advanced.component.RandomUserComponent;
import com.hariofspades.dagger2advanced.module.ContextModule;

/**
 * Created by abhishekn on 8/30/2018.
 */

public class RandomUserApplication extends Application {

    private RandomUserComponent randomUserComponent;

    public static RandomUserApplication get(Activity activity){
        return (RandomUserApplication) activity.getApplication();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        randomUserComponent = DaggerRandomUserComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public RandomUserComponent getRandomUserComponent(){
        return randomUserComponent;
    }
}
