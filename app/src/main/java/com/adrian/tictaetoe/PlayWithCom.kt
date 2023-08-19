package com.adrian.tictaetoe

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.adrian.tictaetoe.databinding.TictaetoeboardBinding
import com.adrian.tictaetoe.utils.GameLogic
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayWithCom: AppCompatActivity() {

    private lateinit var binding: TictaetoeboardBinding

    private lateinit var buttons: Array<Array<Button>>

    private var isRunning = false
    private var currentTurn = Turn.NOUGHT
    private var player : String = ""
    private var computer : String = "computer"

    private var crossesScore = 0
    private var noughtScores = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TictaetoeboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        start()
        getPlayerName()
    }


    fun start() {
        binding.run {

            buttons = arrayOf(
                arrayOf(btnTop1, btnTop2, btnTop3),
                arrayOf(btnM1, btnM2, btnM3),
                arrayOf(btnB1, btnB2, btnB3)
            )

            btnBack.setOnClickListener {
                intent = Intent(this@PlayWithCom, MainActivity::class.java)
                startActivity(intent)
            }

            for(i in 0 .. 2){
                for(j in 0 .. 2) {
                    buttons[i][j].setOnClickListener {
                        if(isRunning && currentTurn == Turn.NOUGHT) {
                            if(GameLogic.board[i][j] == '?') {
                                buttons[i][j].text = "O"
                                GameLogic.board[i][j] = 'o'
                                nextTurn()
                            }
                        }
                    }
                }
            }
            isRunning = true
        }
    }

    fun resetBoard() {
        GameLogic.resetBoard()
        for (row in buttons) {
            for(button in row) {
                button.text = ""
            }
        }

        currentTurn = Turn.CROSS
        isRunning = true
        nextTurn()
    }

    fun getPlayerName() {
        player = intent.getStringExtra("PLAYER_COM") ?: ""
        computer = intent.getStringExtra("computer") ?: "Computer"

        setTurnLabel()
    }

    fun nextTurn() {
        checkStatus()
        if (currentTurn == Turn.NOUGHT) {
            currentTurn = Turn.CROSS
            computerMove()
        } else {
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    fun computerMove() {
        setTurnLabel()
        lifecycleScope.launch {
            delay(1000)
            if (isRunning) {
                val (x, y) = GameLogic.getBestMove(GameLogic.board)
                GameLogic.board[x][y] = 'x'
                buttons[x][y].text = "X"
                nextTurn()
            }
        }
    }
    fun result(title: String){
        val message = "$player : $crossesScore \n\n$computer : $noughtScores"

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


    fun checkStatus() {
        val status = GameLogic.getStatus(GameLogic.board)
        if (status == -1) {
            crossesScore++
            result("$player Won!")
            isRunning = false
            return
        }else if (status == 1) {
            noughtScores++
            result("$computer Won!")
            isRunning = false
            return
        }else if (GameLogic.isGameFinished()) {
            result("Wow...It's a draw!")
            isRunning = false
        } else {
            setTurnLabel()
        }
    }

    fun setTurnLabel() {
        var turnText = ""
        if(currentTurn == Turn.NOUGHT) {
            turnText = "$player's turn"
        } else if(currentTurn == Turn.CROSS){
            turnText = "$computer's turn"
        }

        binding.tvText.text = turnText
    }

    enum class Turn {
        NOUGHT,
        CROSS
    }

}