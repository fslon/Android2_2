package ru.geekbrains.android2_2.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geekbrains.android2_2.R
import ru.geekbrains.android2_2.model.MainBroadcastReceiver
import ru.geekbrains.android2_2.view.main.MainFragment

class MainActivity : AppCompatActivity() { // 41415

    private val receiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }


}