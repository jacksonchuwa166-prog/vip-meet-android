package com.example.data

import com.example.model.CallDirection
import com.example.model.CallRecord
import com.example.model.CallType
import com.example.model.ChatMessage
import com.example.model.ChatThread
import com.example.model.GroupItem
import com.example.model.MessageMediaType
import com.example.model.UbaoniPost
import com.example.model.UbaoniType
import com.example.model.VipUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * VIP MEET Phase 1 Repository
 * 
 * Note on Security & Transparency:
 * All records here are strictly UI demonstrative previews for Phase 1.
 * No real user credentials or live server connections are active.
 * In Phase 2, this layer directly bridges to Firebase Firestore, Authentication,
 * and WebRTC media relays.
 */
class VipRepository {

    private val _currentUser = MutableStateFlow(
        VipUser(
            id = "vip-user-001",
            fullName = "Ambassador K. Mwangi",
            handle = "@kmwangi_vip",
            email = "vip.ambassador@network.com",
            phone = "+254 700 889900",
            vipTier = "VIP Founding Member",
            bio = "Global Diplomat & Tech Investor • Connecting visionaries worldwide from Nairobi & Geneva.",
            location = "Nairobi / Geneva / London",
            status = "Online • Open for VIP Consultations",
            joinYear = "Founding Member 2026"
        )
    )
    val currentUser: StateFlow<VipUser> = _currentUser.asStateFlow()

    private val _chats = MutableStateFlow(
        listOf(
            ChatThread(
                id = "chat-01",
                name = "VIP Concierge Global",
                handle = "@vip_concierge",
                lastMessage = "Your international summit pass is confirmed for Friday.",
                timestamp = "10:45 AM",
                unreadCount = 1,
                isPinned = true,
                isOnline = true
            ),
            ChatThread(
                id = "chat-02",
                name = "Dr. Elena Rostova",
                handle = "@elena_rostova",
                lastMessage = "I reviewed the cross-border trade agreement draft.",
                timestamp = "09:30 AM",
                unreadCount = 2,
                isPinned = true,
                isOnline = true
            ),
            ChatThread(
                id = "chat-03",
                name = "Malik Al-Farooq",
                handle = "@malik_dubai",
                lastMessage = "Looking forward to our video session at 3 PM UTC.",
                timestamp = "Yesterday",
                unreadCount = 0,
                isPinned = false,
                isOnline = false
            ),
            ChatThread(
                id = "chat-04",
                name = "East Africa Venture Board",
                handle = "@ea_venture_vip",
                lastMessage = "Audio briefing: Sustainable energy fund allocation.",
                timestamp = "Yesterday",
                unreadCount = 0,
                isPinned = false,
                isOnline = true
            ),
            ChatThread(
                id = "chat-05",
                name = "Chen Wei",
                handle = "@chen_wei_shanghai",
                lastMessage = "Agreed, One World, One Connection is truly revolutionary.",
                timestamp = "Oct 26",
                unreadCount = 0,
                isPinned = false,
                isOnline = false
            )
        )
    )
    val chats: StateFlow<List<ChatThread>> = _chats.asStateFlow()

    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                id = "m-1",
                text = "Welcome to VIP MEET! One World. One Connection.",
                timestamp = "10:40 AM",
                isFromMe = false
            ),
            ChatMessage(
                id = "m-2",
                text = "Thank you. Excited to preview this world-class platform.",
                timestamp = "10:42 AM",
                isFromMe = true
            ),
            ChatMessage(
                id = "m-3",
                text = "Here is the verified executive voice note regarding Friday's schedule.",
                timestamp = "10:44 AM",
                isFromMe = false,
                mediaType = MessageMediaType.AUDIO,
                durationText = "0:34"
            ),
            ChatMessage(
                id = "m-4",
                text = "Your international summit pass is confirmed for Friday.",
                timestamp = "10:45 AM",
                isFromMe = false
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _calls = MutableStateFlow(
        listOf(
            CallRecord(
                id = "call-1",
                contactName = "Dr. Elena Rostova",
                contactHandle = "@elena_rostova",
                type = CallType.VIDEO,
                direction = CallDirection.INCOMING,
                timestamp = "Today, 11:15 AM",
                duration = "14m 22s"
            ),
            CallRecord(
                id = "call-2",
                contactName = "Malik Al-Farooq",
                contactHandle = "@malik_dubai",
                type = CallType.VOICE,
                direction = CallDirection.OUTGOING,
                timestamp = "Yesterday, 4:20 PM",
                duration = "08m 45s"
            ),
            CallRecord(
                id = "call-3",
                contactName = "Chen Wei",
                contactHandle = "@chen_wei_shanghai",
                type = CallType.VIDEO,
                direction = CallDirection.MISSED,
                timestamp = "Oct 26, 8:12 PM",
                duration = "Missed"
            ),
            CallRecord(
                id = "call-4",
                contactName = "VIP Concierge Global",
                contactHandle = "@vip_concierge",
                type = CallType.VOICE,
                direction = CallDirection.INCOMING,
                timestamp = "Oct 25, 2:00 PM",
                duration = "04m 10s"
            )
        )
    )
    val calls: StateFlow<List<CallRecord>> = _calls.asStateFlow()

    private val _ubaoniPosts = MutableStateFlow(
        listOf(
            UbaoniPost(
                id = "ub-01",
                authorName = "VIP Official Board",
                authorHandle = "@vip_meet",
                authorVipTier = "Global Headquarters",
                timeAgo = "1h ago",
                type = UbaoniType.TEXT,
                contentText = "Welcome to Ubaoni (On the Board) — The premier global stage where leaders, visionaries, and creators share breaking ideas, announcements, and cultural milestones with the world. One World. One Connection.",
                mediaCaption = "Official Charter Note",
                likesCount = 342,
                commentsCount = 48,
                isLiked = true
            ),
            UbaoniPost(
                id = "ub-02",
                authorName = "Amina Diallo",
                authorHandle = "@amina_dakar",
                authorVipTier = "Pan-African Leader",
                timeAgo = "3h ago",
                type = UbaoniType.AUDIO,
                contentText = "Brief morning reflections on unifying digital infrastructure across emerging markets. Listen to this 1-minute voice dispatch.",
                mediaCaption = "Executive Voice Note Dispatch",
                mediaDuration = "1:02",
                likesCount = 189,
                commentsCount = 27
            ),
            UbaoniPost(
                id = "ub-03",
                authorName = "Lord Sterling Archer",
                authorHandle = "@sterling_london",
                authorVipTier = "VIP Patron",
                timeAgo = "5h ago",
                type = UbaoniType.PHOTO,
                contentText = "Live from the Global Innovation Forum. Honored to connect with distinguished partners from over 40 countries under the VIP MEET network.",
                mediaCaption = "Global Innovation Forum Summit",
                likesCount = 520,
                commentsCount = 74
            ),
            UbaoniPost(
                id = "ub-04",
                authorName = "Kenji Sato",
                authorHandle = "@kenji_tokyo",
                authorVipTier = "Global Technologist",
                timeAgo = "8h ago",
                type = UbaoniType.VIDEO,
                contentText = "Keynote teaser: Next-generation quantum encryption for ultra-secure worldwide communications.",
                mediaCaption = "4K Keynote Highlight • 03:15",
                mediaDuration = "3:15",
                likesCount = 412,
                commentsCount = 61
            )
        )
    )
    val ubaoniPosts: StateFlow<List<UbaoniPost>> = _ubaoniPosts.asStateFlow()

    private val _groups = MutableStateFlow(
        listOf(
            GroupItem(
                id = "grp-01",
                name = "Global Leaders & Diplomats",
                category = "Diplomacy & Trade",
                description = "High-level forum for multilateral ambassadors, policy shapers, and international liaisons.",
                memberCount = 1240,
                isPrivate = true,
                isJoined = true
            ),
            GroupItem(
                id = "grp-02",
                name = "African Tech Pioneers",
                category = "Innovation",
                description = "Connecting founders, investors, and engineers driving the next frontier across Africa.",
                memberCount = 3890,
                isPrivate = false,
                isJoined = true
            ),
            GroupItem(
                id = "grp-03",
                name = "VIP Media & Creative Guild",
                category = "Arts & Entertainment",
                description = "Producers, authors, designers, and cinematic storytellers with worldwide reach.",
                memberCount = 2150,
                isPrivate = false,
                isJoined = false
            ),
            GroupItem(
                id = "grp-04",
                name = "Silicon & Quantum Syndicate",
                category = "Deep Tech",
                description = "Cutting-edge discussions on artificial intelligence, sovereign compute, and cryptography.",
                memberCount = 980,
                isPrivate = true,
                isJoined = false
            ),
            GroupItem(
                id = "grp-05",
                name = "East Africa Business Nexus",
                category = "Commerce",
                description = "Direct chamber of commerce and strategic alliance exchange for East African markets.",
                memberCount = 4120,
                isPrivate = false,
                isJoined = true
            )
        )
    )
    val groups: StateFlow<List<GroupItem>> = _groups.asStateFlow()

    fun sendMessage(text: String) {
        val newMsg = ChatMessage(
            id = "m-${System.currentTimeMillis()}",
            text = text,
            timestamp = "Just now",
            isFromMe = true
        )
        _messages.value = _messages.value + newMsg
    }

    fun toggleLike(postId: String) {
        _ubaoniPosts.value = _ubaoniPosts.value.map { post ->
            if (post.id == postId) {
                post.copy(
                    isLiked = !post.isLiked,
                    likesCount = if (post.isLiked) post.likesCount - 1 else post.likesCount + 1
                )
            } else post
        }
    }

    fun addUbaoniPost(content: String, type: UbaoniType) {
        val newPost = UbaoniPost(
            id = "ub-${System.currentTimeMillis()}",
            authorName = _currentUser.value.fullName,
            authorHandle = _currentUser.value.handle,
            authorVipTier = _currentUser.value.vipTier,
            timeAgo = "Just now",
            type = type,
            contentText = content,
            mediaCaption = if (type != UbaoniType.TEXT) "VIP User Upload" else null,
            likesCount = 1,
            commentsCount = 0,
            isLiked = true
        )
        _ubaoniPosts.value = listOf(newPost) + _ubaoniPosts.value
    }

    fun toggleGroupJoin(groupId: String) {
        _groups.value = _groups.value.map { group ->
            if (group.id == groupId) {
                group.copy(
                    isJoined = !group.isJoined,
                    memberCount = if (group.isJoined) group.memberCount - 1 else group.memberCount + 1
                )
            } else group
        }
    }

    fun updateProfile(name: String, bio: String, location: String) {
        _currentUser.value = _currentUser.value.copy(
            fullName = name,
            bio = bio,
            location = location
        )
    }
}
