package com.kotlin.tennisapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class MainActivity : AppCompatActivity() {
    private var keepRunning: Boolean = true
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()
    private var flag: Int = 0
    private var actionCountPlayer1:Int = 0
    private var actionCountPlayer2:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startGame(view: View) {
        startThreads()
    }

    private fun startThreads() {
        val player1 = Thread(Runnable {
            while (keepRunning) {
                lock.withLock {
                    if (flag != 0) {
                        condition.await()
                    }
                    player_1.post {
                        player_1.text= "Player 1 action " + actionCountPlayer1++
                    };
                    flag = 1;
                    Thread.sleep(1000)
                    condition.signalAll()
                }
            }
        }, "Player1")

        val player2 = Thread(Runnable {
            while (keepRunning) {
                lock.withLock {
                    if (flag != 1) {
                        condition.await()
                    }
                    player_2.post {
                        player_2.text= "Player 2 action " + actionCountPlayer2++
                    };
                    flag = 0;
                    Thread.sleep(1000)
                    condition.signalAll()
                }
            }
        }, "Player2")
        player1.start()
        player2.start()
    }

    override fun onDestroy() {
        keepRunning = false
        super.onDestroy()
    }

    private fun isMiss(random: Double): Boolean {
        return (random % 2).compareTo(0) == 0
    }
}