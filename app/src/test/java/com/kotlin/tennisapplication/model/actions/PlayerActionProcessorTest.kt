package com.kotlin.tennisapplication.model.actions

import androidx.lifecycle.MutableLiveData
import com.kotlin.tennisapplication.Constant
import com.kotlin.tennisapplication.model.entity.PlayerActionEntity
import com.kotlin.tennisapplication.model.entity.PlayerEntity
import com.kotlin.tennisapplication.model.points.PointsProcessor
import com.kotlin.tennisapplication.viewmodel.PlayerViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PlayerActionProcessorTest {

    private lateinit var playerActionProcessor: PlayerActionProcessor

    @Mock
    lateinit var player1: PlayerEntity

    @Mock
    lateinit var player2: PlayerEntity

    @Mock
    lateinit var pointsProcessor: PointsProcessor

    @Mock
    lateinit var playerAction: MutableLiveData<PlayerActionEntity>

    @Mock
    lateinit var viewModel: PlayerViewModel

    @Mock
    lateinit var runnable: Runnable

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playerActionProcessor = PlayerActionProcessor(pointsProcessor)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSamePlayersProcessed() {
        playerActionProcessor.processActionEvent(
            playerAction,
            viewModel,
            Constant.ACTION_HIT,
            player1,
            player1
        )
    }

    @Test
    fun testDifferentPlayersNoError() {
        playerActionProcessor.processActionEvent(
            playerAction,
            viewModel,
            Constant.ACTION_HIT,
            player1,
            player2
        )
    }

    @Test
    fun testPlayer1HitsBall() {
        player1 = PlayerEntity(
            0,
            runnable,
            "Player 1"
        )
        player2 = PlayerEntity(
            0,
            runnable,
            "Player 2"
        )
        val initialHit = player1.hitCount
        playerActionProcessor.processActionEvent(
            playerAction,
            viewModel,
            Constant.ACTION_HIT,
            player1,
            player2
        )
        assertTrue(player1.hitCount == initialHit + 1)
    }

    @Test
    fun testPlayer1MissesBall() {
        player1 = PlayerEntity(
            0,
            runnable,
            "Player 1"
        )
        player2 = PlayerEntity(
            0,
            runnable,
            "Player 2"
        )
        val initialHit = player1.missCount
        val otherPlayerTotal = player2.totalPoints

        playerActionProcessor.processActionEvent(
            playerAction,
            viewModel,
            Constant.ACTION_MISS,
            player1,
            player2
        )

        assertTrue(player1.missCount == initialHit + 1)
        assertTrue(player2.totalPoints == otherPlayerTotal + 1)
        verify(pointsProcessor).processScore(viewModel, player1, player2)
    }

    @Test
    fun testPlayer1MissPlayer2GainPoint() {
        val playerActionEntityCaptor: ArgumentCaptor<PlayerActionEntity> =
            ArgumentCaptor.forClass(PlayerActionEntity::class.java)

        player1 = PlayerEntity(
            0,
            runnable,
            "Player 1"
        )
        player2 = PlayerEntity(
            0,
            runnable,
            "Player 2"
        )
        val initialHit = player1.missCount
        val otherPlayerTotal = player2.totalPoints

        playerActionProcessor.processActionEvent(
            playerAction,
            viewModel,
            Constant.ACTION_MISS,
            player1,
            player2
        )

        assertTrue(player1.missCount == initialHit + 1)
        assertTrue(player2.totalPoints == otherPlayerTotal + 1)
        verify(pointsProcessor).processScore(viewModel, player1, player2)
        verify(playerAction).postValue(playerActionEntityCaptor.capture())
        //below 2 statements will verify if player 2 gains point
        assertEquals(player2, playerActionEntityCaptor.value.player)
        assertEquals(Constant.ACTION_GAINED_POINT, playerActionEntityCaptor.value.action)
    }
}