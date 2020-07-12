package com.kotlin.tennisapplication

import androidx.annotation.IntDef

interface Constant {
    companion object {
        const val PLAYER_ACTION_TIME_SIMULATION = 500L // 500 millisecond

        const val ACTION_HIT = 0
        const val ACTION_MISS = 1
        const val ACTION_GAINED_POINT = 2

        @IntDef(ACTION_HIT,ACTION_MISS)
        @Retention(AnnotationRetention.SOURCE)
        annotation class PlayerActionEvent
    }
}