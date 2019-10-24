package labin.immortalservice.Service

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import labin.immortalservice.Receiver.ServiceDestroyReceiver
import java.util.*

/**
 * Created by Labyrintos on 2019-10-24
 */
class MainService : Service(){
    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showToast("Start Service")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        showToast("Destroy Service")
        setAlarmTimer()
    }

    private fun setAlarmTimer(){
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        cal.add(Calendar.SECOND,1)

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, PendingIntent.getBroadcast(this, 0, Intent(this,ServiceDestroyReceiver::class.java),0))
    }

    fun showToast(msg : String){
        Toast.makeText(application,msg,Toast.LENGTH_SHORT).show()
    }
}