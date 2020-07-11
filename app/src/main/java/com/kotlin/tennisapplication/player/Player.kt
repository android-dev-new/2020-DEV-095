package com.kotlin.tennisapplication.player

class Player(var playerNumber:Int, runnable: Runnable, name: String) : Thread(runnable, name){
    var hitCount = 0
    var missCount = 0
    var totalPoints = 0
}