package life.league.challenge.kotlin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import life.league.challenge.kotlin.ui.model.Post
import life.league.challenge.kotlin.ui.post.PostViewModel
import life.league.challenge.kotlin.ui.post.PostViewModel.UiEvent


@Composable
fun PostsList(posts: List<Post>, modifier: Modifier = Modifier) {
    Box(modifier) {
        LazyColumn(Modifier.fillMaxWidth()) {
            items(items = posts) { post ->
                PostItem(post)
            }
        }
    }
}

@Composable
fun PostItem(post: Post) {
    Box(Modifier.fillMaxWidth()) {
        PostThumbnail(thumbnail = post.thumbnail)
        PostTitle(title = post.title)
        PostUsername(name = post.name)
        PostBody(body = post.body)
    }
}

@Composable
fun PostBody(body: String) {
    TODO("Not yet implemented")
}

@Composable
fun PostUsername(name: String) {
    TODO("Not yet implemented")
}

@Composable
fun PostThumbnail(thumbnail: String) {
    Image(
        painter = rememberImagePainter(
            data = thumbnail,
            builder = { transformations(CircleCropTransformation()) }
        ),
        contentDescription = null,
        modifier = Modifier.size(35.dp)
    )
}

@Composable
fun PostTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h4
    )

}

@Preview
@Composable
fun ErrorMessage(message: String = "Something went wrong. Swipe to refresh.") {
    Text(text = message)

}

@Preview
@Composable
fun ProgressBar() {
    CircularProgressIndicator()
}