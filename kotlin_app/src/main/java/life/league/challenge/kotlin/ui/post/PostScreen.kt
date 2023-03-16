package life.league.challenge.kotlin.ui.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.ui.model.Post
import life.league.challenge.kotlin.ui.post.PostViewModel.State

@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    state: State
) {
    Column(modifier = modifier.fillMaxSize()) {
        when (state) {
            is State.Content -> Content(posts = state.posts)
            is State.Error -> Error()
            is State.Loading -> Loading()
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    posts: List<Post>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts) { post ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO: render from url
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp),
                    text = post.name,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            Row(modifier = Modifier.padding(top = 12.dp)) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = post.title,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = post.body,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun Error(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.error_message))
    }
}

@Composable
private fun Loading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
private fun PostScreenContentPreview() {
    Surface(color = Color.White) {
        PostScreen(
            state = State.Content(
                listOf(
                    Post(
                        userId = 1,
                        name = "A very very very very very username 1",
                        thumbnail = "",
                        title = "A very very very very very very very veeery very very long post title",
                        body = "Post body"
                    ),
                    Post(
                        userId = 2,
                        name = "Username 2",
                        thumbnail = "",
                        title = "Title 2",
                        body = "Body 2"
                    ),
                )
            )
        )
    }
}

@Composable
@Preview
private fun PostScreenErrorPreview() {
    Surface(color = Color.White) {
        PostScreen(state = State.Error)
    }
}

@Composable
@Preview
private fun PostScreenLoadingPreview() {
    Surface(color = Color.White) {
        PostScreen(state = State.Loading)
    }
}
