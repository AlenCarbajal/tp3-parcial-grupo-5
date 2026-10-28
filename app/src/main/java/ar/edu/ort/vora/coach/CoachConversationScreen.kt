
package ar.edu.ort.vora.coach

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ConversationBackground = Color(0xFFF7F8F5)
private val ConversationGreen = Color(0xFF103F35)
private val ConversationMint = Color(0xFF62D8B5)
private val ConversationMuted = Color(0xFF8A9690)
private val ConversationBorder = Color(0xFFE6EBE7)

private data class ConversationItem(
    val text: String,
    val fromUser: Boolean,
    val time: String,
    val audioAvailable: Boolean = false
)

@Composable
fun CoachConversationScreen(
    onBack: () -> Unit = {},
    onPlayResponse: (String) -> Unit = {},
    onStartVoice: () -> Unit = {}
) {
    var input by remember { mutableStateOf("") }

    val messages = remember {
        mutableStateListOf(
            ConversationItem(
                text = "What should I eat for dinner?",
                fromUser = true,
                time = "12:41"
            ),
            ConversationItem(
                text = "You're currently 36g short of your protein target today. " +
                        "I'd recommend grilled chicken breast with roasted seasonal " +
                        "vegetables and a side of Greek yoghurt.",
                fromUser = false,
                time = "12:41",
                audioAvailable = true
            ),
            ConversationItem(
                text = "Can you make that vegetarian?",
                fromUser = true,
                time = "12:42"
            ),
            ConversationItem(
                text = "Absolutely. Try grilled organic tofu with roasted " +
                        "vegetables, quinoa and Greek yoghurt.",
                fromUser = false,
                time = "12:43"
            )
        )
    }

    val listState = rememberLazyListState()

    Scaffold(
        containerColor = ConversationBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ConversationBackground)
                .padding(padding)
        ) {
            // Encabezado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(29.dp)
                        .border(1.dp, ConversationBorder, CircleShape)
                        .background(Color.White, CircleShape)
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("‹", fontSize = 23.sp, color = ConversationGreen)
                }

                Text(
                    text = "VORA Coach",
                    modifier = Modifier.padding(start = 8.dp),
                    fontSize = 12.sp,
                    color = Color(0xFF202A25)
                )

                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(5.dp)
                        .background(ConversationMint, CircleShape)
                )

                Spacer(Modifier.weight(1f))

                MiniVoraOrb()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(ConversationBorder)
            )

            // Fecha
            Text(
                text = "TODAY AT 12:40 PM",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 13.dp),
                color = ConversationMuted,
                fontSize = 8.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Mensajes
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp),
                contentPadding = PaddingValues(bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(messages) { message ->
                    if (message.fromUser) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.79f)
                                    .background(
                                        ConversationGreen,
                                        RoundedCornerShape(
                                            topStart = 14.dp,
                                            topEnd = 14.dp,
                                            bottomStart = 14.dp,
                                            bottomEnd = 4.dp
                                        )
                                    )
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = message.text,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp
                                )

                                Text(
                                    text = message.time,
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .padding(top = 5.dp),
                                    color = Color(0xFFB3D2C7),
                                    fontSize = 7.sp
                                )
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            MiniVoraOrb(
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(20.dp)
                            )

                            Spacer(Modifier.width(8.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.87f)
                                    .background(
                                        Color.White,
                                        RoundedCornerShape(
                                            topStart = 13.dp,
                                            topEnd = 13.dp,
                                            bottomStart = 3.dp,
                                            bottomEnd = 13.dp
                                        )
                                    )
                                    .border(
                                        1.dp,
                                        ConversationBorder,
                                        RoundedCornerShape(
                                            topStart = 13.dp,
                                            topEnd = 13.dp,
                                            bottomStart = 3.dp,
                                            bottomEnd = 13.dp
                                        )
                                    )
                                    .padding(11.dp)
                            ) {
                                Text(
                                    text = message.text,
                                    color = Color(0xFF27332D),
                                    fontSize = 10.sp,
                                    lineHeight = 15.sp
                                )

                                if (message.audioAvailable) {
                                    Spacer(Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                onPlayResponse(message.text)
                                            }
                                            .padding(top = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(19.dp)
                                                .background(
                                                    Color(0xFFE7F2EC),
                                                    CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "▶",
                                                color = ConversationGreen,
                                                fontSize = 9.sp
                                            )
                                        }

                                        Spacer(Modifier.width(7.dp))

                                        Text(
                                            text = "Listen to response (0:14)",
                                            fontSize = 9.sp,
                                            color = Color(0xFF63736A)
                                        )
                                    }

                                    Spacer(Modifier.height(4.dp))

                                    Box(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(ConversationBorder)
                                    )
                                }

                                Text(
                                    text = message.time,
                                    modifier = Modifier.padding(top = 5.dp),
                                    color = ConversationMuted,
                                    fontSize = 7.sp
                                )
                            }
                        }
                    }
                }
            }

            // Barra inferior para escribir
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = ConversationBorder
                    )
                    .padding(horizontal = 10.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(29.dp)
                        .background(ConversationBackground, CircleShape)
                        .clickable { onStartVoice() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        color = ConversationMuted,
                        fontSize = 21.sp
                    )
                }

                Spacer(Modifier.width(7.dp))

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            ConversationBackground,
                            RoundedCornerShape(50)
                        )
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (input.isEmpty()) {
                            Text(
                                text = "Ask VORA anything...",
                                color = ConversationMuted,
                                fontSize = 9.sp
                            )
                        }

                        BasicTextField(
                            value = input,
                            onValueChange = { input = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                color = Color.Black,
                                fontSize = 11.sp
                            ),
                            singleLine = true
                        )
                    }

                    Text(
                        text = "🎙",
                        modifier = Modifier.clickable { onStartVoice() },
                        color = ConversationMuted,
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.width(7.dp))

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(ConversationGreen, CircleShape)
                        .clickable {
                            val question = input.trim()
                            if (question.isNotEmpty()) {
                                messages.add(
                                    ConversationItem(
                                        text = question,
                                        fromUser = true,
                                        time = "Now"
                                    )
                                )
                                input = ""
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "➤",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun MiniVoraOrb(
    modifier: Modifier = Modifier
        .size(21.dp)
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFBDEB70),
                        ConversationMint,
                        ConversationGreen
                    )
                ),
                shape = CircleShape
            )
            .border(1.dp, ConversationMint, CircleShape)
    )
}
