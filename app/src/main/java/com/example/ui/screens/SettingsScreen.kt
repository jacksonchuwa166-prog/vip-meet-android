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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.localization.AVAILABLE_LANGUAGES
import com.example.localization.LocalVipLocale
import com.example.localization.vipString
import com.example.ui.components.VipBadge
import com.example.ui.components.VipHonestyBanner
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDanger
import com.example.ui.theme.VipDarkCard
import com.example.ui.theme.VipDarkCardElevated
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
fun SettingsScreen(
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    val localeManager = LocalVipLocale.current
    val scrollState = rememberScrollState()

    var showLanguageModal by remember { mutableStateOf(false) }
    var showBlockedModal by remember { mutableStateOf(false) }
    var showReportsModal by remember { mutableStateOf(false) }
    var showVersionModal by remember { mutableStateOf(false) }

    var notificationsEnabled by remember { mutableStateOf(true) }
    var callRingtoneEnabled by remember { mutableStateOf(true) }
    var readReceiptsEnabled by remember { mutableStateOf(true) }
    var endToEndVerified by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VipObsidianBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = vipString("tab_settings"),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                ),
                color = VipGoldChampagne
            )
            VipBadge(text = "VIP PROTOCOL")
        }

        VipHonestyBanner(
            text = "Settings & Localization • Switch between 10 planned world languages instantly below. Core UI keys translated for Phase 1.",
            isSecurityNote = false
        )

        // 1. Language Section
        SettingsSectionCard(title = vipString("language_select")) {
            SettingsClickableRow(
                icon = Icons.Default.Language,
                title = "App Language",
                subtitle = "Active: ${AVAILABLE_LANGUAGES.find { it.code == localeManager.currentLanguageCode }?.nativeName ?: "English"}",
                trailingText = "${AVAILABLE_LANGUAGES.size} Languages",
                onClick = { showLanguageModal = true }
            )
        }

        // 2. Account & Identity
        SettingsSectionCard(title = vipString("account_settings")) {
            SettingsClickableRow(
                icon = Icons.Default.Person,
                title = "VIP Credentials",
                subtitle = "Manage verified email and phone number",
                onClick = { /* Demo click */ }
            )
            HorizontalDivider(color = VipBorderDark, thickness = 0.5.dp)
            SettingsClickableRow(
                icon = Icons.Default.Lock,
                title = "Security Key & PIN",
                subtitle = "Biometric passkey and hardware auth",
                onClick = { /* Demo click */ }
            )
        }

        // 3. Privacy & Security
        SettingsSectionCard(title = vipString("privacy_settings")) {
            SettingsToggleRow(
                icon = Icons.Default.Security,
                title = "End-to-End Encryption",
                subtitle = "Quantum-resistant encryption protocol",
                checked = endToEndVerified,
                onCheckedChange = { endToEndVerified = it }
            )
            HorizontalDivider(color = VipBorderDark, thickness = 0.5.dp)
            SettingsToggleRow(
                icon = Icons.Default.Check,
                title = "Read Receipts",
                subtitle = "Allow VIP contacts to see message read status",
                checked = readReceiptsEnabled,
                onCheckedChange = { readReceiptsEnabled = it }
            )
        }

        // 4. Notifications & Sounds
        SettingsSectionCard(title = vipString("notifications_settings")) {
            SettingsToggleRow(
                icon = Icons.Default.Notifications,
                title = "Push Notifications",
                subtitle = "VIP instant chat and board alerts",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            HorizontalDivider(color = VipBorderDark, thickness = 0.5.dp)
            SettingsToggleRow(
                icon = Icons.Default.Notifications,
                title = "VIP Call Ringtones",
                subtitle = "Executive chime for incoming calls",
                checked = callRingtoneEnabled,
                onCheckedChange = { callRingtoneEnabled = it }
            )
        }

        // 5. Blocked Users
        SettingsSectionCard(title = vipString("blocked_users")) {
            SettingsClickableRow(
                icon = Icons.Default.Block,
                title = "Manage Blocked Contacts",
                subtitle = "View and unblock restricted members",
                trailingText = "0 Blocked",
                onClick = { showBlockedModal = true }
            )
        }

        // 6. Reports & Safety
        SettingsSectionCard(title = vipString("reports_safety")) {
            SettingsClickableRow(
                icon = Icons.Default.ReportProblem,
                title = "Safety & Reports Center",
                subtitle = "Confidential reporting & VIP trust charter",
                onClick = { showReportsModal = true }
            )
        }

        // 7. App Version & Update Information
        SettingsSectionCard(title = vipString("app_version")) {
            SettingsClickableRow(
                icon = Icons.Default.SystemUpdate,
                title = "VIP MEET v1.0.0-phase1",
                subtitle = "Target: Google Play & Android Direct APK",
                trailingText = "Details",
                onClick = { showVersionModal = true }
            )
        }

        // Sign Out / Switch Profile
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(VipDarkCard)
                .border(1.dp, VipDanger.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                .clickable(onClick = onSignOut)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sign Out from VIP MEET",
                color = VipDanger,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Modal: Language Selector
    if (showLanguageModal) {
        AlertDialog(
            onDismissRequest = { showLanguageModal = false },
            containerColor = VipDarkSurface,
            title = {
                Text("Select Language (Lugha)", color = VipGoldChampagne, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AVAILABLE_LANGUAGES.forEach { lang ->
                        val isSelected = lang.code == localeManager.currentLanguageCode
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) VipPurpleContainer else VipDarkCard)
                                .border(
                                    1.dp,
                                    if (isSelected) VipGoldPrimary else VipBorderDark,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    localeManager.setLanguage(lang.code)
                                    showLanguageModal = false
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = lang.nativeName,
                                    color = VipWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = lang.englishName,
                                    color = VipTextMuted,
                                    fontSize = 11.sp
                                )
                            }
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = VipGoldPrimary)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageModal = false }) {
                    Text("Close", color = VipGoldChampagne)
                }
            }
        )
    }

    // Modal: Blocked Users
    if (showBlockedModal) {
        AlertDialog(
            onDismissRequest = { showBlockedModal = false },
            containerColor = VipDarkSurface,
            title = { Text("Blocked Contacts", color = VipGoldChampagne) },
            text = {
                Text(
                    "You currently have no blocked contacts on VIP MEET. All communications remain protected by your security tier.",
                    color = VipTextSecondary
                )
            },
            confirmButton = {
                TextButton(onClick = { showBlockedModal = false }) {
                    Text("OK", color = VipGoldChampagne)
                }
            }
        )
    }

    // Modal: Reports & Safety
    if (showReportsModal) {
        AlertDialog(
            onDismissRequest = { showReportsModal = false },
            containerColor = VipDarkSurface,
            title = { Text("Safety & Reports Center", color = VipGoldChampagne) },
            text = {
                Text(
                    "VIP MEET enforces an uncompromising code of mutual respect and integrity under our Global Charter. Any misconduct or impersonation can be confidentially submitted for review by the VIP Governance Board.",
                    color = VipTextSecondary,
                    lineHeight = 18.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showReportsModal = false }) {
                    Text("Understood", color = VipGoldChampagne)
                }
            }
        )
    }

    // Modal: Version & APK Info
    if (showVersionModal) {
        AlertDialog(
            onDismissRequest = { showVersionModal = false },
            containerColor = VipDarkSurface,
            title = { Text("App Architecture & Release", color = VipGoldChampagne) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("App: DIARY V.I.P MEET (VIP MEET)", color = VipWhite, fontWeight = FontWeight.Bold)
                    Text("Tagline: One World. One Connection.", color = VipPurpleGlow)
                    Text("Platform: Genuine Native Android (Kotlin + Jetpack Compose)", color = VipGoldChampagne)
                    Text("Package: com.aistudio.vipmeet.kxpqzm", color = VipTextSecondary, fontSize = 12.sp)
                    Text("Phase: Phase 1 (UI, Navigation, Responsive Layouts)", color = VipTextSecondary, fontSize = 12.sp)
                    Text("Distribution: Ready for Android Test APK build and Play Store packaging", color = VipSuccess, fontSize = 12.sp)
                }
            },
            confirmButton = {
                TextButton(onClick = { showVersionModal = false }) {
                    Text("Done", color = VipGoldChampagne)
                }
            }
        )
    }
}

@Composable
fun SettingsSectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = VipGoldChampagne,
            modifier = Modifier.padding(start = 4.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(VipDarkCard)
                .border(1.dp, VipBorderDark, RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SettingsClickableRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    trailingText: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(VipPurpleContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = VipGoldPrimary, modifier = Modifier.size(18.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = VipWhite, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(subtitle, color = VipTextSecondary, fontSize = 11.5.sp)
        }

        if (trailingText != null) {
            Text(trailingText, color = VipGoldChampagne, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = VipTextMuted, modifier = Modifier.size(18.dp))
    }
}

@Composable
fun SettingsToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(VipPurpleContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = VipGoldPrimary, modifier = Modifier.size(18.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = VipWhite, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(subtitle, color = VipTextSecondary, fontSize = 11.5.sp)
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = VipObsidianBg,
                checkedTrackColor = VipGoldPrimary,
                uncheckedThumbColor = VipTextSecondary,
                uncheckedTrackColor = VipDarkCardElevated
            )
        )
    }
}
