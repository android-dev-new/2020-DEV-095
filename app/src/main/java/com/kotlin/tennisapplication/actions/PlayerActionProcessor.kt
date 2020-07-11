package com.kotlin.tennisapplication.actions

import com.kotlin.tennisapplication.Constant
import com.kotlin.tennisapplication.player.Player
import com.kotlin.tennisapplication.points.PointsProcessor
import javax.inject.Inject

class PlayerActionProcessor @Inject constructor(private val pointsProcessor: PointsProcessor){
    companion object {
        private const val ACTION_BALL_HIT: Int = 0
    }

    fun processActionEvent(@Constant.Companion.PlayerAction action: Int, currentPlayer: Player, otherPlayer: Player) {
        if (currentPlayer == otherPlayer) {
            throw IllegalArgumentException("Players are same")
        }

        if (isActionABallHit(action)) { // check if action is a ball hit or loose
            currentPlayer.hitCount++
        } else {
            currentPlayer.missCount++
            // if current player misses then it is plus point for other player
            otherPlayer.totalPoints++
            pointsProcessor.getScore(currentPlayer, otherPlayer);
        }
    }

    /**
     * This method checks if given number is even or odd. If it is even then it compares it to
     * #ACTION_BALL_HIT
     */
    private fun isActionABallHit(action: Int): Boolean {
        return (action % 2).compareTo(ACTION_BALL_HIT) == 0 // use Double.compareTo() to compare 2 Double values
    }
}