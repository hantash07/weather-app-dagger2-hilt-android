package com.hantash.weatherapp.di.activiry

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.hantash.weatherapp.view.ui.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class, ViewModelModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance fun activity(activity: AppCompatActivity): Builder
        @BindsInstance fun fragmentManager(fragmentManager: FragmentManager): Builder
        fun activityModule(activityModule: ActivityModule): Builder //This can be removed as Dagger knows how to find it.
        fun build(): ActivityComponent
    }
}