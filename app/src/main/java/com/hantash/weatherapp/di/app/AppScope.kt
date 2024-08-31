package com.hantash.weatherapp.di.app

import dagger.hilt.migration.AliasOf
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@AliasOf(Singleton::class) //Renaming the Singleton scope into AppScope
annotation class AppScope {

}
