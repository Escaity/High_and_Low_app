package com.example.highandlow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private val tag = "high and low"
    private var yourCard = Pair("", 0)
    private var droidCard = Pair("", 0)
    private var hitCount = 0
    private var loseCount = 0
    private var money = 1000
    private var betMoney = 100
    private val commision = 0.05
    private var gameStart = false
    private var answered = false
    private var bankrupted = false

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
        nextBtn.setOnClickListener {
            if (gameStart) {
                drawCard()
            }
        }
        betBtn.setOnClickListener{
            if(answered && betEdit.text.toString() != null && money >= betEdit.text.toString().toInt()) {
                betMoney = betEdit.text.toString().toInt()
                betting_text.text = getString(R.string.betting_text) + betMoney
            }
        }
        resetBtn.setOnClickListener {
            if (bankrupted) {
                money = 1000
                hitCount = 0
                loseCount = 0
                betMoney = 100
                answered = false
                resultText.text = ""
                hitText.text = getString(R.string.hit_text)
                loseText.text = getString(R.string.lose_text)
                moneyText.text = getString(R.string.money_text) + money
                betting_text.text = getString(R.string.betting_text) + betMoney
                gameStart = true
            }
        }



    }

    override fun onResume() {
        super.onResume()
        hitCount = 0
        loseCount = 0
        hitText.text = getString(R.string.hit_text)
        loseText.text = getString(R.string.lose_text)
        moneyText.text = getString(R.string.money_text)+money
        betting_text.text = getString(R.string.betting_text) + betMoney
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
            money = (money + betMoney*(1 - commision)).roundToInt()
            moneyText.text = getString(R.string.money_text)+money
        } else if ((balance < 0 && answer == 'l')) {
            hitCount++
            hitText.text = getString(R.string.hit_text) + hitCount
            money = (money + betMoney*(1 - commision)).roundToInt()
            moneyText.text = getString(R.string.money_text)+money
        } else {
            loseCount++
            loseText.text = getString(R.string.lose_text) + loseCount
            money = money - betMoney
            moneyText.text = getString(R.string.money_text)+money
        }
        if (money >= 1000000) {
            resultText.text = "You Rich!!"
            gameStart = false
        } else if (money <= 0) {
            resultText.text = "You bankrupt..."
            gameStart = false
            bankrupted = true
        } else {

        }
    }
    private fun showDroidCard() {
        droidCardImage.setImageResource(resources.getIdentifier("${droidCard.first}","drawable", packageName))
    }

}