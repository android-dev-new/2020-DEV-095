package com.kotlin.tennisapplication.actions

import com.kotlin.tennisapplication.Constant
import com.kotlin.tennisapplication.player.Player
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException
import org.mockito.Mockito.`when` as _when

class PlayerActionProcessorTest {

    lateinit var playerActionProcessor: PlayerActionProcessor

    @Mock
    lateinit var player1: Player

    @Mock
    lateinit var player2: Player

    @Before
    fun setUp() {
        playerActionProcessor = PlayerActionProcessor()
        MockitoAnnotations.initMocks(this)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSamePlayersProcessed() {
        playerActionProcessor.processActionEvent(Constant.ACTION_HIT, player1, player1)
    }

    @Test
    fun testDifferentPlayersNoError() {
        playerActionProcessor.processActionEvent(Constant.ACTION_HIT, player1, player2)
    }
}