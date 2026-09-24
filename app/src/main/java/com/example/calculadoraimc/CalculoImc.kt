package com.example.calculadoraimc

import androidx.compose.ui.graphics.Color
import kotlin.math.pow

fun calcularIMC(altura: Double, peso: Double): Double {
    return peso / (altura / 100).pow(2.0)
}

fun determinarCategoriaIMC(imc: Double): String {
    return if (imc < 18.5) {
        "Abaixo do peso"
    } else if (imc >= 18.5 && imc < 25.0) {
        "Peso ideal"
    } else if (imc >= 25.0 && imc < 30.0) {
        "Levemente acima do peso"
    } else if (imc >= 30.0 && imc < 35.0) {
        "Obesidade Grau I"
    } else if (imc >= 35.0 && imc < 40.0) {
        "Obesidade Grau II"
    } else {
        "Obesidade Grau III"
    }
}

fun determinarCorImc(imc: Double): Color {
    return if (imc < 18.5) {
        Color(0xFF2196F3)
    } else if (imc >= 18.5 && imc < 25.0) {
        Color(0xFF56C439)
    } else if (imc >= 25.0 && imc < 30.0) {
        Color(0xFFFFC107)
    } else {
        Color(0xFFF44336)
    }
}