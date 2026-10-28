package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VipRepository
import com.example.model.ChatMessage
import com.example.model.MessageMediaType
import com.example.ui.components.VipAvatar
import com.example.ui.components.VipHonestyBanner
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDarkCard
import com.example.ui.theme.VipDarkCardElevated
import com.example.ui.theme.VipDarkSurface
import com.example.ui.theme.VipGoldChampagne
import com.example.ui.theme.VipGoldDark
import com.example.ui.theme.VipGoldPrimary
import com.example.ui.theme.VipObsidianBg
import com.example.ui.theme.VipPurpleContainer
import com.example.ui.theme.VipPurpleDeep
import com.example.ui.theme.VipPurpleGlow
import com.example.ui.theme.VipPurplePrimary
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite
import kotlinx.coroutines.launch

@Composable
fun ConversationScreen(
    chatId: String,
    contactName: String,
    contactHandle: String,
    repository: VipRepository,
    onBack: () -> Unit,
    onStartCall: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val messages by repository.messages.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VipObsidianBg)
    ) {
        // Conversation Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VipDarkSurface)
                .border(0.5.dp, VipBorderDark, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = VipGoldChampagne
                )
            }

            VipAvatar(name = contactName, size = 38, isOnline = true)
            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contactName,
                    color = VipWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    maxLines = 1
                )
                Text(
                    text = "$contactHandle • VIP Encrypted Channel",
                    color = VipPurpleGlow,
                    fontSize = 10.5.sp
                )
            }

            // Quick Call Actions
            IconButton(onClick = { onStartCall(contactName, false) }) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Voice Call",
                    tint = VipGoldPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            IconButton(onClick = { onStartCall(contactName, true) }) {
                Icon(
                    imageVector = Icons.Default.Videocam,
                    contentDescription = "Video Call",
                    tint = VipPurpleGlow,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Transparency note
        VipHonestyBanner(
            text = "UI Preview • Message delivery and network relay are not connected in Phase 1.",
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )

        // Messages list
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                MessageBubble(message = message)
            }
        }

        // Bottom Message Composer
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(VipDarkSurface)
                .border(0.5.dp, VipBorderDark)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = { /* Attachment picker placeholder */ },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Attach file",
                    tint = VipPurpleGlow
                )
            }

            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("Write VIP message...", fontSize = 13.sp) },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VipGoldPrimary,
                    unfocusedBorderColor = VipBorderDark,
                    focusedTextColor = VipTextPrimary,
                    unfocusedTextColor = VipTextPrimary,
                    focusedPlaceholderColor = VipTextSecondary,
                    unfocusedPlaceholderColor = VipTextMuted
                ),
                shape = RoundedCornerShape(20.dp),
                maxLines = 3
            )

            if (inputText.isBlank()) {
                IconButton(
                    onClick = {
                        repository.sendMessage("🎤 [Voice Note Sample • 0:15]")
                        coroutineScope.launch {
                            listState.animateScrollToItem(messages.size)
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(VipPurpleContainer)
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Send Voice Note",
                        tint = VipGoldPrimary
                    )
                }
            } else {
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            repository.sendMessage(inputText.trim())
                            inputText = ""
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.size)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(VipGoldPrimary)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send Message",
                        tint = VipObsidianBg
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val isMe = message.isFromMe

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isMe) 16.dp else 4.dp,
                        bottomEnd = if (isMe) 4.dp else 16.dp
                    )
                )
                .background(if (isMe) VipPurpleContainer else VipDarkCardElevated)
                .border(
                    width = 1.dp,
                    color = if (isMe) VipPurplePrimary.copy(alpha = 0.6f) else VipBorderDark,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isMe) 16.dp else 4.dp,
                        bottomEnd = if (isMe) 4.dp else 16.dp
                    )
                )
                .padding(horizontal = 14.dp, vertical = 10.dp)
                .fillMaxWidth(0.78f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (message.mediaType == MessageMediaType.AUDIO) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(VipGoldPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play Audio",
                            tint = VipObsidianBg,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = VipPurpleGlow,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = message.durationText ?: "0:30",
                        color = VipGoldChampagne,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = message.text,
                color = VipTextPrimary,
                fontSize = 13.5.sp,
                lineHeight = 18.sp
            )

            Text(
                text = message.timestamp,
                color = VipTextMuted,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
