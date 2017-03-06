package com.wassim.mvvmboilerplate.injection;

import com.wassim.mvvmboilerplate.injection.component.ViewModelComponent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A scoping annotation to permit dependencies conform to the life of the
 * {@link ViewModelComponent}
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewModel {
}
