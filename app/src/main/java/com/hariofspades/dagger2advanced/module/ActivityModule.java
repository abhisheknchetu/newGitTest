package com.hariofspades.dagger2advanced.module;

import android.content.Context;

import com.hariofspades.dagger2advanced.interfaces.RandomUserApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abhishekn on 8/28/2018.
 */

@Module
public class ActivityModule {

    private final Context context;

    ActivityModule(Context context) {
        this.context = context;
    }

    @Named("activity_context")
    @RandomUserApplicationScope
    @Provides
    public Context provideContext(){ return context; }
}
