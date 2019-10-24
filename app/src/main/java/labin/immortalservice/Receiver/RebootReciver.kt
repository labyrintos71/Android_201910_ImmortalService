package labin.immortalservice.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import labin.immortalservice.Service.MainService
import labin.immortalservice.Service.TempService

/**
 * Created by Labyrintos on 2019-10-24
 */
class RebootReciver : BroadcastReceiver(){
    //기기가 부팅 되었을 때 서비스 실행
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context?.startForegroundService(Intent(context,TempService::class.java))
        else
            context?.startService(Intent(context,MainService::class.java))
    }
}