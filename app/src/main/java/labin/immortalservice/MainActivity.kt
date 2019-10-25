package labin.immortalservice

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import labin.immortalservice.Service.MainService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val manager = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        var isWhiteList = false

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            isWhiteList = manager.isIgnoringBatteryOptimizations(applicationContext.packageName)

        if(!isWhiteList){
            val intent = Intent()
            intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            intent.data= Uri.parse("package:"+applicationContext.packageName)
            startActivity(intent)
        }
        startService(Intent(this,MainService::class.java))
    }
}
