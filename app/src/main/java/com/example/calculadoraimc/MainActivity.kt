package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraIMCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IMCscreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun IMCscreen (modifier: Modifier = Modifier) {

//  -- variáveis --
    var altura by remember {
        mutableStateOf("")
    }

    var peso by remember {
        mutableStateOf("")
    }

    var imc by remember {
        mutableStateOf(0.0)
    }

    var categotiaImc by remember {
        mutableStateOf("")
    }

    var corCard by remember {
        mutableStateOf(Color(0xFF56C439))
    }

    val alturaFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // -- header --
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(
                            color = colorResource(R.color.cor_app)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(80.dp)
                            .padding(vertical = 16.dp),
                        painter = painterResource(
                            R.drawable.bmi
                        ),
                        contentDescription = "Logo App"
                    )

                    Text(
                        text = "Calculadora de IMC",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }


//              -- formulário --
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 32.dp),
                ) {
                    // -- card resultado --
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .offset(y = (-30).dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF9F6F6)
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Seus dados",
                                fontSize = 24.sp,
                                color = colorResource(R.color.cor_app),
                                fontWeight = FontWeight.Bold
                            )

                            // -- campos de texto --
                            OutlinedTextField(
                                value = altura,
                                onValueChange = { altura = it },
                                singleLine = true,
                                modifier = Modifier.focusRequester(alturaFocusRequester),
                                label = {
                                    Text(text = "Altura")
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorResource(R.color.cor_app),
                                    focusedLabelColor = colorResource(R.color.cor_app),
                                    unfocusedBorderColor = colorResource(R.color.cor_app),
                                    cursorColor = colorResource(R.color.cor_app)
                                ),
                                placeholder = {
                                    Text(
                                        text = "Escreva em cm",
                                        color = Color.Gray
                                    )
                                },
                                shape = CardDefaults.shape
                            )

                            OutlinedTextField(
                                value = peso,
                                onValueChange = { peso = it },
                                singleLine = true,
                                modifier = Modifier,
                                label = {
                                    Text(text = "Peso")
                                },
                                placeholder = {
                                    Text(
                                        text = "Escreva seu peso atual",
                                        color = Color.Gray
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorResource(R.color.cor_app),
                                    focusedLabelColor = colorResource(R.color.cor_app),
                                    unfocusedBorderColor = colorResource(R.color.cor_app),
                                    cursorColor = colorResource(R.color.cor_app)
                                ),
                                shape = CardDefaults.shape
                            )

                            // -- botões calcular e limpar --
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = {
                                        // Receberá os valores dos campos e calculará o IMC
                                        imc = calcularIMC(
                                            altura = altura.toDouble(),
                                            peso = peso.toDouble()
                                        )
                                        // Determinará a categoria do IMC
                                        categotiaImc = determinarCategoriaIMC(imc)
                                        corCard = determinarCorImc(imc)
                                    },
                                    modifier = Modifier
                                        .width(140.dp)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_app))
                                ) {
                                    Text(
                                        text = "CALCULAR",
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )
                                }

                                Button(
                                    onClick = {
                                        altura = ""
                                        peso = ""
                                        imc = 0.0
                                        categotiaImc = ""
                                        corCard = Color(0xFF56C439)
                                        alturaFocusRequester.requestFocus()
                                    },  // -- limpa os campos --
                                    modifier = Modifier
                                        .width(140.dp)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_app)),
                                ) {
                                    Text(
                                        text = "LIMPAR",
                                        color = Color.White,
                                        fontSize = 18.sp
                                    )

                                }

                            }

                        }

                    }


                    // -- card resultado --
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .height(80.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = corCard
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = String.format("%.1f", imc),  // Formata o IMC com 1 casa decimal
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 28.sp
                            )

                            Text(
                                text = categotiaImc,    // Exibe a categoria do IMC
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }


                }
            }



        }
    }
}

