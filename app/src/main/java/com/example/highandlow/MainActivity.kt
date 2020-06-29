package com.example.highandlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = "high and low"
    private var yourCard = Pair("", 0)
    private var droidCard = Pair("", 0)
    private var hitCount = 0
    private var loseCount = 0
    private var gameStart = false
    private var answered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        highBtn.setOnClickListener{
            if ((gameStart && !answered)) {
                highAndLow('h')
            }
        }
        lowBtn.setOnClickListener{
            if ((gameStart && !answered)) {
                highAndLow('l')
            }
        }
        nextBtn.setOnClickListener{
            if (gameStart) {
                drawCard()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hitCount = 0
        loseCount = 0
        hitText.text = getString(R.string.hit_text)
        loseText.text = getString(R.string.lose_text)
        gameStart = true
        drawCard()
    }

    fun randCard(): Pair<String, Int> {
        var i = (1..13).random()
        var ist = "0"
        var s = arrayOf('d','c','s','h').random()
        if (i < 10) {
            ist = ist + i.toString()
        } else {
            ist = i.toString()
        }
        return Pair(s+ist, i)
    }

    private fun drawCard(){
        yourCardImage.setImageResource(R.drawable.z02)
        droidCardImage.setImageResource(R.drawable.z01)
        yourCard = randCard()
        Log.d(tag, "YourCard:"+yourCard.first+" YourNum:"+yourCard.second)
        yourCardImage.setImageResource(resources.getIdentifier("${yourCard.first}","drawable", packageName))
        droidCard = randCard()
        Log.d(tag, "droidCard:"+droidCard.first+" droidNum:"+droidCard.second)
        answered = false

    }

    fun highAndLow(answer:Char) {
        showDroidCard()
        answered = true
        //Log.d(tag, "droidNum:"+droidCard.second+" YourNum:"+yourCard.second)
        val balance = droidCard.second - yourCard.second
        if (balance == 0) {

        } else if ((balance > 0 && answer == 'h')) {
            hitCount++
            hitText.text = getString(R.string.hit_text) + hitCount
        } else if ((balance < 0 && answer == 'l')) {
            hitCount++
            hitText.text = getString(R.string.hit_text) + hitCount
        } else {
            loseCount++
            loseText.text = getString(R.string.lose_text) + loseCount
        }
        if (hitCount == 5) {
            resultText.text = "You Win"
            gameStart = false
        } else if (loseCount == 5) {
            resultText.text = "You Lose"
            gameStart = false
        } else {

        }
    }
    private fun showDroidCard() {
        droidCardImage.setImageResource(resources.getIdentifier("${droidCard.first}","drawable", packageName))
    }

}