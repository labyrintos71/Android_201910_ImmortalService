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
import android.widget.Toast
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {
    var serviceIntent : Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //절전모드 미사용 확인
        val manager = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        var isWhiteList = false

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            isWhiteList = manager.isIgnoringBatteryOptimizations(applicationContext.packageName)

        if(!isWhiteList){
            val intent = Intent().apply {
                action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                data =  Uri.parse("package:"+applicationContext.packageName)
            }
            startActivity(intent)
        }

        if (MainService.serviceintent == null) {
            serviceIntent = Intent(this, MainService::class.java)
            startService(serviceIntent)
        } else {
            serviceIntent = MainService.serviceintent//getInstance().getApplication();
            Toast.makeText(applicationContext, "already", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceIntent.let {
            stopService(serviceIntent)
            serviceIntent =null
        }
    }
}
