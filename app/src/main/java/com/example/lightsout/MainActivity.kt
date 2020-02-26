package com.example.lightsout

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var gameStart: Boolean = false;
    private var tapCount: Int = 0
    private lateinit var tapShowCount: TextView
    private lateinit var retryBtn: Button
    private var gameGrid = listOf(
        arrayOf(1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retryBtn = findViewById(R.id.retry)
        retryBtn.visibility = View.GONE
        tapShowCount = findViewById(R.id.tapShowCount)
        setListeners()
        retryBtn.setOnClickListener { retry() }

    }

    private fun getId(row: Int, column: Int): Int{
        val gridID = listOf(
            listOf(R.id.light1, R.id.light2, R.id.light3, R.id.light4, R.id.light5),
            listOf(R.id.light6, R.id.light7, R.id.light8, R.id.light9, R.id.light10),
            listOf(R.id.light11, R.id.light12, R.id.light13, R.id.light14, R.id.light15),
            listOf(R.id.light16, R.id.light17, R.id.light18, R.id.light19, R.id.light20),
            listOf(R.id.light21, R.id.light22, R.id.light23, R.id.light24, R.id.light25)
        )

        return gridID[row][column]
    }

    private fun setListeners(){
        for(row: Int in (0..4)){
            for(col: Int in (0..4)){
                findViewById<ImageView>(getId(row, col)).setOnClickListener{
                    flipLights(it, row, col)
                }
            }
        }
    }

    private fun flipLights(view: View, row: Int, col: Int) {
        if(!gameStart){
            gameStart = true
            retryBtn.visibility = View.VISIBLE
        }
        //flip light itself
        val mainLight: ImageView = findViewById(getId(row, col))
        if(gameGrid[row][col] == 0){
            mainLight.setImageResource(R.drawable.on)
            gameGrid[row][col] = 1
        }else{
            mainLight.setImageResource(R.drawable.off)
            gameGrid[row][col] = 0
        }

        //flip light on top
        if(row - 1 >= 0){ //check if top button exists
            val topLight: ImageView = findViewById(getId(row-1, col))
            if(gameGrid[row-1][col] == 0){
                topLight.setImageResource(R.drawable.on)
                gameGrid[row-1][col] = 1
            }else{
                topLight.setImageResource(R.drawable.off)
                gameGrid[row-1][col] = 0
            }
        }

        //flip light below
        if(row+1 < 5){

            val bottomLight: ImageView = findViewById(getId(row+1, col))
            if(gameGrid[row+1][col] == 0){
                bottomLight.setImageResource(R.drawable.on)
                gameGrid[row+1][col] = 1
            }else{
                bottomLight.setImageResource(R.drawable.off)
                gameGrid[row+1][col] = 0
            }

        }

        //flip light on the left
        if(col-1 >= 0){
            val leftLight: ImageView = findViewById(getId(row, col-1))
            if(gameGrid[row][col-1] == 0){
                leftLight.setImageResource(R.drawable.on)
                gameGrid[row][col-1] = 1
            }else{
                leftLight.setImageResource(R.drawable.off)
                gameGrid[row][col-1] = 0
            }
        }

        //flip light on the right
        if(col+1 < 5){
            val leftLight: ImageView = findViewById(getId(row, col+1))
            if(gameGrid[row][col+1] == 0){
                leftLight.setImageResource(R.drawable.on)
                gameGrid[row][col+1] = 1
            }else{
                leftLight.setImageResource(R.drawable.off)
                gameGrid[row][col+1] = 0
            }
        }

        countUp(view)

    }

    private fun countUp(view: View) {
        tapCount++
        if(tapShowCount !== null){
            tapShowCount.text = tapCount.toString()
        }
    }

    private fun retry(){
        tapCount = 0
        if(tapShowCount !== null){
            tapShowCount.text = tapCount.toString()
        }

        for(i: Int in (0..4)){
            for(j: Int in (0..4)){
                val light: ImageView = findViewById(getId(i,j))
                light.setImageResource(R.drawable.on)

                gameGrid[i][j] = 1
            }
        }
    }




}
