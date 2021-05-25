package br.com.halsservice.utils.others

import android.graphics.Color
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Created by RubioAlves on 16/04/2021
 */
class RandomColors {

    val listaColor = mutableListOf(
        "#F44336", "#E91E63", "#9C27B0", "#673AB7",
        "#3F51B5", "#2196F3", "#03A9F4", "#00BCD4", "#009688",
        "#4CAF50", "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107",
        "#FF9800", "#FF5722", "#795548", "#9E9E9E", "#607D8B")

    fun getColor():Int{

        val color: String
        val random = Random.nextInt(0 until listaColor.size)

        color = listaColor[random]

        return Color.parseColor(color)

    }


}