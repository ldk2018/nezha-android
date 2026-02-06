package com.nezha.monitor

import android.content.Intent
android.os.Build
android.os.Bundle
android.widget.Button
android.widget.EditText
android.widget.Toast
androidx.appcompat.app.AppCompatActivity
androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var etServer: EditText
    private lateinit var etAgentId: EditText
    private lateinit var etSecret: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etServer = findViewById(R.id.et_server)
        etAgentId = findViewById(R.id.et_agent_id)
        etSecret = findViewById(R.id.et_secret)
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            startService()
        }
        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            stopService(Intent(this, MonitoringService::class.java))
        }
    }
    private fun startService() {
        val server = etServer.text.toString()
        val agentId = etAgentId.text.toString()
        val secret = etSecret.text.toString()
        if (server.isEmpty() || agentId.isEmpty() || secret.isEmpty()) {
            Toast.makeText(this, "请填写完整配置", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, MonitoringService::class.java).apply {
            putExtra("SERVER_URL", server)
            putExtra("AGENT_ID", agentId)
            putExtra("SECRET_KEY", secret)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, intent)
        } else {
            startService(intent)
        }
        Toast.makeText(this, "监控服务已启动", Toast.LENGTH_SHORT).show()
    }
}
