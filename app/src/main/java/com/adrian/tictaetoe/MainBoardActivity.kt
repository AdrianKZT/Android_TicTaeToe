package com.adrian.tictaetoe

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adrian.tictaetoe.databinding.TictaetoeboardBinding
import kotlin.system.exitProcess

class MainBoardActivity : AppCompatActivity() {
    enum class Turn {
        NOUGHT,
        CROSS
    }

    private var playerOne : String = ""
    private var playerTwo : String = ""

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var crossesScore = 0
    private var noughtsScore = 0

    private var boardList = mutableListOf<Button>()

    private lateinit var binding : TictaetoeboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TictaetoeboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
        getPlayerName()

        binding.btnBack.setOnClickListener {
           intent = Intent(this@MainBoardActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getPlayerName(){
        playerOne = intent.getStringExtra("PLAYER_ONE") ?: ""
        playerTwo = intent.getStringExtra("PLAYER_TWO") ?: ""

        setTurnLabel()
    }

    private fun initBoard() {
        boardList.add(binding.btnTop1)
        boardList.add(binding.btnTop2)
        boardList.add(binding.btnTop3)
        boardList.add(binding.btnM1)
        boardList.add(binding.btnM2)
        boardList.add(binding.btnM3)
        boardList.add(binding.btnB1)
        boardList.add(binding.btnB2)
        boardList.add(binding.btnB3)
    }

    fun boardTapped(view: View) {
        val button = view as Button
        var player = ""

        if(currentTurn == Turn.CROSS){
            player = "X"
            currentTurn = Turn.NOUGHT
        } else if (currentTurn == Turn.NOUGHT) {
            player = "O"
            currentTurn = Turn.CROSS
        }

        button.text = player
        button.isEnabled = false

        if(checkForVictory(player)) {
            if(player == "X") {
                crossesScore++
                result("$playerOne Won!")
            } else {
                noughtsScore++
                result("$playerTwo Won!")
            }
        } else if (isBoardFull()) {
            result("Wow...It's a draw!")
        } else {
            setTurnLabel()
        }

    }

    private fun checkForVictory(s: String) : Boolean {
        //Horizontal Victory
        if(match(binding.btnTop1,s) && match(binding.btnTop2,s) && match(binding.btnTop3,s))
            return true
        if(match(binding.btnM1,s) && match(binding.btnM2,s) && match(binding.btnM3,s))
            return true
        if(match(binding.btnB1,s) && match(binding.btnB2,s) && match(binding.btnB3,s))
            return true

        //Vertical Victory
        if(match(binding.btnTop1,s) && match(binding.btnM1,s) && match(binding.btnB1,s))
            return true
        if(match(binding.btnTop2,s) && match(binding.btnM2,s) && match(binding.btnB2,s))
            return true
        if(match(binding.btnTop3,s) && match(binding.btnM3,s) && match(binding.btnB3,s))
            return true

        //Diagonal Victory
        if(match(binding.btnTop1,s) && match(binding.btnM2,s) && match(binding.btnB3,s))
            return true
        if(match(binding.btnTop3,s) && match(binding.btnM2,s) && match(binding.btnB1,s))
            return true

        return false
    }

    private fun match(button: Button, symbol: String) : Boolean = button.text == symbol

    private fun result(title: String) {
        val message = "$playerOne : $crossesScore \n\n$playerTwo : $noughtsScore"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton("Close") {
                    _, _ ->
            }
            .setPositiveButton("Play Again") {
                _, _ -> resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
        for(button in boardList){
            button.text = ""
            button.isEnabled = true
        }

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun isBoardFull(): Boolean {
        for(button in boardList) {
            if(button.text == "")
                return false
        }
        return true
    }

//    private fun addToBoard(button: Button){
//        if(button.text != "")
//            return
//
//        if(currentTurn == Turn.NOUGHT){
//            button.text = NOUGHT
//            currentTurn = Turn.CROSS
//        } else if(currentTurn == Turn.CROSS){
//            button.text = CROSS
//            currentTurn = Turn.NOUGHT
//        }
//        setTurnLabel()
//    }

    private fun setTurnLabel() {
        var turnText = ""
        if(currentTurn == Turn.CROSS)
            turnText = "$playerOne's turn"
        else if(currentTurn == Turn.NOUGHT)
            turnText = "$playerTwo's turn"

        binding.tvText.text = turnText
    }

    companion object
    {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}