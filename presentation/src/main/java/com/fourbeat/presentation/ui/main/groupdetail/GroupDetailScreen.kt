package com.fourbeat.presentation.ui.main.groupdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fourbeat.presentation.model.group.GroupFeedUiData
import com.fourbeat.presentation.ui.main.groupdetail.header.GroupDetailHeader

@Composable
fun GroupDetailRoute(
    modifier: Modifier = Modifier,
    navigateToSelectSong: (Long) -> Unit,
    showGroupCodeDialog: (String) -> Unit,
    viewModel: GroupDetailViewModel = hiltViewModel(),
) {
    GroupDetailScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onEvent = viewModel::onEvent,
        navigateToSelectSong = navigateToSelectSong,
        showGroupCodeDialog = showGroupCodeDialog,
    )
}

@Composable
private fun GroupDetailScreen(
    modifier: Modifier = Modifier,
    uiState: GroupDetailUiState,
    onEvent: (GroupDetailEvent) -> Unit,
    navigateToSelectSong: (Long) -> Unit,
    showGroupCodeDialog: (String) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        GroupDetailHeader(
            navigateToSelectSong = navigateToSelectSong,
            showGroupCodeDialog = showGroupCodeDialog,
        )
        GroupDetailFeed(
            modifier = Modifier.weight(1f),
            uiState = uiState,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun GroupDetailFeed(
    modifier: Modifier = Modifier,
    uiState: GroupDetailUiState,
    onEvent: (GroupDetailEvent) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 1) { 3 }

    // Reset to center when current changes after a shift
    LaunchedEffect(uiState.currentFeed?.date) {
        if (pagerState.currentPage != 1) {
            pagerState.scrollToPage(1)
        }
    }

    // page 2 = prev (scroll UP), page 0 = next (scroll DOWN)
    LaunchedEffect(pagerState.settledPage) {
        when (pagerState.settledPage) {
            2 -> onEvent(GroupDetailEvent.OnScrollToPrev)
            0 -> onEvent(GroupDetailEvent.OnScrollToNext)
        }
    }

    val previousFeed by rememberUpdatedState(uiState.previousFeed)
    val nextFeed by rememberUpdatedState(uiState.nextFeed)

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset =
                when {
                    available.y < 0 && previousFeed == null -> available  // UP blocked: no prev
                    available.y > 0 && nextFeed == null -> available      // DOWN blocked: no next
                    else -> Offset.Zero
                }
        }
    }

    VerticalPager(
        state = pagerState,
        modifier = modifier.nestedScroll(nestedScrollConnection),
        userScrollEnabled = !uiState.isLoading,
    ) { page ->
        // page 0 = next (bottom, scroll DOWN reveals)
        // page 1 = current
        // page 2 = prev (top, scroll UP reveals)
        val feed = when (page) {
            0 -> uiState.nextFeed
            1 -> uiState.currentFeed
            2 -> uiState.previousFeed
            else -> null
        }
        if (feed != null) {
            GroupDetailDatePage(feed = feed)
        } else {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun GroupDetailDatePage(
    modifier: Modifier = Modifier,
    feed: GroupFeedUiData,
) {
    val slotGroups = remember(feed.slots) { feed.slots.chunked(3) }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val slotHeight = maxHeight / 3

        key(feed.date) {
            val pagerState = rememberPagerState { slotGroups.size }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                val group = slotGroups.getOrNull(page) ?: return@HorizontalPager
                Column(modifier = Modifier.fillMaxSize()) {
                    group.forEach { slot ->
                        GroupDetailSlotItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(slotHeight),
                            slot = slot,
                            slotHeight = slotHeight,
                        )
                    }
                }
            }
        }
    }
}
