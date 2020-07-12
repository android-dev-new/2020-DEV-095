package com.kotlin.tennisapplication.model.actions

import androidx.lifecycle.MutableLiveData
import com.kotlin.tennisapplication.Constant
import com.kotlin.tennisapplication.model.entity.PlayerActionEntity
import com.kotlin.tennisapplication.model.entity.PlayerEntity
import com.kotlin.tennisapplication.model.points.PointsProcessor
import com.kotlin.tennisapplication.viewmodel.PlayerViewModel
import javax.inject.Inject

class PlayerActionProcessor @Inject constructor(private val pointsProcessor: PointsProcessor) {
    fun processActionEvent(
        playerAction: MutableLiveData<PlayerActionEntity>,
        viewModel: PlayerViewModel,
        @Constant.Companion.PlayerActionEvent action: Int,
        currentPlayer: PlayerEntity,
        otherPlayer: PlayerEntity
    ) {
        if (currentPlayer == otherPlayer) {
            throw IllegalArgumentException("Players are same")
        }

        if (isActionABallHit(action)) { // check if action is a ball hit or loose
            currentPlayer.hitCount++
        } else {
            currentPlayer.missCount++
            // if current player misses then it is plus point for other player
            otherPlayer.totalPoints++
            playerAction.postValue(
                PlayerActionEntity(
                    otherPlayer,
                    Constant.ACTION_GAINED_POINT
                )
            )
            pointsProcessor.processScore(viewModel, currentPlayer, otherPlayer)
        }
    }

    /**
     * This method checks if given number is even or odd. If it is even then it compares it to
     * #ACTION_BALL_HIT
     */
    private fun isActionABallHit(action: Int): Boolean {
        return (action % 2).compareTo(Constant.ACTION_HIT) == 0 // use Double.compareTo() to compare 2 Double values
    }
}