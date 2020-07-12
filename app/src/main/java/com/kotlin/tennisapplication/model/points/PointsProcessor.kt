package com.kotlin.tennisapplication.model.points

import com.kotlin.tennisapplication.model.entity.PlayerEntity
import com.kotlin.tennisapplication.viewmodel.PlayerViewModel

class PointsProcessor {

    fun processScore(
        viewModel: PlayerViewModel,
        player1: PlayerEntity,
        player2: PlayerEntity
    ) {
        hasAdvantage(player1, player2)?.apply {
            viewModel.hasAdvantage.postValue(this)
        }
        // if no player has advantage then check if Deuce has happened
        if (isDeuce(player1, player2)) {
            //reset player points back to Deuce
            player1.resetToDeuceScore()
            player2.resetToDeuceScore()
            viewModel.isDeuce.postValue(true)
        } else {
            // if there is no Deuce then we may have a winner, check for winner
            getWinner(player1, player2)?.apply {
                viewModel.winner.postValue(this)
            }
        }
    }

    /**
     * This method checks if score for both players are equal and has reached score 40
     * (score point 3) or if both players have gained advantage(score point 4). If these conditions
     * are match then treat is as a Deuce and return true else will return false
     */
    private fun isDeuce(currentPlayer: PlayerEntity, otherPlayer: PlayerEntity): Boolean {
        return (currentPlayer.totalPoints >= 3 || otherPlayer.totalPoints >= 3)
                && otherPlayer.totalPoints == currentPlayer.totalPoints
    }

    /**
     * This method checks score for both players and if any player has reached Advantage (i.e.
     * score point 4) and has 2 points more than other player then he/she is clear winner. If there
     * is a winner then this method returns that winner else returns null
     */
    private fun getWinner(player1: PlayerEntity, player2: PlayerEntity): PlayerEntity? {
        if (player1.totalPoints >= 4 && player1.totalPoints >= player2.totalPoints + 2)
            return player1
        if (player2.totalPoints >= 4 && player2.totalPoints >= player1.totalPoints + 2)
            return player2
        return null
    }

    /**
     * check and return if any player has 1 point advantage than other player else return null
     */
    private fun hasAdvantage(currentPlayer: PlayerEntity, otherPlayer: PlayerEntity): PlayerEntity? {
        if (currentPlayer.totalPoints >= 4 && currentPlayer.totalPoints == otherPlayer.totalPoints + 1) return currentPlayer
        return if (otherPlayer.totalPoints >= 4 && otherPlayer.totalPoints == currentPlayer.totalPoints + 1) otherPlayer else null
    }
}