package com.kotlin.tennisapplication.points

import android.util.Log
import com.kotlin.tennisapplication.player.Player

class PointsProcessor {
    private val TAG: String = "PointsProcessor"

    fun getScore(currentPlayer: Player, otherPlayer: Player) {
        hasAdvantage(currentPlayer, otherPlayer)?.apply {
            // TODO notify advantage state
            Log.d(TAG, "Advantage for player " + this.name)
        }
        // if no player has advantage then check if Deuce has happened
        if (isDeuce(currentPlayer, otherPlayer)) {
            Log.d(TAG, "Deuce happened")
        } else {
            // if there is no Deuce then we may have a winner, check for winner
            getWinner(currentPlayer, otherPlayer)?.apply {
                //TODO notify winner
                Log.d(TAG, "Winner " + this.name)
            }
        }
    }

    /**
     * This method checks if score for both players are equal and has reached score 40 or
     * score point 4
     */
    private fun isDeuce(currentPlayer: Player, otherPlayer: Player): Boolean {
        return (currentPlayer.totalPoints >= 3 || otherPlayer.totalPoints >= 3)
                && otherPlayer.totalPoints == currentPlayer.totalPoints;
    }

    /**
     * This method checks score for both players and if any player has reached Advantage (i.e.
     * score point 4) and has 2 points more than other player then he/she is clear winner
     */
    private fun getWinner(currentPlayer: Player, otherPlayer: Player): Player? {
        if (currentPlayer.totalPoints >= 4 && currentPlayer.totalPoints >= otherPlayer.totalPoints + 2)
            return currentPlayer
        if (otherPlayer.totalPoints >= 4 && otherPlayer.totalPoints >= currentPlayer.totalPoints + 2)
            return currentPlayer
        return null
    }

    /**
     * check if any player has 1 point advantage than other player
     */
    private fun hasAdvantage(currentPlayer: Player, otherPlayer: Player): Player? {
        if (currentPlayer.totalPoints >= 4 && currentPlayer.totalPoints == otherPlayer.totalPoints + 1) return currentPlayer
        return if (otherPlayer.totalPoints >= 4 && otherPlayer.totalPoints == currentPlayer.totalPoints + 1) otherPlayer else null
    }

    private fun translateScore(score: Int): String {
        when (score) {
            3 -> return "Forty"
            2 -> return "Thirty"
            1 -> return "Fifteen"
            0 -> return "Love"
        }
        throw IllegalArgumentException("Illegal score: $score")
    }
}