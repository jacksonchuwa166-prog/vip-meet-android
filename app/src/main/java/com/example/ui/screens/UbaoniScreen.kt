package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VipRepository
import com.example.localization.vipString
import com.example.model.UbaoniPost
import com.example.model.UbaoniType
import com.example.ui.components.VipAvatar
import com.example.ui.components.VipBadge
import com.example.ui.components.VipGoldButton
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
import com.example.ui.theme.VipTextMuted
import com.example.ui.theme.VipTextPrimary
import com.example.ui.theme.VipTextSecondary
import com.example.ui.theme.VipWhite

@Composable
fun UbaoniScreen(
    repository: VipRepository,
    modifier: Modifier = Modifier
) {
    val posts by repository.ubaoniPosts.collectAsState()
    var selectedCategory by remember { mutableStateOf("All") }
    var showCreateDialog by remember { mutableStateOf(false) }

    val categories = listOf("All", "Stories", "Audio", "Video", "Announcements")

    val filteredPosts = posts.filter { post ->
        when (selectedCategory) {
            "Audio" -> post.type == UbaoniType.AUDIO
            "Video" -> post.type == UbaoniType.VIDEO
            "Stories" -> post.type == UbaoniType.PHOTO || post.type == UbaoniType.TEXT
            else -> true
        }
    }

    Box(modifier = modifier.fillMaxSize().background(VipObsidianBg)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "UBAONI (ON THE BOARD)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        ),
                        color = VipGoldChampagne
                    )
                    Text(
                        text = "Global Executive Stories & Dispatches",
                        style = MaterialTheme.typography.bodySmall,
                        color = VipPurpleGlow
                    )
                }
                VipBadge(text = "LIVE BOARD")
            }

            VipHonestyBanner(
                text = "Ubaoni Demo Feed • Real-time global broadcasting and media storage will connect via Firebase in Phase 2."
            )

            // Category Filter Pills
            val categoryScroll = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(categoryScroll),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(if (isSelected) VipPurpleContainer else VipDarkSurface)
                            .border(
                                1.dp,
                                if (isSelected) VipGoldPrimary else VipBorderDark,
                                RoundedCornerShape(100.dp)
                            )
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) VipGoldChampagne else VipTextSecondary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Posts feed
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredPosts) { post ->
                    UbaoniPostCard(
                        post = post,
                        onLikeClick = { repository.toggleLike(post.id) }
                    )
                }
            }
        }

        // FAB to post on board
        FloatingActionButton(
            onClick = { showCreateDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = VipGoldPrimary,
            contentColor = VipObsidianBg
        ) {
            Icon(Icons.Default.Add, contentDescription = "Post to Ubaoni")
        }

        // Create Post Dialog
        if (showCreateDialog) {
            CreateUbaoniPostDialog(
                onDismiss = { showCreateDialog = false },
                onPost = { text, type ->
                    repository.addUbaoniPost(text, type)
                    showCreateDialog = false
                }
            )
        }
    }
}

@Composable
fun UbaoniPostCard(
    post: UbaoniPost,
    onLikeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(VipDarkCard)
            .border(1.dp, VipBorderDark, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Author row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                VipAvatar(name = post.authorName, size = 42)
                Column {
                    Text(
                        text = post.authorName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        color = VipWhite
                    )
                    Text(
                        text = "${post.authorHandle} • ${post.timeAgo}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = VipTextMuted
                    )
                }
            }
            VipBadge(text = post.authorVipTier)
        }

        // Text body
        Text(
            text = post.contentText,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 13.5.sp,
                lineHeight = 19.sp
            ),
            color = VipTextPrimary
        )

        // Rich Media Section based on type
        when (post.type) {
            UbaoniType.AUDIO -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(VipPurpleContainer)
                        .border(1.dp, VipPurpleGlow.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(VipGoldPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play Audio",
                            tint = VipObsidianBg
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = VipGoldChampagne,
                        modifier = Modifier.size(24.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = post.mediaCaption ?: "Executive Voice Dispatch",
                            color = VipWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = post.mediaDuration ?: "1:02",
                            color = VipPurpleGlow,
                            fontSize = 10.5.sp
                        )
                    }
                }
            }
            UbaoniType.PHOTO -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(VipDarkCardElevated)
                        .border(1.dp, VipBorderDark, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            tint = VipGoldPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = post.mediaCaption ?: "VIP Photo Dispatch",
                            color = VipGoldChampagne,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            UbaoniType.VIDEO -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(VipPurpleContainer)
                        .border(1.dp, VipGoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(VipGoldPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play Video",
                                tint = VipObsidianBg
                            )
                        }
                        Text(
                            text = post.mediaCaption ?: "4K Keynote Stream",
                            color = VipGoldChampagne,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            UbaoniType.TEXT -> {
                // Already displayed body
            }
        }

        // Engagement action row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Like Button
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onLikeClick)
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = if (post.isLiked) VipDanger else VipTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = post.likesCount.toString(),
                        color = if (post.isLiked) VipDanger else VipTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Comments
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Comments",
                        tint = VipTextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = post.commentsCount.toString(),
                        color = VipTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            IconButton(onClick = { /* Share dialog placeholder */ }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = VipPurpleGlow,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun CreateUbaoniPostDialog(
    onDismiss: () -> Unit,
    onPost: (String, UbaoniType) -> Unit
) {
    var postText by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(UbaoniType.TEXT) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VipDarkSurface,
        title = {
            Text(
                text = "Post on Ubaoni",
                color = VipGoldChampagne,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Select Post Category:",
                    color = VipTextSecondary,
                    fontSize = 12.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(
                        UbaoniType.TEXT to "Text",
                        UbaoniType.AUDIO to "Audio",
                        UbaoniType.PHOTO to "Photo",
                        UbaoniType.VIDEO to "Video"
                    ).forEach { (type, label) ->
                        val isSelected = selectedType == type
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) VipPurpleContainer else VipDarkCard)
                                .border(
                                    1.dp,
                                    if (isSelected) VipGoldPrimary else VipBorderDark,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedType = type }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                color = if (isSelected) VipGoldChampagne else VipTextSecondary,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = postText,
                    onValueChange = { postText = it },
                    placeholder = { Text("Share an executive note or idea with the world...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VipGoldPrimary,
                        unfocusedBorderColor = VipBorderDark,
                        focusedTextColor = VipTextPrimary,
                        unfocusedTextColor = VipTextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            VipGoldButton(
                text = "Broadcast",
                enabled = postText.isNotBlank(),
                onClick = { onPost(postText.trim(), selectedType) }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = VipTextMuted)
            }
        }
    )
}
