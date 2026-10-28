package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VipRepository
import com.example.localization.vipString
import com.example.model.ScreenTab
import com.example.ui.components.VipAvatar
import com.example.ui.components.VipBadge
import com.example.ui.components.VipHonestyBanner
import com.example.ui.components.VipStatCard
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDarkCard
import com.example.ui.theme.VipDarkCardElevated
import com.example.ui.theme.VipDarkSurface
import com.example.ui.theme.VipGoldChampagne
import com.example.ui.theme.VipGoldDark
import com.example.ui.theme.VipGoldPrimary
import com.example.ui.theme.VipHeroGradient
import com.example.ui.theme.VipObsidianBg
import com.example.ui.theme.VipPurpleContainer
import com.example.ui.theme.VipPurpleDeep
import com.example.ui.theme.VipPurpleGlow
import com.example.ui.theme.VipPurplePrimary
import com.example.ui.theme.VipSuccess
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite

@Composable
fun HomeScreen(
    repository: VipRepository,
    onNavigateTab: (ScreenTab) -> Unit,
    onOpenConversation: (String, String, String) -> Unit,
    onStartCall: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val user by repository.currentUser.collectAsState()
    val chats by repository.chats.collectAsState()
    val calls by repository.calls.collectAsState()
    val ubaoniPosts by repository.ubaoniPosts.collectAsState()
    val groups by repository.groups.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VipObsidianBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Honesty Banner
        VipHonestyBanner(
            text = "Phase 1 Native Preview • Complete UI, responsive navigation, and localization architecture ready for backend integration."
        )

        // Hero VIP Membership Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(VipHeroGradient)
                .border(1.5.dp, VipGoldPrimary.copy(alpha = 0.7f), RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(VipPurpleContainer)
                                .border(1.dp, VipGoldPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = null,
                                tint = VipGoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "VIP MEET",
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp,
                            color = VipGoldChampagne,
                            letterSpacing = 1.sp
                        )
                    }
                    VipBadge(text = user.vipTier)
                }

                Column {
                    Text(
                        text = user.fullName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = VipWhite
                    )
                    Text(
                        text = user.handle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = VipPurpleGlow
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = vipString("tagline"),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = VipGoldPrimary
                        )
                        Text(
                            text = user.location,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = VipTextSecondary
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(VipSuccess)
                        )
                        Text(
                            text = "Connected Worldwide",
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = VipTextSecondary
                        )
                    }
                }
            }
        }

        // Quick Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            VipStatCard(
                title = vipString("tab_chats"),
                value = chats.size.toString(),
                subtitle = "Active Threads",
                modifier = Modifier.weight(1f)
            )
            VipStatCard(
                title = vipString("tab_calls"),
                value = calls.size.toString(),
                subtitle = "Call Records",
                modifier = Modifier.weight(1f)
            )
            VipStatCard(
                title = vipString("tab_ubaoni"),
                value = ubaoniPosts.size.toString(),
                subtitle = "Live Posts",
                modifier = Modifier.weight(1f)
            )
        }

        // Quick Action Shortcuts Grid
        Text(
            text = "VIP QUICK ACTIONS",
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = VipGoldChampagne
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ActionLauncherTile(
                title = vipString("start_chat"),
                icon = Icons.Default.Chat,
                onClick = { onNavigateTab(ScreenTab.CHATS) },
                modifier = Modifier.weight(1f)
            )
            ActionLauncherTile(
                title = vipString("start_call"),
                icon = Icons.Default.Call,
                onClick = { onNavigateTab(ScreenTab.CALLS) },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ActionLauncherTile(
                title = vipString("post_to_ubaoni"),
                icon = Icons.Default.Dashboard,
                onClick = { onNavigateTab(ScreenTab.UBAONI) },
                modifier = Modifier.weight(1f)
            )
            ActionLauncherTile(
                title = vipString("create_group"),
                icon = Icons.Default.Groups,
                onClick = { onNavigateTab(ScreenTab.GROUPS) },
                modifier = Modifier.weight(1f)
            )
        }

        // Ubaoni Highlights / Stories Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "UBAONI (ON THE BOARD)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = VipGoldChampagne
                )
                Text(
                    text = "Story-style dispatches & executive notes",
                    style = MaterialTheme.typography.bodySmall,
                    color = VipTextMuted
                )
            }
            Text(
                text = "View All",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = VipGoldPrimary,
                modifier = Modifier.clickable { onNavigateTab(ScreenTab.UBAONI) }
            )
        }

        // Horizontal Story Cards
        val storyScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(storyScrollState),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ubaoniPosts.take(4).forEach { post ->
                Card(
                    modifier = Modifier
                        .width(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavigateTab(ScreenTab.UBAONI) },
                    colors = CardDefaults.cardColors(containerColor = VipDarkCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, VipBorderDark)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            VipAvatar(name = post.authorName, size = 32)
                            Column {
                                Text(
                                    text = post.authorName,
                                    color = VipWhite,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                                Text(
                                    text = post.timeAgo,
                                    color = VipTextMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Text(
                            text = post.contentText,
                            style = MaterialTheme.typography.bodySmall,
                            color = VipTextSecondary,
                            maxLines = 3,
                            fontSize = 11.5.sp,
                            lineHeight = 15.sp
                        )

                        if (post.mediaCaption != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(VipPurpleContainer)
                                    .padding(horizontal = 8.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = "★ ${post.mediaCaption}",
                                    fontSize = 10.sp,
                                    color = VipGoldChampagne,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Recent VIP Conversations Shortcut
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "RECENT CONVERSATIONS",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                ),
                color = VipGoldChampagne
            )
            Text(
                text = "Open Chats",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = VipGoldPrimary,
                modifier = Modifier.clickable { onNavigateTab(ScreenTab.CHATS) }
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            chats.take(3).forEach { chat ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(VipDarkCard)
                        .border(1.dp, VipBorderDark, RoundedCornerShape(14.dp))
                        .clickable { onOpenConversation(chat.id, chat.name, chat.handle) }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    VipAvatar(name = chat.name, isOnline = chat.isOnline, size = 44)
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = chat.name,
                                color = VipWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            if (chat.isPinned) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(VipGoldDark.copy(alpha = 0.2f))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text("PINNED", fontSize = 8.sp, color = VipGoldPrimary, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Text(
                            text = chat.lastMessage,
                            color = VipTextSecondary,
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                    Text(
                        text = chat.timestamp,
                        color = VipTextMuted,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ActionLauncherTile(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(VipDarkCardElevated)
            .border(1.dp, VipBorderDark, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(VipPurpleContainer)
                .border(1.dp, VipGoldPrimary.copy(alpha = 0.6f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = VipGoldPrimary,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = VipGoldChampagne,
            maxLines = 2
        )
    }
}
