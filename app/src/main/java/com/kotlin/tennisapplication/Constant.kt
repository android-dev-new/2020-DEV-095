package com.kotlin.tennisapplication

import androidx.annotation.IntDef

interface Constant {
    companion object {
        const val PLAYER_ACTION_TIME_SIMULATION = 1000L

        const val ACTION_HIT = 0;
        const val ACTION_MISS = 1;

        @IntDef(ACTION_HIT,ACTION_MISS)
        @Retention(AnnotationRetention.SOURCE)
        annotation class PlayerAction {
        }
    }
}