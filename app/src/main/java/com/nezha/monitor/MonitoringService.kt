package com.nezha.monitor

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MonitoringService : Service() {
    private val scope = CoroutineScope(Dispatchers.IO)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            // 这里可以获取配置：it.getStringExtra("SERVER_URL")
        }
        startForeground(1, createNotification())
        scope.launch {
            while (true) {
                // 模拟每60秒上报一次数据
                // 实际应在此调用 SystemInfoUtils 采集数据，并通过 ApiClient 上报
                delay(60000L)
            }
        }
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? = null
    private fun createNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "nezha_channel", "监控服务",
                NotificationManager.IMPORTANCE_LOW
            ).apply { description = "哪吒监控运行中" }
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
        }
        return NotificationCompat.Builder(this, "nezha_channel")
            .setContentTitle("哪吒监控")
            .setContentText("服务运行中...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }
}
