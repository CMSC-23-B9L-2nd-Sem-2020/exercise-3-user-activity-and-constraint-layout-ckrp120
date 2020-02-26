package com.example.lightsout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val gameStart: Boolean = false
    private val gameWin: Boolean = true
    private var tapCount: Int = 0
    private lateinit var tapShowCount: TextView
    private val gameGrid = listOf(
        listOf(0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tapShowCount = findViewById(R.id.tapShowCount)
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

 /*   private fun flipLights(view: View, row: Int, col: Int) {
        //flip button itself
        if(gameGrid[row][col] == 0){
            val mainLight: ImageView =
        }

    }
*/

    private fun countUp(view: View) {
        tapCount++
        if(tapShowCount !== null){
            tapShowCount.text = tapCount.toString()
        }
    }
}
