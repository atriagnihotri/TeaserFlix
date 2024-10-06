package com.example.teaserflix.Activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.teaserflix.Models.MovieResponse
import com.example.teaserflix.Models.Token
import com.example.teaserflix.Models.User
import com.example.teaserflix.R
import com.example.teaserflix.Retrofit.ApiInterface
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Register : AppCompatActivity() {
    lateinit var username:EditText
    lateinit var password:EditText
    lateinit var register:Button
    lateinit var already:TextView
    var dialog:Dialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        username=findViewById(R.id.username)
        password=findViewById(R.id.password)
        register=findViewById(R.id.register)
        already=findViewById(R.id.already)
        dialog=Dialog(this).apply {
            val view = LayoutInflater.from(applicationContext).inflate(R.layout.progressbg, null)
            val progressText = view.findViewById<TextView>(R.id.progress_text)
            progressText.text = "Creating Account..."
            setContentView(view)
            setCancelable(false)
        }

        already.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finishAffinity()

        }


        register.setOnClickListener {
            dialog?.show()
            val user:String=username.text.toString()
            val pass:String=password.text.toString()
            val data=User(username=user,password=pass)
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.43.249:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)

            val retrofitData = retrofit.registerUser(data)
            retrofitData.enqueue(object : Callback<Token?> {
                override fun onResponse(
                    call: Call<Token?>,
                    response: Response<Token?>
                ) {
                    val dataResponse = response.body()

                    if (dataResponse != null) {
                       val name:String= dataResponse.user
                        dialog?.hide()

                        Toasty.success(applicationContext, "Creating Your Account", Toast.LENGTH_SHORT, true).show();                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finishAffinity()

                        val pref=getSharedPreferences("mypref", MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putBoolean("access",true)
                        editor.putString("User",name)
                        editor.apply()
                    }

                }

                override fun onFailure(call: Call<Token?>, t: Throwable) {
                    dialog?.hide()
                    Toasty.error(applicationContext, "Please Enter Valid Details", Toast.LENGTH_SHORT, true).show();
                }
            })

        }



    }

}