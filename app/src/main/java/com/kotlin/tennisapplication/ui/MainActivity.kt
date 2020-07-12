package com.kotlin.tennisapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlin.tennisapplication.Constant
import com.kotlin.tennisapplication.R
import com.kotlin.tennisapplication.databinding.ActivityMainBinding
import com.kotlin.tennisapplication.viewmodel.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG: String = "MainActivity"
    }

    private var isGameStarted: Boolean = false
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewBinding.viewmodel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        viewBinding.lifecycleOwner = this

        winner_name.text = ""

        viewBinding.viewmodel?.winner?.observe(this, Observer {
            toggleGame()
            winner_name.text = String.format(getString(R.string.player_wins), it.name)
        })
        viewBinding.viewmodel?.isDeuce?.observe(this, Observer {
            if (it) {
                Toast.makeText(winner_name.context, getString(R.string.deuce), Toast.LENGTH_SHORT)
                    .show()
            }
        })
        viewBinding.viewmodel?.hasAdvantage?.observe(this, Observer {
            Toast.makeText(
                winner_name.context, String.format(getString(R.string.has_advantage), it.name)
                , Toast.LENGTH_SHORT
            ).show()
        })
        viewBinding.viewmodel?.playerAction?.observe(this, Observer {
            Log.d(TAG, "player action " + it.player.name + " totalPoints " + it.player.totalPoints)

            if (it.action == Constant.ACTION_GAINED_POINT) {
                if (it.player == viewBinding.viewmodel?.player1) {
                    player_1.text = "Player 1 score " + translateScore(it.player.totalPoints)
                } else if (it.player == viewBinding.viewmodel?.player2) {
                    player_2.text = "Player 2 score " + translateScore(it.player.totalPoints)
                }
            }
        })
    }

    fun startGame(view: View) {
        toggleGame()
    }

    private fun toggleGame() {
        if (isGameStarted) {
            isGameStarted = false
            viewBinding.viewmodel?.stopGame()
            toggle_game.text = getString(R.string.start_new_game)
        } else {
            isGameStarted = true
            winner_name.text = ""
            viewBinding.viewmodel?.startGame()
            toggle_game.text = getString(R.string.stop_game)
        }
    }

    private fun translateScore(score: Int): String {
        when (score) {
            6 -> return "Win"
            5 -> return "Game"
            4 -> return "Advantage"
            3 -> return "Forty"
            2 -> return "Thirty"
            1 -> return "Fifteen"
            0 -> return "Love"
        }
        throw IllegalArgumentException("Illegal score: $score")
    }
}