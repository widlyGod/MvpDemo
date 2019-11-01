package com.example.safe.component

import com.example.safe.ui.activity.MainActivity
import com.example.safe.moudule.MainModule
import com.example.safe.utils.ActivityScope
import dagger.Component

@ActivityScope
@Component(modules = [(MainModule::class)])
interface MainComponent {

    fun inject(activity: MainActivity)
}
