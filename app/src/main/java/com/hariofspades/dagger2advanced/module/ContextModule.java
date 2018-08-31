package com.hariofspades.dagger2advanced.module;

import android.content.Context;

import com.hariofspades.dagger2advanced.interfaces.ApplicationContext;
import com.hariofspades.dagger2advanced.interfaces.RandomUserApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abhishekn on 8/28/2018.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @ApplicationContext
    @RandomUserApplicationScope
    @Provides
    public Context provideContext(){ return context;}
}
