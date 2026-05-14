package com.fourbeat.presentation.ui.main.selectsong

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fourbeat.domain.model.post.Song
import com.fourbeat.domain.usecase.music.SearchSongPageUseCase

class SongSearchPagingSource(
    private val query: String,
    private val searchSongPageUseCase: SearchSongPageUseCase,
) : PagingSource<String, Song>() {

    override fun getRefreshKey(state: PagingState<String, Song>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Song> =
        searchSongPageUseCase(
            query = query,
            limit = params.loadSize,
            nextUrl = params.key,
        ).fold(
            onSuccess = { page ->
                LoadResult.Page(
                    data = page.songs,
                    prevKey = null,
                    nextKey = page.nextUrl,
                )
            },
            onFailure = { LoadResult.Error(it) },
        )
}
