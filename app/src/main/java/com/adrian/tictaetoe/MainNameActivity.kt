package com.adrian.tictaetoe

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adrian.tictaetoe.databinding.PlayernameBinding

class MainNameActivity : AppCompatActivity() {

    lateinit var binding : PlayernameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PlayernameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
           finish()
        }

        binding.btnSubmit.setOnClickListener {
            val playerOne = binding.Person1.text.toString()
            val playerTwo = binding.Person2.text.toString()

            if(playerOne == "" || playerTwo == "" ){
                Toast.makeText(this, "Please Enter Your Name!", Toast.LENGTH_SHORT).show()
            } else if (playerOne == ""){
                Toast.makeText(this, "First Player, Please Enter Your Name!", Toast.LENGTH_SHORT).show()
            } else if(playerTwo == ""){
                Toast.makeText(this, "Second Player, Please Enter Your Name!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainBoardActivity::class.java)
                intent.putExtra("PLAYER_ONE", playerOne)
                intent.putExtra("PLAYER_TWO", playerTwo)
                startActivity(intent)
                finish()
            }
        }


    }
}