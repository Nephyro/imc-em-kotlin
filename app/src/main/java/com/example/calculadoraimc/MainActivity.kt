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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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

    var alturaError by remember {
        mutableStateOf(false)
    }

    var pesoError by remember {
        mutableStateOf(false)
    }

    // Estados do Toast
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastIsError by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current    // Controle do teclado
    val focusManager = LocalFocusManager.current    // Gerenciador de foco

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
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
                            .wrapContentHeight() // Ajusta a altura automaticamente para não cortar o erro
                            .offset(y = (-30).dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFF9F6F6)
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
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
                                onValueChange = {
                                    altura = it
                                    alturaError = false
                                },
                                singleLine = true,
                                isError = alturaError,
                                label = { Text(text = "Altura") },
                                placeholder = { Text(text = "Escreva em cm") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    }
                                ),
                                supportingText = {
                                    if (alturaError) {
                                        Text(
                                            text = "Campo obrigatório",
                                            color = Color.Red,
                                            fontSize = 12.sp
                                        )
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorResource(R.color.cor_app),
                                    focusedLabelColor = colorResource(R.color.cor_app),
                                    unfocusedBorderColor = colorResource(R.color.cor_app),
                                    cursorColor = colorResource(R.color.cor_app),
                                    errorBorderColor = Color.Red,
                                    errorLabelColor = Color.Red
                                ),
                                textStyle = TextStyle(color = Color.Black),
                                shape = CardDefaults.shape
                            )

                            OutlinedTextField(
                                value = peso,
                                onValueChange = {
                                    peso = it
                                    pesoError = false
                                },
                                singleLine = true,
                                isError = pesoError,
                                label = { Text(text = "Peso") },
                                placeholder = { Text(text = "Escreva seu peso atual") },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    }
                                ),
                                supportingText = {
                                    if (pesoError) {
                                        Text(
                                            text = "Campo obrigatório",
                                            color = Color.Red,
                                            fontSize = 12.sp
                                        )
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colorResource(R.color.cor_app),
                                    focusedLabelColor = colorResource(R.color.cor_app),
                                    unfocusedBorderColor = colorResource(R.color.cor_app),
                                    cursorColor = colorResource(R.color.cor_app),
                                    errorBorderColor = Color.Red,
                                    errorLabelColor = Color.Red
                                ),
                                textStyle = TextStyle(color = Color.Black),
                                shape = CardDefaults.shape
                            )

                            // -- botões calcular e limpar --
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = {
                                        // Esconde o teclado e remove o foco imediatamente ao clicar
                                        keyboardController?.hide()
                                        focusManager.clearFocus()

                                        // Valida se estão vazios ou em branco
                                        alturaError = altura.isBlank()
                                        pesoError = peso.isBlank()

                                        // Verifica se há algum erro
                                        if (alturaError || pesoError) {
                                            // DISPARA TOAST DE ERRO
                                            toastMessage = "Preencha todos os campos!"
                                            toastIsError = true
                                        } else {

                                            val alturaVal = altura.toDoubleOrNull() ?: 0.0
                                            val pesoVal = peso.toDoubleOrNull() ?: 0.0

                                            if (alturaVal > 0) {
                                                imc = calcularIMC(altura = alturaVal, peso = pesoVal)
                                                categotiaImc = determinarCategoriaIMC(imc)
                                                corCard = determinarCorImc(imc)

                                                // DISPARA TOAST DE SUCESSO
                                                toastMessage = "IMC calculado com sucesso!"
                                                toastIsError = false
                                            } else {
                                                // DISPARA TOAST DE ERRO
                                                toastMessage = "Preencha todos os campos!"
                                                toastIsError = true
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_app))
                                ) {
                                    Text(
                                        text = "CALCULAR",
                                        color = Color.White,
                                        fontSize = 15.sp
                                    )
                                }

                                Button(
                                    onClick = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                        altura = ""
                                        peso = ""
                                        imc = 0.0
                                        categotiaImc = ""
                                        corCard = Color(0xFF56C439)
                                        alturaError = false
                                        pesoError = false

                                        // DISPARA TOAST DE LIMPEZA
                                        toastMessage = "Campos limpos!"
                                        toastIsError = false
                                    },
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(colorResource(R.color.cor_app)),
                                ) {
                                    Text(
                                        text = "LIMPAR",
                                        color = Color.White,
                                        fontSize = 15.sp
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
                                text = String.format("%.1f", imc),
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 28.sp
                            )

                            Text(
                                text = categotiaImc,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            toastMessage?.let { message ->
                CustomToast(
                    message = message,
                    isError = toastIsError,
                    onDismiss = { toastMessage = null }
                )
            }
        }
    }
}

