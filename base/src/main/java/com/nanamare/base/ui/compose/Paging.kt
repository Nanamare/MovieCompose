package com.nanamare.base.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.nanamare.base.R

@ExperimentalFoundationApi
fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}

@ExperimentalFoundationApi
fun <T : Any> LazyGridScope.itemsIndexed(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T, position: Int) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        lazyPagingItems[index]?.let {
            itemContent(it, index)
        }
    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun EmptyPlaceHolder(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = AppBarHeight),
            imageVector = Icons.Filled.Close,
            contentDescription = "EmptyPlaceholder",
            tint = Color.White
        )
    }
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

fun <T : Any> LazyListScope.setPagingStateListener(
    items: LazyPagingItems<T>,
    isNotEmptyQuery: () -> Boolean,
    refresh: (LazyPagingItems<T>).() -> Unit,
    append: (LazyPagingItems<T>).() -> Unit,
    empty: (LazyPagingItems<T>).() -> Unit
) {
    items.apply {
        when {
            loadState.append is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached
                    && itemCount == 0 && isNotEmptyQuery() -> empty(this)

            loadState.refresh is LoadState.Loading -> refresh(this)
            loadState.append is LoadState.Loading -> append(this)
            loadState.refresh is LoadState.Error -> {
                val error = items.loadState.refresh as LoadState.Error
                item {
                    ErrorItem(
                        message = error.error.localizedMessage.orEmpty(),
                        modifier = Modifier.fillParentMaxSize(),
                        onClickRetry = { retry() }
                    )
                }
            }

            loadState.append is LoadState.Error -> {
                val error = items.loadState.append as LoadState.Error
                item {
                    ErrorItem(
                        message = error.error.localizedMessage.orEmpty(),
                        onClickRetry = { retry() }
                    )
                }
            }
        }
    }
}

// AppBar.kt copy
private val AppBarHeight = 56.dp