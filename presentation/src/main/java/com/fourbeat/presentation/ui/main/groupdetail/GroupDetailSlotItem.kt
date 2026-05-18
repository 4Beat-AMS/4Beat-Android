package com.fourbeat.presentation.ui.main.groupdetail

import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.fourbeat.domain.model.user.User
import com.fourbeat.presentation.model.group.FeedPostUiModel
import com.fourbeat.presentation.model.group.GroupFeedSlotUiModel
import com.fourbeat.presentation.model.post.VideoSource
import com.fourbeat.presentation.theme.Gray100
import com.fourbeat.presentation.theme.Gray500
import com.fourbeat.presentation.theme.White
import com.fourbeat.presentation.theme.bold14
import com.fourbeat.presentation.theme.normal14
import com.fourbeat.presentation.ui.component.VideoPlayer

@Composable
fun GroupDetailSlotItem(
    modifier: Modifier = Modifier,
    slot: GroupFeedSlotUiModel,
    slotHeight: Dp,
) {
    if (slot.posts.isEmpty()) {
        SlotEmptyContent(modifier = modifier, member = slot.member)
    } else {
        val pagerState = rememberPagerState { slot.posts.size }
        HorizontalPager(
            state = pagerState,
            modifier = modifier,
        ) { page ->
            SlotPostItem(
                modifier = Modifier.fillMaxWidth().height(slotHeight),
                post = slot.posts[page],
                member = slot.member,
            )
        }
    }
}

@Composable
private fun SlotPostItem(
    modifier: Modifier = Modifier,
    post: FeedPostUiModel,
    member: User,
) {
    Box(modifier = modifier.clipToBounds()) {
        if (post.videoUrl != null) {
            VideoPlayer(
                modifier = Modifier.fillMaxSize(),
                source = VideoSource.Remote(post.videoUrl),
            )
        } else {
            AsyncImage(
                model = post.song.albumImageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.1f))
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(text = member.nickname, style = bold14, color = White)
                    Text(text = member.name, style = normal14, color = White.copy(alpha = 0.7f))
                }
                Text(
                    text = post.createdAt,
                    style = normal14,
                    color = White.copy(alpha = 0.7f),
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                post.comment?.let {
                    Text(
                        text = it,
                        style = normal14,
                        color = White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                    )
                } ?: Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    AsyncImage(
                        model = post.song.albumImageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(56.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = post.song.title,
                        style = bold14,
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = post.song.artist,
                        style = normal14,
                        color = White.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
private fun SlotEmptyContent(
    modifier: Modifier = Modifier,
    member: User,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Gray100),
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(text = member.nickname, style = bold14)
            Text(text = member.name, style = normal14, color = Gray500)
        }
        Text(
            text = "아직 게시글이 없어요",
            modifier = Modifier.align(Alignment.Center),
            style = normal14,
            color = Gray500,
        )
    }
}
