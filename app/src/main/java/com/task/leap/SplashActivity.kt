package com.task.leap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long=3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        runtimePermission()

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)

    }

    private fun runtimePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    100)
            }
        } else{
        }
    }
}
