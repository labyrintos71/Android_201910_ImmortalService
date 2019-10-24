package labin.immortalservice.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import labin.immortalservice.R

/**
 * Created by Labyrintos on 2019-10-24
 */
class TempService : Service(){

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //notify 알림 만듦
        val builder = NotificationCompat.Builder(this, "default")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle(null)
        builder.setContentText(null)

        //오레오 이상일 경우 notify 채널 등록
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            manager.createNotificationChannel(NotificationChannel("default", "default channel", NotificationManager.IMPORTANCE_NONE))

        //메인서비스 등록
        startForeground(9, builder.build())
        startService(Intent(this, MainService::class.java))

        //자신을 중지시킴
        stopForeground(true)
        stopSelf()

        //서비스를 강제로 죽여도 재실행 X
        return START_NOT_STICKY

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}