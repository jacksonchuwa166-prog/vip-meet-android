package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
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
import com.example.localization.vipString
import com.example.model.ScreenTab
import com.example.ui.theme.VipBorderDark
import com.example.ui.theme.VipDarkCardElevated
import com.example.ui.theme.VipDarkSurface
import com.example.ui.theme.VipGoldChampagne
import com.example.ui.theme.VipGoldDark
import com.example.ui.theme.VipGoldPrimary
import com.example.ui.theme.VipObsidianBg
import com.example.ui.theme.VipPurpleContainer
import com.example.ui.theme.VipPurpleGlow
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite

data class NavItem(
    val tab: ScreenTab,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val badgeCount: Int = 0
)

val NAV_ITEMS = listOf(
    NavItem(ScreenTab.HOME, Icons.Default.Home, Icons.Outlined.Home),
    NavItem(ScreenTab.CHATS, Icons.Default.ChatBubble, Icons.Outlined.ChatBubbleOutline, badgeCount = 3),
    NavItem(ScreenTab.CALLS, Icons.Default.Call, Icons.Outlined.Call),
    NavItem(ScreenTab.UBAONI, Icons.Default.Dashboard, Icons.Outlined.Dashboard),
    NavItem(ScreenTab.GROUPS, Icons.Default.Groups, Icons.Outlined.Groups),
    NavItem(ScreenTab.PROFILE, Icons.Default.Person, Icons.Outlined.PersonOutline),
    NavItem(ScreenTab.SETTINGS, Icons.Default.Settings, Icons.Outlined.Settings)
)

@Composable
fun VipBottomNavigationBar(
    currentTab: ScreenTab,
    onTabSelected: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(VipDarkSurface)
            .border(0.5.dp, VipBorderDark, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(vertical = 6.dp, horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NAV_ITEMS.forEach { item ->
                val isSelected = item.tab == currentTab
                val label = vipString(item.tab.titleKey)

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(item.tab) }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) VipPurpleContainer else Color.Transparent)
                            .border(
                                width = if (isSelected) 1.dp else 0.dp,
                                color = if (isSelected) VipGoldPrimary else Color.Transparent,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount > 0) {
                                    Badge(
                                        containerColor = VipGoldDark,
                                        contentColor = VipWhite
                                    ) {
                                        Text(
                                            text = item.badgeCount.toString(),
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                                contentDescription = label,
                                tint = if (isSelected) VipGoldPrimary else VipTextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = if (isSelected) VipGoldChampagne else VipTextMuted,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun VipNavigationRail(
    currentTab: ScreenTab,
    onTabSelected: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        modifier = modifier
            .fillMaxHeight()
            .width(88.dp),
        containerColor = VipDarkSurface,
        contentColor = VipTextPrimary
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NAV_ITEMS.forEach { item ->
                val isSelected = item.tab == currentTab
                val label = vipString(item.tab.titleKey)

                NavigationRailItem(
                    selected = isSelected,
                    onClick = { onTabSelected(item.tab) },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (item.badgeCount > 0) {
                                    Badge(
                                        containerColor = VipGoldDark,
                                        contentColor = VipWhite
                                    ) {
                                        Text(text = item.badgeCount.toString(), fontSize = 9.sp)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) item.filledIcon else item.outlinedIcon,
                                contentDescription = label
                            )
                        }
                    },
                    label = {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            maxLines = 1
                        )
                    },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = VipGoldPrimary,
                        selectedTextColor = VipGoldChampagne,
                        indicatorColor = VipPurpleContainer,
                        unselectedIconColor = VipTextSecondary,
                        unselectedTextColor = VipTextMuted
                    )
                )
            }
        }
    }
}
