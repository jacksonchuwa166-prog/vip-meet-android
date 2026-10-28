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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VipRepository
import com.example.localization.vipString
import com.example.model.GroupItem
import com.example.ui.components.VipBadge
import com.example.ui.components.VipHonestyBanner
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDarkCard
import com.example.ui.theme.VipDarkSurface
import com.example.ui.theme.VipGoldChampagne
import com.example.ui.theme.VipGoldDark
import com.example.ui.theme.VipGoldPrimary
import com.example.ui.theme.VipObsidianBg
import com.example.ui.theme.VipPurpleContainer
import com.example.ui.theme.VipPurpleGlow
import com.example.ui.theme.VipPurplePrimary
import com.example.ui.theme.VipSuccess
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite

@Composable
fun GroupsScreen(
    repository: VipRepository,
    onOpenGroupChat: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val groups by repository.groups.collectAsState()

    Box(modifier = modifier.fillMaxSize().background(VipObsidianBg)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "VIP LOUNGES & GROUPS",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = VipGoldChampagne
                    )
                    Text(
                        text = "Curated worldwide hubs & syndicates",
                        style = MaterialTheme.typography.bodySmall,
                        color = VipPurpleGlow
                    )
                }
                VipBadge(text = "${groups.size} LOUNGES")
            }

            VipHonestyBanner(
                text = "Lounges Demo • Multi-user real-time channel sync and membership management will be configured with Firestore in Phase 2."
            )

            // Groups list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(groups) { group ->
                    GroupCard(
                        group = group,
                        onToggleJoin = { repository.toggleGroupJoin(group.id) },
                        onOpen = { onOpenGroupChat(group.id, group.name) }
                    )
                }
            }
        }

        // FAB to create lounge
        FloatingActionButton(
            onClick = { /* Create group modal trigger */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = VipGoldPrimary,
            contentColor = VipObsidianBg
        ) {
            Icon(Icons.Default.Add, contentDescription = "Create Lounge")
        }
    }
}

@Composable
fun GroupCard(
    group: GroupItem,
    onToggleJoin: () -> Unit,
    onOpen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(VipDarkCard)
            .border(1.dp, VipBorderDark, RoundedCornerShape(16.dp))
            .clickable(onClick = onOpen)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(VipPurpleContainer)
                        .border(1.5.dp, VipGoldPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (group.isPrivate) Icons.Default.Lock else Icons.Default.Public,
                        contentDescription = null,
                        tint = VipGoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        color = VipWhite
                    )
                    Text(
                        text = "${group.category} • ${group.memberCount} VIP Members",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = VipPurpleGlow
                    )
                }
            }

            // Join / Joined Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(if (group.isJoined) VipPurpleContainer else VipGoldDark.copy(alpha = 0.25f))
                    .border(
                        1.dp,
                        if (group.isJoined) VipSuccess else VipGoldPrimary,
                        RoundedCornerShape(100.dp)
                    )
                    .clickable(onClick = onToggleJoin)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (group.isJoined) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = VipSuccess,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(
                        text = if (group.isJoined) "Joined" else "Join",
                        color = if (group.isJoined) VipSuccess else VipGoldChampagne,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Text(
            text = group.description,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.5.sp),
            color = VipTextSecondary
        )
    }
}
