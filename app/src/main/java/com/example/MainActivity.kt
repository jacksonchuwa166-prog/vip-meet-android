package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data.VipRepository
import com.example.localization.LocalVipLocale
import com.example.localization.VipLocaleManager
import com.example.model.AppDestination
import com.example.model.ScreenTab
import com.example.ui.components.VipBottomNavigationBar
import com.example.ui.components.VipNavigationRail
import com.example.ui.components.VipTopBar
import com.example.ui.screens.ActiveCallScreen
import com.example.ui.screens.CallsScreen
import com.example.ui.screens.ChatsScreen
import com.example.ui.screens.ConversationScreen
import com.example.ui.screens.GroupsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.UbaoniScreen
import com.example.ui.screens.WelcomeAuthScreen
import com.example.ui.theme.VipMeetTheme
import com.example.ui.theme.VipObsidianBg

class MainActivity : ComponentActivity() {

    private val repository = VipRepository()
    private val localeManager = VipLocaleManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider(LocalVipLocale provides localeManager) {
                VipMeetTheme {
                    VipMeetApp(repository = repository)
                }
            }
        }
    }
}

@Composable
fun VipMeetApp(
    repository: VipRepository,
    modifier: Modifier = Modifier
) {
    var destination by remember { mutableStateOf<AppDestination>(AppDestination.MainTab(ScreenTab.HOME)) }
    var currentTab by remember { mutableStateOf(ScreenTab.HOME) }
    var isAuthenticated by remember { mutableStateOf(true) }

    // Intercept back button when inside detail sub-screens or sub-tabs
    BackHandler(enabled = destination !is AppDestination.MainTab || currentTab != ScreenTab.HOME) {
        when {
            destination !is AppDestination.MainTab -> {
                destination = AppDestination.MainTab(currentTab)
            }
            currentTab != ScreenTab.HOME -> {
                currentTab = ScreenTab.HOME
                destination = AppDestination.MainTab(ScreenTab.HOME)
            }
        }
    }

    if (!isAuthenticated) {
        WelcomeAuthScreen(
            onAuthSuccess = {
                isAuthenticated = true
                destination = AppDestination.MainTab(ScreenTab.HOME)
                currentTab = ScreenTab.HOME
            }
        )
        return
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(VipObsidianBg)
    ) {
        val isTabletOrExpanded = maxWidth >= 600.dp

        when (val currentDest = destination) {
            is AppDestination.Conversation -> {
                ConversationScreen(
                    chatId = currentDest.chatId,
                    contactName = currentDest.name,
                    contactHandle = currentDest.handle,
                    repository = repository,
                    onBack = { destination = AppDestination.MainTab(currentTab) },
                    onStartCall = { name, isVideo ->
                        destination = AppDestination.ActiveCall(name = name, isVideo = isVideo)
                    }
                )
            }
            is AppDestination.ActiveCall -> {
                ActiveCallScreen(
                    contactName = currentDest.name,
                    isVideo = currentDest.isVideo,
                    onEndCall = { destination = AppDestination.MainTab(currentTab) }
                )
            }
            is AppDestination.AuthWelcome, is AppDestination.AuthRegister -> {
                WelcomeAuthScreen(
                    onAuthSuccess = {
                        destination = AppDestination.MainTab(ScreenTab.HOME)
                        currentTab = ScreenTab.HOME
                    }
                )
            }
            is AppDestination.MainTab -> {
                Scaffold(
                    topBar = {
                        VipTopBar(
                            onLanguageClick = {
                                currentTab = ScreenTab.SETTINGS
                                destination = AppDestination.MainTab(ScreenTab.SETTINGS)
                            },
                            onProfileClick = {
                                currentTab = ScreenTab.PROFILE
                                destination = AppDestination.MainTab(ScreenTab.PROFILE)
                            }
                        )
                    },
                    bottomBar = {
                        if (!isTabletOrExpanded) {
                            VipBottomNavigationBar(
                                currentTab = currentTab,
                                onTabSelected = { tab ->
                                    currentTab = tab
                                    destination = AppDestination.MainTab(tab)
                                }
                            )
                        }
                    },
                    containerColor = VipObsidianBg
                ) { innerPadding ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (isTabletOrExpanded) {
                            VipNavigationRail(
                                currentTab = currentTab,
                                onTabSelected = { tab ->
                                    currentTab = tab
                                    destination = AppDestination.MainTab(tab)
                                }
                            )
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            when (currentTab) {
                                ScreenTab.HOME -> {
                                    HomeScreen(
                                        repository = repository,
                                        onNavigateTab = { tab ->
                                            currentTab = tab
                                            destination = AppDestination.MainTab(tab)
                                        },
                                        onOpenConversation = { chatId, name, handle ->
                                            destination = AppDestination.Conversation(chatId, name, handle)
                                        },
                                        onStartCall = { name, isVideo ->
                                            destination = AppDestination.ActiveCall(name, isVideo)
                                        }
                                    )
                                }
                                ScreenTab.CHATS -> {
                                    ChatsScreen(
                                        repository = repository,
                                        onOpenConversation = { chatId, name, handle ->
                                            destination = AppDestination.Conversation(chatId, name, handle)
                                        }
                                    )
                                }
                                ScreenTab.CALLS -> {
                                    CallsScreen(
                                        repository = repository,
                                        onStartCall = { name, isVideo ->
                                            destination = AppDestination.ActiveCall(name, isVideo)
                                        }
                                    )
                                }
                                ScreenTab.UBAONI -> {
                                    UbaoniScreen(repository = repository)
                                }
                                ScreenTab.GROUPS -> {
                                    GroupsScreen(
                                        repository = repository,
                                        onOpenGroupChat = { groupId, name ->
                                            destination = AppDestination.Conversation(
                                                chatId = groupId,
                                                name = name,
                                                handle = "@vip_lounge"
                                            )
                                        }
                                    )
                                }
                                ScreenTab.PROFILE -> {
                                    ProfileScreen(
                                        repository = repository,
                                        onNavigateSettings = {
                                            currentTab = ScreenTab.SETTINGS
                                            destination = AppDestination.MainTab(ScreenTab.SETTINGS)
                                        }
                                    )
                                }
                                ScreenTab.SETTINGS -> {
                                    SettingsScreen(
                                        onSignOut = {
                                            isAuthenticated = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
