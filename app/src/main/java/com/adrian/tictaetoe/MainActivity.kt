package com.adrian.tictaetoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrian.tictaetoe.databinding.HomepageBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    lateinit var binding : HomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlayWithFrd.setOnClickListener {
            val intent = Intent(this, MainNameActivity::class.java)
            startActivity(intent)
        }

        binding.btnPlayWithCom.setOnClickListener {
            val intent = Intent(this, MainComPlayer::class.java)
            startActivity(intent)
        }

        binding.btnQuit.setOnClickListener {
            finish()

        }
    }
}