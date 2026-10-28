package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VipRepository
import com.example.localization.vipString
import com.example.ui.components.VipAvatar
import com.example.ui.components.VipBadge
import com.example.ui.components.VipHonestyBanner
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDarkCard
import com.example.ui.theme.VipDarkSurface
import com.example.ui.theme.VipGoldChampagne
import com.example.ui.theme.VipGoldDark
import com.example.ui.theme.VipGoldGradient
import com.example.ui.theme.VipGoldPrimary
import com.example.ui.theme.VipObsidianBg
import com.example.ui.theme.VipPurpleContainer
import com.example.ui.theme.VipPurpleGlow
import com.example.ui.theme.VipPurplePrimary
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite

@Composable
fun ChatsScreen(
    repository: VipRepository,
    onOpenConversation: (String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val chats by repository.chats.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredChats = chats.filter {
        val matchesQuery = it.name.contains(searchQuery, ignoreCase = true) ||
                it.lastMessage.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "Unread" -> it.unreadCount > 0
            "Pinned" -> it.isPinned
            else -> true
        }
        matchesQuery && matchesFilter
    }

    Box(modifier = modifier.fillMaxSize().background(VipObsidianBg)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(vipString("search_chats"), fontSize = 13.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = VipPurpleGlow)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VipGoldPrimary,
                    unfocusedBorderColor = VipBorderDark,
                    focusedTextColor = VipTextPrimary,
                    unfocusedTextColor = VipTextPrimary,
                    unfocusedPlaceholderColor = VipTextMuted,
                    focusedPlaceholderColor = VipTextSecondary
                ),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            // Filter Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Unread", "Pinned").forEach { filter ->
                    val isSelected = filter == selectedFilter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(if (isSelected) VipPurpleContainer else VipDarkSurface)
                            .border(
                                1.dp,
                                if (isSelected) VipGoldPrimary else VipBorderDark,
                                RoundedCornerShape(100.dp)
                            )
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) VipGoldChampagne else VipTextSecondary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            VipHonestyBanner(
                text = "Chats Demo • Messages are simulated locally for Phase 1 UI preview. Firestore sync will be plugged in Phase 2."
            )

            // Chat List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredChats) { chat ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(VipDarkCard)
                            .border(1.dp, VipBorderDark, RoundedCornerShape(16.dp))
                            .clickable { onOpenConversation(chat.id, chat.name, chat.handle) }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        VipAvatar(name = chat.name, isOnline = chat.isOnline, size = 48)

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = chat.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    ),
                                    color = VipWhite,
                                    maxLines = 1
                                )
                                if (chat.isPinned) {
                                    Icon(
                                        imageVector = Icons.Default.PushPin,
                                        contentDescription = "Pinned",
                                        tint = VipGoldPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Text(
                                text = chat.handle,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = VipPurpleGlow
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = chat.lastMessage,
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp),
                                color = VipTextSecondary,
                                maxLines = 1
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = chat.timestamp,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
                                color = VipTextMuted
                            )
                            if (chat.unreadCount > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(VipGoldDark),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = chat.unreadCount.toString(),
                                        color = VipWhite,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // FAB to start new chat
        FloatingActionButton(
            onClick = {
                if (chats.isNotEmpty()) {
                    onOpenConversation(chats.first().id, chats.first().name, chats.first().handle)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = VipGoldPrimary,
            contentColor = VipObsidianBg
        ) {
            Icon(Icons.Default.Add, contentDescription = "New VIP Chat")
        }
    }
}
