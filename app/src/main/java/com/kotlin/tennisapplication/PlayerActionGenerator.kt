package com.kotlin.tennisapplication

/**
 * In real world Tennis game when a player receives a ball then he/she can either hit the ball
 * or miss tha ball. So there are at most 2 possible outcomes. This class generates a random number
 * and if that number is even then consider it as current player has hit the ball. If the generated
 * number is odd then consider it as player has miss the ball.
 */
class PlayerActionGenerator {
    companion object {
        private val ACTION_BALL_HIT: Int = 0;

        fun generatePlayerEvent() {
            val action: Double = Math.random() // generate a random action
            if (isActionABallHit(action)) { // check if action is a ball hit or loose
            }else{

            }
        }

        /**
         * This method checks if given number is even or odd. If it is even then it compares it to
         * #ACTION_BALL_HIT
         */
        fun isActionABallHit(action: Double): Boolean {
            return (action % 2).compareTo(ACTION_BALL_HIT) == 0 // use Double.compareTo() to compare 2 Double values
        }
    }
}