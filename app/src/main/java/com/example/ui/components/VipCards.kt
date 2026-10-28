package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDarkCard
import com.example.ui.theme.VipDarkCardElevated
import com.example.ui.theme.VipGoldChampagne
import com.example.ui.theme.VipGoldDark
import com.example.ui.theme.VipGoldGradient
import com.example.ui.theme.VipGoldPrimary
import com.example.ui.theme.VipObsidianBg
import com.example.ui.theme.VipPurplePrimary
import com.example.ui.theme.VipSuccess
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite

@Composable
fun VipBadge(
    text: String,
    modifier: Modifier = Modifier,
    isGold: Boolean = true
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(if (isGold) VipGoldDark.copy(alpha = 0.25f) else VipPurplePrimary.copy(alpha = 0.25f))
            .border(
                1.dp,
                if (isGold) VipGoldPrimary.copy(alpha = 0.8f) else VipPurplePrimary.copy(alpha = 0.8f),
                RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 8.dp, vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (isGold) VipGoldPrimary else VipGoldChampagne,
            modifier = Modifier.size(11.dp)
        )
        Text(
            text = text,
            color = if (isGold) VipGoldChampagne else VipWhite,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
fun VipAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Int = 48,
    isOnline: Boolean = false,
    hasGoldRing: Boolean = true
) {
    val initials = name.split(" ")
        .filter { it.isNotEmpty() }
        .take(2)
        .map { it.first().uppercase() }
        .joinToString("")
        .ifEmpty { "VIP" }

    Box(
        modifier = modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(VipDarkCardElevated)
                .border(
                    width = if (hasGoldRing) 2.dp else 1.dp,
                    color = if (hasGoldRing) VipGoldPrimary else VipBorderDark,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                color = VipGoldChampagne,
                fontWeight = FontWeight.Bold,
                fontSize = (size * 0.38).sp
            )
        }

        if (isOnline) {
            Box(
                modifier = Modifier
                    .size((size * 0.28).dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(VipSuccess)
                    .border(2.dp, VipObsidianBg, CircleShape)
            )
        }
    }
}

@Composable
fun VipGoldButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (enabled) VipGoldGradient else androidx.compose.ui.graphics.Brush.linearGradient(listOf(Color.Gray, Color.DarkGray)))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = VipObsidianBg,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = text,
                color = VipObsidianBg,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                letterSpacing = 0.3.sp
            )
        }
    }
}

@Composable
fun VipOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, VipGoldPrimary.copy(alpha = 0.7f)),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = VipGoldChampagne
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = VipGoldPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun VipStatCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(VipDarkCard)
            .border(1.dp, VipBorderDark, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = VipTextMuted
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = VipGoldPrimary,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = VipTextSecondary,
            fontSize = 11.sp
        )
    }
}
