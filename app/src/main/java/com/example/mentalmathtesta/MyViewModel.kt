package com.example.mentalmathtesta

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import kotlin.random.Random
import androidx.core.content.edit

class MyViewModel(val app : Application, private val handle : SavedStateHandle) : AndroidViewModel(app) {
    companion object {
        const val MAX_SCORE = "max_score_key"
        const val CUR_SCORE = "cur_score_key"
        const val ANSWER = "answer_key"
        const val QUESTION_TEXT = "question_text_key"
        const val IS_WIN = "is_win_key"
        const val FILE_NAME = "shp_file_name"
    }

    private val shp : SharedPreferences = app.getSharedPreferences(FILE_NAME, Application.MODE_PRIVATE)

    // 当前局分数
    private val _cur : MutableLiveData<Int> = handle.getLiveData(CUR_SCORE, 0)
    val cur : LiveData<Int> = _cur

    // 历史最高分
    private val _max : MutableLiveData<Int> = handle.getLiveData(MAX_SCORE, 0)
    val max : LiveData<Int> = _max

    // 当前题正确答案
    private val _answer : MutableLiveData<Int> = handle.getLiveData(ANSWER,0)
    val answer : LiveData<Int> = _answer

    // UI展示题目字符串
    private val _questionText : MutableLiveData<String> = handle.getLiveData(QUESTION_TEXT,"")
    val questionText : LiveData<String> = _questionText

    // 是否刷新了最高分
    private val _isWin : MutableLiveData<Boolean> = handle.getLiveData(IS_WIN, false)
    val isWin : LiveData<Boolean> = _isWin

    init {
        handle[MAX_SCORE] = shp.getInt(MAX_SCORE, 0)
    }

    // 增加：用户正在输入的答案字符串
    private val _userInput = handle.getLiveData("user_input","")
    val userInput : LiveData<String> = _userInput

    // 追加数字
    fun appendUserInput(num:String){
        val old = _userInput.value ?: ""
        _userInput.value = old + num
    }

    // 清空用户输入
    fun clearUserInput(){
        _userInput.value = ""
    }
    /**
     * 开启全新一局游戏
     * 重置本局分数、胜利标记，生成第一道题目
     */
    fun startNewGame() {
        handle[CUR_SCORE] = 0
        handle[IS_WIN] = false
        getNewQuestion()
    }

    fun getNewQuestion() {
        val level = 20
        val x = Random.nextInt(1, level + 1)
        val y = Random.nextInt(1, level + 1)

        val leftNum: Int
        val rightNum: Int
        val opStr: String
        val result: Int

        if (Random.nextBoolean()) {
            leftNum = x
            rightNum = y
            opStr = "+"
            result = leftNum + rightNum
        } else {
            leftNum = maxOf(x, y)
            rightNum = minOf(x, y)
            opStr = "-"
            result = leftNum - rightNum
        }

        handle[ANSWER] = result

        val sb = StringBuilder()
        sb.append(leftNum)
        sb.append(" ")
        sb.append(opStr)
        sb.append(" ")
        sb.append(rightNum)
        sb.append(" = ?")
        handle[QUESTION_TEXT] = sb.toString()
    }

    fun onCorrect() {
        val currentScore = (handle[CUR_SCORE] as? Int ?: 0) + 1
        handle[CUR_SCORE] = currentScore

        val currentMax = handle[MAX_SCORE] as? Int ?: 0
        if (currentScore > currentMax) {
            handle[MAX_SCORE] = currentScore
            shp.edit { putInt(MAX_SCORE, currentScore) }
            // 刷新历史记录，标记胜利
            handle[IS_WIN] = true
        }
    }
}