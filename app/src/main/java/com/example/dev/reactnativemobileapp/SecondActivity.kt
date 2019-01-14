package com.example.dev.reactnativemobileapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_second.*
import okhttp3.*
import java.io.IOException


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        gitHubButton.setOnClickListener {
            val intent = Intent(Intent.getIntentOld("https://github.com/olga-zyk/ReactNativeMobileApp"))
            startActivity(intent)
        }

        getJsonData()


    }

    private fun getJsonData() {
        val url = "https://www.metaweather.com/api/location/890869/"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        return client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body()?.string()

                val gson = GsonBuilder().create()
                val jsonArray = gson.fromJson(jsonString, JsonObject::class.java)

                val temperature = jsonArray.consolidated_weather[0].max_temp.toInt()
            }

            override fun onFailure(call: Call, e: IOException) {
                println("not available")
            }
        })
    }

    class JsonObject(val consolidated_weather: List<WeatherInfo>)
    class WeatherInfo(val max_temp: Double)
}
