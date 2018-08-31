package com.hariofspades.dagger2advanced.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by abhishekn on 8/28/2018.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface RandomUserApplicationScope {
}
