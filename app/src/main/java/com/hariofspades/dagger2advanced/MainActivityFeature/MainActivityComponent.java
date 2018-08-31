package com.hariofspades.dagger2advanced.MainActivityFeature;

import com.hariofspades.dagger2advanced.adapter.RandomUserAdapter;
import com.hariofspades.dagger2advanced.component.RandomUserComponent;
import com.hariofspades.dagger2advanced.interfaces.RandomUsersApi;

import dagger.Component;

/**
 * Created by abhishekn on 8/30/2018.
 */
@Component( modules = {MainActivityModule.class}, dependencies = RandomUserComponent.class)
@MainActivityScope
public interface MainActivityComponent {

    RandomUserAdapter getRandomUserAdapter();

    RandomUsersApi getRandomUserService();
}
