package com.example.testone

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val NOTIFICATION_REQUEST_CODE = 1234
    }

    private val db = FirebaseFirestore.getInstance()
    lateinit var name: EditText
    lateinit var mail: EditText
    lateinit var phone: EditText
    lateinit var address: EditText
    lateinit var dataFromDB: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        name = findViewById(R.id.name)
        mail = findViewById(R.id.mail)
        address = findViewById(R.id.address)
        phone = findViewById(R.id.phone)
        dataFromDB = findViewById(R.id.consulta)
        val save: Button = findViewById(R.id.btn_guardar)
        val get: Button = findViewById(R.id.btn_get)

        save.setOnClickListener {
            val email: String = mail.text.toString()
            val users = db.collection("users")
            val data = hashMapOf(
                "name" to name.text.toString(),
                "phone" to phone.text.toString(),
                "address" to address.text.toString()
            )
            users.document(email).set(
                data
            )
            Toast.makeText(baseContext, "Se realizó la actualización", Toast.LENGTH_SHORT).show()
        }

        get.setOnClickListener {
            val email: String = mail.text.toString()
            val docRef = db.collection("users").document(email)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("GET", "DocumentSnapshot data: ${document.data}")
                        dataFromDB.text = document.data.toString()
                    } else {
                        Log.d("GET", "No such document")
                        dataFromDB.text = "No se encontró el usuario"
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("GET", "get failed with ", exception)
                }
        }
        notificacion()
    }

    private fun notificacion() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "Token de esta instalación" + token
            Log.d(TAG, msg)
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }


}