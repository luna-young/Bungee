package com.dev.luna.bungee

import android.app.Application

class App : Application() {
    //안드로이드에서 앱이 실행되면 Application이라는 클래스의 전역 객체가 생성된다.
    //이 클래스는 앱의 전역적인 상태를 관리하는 클래스로, 이 클래스를 상속받아 AndroidManifest에 지정해주면
    //우리가 필요한 값들도 이 클래스를 통해 전역적으로 공유할 수 있다.

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    companion object {
        lateinit var instance : App
    }
}