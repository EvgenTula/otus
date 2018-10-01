package ru.otus.task4annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
@interface Before { }

@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
@interface After { }

@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
@interface Test { }
