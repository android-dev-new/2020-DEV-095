package com.kotlin.tennisapplication.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.tennisapplication.Constant
import com.kotlin.tennisapplication.R
import com.kotlin.tennisapplication.actions.PlayerActionGenerator
import com.kotlin.tennisapplication.actions.PlayerActionProcessor
import com.kotlin.tennisapplication.player.Player
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import kotlin.concurrent.withLock

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var keepRunning: Boolean = true
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()
    private var flag: Int = 0
    private var actionCountPlayer1: Int = 1
    private var actionCountPlayer2: Int = 1
    private lateinit var player1: Player
    private lateinit var player2: Player

    @set:Inject
    internal lateinit var playerActionGenerator: PlayerActionGenerator

    @set:Inject
    internal lateinit var playerActionProcessor: PlayerActionProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startGame(view: View) {
        startThreads()
    }

    private fun startThreads() {
        player1 = Player(0, Runnable {
            while (keepRunning) {
                lock.withLock {
                    if (flag != player1.playerNumber) {
                        //wait of this player's turn
                        condition.await()
                    }
                    player_1.post {
                        player_1.text = "Player 1 action " + actionCountPlayer1++
                        val event: Int = playerActionGenerator.generatePlayerEvent()
                        playerActionProcessor.processActionEvent(
                            action = event,
                            currentPlayer = player1,
                            otherPlayer = player2
                        )
                    }
                    flag = 1
                    Thread.sleep(Constant.PLAYER_ACTION_TIME_SIMULATION)
                    condition.signalAll()
                }
            }
        }, "Player1")

        player2 = Player(1, Runnable {
            while (keepRunning) {
                lock.withLock {
                    if (flag != player2.playerNumber) {
                        //wait of this player's turn
                        condition.await()
                    }
                    player_2.post {
                        player_2.text = "Player 2 action " + actionCountPlayer2++
                        val event: Int = playerActionGenerator.generatePlayerEvent()
                        playerActionProcessor.processActionEvent(
                            action = event,
                            currentPlayer = player2,
                            otherPlayer = player1
                        )
                    }
                    flag = 0
                    Thread.sleep(Constant.PLAYER_ACTION_TIME_SIMULATION)
                    condition.signalAll()
                }
            }
        }, "Player2")

        // start game for both players
        player1.start()
        player2.start()
    }

    override fun onDestroy() {
        keepRunning = false
        super.onDestroy()
    }
}