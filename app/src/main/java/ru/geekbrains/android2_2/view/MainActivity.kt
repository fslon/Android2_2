package ru.geekbrains.android2_2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.android2_2.R
import ru.geekbrains.android2_2.view.main.MainFragment

class MainActivity : AppCompatActivity() { // 41415

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }





}