package com.adrian.tictaetoe

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adrian.tictaetoe.databinding.PlayercomBinding

class MainComPlayer : AppCompatActivity() {
    lateinit var binding : PlayercomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PlayercomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackCom.setOnClickListener {
            finish()
        }

        binding.btnSubmitCom.setOnClickListener {
            val player = binding.PlayerCom1.text.toString()
            val computer = binding.etComputer.text.toString()
            if(player == "" || computer == "Computer"){
                Toast.makeText(this, "Please Enter Your Name!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, PlayWithCom::class.java)
                intent.putExtra("PLAYER_COM", player)
                startActivity(intent)
                finish()
            }
        }

        binding.etComputer.setOnClickListener {
            Toast.makeText(this, "Oops! You cannot change this!", Toast.LENGTH_SHORT).show()
        }

    }
}