package com.yusuftalhaklc.todoapplication.ui.theme

import androidx.compose.ui.graphics.Color
import com.yusuftalhaklc.todoapplication.R

class ColorAndIconPicker() {

    private var drawSource:Int =0

    private var colorSource:Color = bgColor

    fun getColor(id:String): Color {

        when (id){
            "all" -> {
                drawSource = R.drawable.all
                colorSource = CategoryAll
            }
            "work" -> {
                drawSource = R.drawable.work
                colorSource = CategoryWork
            }
            "music" -> {
                drawSource = R.drawable.music
                colorSource = CategoryMusic
            }
            "travel" -> {
                drawSource = R.drawable.travel
                colorSource = CategoryTravel
            }
            "study" -> {
                drawSource = R.drawable.study
                colorSource = CategoryStudy
            }
            "home" -> {
                drawSource = R.drawable.home
                colorSource = CategoryHome
            }
            "art" -> {
                drawSource = R.drawable.art
                colorSource = CategoryArt
            }
            "shop" -> {
                drawSource = R.drawable.shop
                colorSource = CategoryShop
            }
            "entertainment" -> {
                drawSource = R.drawable.entertainment
                colorSource = CategoryEnt
            }
            "other" -> {
                drawSource = R.drawable.other
                colorSource = CategoryTravel
            }
        }
        return colorSource
    }
    fun getIcon(id:String): Int {

        when (id){
            "all" -> {
                drawSource = R.drawable.all
            }
            "work" -> {
                drawSource = R.drawable.work
            }
            "music" -> {
                drawSource = R.drawable.music
            }
            "travel" -> {
                drawSource = R.drawable.travel
            }
            "study" -> {
                drawSource = R.drawable.study
            }
            "home" -> {
                drawSource = R.drawable.home
            }
            "art" -> {
                drawSource = R.drawable.art
            }
            "shop" -> {
                drawSource = R.drawable.shop
            }
            "entertainment" -> {
                drawSource = R.drawable.entertainment
            }
            "other" -> {
                drawSource = R.drawable.other
            }
        }
        return drawSource
    }
    fun getList(): List<String>{
        val categories = listOf(
            "work",
            "music",
            "travel",
            "study",
            "home",
            "art",
            "shop",
            "entertainment",
            "other"
        )
        return categories
    }
}