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
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallMissed
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VipRepository
import com.example.localization.vipString
import com.example.model.CallDirection
import com.example.model.CallType
import com.example.ui.components.VipAvatar
import com.example.ui.components.VipHonestyBanner
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDanger
import com.example.ui.theme.VipDarkCard
import com.example.ui.theme.VipDarkSurface
import com.example.ui.theme.VipGoldChampagne
import com.example.ui.theme.VipGoldDark
import com.example.ui.theme.VipGoldPrimary
import com.example.ui.theme.VipObsidianBg
import com.example.ui.theme.VipPurpleContainer
import com.example.ui.theme.VipPurpleGlow
import com.example.ui.theme.VipSuccess
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite

@Composable
fun CallsScreen(
    repository: VipRepository,
    onStartCall: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val calls by repository.calls.collectAsState()
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredCalls = calls.filter {
        when (selectedFilter) {
            "Missed" -> it.direction == CallDirection.MISSED
            "Video" -> it.type == CallType.VIDEO
            "Voice" -> it.type == CallType.VOICE
            else -> true
        }
    }

    Box(modifier = modifier.fillMaxSize().background(VipObsidianBg)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VIP CALL NETWORK",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = VipGoldChampagne
                )
                Text(
                    text = "HD Voice & 4K Video",
                    style = MaterialTheme.typography.labelSmall,
                    color = VipPurpleGlow
                )
            }

            // Honesty Banner
            VipHonestyBanner(
                text = vipString("honest_call_notice")
            )

            // Filter Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Missed", "Video", "Voice").forEach { filter ->
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

            // Call history list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredCalls) { call ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(VipDarkCard)
                            .border(1.dp, VipBorderDark, RoundedCornerShape(16.dp))
                            .clickable { onStartCall(call.contactName, call.type == CallType.VIDEO) }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        VipAvatar(name = call.contactName, size = 46)

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = call.contactName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.5.sp
                                ),
                                color = VipWhite
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                val directionIcon = when (call.direction) {
                                    CallDirection.INCOMING -> Icons.AutoMirrored.Filled.CallReceived
                                    CallDirection.OUTGOING -> Icons.AutoMirrored.Filled.CallMade
                                    CallDirection.MISSED -> Icons.AutoMirrored.Filled.CallMissed
                                }
                                val directionTint = when (call.direction) {
                                    CallDirection.INCOMING -> VipSuccess
                                    CallDirection.OUTGOING -> VipGoldPrimary
                                    CallDirection.MISSED -> VipDanger
                                }

                                Icon(
                                    imageVector = directionIcon,
                                    contentDescription = null,
                                    tint = directionTint,
                                    modifier = Modifier.size(14.dp)
                                )

                                Text(
                                    text = "${call.timestamp} • ${call.duration}",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                    color = if (call.direction == CallDirection.MISSED) VipDanger else VipTextSecondary
                                )
                            }
                        }

                        // Call back button
                        IconButton(
                            onClick = { onStartCall(call.contactName, call.type == CallType.VIDEO) },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(VipPurpleContainer)
                                .border(1.dp, VipPurpleGlow.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(
                                imageVector = if (call.type == CallType.VIDEO) Icons.Default.Videocam else Icons.Default.Call,
                                contentDescription = "Call back",
                                tint = VipGoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // FAB to start new call
        FloatingActionButton(
            onClick = {
                if (calls.isNotEmpty()) {
                    onStartCall(calls.first().contactName, false)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = VipGoldPrimary,
            contentColor = VipObsidianBg
        ) {
            Icon(Icons.Default.PhoneInTalk, contentDescription = "Start VIP Call")
        }
    }
}
