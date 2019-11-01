package labin.immortalservice.Service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlins.module.labyrintos.RetrofitForRXJava.GithubService
import kotlins.module.labyrintos.RetrofitForRXJava.RetrofitCreator
import labin.immortalservice.MainActivity
import labin.immortalservice.Receiver.ServiceDestroyReceiver
import java.util.*


/**
 * Created by Labyrintos on 2019-10-24
 */
class MainService : Service(){
    companion object{
        var serviceintent : Intent? = null
    }
    var mainThread: Thread? = null

    lateinit var compositeDisposable: CompositeDisposable

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()
        compositeDisposable = CompositeDisposable()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceintent = intent
        sendMessage("Start Service")
        Log.d("test","Start")


        mainThread = Thread(Runnable {
            var run = true
            while (run) {
                try {
                    Thread.sleep((1000 * 5 * 1).toLong()) // 1 minute
                    sendMessage("Running Service")
                } catch (e: InterruptedException) {
                    run = false
                    e.printStackTrace()
                }

            }
        })
        mainThread?.start()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceintent = null
        compositeDisposable.dispose()
        setAlarmTimer()
        Thread.currentThread().interrupt()
        mainThread.let { mainThread = null }
    }

    private fun setAlarmTimer(){
        val cal = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.SECOND,1)
        }

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, PendingIntent.getBroadcast(this, 0, Intent(this,ServiceDestroyReceiver::class.java),0))
    }

    fun showToast(msg : String){
        Toast.makeText(application,msg,Toast.LENGTH_SHORT).show()
    }

    fun sendNotification(msg : String){
        //notify 알림 만듦
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pending = PendingIntent.getActivity(this, 1234, intent, PendingIntent.FLAG_ONE_SHOT)

        val builder = NotificationCompat.Builder(this, "test")
           // .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(msg)
            .setContentText(msg)
            .setAutoCancel(true)
            .setContentIntent(pending)

        //오레오 이상일 경우 notify 채널 등록
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            manager.createNotificationChannel(NotificationChannel("test", "test channel", NotificationManager.IMPORTANCE_HIGH))

        manager.notify(1234,builder.build())
    }
    fun sendMessage(msg: String){
        compositeDisposable.add(RetrofitCreator.create(GithubService::class.java)
            .getRepoList(msg).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({},{
            }))
    }
}