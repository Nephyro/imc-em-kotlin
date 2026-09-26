package com.example.calculadoraimc

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun CustomToast(
    message: String,
    isError: Boolean = false,
    onDismiss: () -> Unit
) {
    var progress by remember { mutableStateOf(1f) }

    LaunchedEffect(Unit) {
        val duration = 1700L
        val steps = 60
        val delayTime = duration / steps
        for (i in steps downTo 0) {
            progress = i.toFloat() / steps
            delay(delayTime)
        }
        onDismiss()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, end = 20.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically { -it },
            exit = fadeOut() + slideOutVertically { -it }
        ) {
            // Container principal com sombra elegante
            Column(
                modifier = Modifier
                    .widthIn(min = 220.dp, max = 280.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isError) Color(0xFF1E1E1E) else Color(0xFF1E1E1E)) // Fundo escuro sofisticado (Dark Mode feel) ou altere se preferir colorido
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Ícone dinâmico (Erro vs Sucesso)
                    Icon(
                        imageVector = if (isError) Icons.Rounded.Warning else Icons.Rounded.CheckCircle,
                        contentDescription = null,
                        tint = if (isError) Color(0xFFEF5350) else Color(0xFF66BB6A),
                        modifier = Modifier.size(24.dp)
                    )

                    // Texto da Mensagem
                    Text(
                        text = message,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Barrinha de progresso fina e moderna
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(RoundedCornerShape(1.5.dp)),
                    color = if (isError) Color(0xFFEF5350) else Color(0xFF66BB6A),
                    trackColor = Color.White.copy(alpha = 0.1f),
                )
            }
        }
    }
}