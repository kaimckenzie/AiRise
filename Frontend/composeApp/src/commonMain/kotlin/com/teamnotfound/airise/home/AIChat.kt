package com.teamnotfound.airise.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.teamnotfound.airise.util.BgBlack
import com.teamnotfound.airise.util.Silver
import com.teamnotfound.airise.util.DeepBlue
import com.teamnotfound.airise.util.Transparent
import com.teamnotfound.airise.util.White

@Composable
fun AiChat(navController: NavHostController) {
    var messageText by remember { mutableStateOf(TextFieldValue("")) }
    val messageResponse = Message("Default response.", ai = true)
    val messageHistory = remember {
        mutableStateListOf(
            Message("Hello! How can I help you?", ai = true),
            Message("Hi! I have a question about my fitness.", ai = false),
            Message("Sure, what can I help you with?", ai = true)
        )
    }
    val messageSuggested = remember {
        mutableStateListOf(
            Message("What should I eat before exercising?", ai = false),
            Message("How do I stay motivated?", ai = false),
            Message("What is the best pizza topping?", ai = false)
        )
    }
    // body
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgBlack)
            .padding(bottom = 8.dp)
    ) {
        // back button bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Silver)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Back",
                        tint = Color.Red
                    )
                }
            }

        }
        // message history list
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            messageHistory.forEach { message ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (message.ai) Arrangement.Start else Arrangement.End
                ) {
                    Column {
                        Row (modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)) {
                            if (message.ai) {
                                Icon(
                                    imageVector = Icons.Filled.TagFaces,
                                    contentDescription = "Smiley Face",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Coach Rise",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = White
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (message.ai) Silver else DeepBlue
                                )
                                .padding(12.dp)
                                .widthIn(max = 280.dp)
                        ) {
                            Text(
                                message.text,
                                fontSize = 16.sp,
                                color = if (message.ai) Color.Black else Color.White
                            )
                        }
                    }
                }
            }
        }
        // message suggestion list
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messageSuggested) { suggestion ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 1.dp,
                            color = White,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .background(Transparent)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .clickable {
                            messageHistory.add(Message(suggestion.text, ai = false))
                            messageHistory.add(messageResponse)
                        }
                ) {
                    Text(
                        text = suggestion.text,
                        color = White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        // message input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                // add image logic
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Image",
                    tint = White
                )
            }
            BasicTextField(
                value = messageText.text,
                onValueChange = { messageText = TextFieldValue(it) },
                singleLine = true,
                textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(White)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                decorationBox = { placeHolder ->
                    if (messageText.text.isEmpty()) {
                        Text(
                            text = "Message...",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    placeHolder()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                if (messageText.text.isNotBlank()) {
                    messageHistory.add(Message(messageText.text, ai = false))
                    messageText = TextFieldValue("")
                    messageHistory.add(messageResponse)
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = White
                )
            }
        }
    }
}

data class Message(val text: String, val ai: Boolean)
