package com.lab.ffoverlay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 200, 60, 60)
        }

        val info = TextView(this).apply {
            text = "1. Cấp quyền overlay\n" +
                   "2. Bật overlay\n" +
                   "3. Tạo file pos.txt mẫu\n" +
                   "Đường dẫn pos.txt:\n" +
                   "Android/data/com.lab.ffoverlay/files/pos.txt\n" +
                   "Nội dung: X,Y (ví dụ 500,800)"
            textSize = 16f
        }

        val btnPerm = Button(this).apply {
            text = "1. Cấp quyền Overlay"
            setOnClickListener {
                if (!Settings.canDrawOverlays(this@MainActivity)) {
                    startActivity(Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")))
                }
            }
        }

        val btnStart = Button(this).apply {
            text = "2. Bật Overlay"
            setOnClickListener {
                startForegroundService(
                    Intent(this@MainActivity, OverlayService::class.java))
            }
        }

        val btnStop = Button(this).apply {
            text = "3. Tắt Overlay"
            setOnClickListener {
                stopService(Intent(this@MainActivity, OverlayService::class.java))
            }
        }

        val btnMkfile = Button(this).apply {
            text = "Tạo file pos.txt mẫu"
            setOnClickListener {
                val f = File(getExternalFilesDir(null), "pos.txt")
                f.writeText("500,800")
            }
        }

        layout.addView(info)
        layout.addView(btnPerm)
        layout.addView(btnStart)
        layout.addView(btnStop)
        layout.addView(btnMkfile)
        setContentView(layout)
    }
}
