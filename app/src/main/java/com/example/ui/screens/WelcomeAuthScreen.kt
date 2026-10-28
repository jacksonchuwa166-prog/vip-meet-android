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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.vipString
import com.example.model.AuthMode
import com.example.ui.components.VipGoldButton
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
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary

@Composable
fun WelcomeAuthScreen(
    initialMode: AuthMode = AuthMode.SIGN_IN,
    onAuthSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var authTab by remember { mutableIntStateOf(if (initialMode == AuthMode.SIGN_IN) 0 else 1) }
    var registerWithEmail by remember { mutableStateOf(true) }

    var emailInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var fullNameInput by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val countryCodes = listOf(
        "+254" to "Kenya",
        "+1" to "USA / Canada",
        "+44" to "UK",
        "+971" to "UAE",
        "+33" to "France",
        "+49" to "Germany",
        "+255" to "Tanzania",
        "+234" to "Nigeria",
        "+86" to "China",
        "+91" to "India",
        "+81" to "Japan"
    )
    var selectedCountryCode by remember { mutableStateOf("+254") }
    var countryMenuExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(VipObsidianBg)
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Luxury Monogram Icon
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
                .background(VipPurpleContainer)
                .border(2.dp, VipGoldPrimary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Public,
                contentDescription = "VIP Global",
                tint = VipGoldPrimary,
                modifier = Modifier.size(44.dp)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "VIP MEET",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                ),
                color = VipGoldChampagne
            )
            Text(
                text = vipString("tagline"),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = VipPurpleGlow
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = vipString("welcome_sub"),
                style = MaterialTheme.typography.bodySmall,
                color = VipTextSecondary,
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 16.sp
            )
        }

        // Transparency / Honesty Banner
        VipHonestyBanner(
            text = vipString("honest_auth_notice"),
            isSecurityNote = true
        )

        // Sign In vs Register Tab
        TabRow(
            selectedTabIndex = authTab,
            containerColor = VipDarkSurface,
            contentColor = VipGoldPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[authTab]),
                    color = VipGoldPrimary,
                    height = 3.dp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, VipBorderDark, RoundedCornerShape(12.dp))
        ) {
            Tab(
                selected = authTab == 0,
                onClick = { authTab = 0 },
                text = {
                    Text(
                        text = vipString("sign_in"),
                        fontWeight = if (authTab == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (authTab == 0) VipGoldChampagne else VipTextMuted
                    )
                }
            )
            Tab(
                selected = authTab == 1,
                onClick = { authTab = 1 },
                text = {
                    Text(
                        text = vipString("register"),
                        fontWeight = if (authTab == 1) FontWeight.Bold else FontWeight.Normal,
                        color = if (authTab == 1) VipGoldChampagne else VipTextMuted
                    )
                }
            )
        }

        // Form Container
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(VipDarkCard)
                .border(1.dp, VipBorderDark, RoundedCornerShape(16.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (authTab == 1) {
                // Register: Choice between Email OR Phone Number
                Text(
                    text = vipString("choose_method"),
                    style = MaterialTheme.typography.labelMedium,
                    color = VipGoldChampagne,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(VipDarkSurface)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (registerWithEmail) VipPurpleContainer else VipDarkSurface)
                            .border(
                                1.dp,
                                if (registerWithEmail) VipGoldPrimary.copy(alpha = 0.6f) else VipBorderDark,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { registerWithEmail = true }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = null,
                                tint = if (registerWithEmail) VipGoldPrimary else VipTextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Email",
                                color = if (registerWithEmail) VipGoldChampagne else VipTextSecondary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (!registerWithEmail) VipPurpleContainer else VipDarkSurface)
                            .border(
                                1.dp,
                                if (!registerWithEmail) VipGoldPrimary.copy(alpha = 0.6f) else VipBorderDark,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { registerWithEmail = false }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = if (!registerWithEmail) VipGoldPrimary else VipTextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Phone",
                                color = if (!registerWithEmail) VipGoldChampagne else VipTextSecondary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Full Name for Registration
                OutlinedTextField(
                    value = fullNameInput,
                    onValueChange = { fullNameInput = it },
                    label = { Text("Full Name & Title") },
                    placeholder = { Text("e.g. Dr. Jane Doe") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VipGoldPrimary,
                        unfocusedBorderColor = VipBorderDark,
                        focusedLabelColor = VipGoldPrimary,
                        unfocusedLabelColor = VipTextSecondary,
                        focusedTextColor = VipTextPrimary,
                        unfocusedTextColor = VipTextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                if (registerWithEmail) {
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("VIP Email Address") },
                        placeholder = { Text("name@organization.com") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = VipPurpleGlow)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = VipGoldPrimary,
                            unfocusedBorderColor = VipBorderDark,
                            focusedLabelColor = VipGoldPrimary,
                            unfocusedLabelColor = VipTextSecondary,
                            focusedTextColor = VipTextPrimary,
                            unfocusedTextColor = VipTextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(VipDarkSurface)
                                .border(1.dp, VipBorderDark, RoundedCornerShape(12.dp))
                                .clickable { countryMenuExpanded = true }
                                .padding(horizontal = 12.dp, vertical = 16.dp)
                        ) {
                            Text(
                                text = selectedCountryCode,
                                color = VipGoldChampagne,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            DropdownMenu(
                                expanded = countryMenuExpanded,
                                onDismissRequest = { countryMenuExpanded = false }
                            ) {
                                countryCodes.forEach { (code, country) ->
                                    DropdownMenuItem(
                                        text = { Text("$code ($country)") },
                                        onClick = {
                                            selectedCountryCode = code
                                            countryMenuExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = phoneInput,
                            onValueChange = { phoneInput = it },
                            label = { Text("Mobile Number") },
                            placeholder = { Text("700 123 456") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = VipGoldPrimary,
                                unfocusedBorderColor = VipBorderDark,
                                focusedLabelColor = VipGoldPrimary,
                                unfocusedLabelColor = VipTextSecondary,
                                focusedTextColor = VipTextPrimary,
                                unfocusedTextColor = VipTextPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                    }
                }
            } else {
                // Sign In Form
                OutlinedTextField(
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    label = { Text("Email or Phone Number") },
                    placeholder = { Text("Enter your credentials") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null, tint = VipPurpleGlow)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VipGoldPrimary,
                        unfocusedBorderColor = VipBorderDark,
                        focusedLabelColor = VipGoldPrimary,
                        unfocusedLabelColor = VipTextSecondary,
                        focusedTextColor = VipTextPrimary,
                        unfocusedTextColor = VipTextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Password Field
            OutlinedTextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                label = { Text("Security Key / Password") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null, tint = VipPurpleGlow)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = VipTextSecondary
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VipGoldPrimary,
                    unfocusedBorderColor = VipBorderDark,
                    focusedLabelColor = VipGoldPrimary,
                    unfocusedLabelColor = VipTextSecondary,
                    focusedTextColor = VipTextPrimary,
                    unfocusedTextColor = VipTextPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Action Button
            VipGoldButton(
                text = if (authTab == 0) "Enter VIP MEET" else "Create VIP Account",
                onClick = onAuthSuccess,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "By continuing, you agree to VIP MEET's Global Protocol and End-to-End Privacy Charter.",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.5.sp),
                color = VipTextMuted,
                modifier = Modifier.padding(horizontal = 4.dp),
                lineHeight = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
