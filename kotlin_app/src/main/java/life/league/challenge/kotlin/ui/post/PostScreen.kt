package life.league.challenge.kotlin.ui.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.ui.model.Post
import life.league.challenge.kotlin.ui.post.PostViewModel.State
import life.league.challenge.kotlin.ui.post.PostViewModel.State.Content
import life.league.challenge.kotlin.ui.post.PostViewModel.State.Error
import life.league.challenge.kotlin.ui.post.PostViewModel.State.Loading
import life.league.challenge.kotlin.ui.post.PostViewModel.State.Uninitialized

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    state: State,
    onRefresh: () -> Unit,
) {
    val isLoading = state is Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { onRefresh() },
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        when (state) {
            is Content -> ScreenContent(posts = state.posts)
            is Error -> Error()
            is Uninitialized -> InitializerIndicator()
            is Loading -> Unit
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    posts: List<Post>,
) {
    val rowState = rememberSaveable(posts, saver = LazyListState.Saver) {
        LazyListState()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rowState,
    ) {
        itemsIndexed(posts) { index, post ->
            PostItem(modifier = modifier.fillMaxWidth(), post)

            if (posts.lastIndex != index) {
                Divider(
                    modifier = Modifier.padding(top = 16.dp),
                    color = Color.Gray,
                )
            } else {
                Spacer(modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}

@Composable
private fun PostItem(modifier: Modifier = Modifier, post: Post) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier.size(50.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(post.thumbnail.toUri())
                .transformations(CircleCropTransformation())
                .build(),
            contentDescription = null,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            text = post.name,
            fontSize = 20.sp,
            color = Color.Black,
        )
    }

    Row(modifier = Modifier.padding(top = 12.dp)) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = post.title,
                fontSize = 16.sp,
                color = Color.Black,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = post.body,
                fontSize = 13.sp,
                color = Color.Gray,
            )
        }
    }
}

@Composable
private fun Error(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(stringResource(id = R.string.error_message))
        }
    }
}

@Composable
private fun InitializerIndicator(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
private fun PostScreenContentPreview() {
    Surface(color = Color.White) {
        PostScreen(
            state = Content(
                listOf(
                    Post(
                        userId = 1,
                        name = "A very very very very very username 1",
                        thumbnail = "",
                        title = "A very very very very very very very veeery very very long post title",
                        body = "Post body",
                    ),
                    Post(
                        userId = 2,
                        name = "Username 2",
                        thumbnail = "",
                        title = "Title 2",
                        body = "Body 2",
                    ),
                ),
            ),
            onRefresh = {},
        )
    }
}

@Composable
@Preview
private fun PostScreenErrorPreview() {
    Surface(color = Color.White) {
        PostScreen(state = Error, onRefresh = {})
    }
}

@Composable
@Preview
private fun PostScreenInitializePreview() {
    Surface(color = Color.White) {
        PostScreen(state = Uninitialized, onRefresh = {})
    }
}
