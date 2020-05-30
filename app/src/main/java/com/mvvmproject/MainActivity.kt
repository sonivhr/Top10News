package com.mvvmproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mvvmproject.fragmentutil.addFragment
import com.mvvmproject.login.LoginFragment

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        addFragment(fragmentClass = LoginFragment::class.java, tag = LoginFragment::class.java.simpleName)
    }
}