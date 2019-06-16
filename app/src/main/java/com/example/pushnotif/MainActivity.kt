package com.example.pushnotif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("AAA", "getInstanceId Failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result?.token

                val msg = "INI PESAN" + token
                Log.d("AAA", msg)
                Toast.makeText(baseContext,msg, Toast.LENGTH_SHORT).show()
            })
    }
}
