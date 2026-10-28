package com.example.model

enum class ScreenTab(val titleKey: String) {
    HOME("tab_home"),
    CHATS("tab_chats"),
    CALLS("tab_calls"),
    UBAONI("tab_ubaoni"),
    GROUPS("tab_groups"),
    PROFILE("tab_profile"),
    SETTINGS("tab_settings")
}

sealed class AppDestination {
    data class MainTab(val tab: ScreenTab) : AppDestination()
    data class Conversation(val chatId: String, val name: String, val handle: String) : AppDestination()
    data class ActiveCall(val name: String, val isVideo: Boolean) : AppDestination()
    data object AuthWelcome : AppDestination()
    data object AuthRegister : AppDestination()
}

enum class AuthMode {
    SIGN_IN,
    REGISTER_EMAIL,
    REGISTER_PHONE
}

data class VipUser(
    val id: String,
    val fullName: String,
    val handle: String,
    val email: String,
    val phone: String,
    val vipTier: String = "VIP Gold Elite",
    val bio: String = "Connecting global leaders, innovators, and creators across the world.",
    val location: String = "Global / East Africa",
    val status: String = "Online • Ready to collaborate",
    val joinYear: String = "Member since 2026"
)

data class ChatThread(
    val id: String,
    val name: String,
    val handle: String,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int = 0,
    val isPinned: Boolean = false,
    val isOnline: Boolean = false,
    val isVip: Boolean = true
)

enum class MessageMediaType {
    NONE,
    AUDIO,
    IMAGE,
    DOCUMENT
}

data class ChatMessage(
    val id: String,
    val text: String,
    val timestamp: String,
    val isFromMe: Boolean,
    val mediaType: MessageMediaType = MessageMediaType.NONE,
    val durationText: String? = null
)

enum class CallType {
    VOICE,
    VIDEO
}

enum class CallDirection {
    INCOMING,
    OUTGOING,
    MISSED
}

data class CallRecord(
    val id: String,
    val contactName: String,
    val contactHandle: String,
    val type: CallType,
    val direction: CallDirection,
    val timestamp: String,
    val duration: String
)

data class GroupItem(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val memberCount: Int,
    val isPrivate: Boolean,
    val isJoined: Boolean
)

enum class UbaoniType {
    TEXT,
    PHOTO,
    VIDEO,
    AUDIO
}

data class UbaoniPost(
    val id: String,
    val authorName: String,
    val authorHandle: String,
    val authorVipTier: String,
    val timeAgo: String,
    val type: UbaoniType,
    val contentText: String,
    val mediaCaption: String? = null,
    val mediaDuration: String? = null,
    val likesCount: Int,
    val commentsCount: Int,
    val isLiked: Boolean = false
)

data class SupportedLanguage(
    val code: String,
    val englishName: String,
    val nativeName: String,
    val isFullyTranslatedInPhase1: Boolean
)
