package com.example.service_mymusicapp

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat

class MyService : Service() {
    // chạy mp3 local thông qua mediaPlayer
    private var mediaPlayer : MediaPlayer? = null
    override fun onCreate() {
        //The service is being created
        super.onCreate()
        Log.e("ServiceDemo", "myService onCreate")
    }

    // nhận intent và xử lý data trong intent
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        The service is starting, due to a call to startService()
        val bundle : Bundle? = intent?.extras

        if (bundle != null){
            val song : Song = bundle.get("object_song") as Song
            startMusic(song)
            sendNotification(song)
        }
        ///////////////////////
//        var strDataIntent : String? = intent?.getStringExtra("key_data_intent")
//        sendNotification1(strDataIntent)

        Log.e("ServiceDemo", "myService onStartCommand")
        return START_NOT_STICKY
    }

    private fun sendNotification1(strDataIntent: String?) {

        // bây giờ muốn click vào putNotification thì mở lại main actvity
        var intent : Intent = Intent(this, MainActivity::class.java)// intent này xác định nơi muốn nhảy vào
        var pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)// PendingIntent được sử dụng để đóng gói một Intent và cung cấp quyền truy cập không trực tiếp đến nó.
        // khi cần thực hiện một hành động (ví dụ: mở một Activity, gửi một Broadcast, kích hoạt một dịch vụ) tại một thời điểm sau, thường là từ một thành phần khác hoặc từ một tiến trình khác.
        var channelID = MyApplication.getInstance().CHANNEL_ID
        var notification : Notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Title notification service example")
            .setContentText(strDataIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        // gửi notification cho foreground Service
        startForeground(1, notification) // nếu comment dòng này thì service vẫn chạy ngầm và sau 1 khoảng thời gian thì service tự hủy chứ ko hoạt động vô thời hạn.
        // // nếu ko thì service chạy vô thời hạn
//        stopSelf() // tự dừng
    }

    private  fun startMusic(song: Song) {
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(applicationContext, song.resource)
        }
        mediaPlayer?.start() // start mp3
    }

    private fun sendNotification(song: Song) {
        // bây giờ muốn click vào putNotification thì mở lại main actvity
        val intent : Intent = Intent(this, MainActivity::class.java)// intent này xác định nơi muốn nhảy vào
        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)// được sử dụng để tạo một PendingIntent mà khi được kích hoạt, nó sẽ mở Activity đã được định nghĩa bởi intent.

        val bitmap : Bitmap = BitmapFactory.decodeResource(resources, song.image)

//         xử lý logic gửi dữ liệu lên notification
        val remoteViews : RemoteViews = RemoteViews(packageName, R.layout.layout_custom_notification)
        remoteViews.setTextViewText(R.id.tv_title_song, song.title)
        remoteViews.setTextViewText(R.id.tv_singer_song, song.singer)
        remoteViews.setImageViewBitmap(R.id.img_song, bitmap)
        remoteViews.setImageViewResource(R.id.img_pause_or_play, R.drawable.pause)//


        var channelID = MyApplication.getInstance().CHANNEL_ID
        var notification : Notification = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.music_note)
            .setContentIntent(pendingIntent)// Khi thông báo được nhấn, PendingIntent này sẽ được kích hoạt, thường là để mở một Activity, Fragment, hoặc thực hiện một hành động cụ thể trong ứng dụng của bạn.
            .setCustomContentView(remoteViews)
            .setSound(null) // để khi chạy nhạc thì không phát ra tiếng của notification, chỉ hoạt động với android từ 8.0 trở xuống, nếu từ 8.0 trở lên phải setting cả qua channel ở MyApplication
            .build()

        // gửi notification cho foreground Service
        startForeground(1, notification) // nếu comment dòng này thì service vẫn chạy ngầm và sau 1 khoảng thời gian thì service tự hủy chứ ko hoạt động vô thời hạn.
        // // nếu ko thì service chạy vô thời hạn
//        stopSelf() // tự dừng
    }

    override fun onDestroy() {
        // The service is no longer used and is being destroyed
        super.onDestroy()
        Log.e("ServiceDemo", "myService onDestroy")
        if (mediaPlayer != null){
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}