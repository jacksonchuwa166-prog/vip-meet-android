package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// VIP MEET Brand Identity: Obsidian Black, Regal Purple, and Luxury Gold
val VipObsidianBg = Color(0xFF09080D)
val VipDarkSurface = Color(0xFF13101B)
val VipDarkCard = Color(0xFF1A1626)
val VipDarkCardElevated = Color(0xFF241F33)
val VipBorderDark = Color(0xFF352D48)

// Purples
val VipPurplePrimary = Color(0xFF9333EA)
val VipPurpleLight = Color(0xFFA855F7)
val VipPurpleGlow = Color(0xFFC084FC)
val VipPurpleDeep = Color(0xFF581C87)
val VipPurpleContainer = Color(0xFF2A1B40)

// Golds
val VipGoldPrimary = Color(0xFFF59E0B)
val VipGoldLight = Color(0xFFFDE68A)
val VipGoldDark = Color(0xFFD97706)
val VipGoldGlow = Color(0xFFFBBF24)
val VipGoldChampagne = Color(0xFFFEF3C7)

// Text and Accents
val VipTextPrimary = Color(0xFFF8FAFC)
val VipTextSecondary = Color(0xFF94A3B8)
val VipTextMuted = Color(0xFF64748B)
val VipWhite = Color(0xFFFFFFFF)
val VipSuccess = Color(0xFF10B981)
val VipDanger = Color(0xFFEF4444)
val VipWarning = Color(0xFFF59E0B)

// Luxury Gradients
val VipGoldGradient = Brush.horizontalGradient(
    listOf(VipGoldDark, VipGoldPrimary, VipGoldLight, VipGoldPrimary)
)

val VipPurpleGradient = Brush.linearGradient(
    listOf(VipPurplePrimary, VipPurpleDeep)
)

val VipCardGradient = Brush.verticalGradient(
    listOf(Color(0xFF221C30), Color(0xFF14111D))
)

val VipHeroGradient = Brush.verticalGradient(
    listOf(Color(0xFF2D174D), Color(0xFF110E1A), Color(0xFF09080D))
)
