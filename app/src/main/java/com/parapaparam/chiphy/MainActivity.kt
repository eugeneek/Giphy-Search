package com.parapaparam.chiphy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.parapaparam.chiphy.ui.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.flContainer, SearchFragment())
                    .commit()
        }
    }
}
