package com.fourbeat.presentation.ui.main.selectsong

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fourbeat.domain.model.post.Song
import com.fourbeat.presentation.theme.Gray200
import com.fourbeat.presentation.theme.Gray500
import com.fourbeat.presentation.theme.PrimaryColor
import com.fourbeat.presentation.theme.bold18
import com.fourbeat.presentation.theme.contentPadding
import com.fourbeat.presentation.theme.corderRadius
import com.fourbeat.presentation.theme.normal14
import com.fourbeat.presentation.ui.component.FourBeatButton
import com.fourbeat.presentation.ui.component.FourBeatLabel
import com.fourbeat.presentation.ui.component.FourBeatSpacer
import com.fourbeat.presentation.ui.component.NetworkImage
import com.fourbeat.presentation.ui.component.SearchTextField
import com.fourbeat.presentation.ui.component.TitleTopBar
import com.fourbeat.presentation.ui.util.noRippleClickable

@Composable
fun SelectSongRoute(
    modifier: Modifier = Modifier,
    navigateToCreatePost: (Long) -> Unit,
    navigateToBack: () -> Unit,
    viewModel: SelectSongViewModel = hiltViewModel(),
) {
    val liveSongUiState by viewModel.liveSongFlow.collectAsStateWithLifecycle()
    val searchSongs = viewModel.songPagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is SelectSongSideEffect.NavigateToCreatePost -> navigateToCreatePost(effect.groupId)
                SelectSongSideEffect.NavigateToBack -> navigateToBack()
                SelectSongSideEffect.OpenNotificationListenerSettings -> {
                    val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        }
    }

    SelectSongScreen(
        modifier = modifier,
        uiState = viewModel.uiState,
        liveSongUiState = liveSongUiState,
        searchSongs = searchSongs,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun SelectSongScreen(
    modifier: Modifier = Modifier,
    uiState: SelectSongUiState,
    liveSongUiState: LiveSongUiState,
    searchSongs: LazyPagingItems<Song>,
    onEvent: (SelectSongEvent) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TitleTopBar(
            title = "노래 고르기",
            onBack = { onEvent(SelectSongEvent.OnBackIconClicked) },
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = contentPadding,
                    end = contentPadding,
                    bottom = 100.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                stickyHeader {
                    SearchTextField(
                        value = uiState.searchQuery,
                        placeholder = "spotify에서 제목으로 찾아봐",
                        onValueChange = { value ->
                            onEvent(SelectSongEvent.OnSearchQueryChanged(value))
                        },
                    )
                }
                item { FourBeatLabel(text = "실시간") }
                item {
                    when (liveSongUiState) {
                        LiveSongUiState.Loading -> LiveSongLoading()
                        LiveSongUiState.None -> LiveSongNone()
                        LiveSongUiState.PermissionRequired -> LiveSongPermissionRequired(
                            onRequestPermission = {
                                onEvent(SelectSongEvent.OnRequestPermissionClicked)
                            },
                        )
                        is LiveSongUiState.Live -> SelectSongItem(
                            modifier = Modifier.fillMaxWidth(),
                            song = liveSongUiState.song,
                            isSelected = uiState.selectedSong == liveSongUiState.song,
                            onSelect = { onEvent(SelectSongEvent.OnSongItemToggled(liveSongUiState.song)) },
                        )
                    }
                }
                if (uiState.searchQuery.isNotBlank()) {
                    item { FourBeatLabel(text = "검색 결과") }
                    items(
                        count = searchSongs.itemCount,
                    ) { index ->
                        searchSongs[index]?.let { song ->
                            SelectSongItem(
                                modifier = Modifier.fillMaxWidth(),
                                song = song,
                                isSelected = uiState.selectedSong == song,
                                onSelect = { onEvent(SelectSongEvent.OnSongItemToggled(song)) },
                            )
                        }
                    }
                    if (searchSongs.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
            FourBeatButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(contentPadding),
                isLoading = false,
                enabled = uiState.isValid,
                text = uiState.buttonText,
                onClick = { onEvent(SelectSongEvent.OnNextButtonClicked) },
            )
        }
    }
}

@Composable
private fun LiveSongLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun LiveSongNone(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "실시간 노래 정보가 없어요",
            style = normal14,
            color = Gray500,
        )
    }
}

@Composable
private fun LiveSongPermissionRequired(
    modifier: Modifier = Modifier,
    onRequestPermission: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "실시간 곡 정보를 보려면, 알림 접근 권한이 필요해요",
                style = normal14,
                color = Gray500,
            )
            Text(
                modifier = Modifier
                    .border(
                        border = BorderStroke(width = 1.dp, color = PrimaryColor),
                        shape = RoundedCornerShape(corderRadius),
                    )
                    .noRippleClickable(onClick = onRequestPermission)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
                text = "권한 설정",
                style = normal14,
                color = PrimaryColor,
            )
        }
    }
}

@Composable
fun SelectSongItem(
    modifier: Modifier = Modifier,
    song: Song,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    val buttonBorderColor = if (isSelected) PrimaryColor else Gray200
    val buttonTextColor = if (isSelected) PrimaryColor else Gray500

    Row(
        modifier = modifier.height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NetworkImage(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(4.dp)),
            url = song.albumImageUrl ?: "", // TODO: 기본 이미지 url 넣기
        )
        FourBeatSpacer(size = 12)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = song.title,
                style = bold18.copy(fontWeight = FontWeight.Bold),
            )
            Text(
                text = song.artist,
                style = normal14,
                color = Gray500,
            )
        }
        Text(
            modifier = Modifier
                .border(
                    border = BorderStroke(width = 1.dp, color = buttonBorderColor),
                    shape = RoundedCornerShape(corderRadius),
                )
                .noRippleClickable(onClick = onSelect)
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
            text = "고르기",
            style = normal14,
            color = buttonTextColor,
        )
    }
}
