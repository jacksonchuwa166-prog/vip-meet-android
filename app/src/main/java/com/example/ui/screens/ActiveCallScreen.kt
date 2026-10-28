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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.VipAvatar
import com.example.ui.components.VipBadge
import com.example.ui.components.VipHonestyBanner
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDanger
import com.example.ui.theme.VipDarkCard
import com.example.ui.theme.VipDarkCardElevated
import com.example.ui.theme.VipGoldChampagne
import com.example.ui.theme.VipGoldPrimary
import com.example.ui.theme.VipHeroGradient
import com.example.ui.theme.VipObsidianBg
import com.example.ui.theme.VipPurpleContainer
import com.example.ui.theme.VipPurpleGlow
import com.example.ui.theme.VipPurplePrimary
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite

@Composable
fun ActiveCallScreen(
    contactName: String,
    isVideo: Boolean,
    onEndCall: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onEndCall() }

    var isMuted by remember { mutableStateOf(false) }
    var isVideoEnabled by remember { mutableStateOf(isVideo) }
    var isSpeakerOn by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VipObsidianBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Security & Call Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            VipBadge(text = if (isVideoEnabled) "VIP 4K VIDEO CONNECT" else "VIP HD VOICE CONNECT")

            Text(
                text = contactName,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                ),
                color = VipWhite
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = VipGoldPrimary,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "End-to-End Encrypted Tunnel",
                    style = MaterialTheme.typography.bodySmall,
                    color = VipPurpleGlow
                )
            }
        }

        // Center Visual: Simulated Video Frame or Avatar with Pulse
        if (isVideoEnabled) {
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(VipHeroGradient)
                    .border(2.dp, VipGoldPrimary, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    VipAvatar(name = contactName, size = 80)
                    Text(
                        text = "VIP Ultra-HD Stream",
                        color = VipGoldChampagne,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Camera Active • Phase 1 UI Preview",
                        color = VipTextSecondary,
                        fontSize = 11.sp
                    )
                }
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(VipPurpleContainer)
                        .border(3.dp, VipGoldPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    VipAvatar(name = contactName, size = 130)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = VipGoldPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "00:24",
                        style = MaterialTheme.typography.titleMedium,
                        color = VipGoldChampagne,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Honesty notice
        VipHonestyBanner(
            text = "Demo Screen • Call signaling & WebRTC media relay are intentionally separate and will be wired in Phase 2.",
            isSecurityNote = true
        )

        // Bottom Call Control Buttons
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mic Mute Toggle
                CallControlButton(
                    icon = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                    label = if (isMuted) "Unmute" else "Mute",
                    isActive = isMuted,
                    onClick = { isMuted = !isMuted }
                )

                // Video Toggle
                CallControlButton(
                    icon = if (isVideoEnabled) Icons.Default.Videocam else Icons.Default.VideocamOff,
                    label = if (isVideoEnabled) "Cam Off" else "Cam On",
                    isActive = !isVideoEnabled,
                    onClick = { isVideoEnabled = !isVideoEnabled }
                )

                // Speaker Toggle
                CallControlButton(
                    icon = Icons.Default.VolumeUp,
                    label = if (isSpeakerOn) "Speaker" else "Earpiece",
                    isActive = isSpeakerOn,
                    onClick = { isSpeakerOn = !isSpeakerOn }
                )

                // Flip Camera
                CallControlButton(
                    icon = Icons.Default.Cameraswitch,
                    label = "Flip",
                    isActive = false,
                    onClick = { /* Switch camera placeholder */ }
                )
            }

            // End Call Button
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(VipDanger)
                    .clickable(onClick = onEndCall),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CallEnd,
                    contentDescription = "End Call",
                    tint = VipWhite,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun CallControlButton(
    icon: ImageVector,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(if (isActive) VipPurplePrimary else VipDarkCardElevated)
                .border(
                    width = 1.dp,
                    color = if (isActive) VipGoldPrimary else VipBorderDark,
                    shape = CircleShape
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isActive) VipWhite else VipGoldChampagne,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label,
            fontSize = 11.sp,
            color = VipTextSecondary
        )
    }
}
