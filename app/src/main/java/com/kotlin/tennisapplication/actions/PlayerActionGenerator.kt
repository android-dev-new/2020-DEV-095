package com.kotlin.tennisapplication.actions

import android.util.Log

/**
 * In real world Tennis game when a player receives a ball then he/she can either hit the ball
 * or miss tha ball. So there are at most 2 possible outcomes. This class generates a random number
 * and if that number is even then consider it as current player has hit the ball. If the generated
 * number is odd then consider it as player has miss the ball.
 */
class PlayerActionGenerator {
    private val TAG: String = "PlayerActionGenerator"

    fun generatePlayerEvent(): Int {
        val action: Int = (Math.random() *100).toInt() // generate a random action
        Log.d(TAG, "Generated action " + action)
        return action
    }
}