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
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.ui.components.VipGoldButton
import com.example.ui.components.VipHonestyBanner
import com.example.ui.components.VipOutlineButton
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
import com.example.ui.theme.VipPurpleGlow
import com.example.ui.theme.VipPurplePrimary
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite

@Composable
fun ProfileScreen(
    repository: VipRepository,
    onNavigateSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user by repository.currentUser.collectAsState()
    val scrollState = rememberScrollState()
    var showEditDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VipObsidianBg)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Honesty Banner
        VipHonestyBanner(
            text = "Profile Demo • Cloud profile synchronization and KYC verification channels will be integrated in Phase 2."
        )

        // Luxury VIP Executive Pass Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(VipHeroGradient)
                .border(2.dp, VipGoldPrimary, RoundedCornerShape(22.dp))
                .padding(20.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "VIP MEET PASSPORT",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 2.sp
                            ),
                            color = VipGoldPrimary
                        )
                        Text(
                            text = vipString("tagline"),
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = VipPurpleGlow
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.QrCode2,
                        contentDescription = "Pass QR",
                        tint = VipGoldChampagne,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    VipAvatar(name = user.fullName, size = 64)
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
                        Spacer(modifier = Modifier.height(4.dp))
                        VipBadge(text = user.vipTier)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(VipBorderDark)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("LOCATION", fontSize = 9.sp, color = VipTextMuted, fontWeight = FontWeight.Bold)
                        Text(user.location, fontSize = 12.sp, color = VipGoldChampagne, fontWeight = FontWeight.SemiBold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("STATUS", fontSize = 9.sp, color = VipTextMuted, fontWeight = FontWeight.Bold)
                        Text(user.joinYear, fontSize = 12.sp, color = VipGoldChampagne, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // Action Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            VipGoldButton(
                text = "Edit Profile",
                icon = Icons.Default.Edit,
                onClick = { showEditDialog = true },
                modifier = Modifier.weight(1f)
            )
            VipOutlineButton(
                text = "Share Pass",
                icon = Icons.Default.Share,
                onClick = { /* Share dialog placeholder */ },
                modifier = Modifier.weight(1f)
            )
        }

        // About & Bio
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(VipDarkCard)
                .border(1.dp, VipBorderDark, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "EXECUTIVE BIO",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = VipGoldChampagne
            )
            Text(
                text = user.bio,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.5.sp,
                    lineHeight = 19.sp
                ),
                color = VipTextPrimary
            )
        }

        // Contact Credentials Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(VipDarkCard)
                .border(1.dp, VipBorderDark, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "VERIFIED CREDENTIALS",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = VipGoldChampagne
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Email, contentDescription = null, tint = VipPurpleGlow, modifier = Modifier.size(18.dp))
                Text(user.email, color = VipTextPrimary, fontSize = 13.sp)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Phone, contentDescription = null, tint = VipGoldPrimary, modifier = Modifier.size(18.dp))
                Text(user.phone, color = VipTextPrimary, fontSize = 13.sp)
            }
        }

        // Edit Profile Dialog
        if (showEditDialog) {
            var editName by remember { mutableStateOf(user.fullName) }
            var editBio by remember { mutableStateOf(user.bio) }
            var editLocation by remember { mutableStateOf(user.location) }

            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                containerColor = VipDarkSurface,
                title = {
                    Text("Edit VIP Profile", color = VipGoldChampagne, fontWeight = FontWeight.Bold)
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = editName,
                            onValueChange = { editName = it },
                            label = { Text("Display Name") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = VipGoldPrimary,
                                unfocusedBorderColor = VipBorderDark,
                                focusedTextColor = VipTextPrimary,
                                unfocusedTextColor = VipTextPrimary
                            )
                        )
                        OutlinedTextField(
                            value = editBio,
                            onValueChange = { editBio = it },
                            label = { Text("Executive Bio") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = VipGoldPrimary,
                                unfocusedBorderColor = VipBorderDark,
                                focusedTextColor = VipTextPrimary,
                                unfocusedTextColor = VipTextPrimary
                            )
                        )
                        OutlinedTextField(
                            value = editLocation,
                            onValueChange = { editLocation = it },
                            label = { Text("Location") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = VipGoldPrimary,
                                unfocusedBorderColor = VipBorderDark,
                                focusedTextColor = VipTextPrimary,
                                unfocusedTextColor = VipTextPrimary
                            )
                        )
                    }
                },
                confirmButton = {
                    VipGoldButton(
                        text = "Save",
                        onClick = {
                            repository.updateProfile(editName, editBio, editLocation)
                            showEditDialog = false
                        }
                    )
                },
                dismissButton = {
                    TextButton(onClick = { showEditDialog = false }) {
                        Text("Cancel", color = VipTextMuted)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
