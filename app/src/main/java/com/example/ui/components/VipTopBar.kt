package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.LocalVipLocale
import com.example.localization.vipString
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDarkSurface
import com.example.ui.theme.VipGoldChampagne
import com.example.ui.theme.VipGoldDark
import com.example.ui.theme.VipGoldPrimary
import com.example.ui.theme.VipPurpleContainer
import com.example.ui.theme.VipPurpleGlow
import com.example.ui.theme.VipPurplePrimary
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary

@Composable
fun VipTopBar(
    onLanguageClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val localeManager = LocalVipLocale.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(VipDarkSurface)
            .border(
                width = 0.5.dp,
                color = VipBorderDark,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Brand & Tagline
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(VipPurpleContainer)
                    .border(1.5.dp, VipGoldPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = "VIP Global",
                    tint = VipGoldPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "VIP MEET",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        ),
                        color = VipGoldChampagne
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(VipGoldDark.copy(alpha = 0.3f))
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "GLOBAL",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Black,
                            color = VipGoldPrimary
                        )
                    }
                }
                Text(
                    text = vipString("tagline"),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = VipPurpleGlow
                )
            }
        }

        // Action Buttons: Language Selector Pill & Profile Shortcut
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Language Switcher Pill
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(VipPurpleContainer)
                    .border(1.dp, VipPurpleGlow.copy(alpha = 0.4f), RoundedCornerShape(100.dp))
                    .clickable(onClick = onLanguageClick)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = "Language",
                    tint = VipGoldPrimary,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = localeManager.currentLanguageCode.uppercase(),
                    color = VipGoldChampagne,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Profile Avatar Quick Access
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(VipPurplePrimary.copy(alpha = 0.3f))
                    .border(1.5.dp, VipGoldPrimary, CircleShape)
                    .clickable(onClick = onProfileClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "KM",
                    color = VipGoldChampagne,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
