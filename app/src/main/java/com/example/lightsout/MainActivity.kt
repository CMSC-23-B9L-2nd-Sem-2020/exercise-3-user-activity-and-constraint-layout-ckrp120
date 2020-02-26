package com.example.lightsout

import android.content.Context
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {
    private var gameStart: Boolean = false; //indicates whether the player has tapped at least one light
    private var tapCount: Int = 0 //counter for how many lights a player has tapped
    private lateinit var tapShowCount: TextView //textView for tapCount
    private lateinit var retryBtn: Button //button which resets the game
    private lateinit var nicknameTextView: TextView //textView for nickname
    private lateinit var nicknameEditText: EditText //editText for nickname
    private lateinit var submitBtn: Button //button which submits the nickname
    private var gameGrid = listOf( //array representation of the lights
        arrayOf(1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1),
        arrayOf(1, 1, 1, 1, 1)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideLights()
        nicknameEditText = findViewById(R.id.nickname)
        nicknameTextView = findViewById(R.id.nicknameShow)
        submitBtn = findViewById(R.id.submit)
        retryBtn = findViewById(R.id.retry)
        retryBtn.visibility = View.GONE
        retryBtn.setOnClickListener { retry() }
        submitBtn.setOnClickListener {
            changeNickname(it)
            showLights()
        }
        nicknameTextView.setOnClickListener{ updateNickname() }
        tapShowCount = findViewById(R.id.tapShowCount)
        setListeners()



    }

    private fun getId(row: Int, column: Int): Int{ //creates a list of ids and returns the id
        val gridID = listOf(
            listOf(R.id.light1, R.id.light2, R.id.light3, R.id.light4, R.id.light5),
            listOf(R.id.light6, R.id.light7, R.id.light8, R.id.light9, R.id.light10),
            listOf(R.id.light11, R.id.light12, R.id.light13, R.id.light14, R.id.light15),
            listOf(R.id.light16, R.id.light17, R.id.light18, R.id.light19, R.id.light20),
            listOf(R.id.light21, R.id.light22, R.id.light23, R.id.light24, R.id.light25)
        )

        return gridID[row][column]
    }

    private fun hideLights(){ //toggles visibility of lights off
        for(row: Int in (0..4)){
            for(col: Int in (0..4)){
                findViewById<ImageView>(getId(row, col)).visibility = View.GONE
            }
        }
    }

    private fun showLights(){ //toggles visibility of lights on
        for(row: Int in (0..4)){
            for(col: Int in (0..4)){
                findViewById<ImageView>(getId(row, col)).visibility = View.VISIBLE
            }
        }
    }


    private fun setListeners(){ //checks which button is pressed and then sets a listener to handle flipLights
        for(row: Int in (0..4)){
            for(col: Int in (0..4)){
                findViewById<ImageView>(getId(row, col)).setOnClickListener{
                    flipLights(it, row, col)
                }
            }
        }
    }

    private fun flipLights(view: View, row: Int, col: Int) { //main function for toggling lights on/off
        //toggles retry button visibility at the start
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
        var playerWin: Boolean = true
        //check if the player has won the game
        for(i: Int in (0..4)){
            for(j: Int in (0..4)){
                if(gameGrid[i][j] == 1){
                    playerWin = false
                    break
                }
            }
        }

        //shows a toast when the player has successfully turned off all the lights
        if(playerWin){
            val winToast = Toast.makeText(
                this, R.string.toast_message,
                Toast.LENGTH_SHORT
            )

            winToast.show()
        }
    }

    private fun countUp(view: View) { //adds +1 to the counter whenever the player taps a light and updates the textView
        tapCount++
        if(tapShowCount !== null){
            tapShowCount.text = tapCount.toString()
        }
    }

    private fun retry(){ //resets the game
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

    private fun updateNickname(){
        nicknameTextView.visibility = View.GONE
        nicknameEditText.visibility = View.VISIBLE
        submitBtn.visibility = View.VISIBLE
        nicknameEditText.requestFocus()


    }

    private fun changeNickname(view: View){
        nicknameTextView.text = nicknameEditText.text

        nicknameTextView.visibility = View.VISIBLE
        nicknameEditText.visibility = View.GONE
        submitBtn.visibility = View.GONE

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }



}
