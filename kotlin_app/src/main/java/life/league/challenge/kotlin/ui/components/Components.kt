package life.league.challenge.kotlin.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import life.league.challenge.kotlin.ui.model.Post

@Composable
fun PostsList(posts: List<Post>, modifier: Modifier = Modifier) {
    Row {
        LazyColumn(
            Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(items = posts) { index, post ->
                PostItem(post)
                Spacer(modifier.height(22.dp))
                if (index != posts.size) Divider(modifier)
            }
        }
    }
}

@Composable
fun PostItem(post: Post, modifier: Modifier = Modifier) {
    ConstraintLayout(Modifier.fillMaxWidth()) {
        val (thumbnail, title, username, body) = createRefs()

        PostThumbnail(
            thumbnail = post.thumbnail,
            modifier = Modifier.constrainAs(thumbnail) {
                top.linkTo(parent.top, margin = 22.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        )
        PostUsername(
            name = post.name,
            modifier = Modifier.constrainAs(username) {
                top.linkTo(thumbnail.top)
                start.linkTo(thumbnail.end, margin = 6.dp)
            }
        )
        PostTitle(
            title = post.title,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(username.bottom)
                    bottom.linkTo(thumbnail.bottom)
                    start.linkTo(thumbnail.end, margin = 6.dp)
                }
                .fillMaxWidth()
        )
        PostBody(
            body = post.body,
            modifier = Modifier
                .constrainAs(body) {
                    top.linkTo(thumbnail.bottom, margin = 6.dp)
                    start.linkTo(parent.start, margin = 22.dp)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
        )
    }
}

@Composable
fun PostThumbnail(thumbnail: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberImagePainter(
            data = thumbnail,
            builder = { transformations(CircleCropTransformation()) }
        ),
        contentDescription = null,
        modifier = modifier.size(55.dp)
    )
}

@Preview
@Composable
fun PostUsername(name: String = "Username", modifier: Modifier = Modifier) {
    Text(
        text = name,
        style = MaterialTheme.typography.h6,
        modifier = modifier
    )
}

@Preview
@Composable
fun PostTitle(title: String = "Title", modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.subtitle2,
        modifier = modifier
    )
}

@Preview
@Composable
fun PostBody(body: String = "Text body", modifier: Modifier = Modifier) {
    Text(
        text = body,
        style = MaterialTheme.typography.body2,
        modifier = modifier,
        softWrap = true
    )
}

@Preview
@Composable
fun ErrorMessage(message: String = "Something went wrong. Swipe to refresh.") {
    ConstraintLayout(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        val error = createRef()

        Text(
            text = message,
            modifier = Modifier.constrainAs(error) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        )
    }
}

@Preview
@Composable
fun ProgressBar() {
    ConstraintLayout(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        val loading = createRef()

        CircularProgressIndicator(modifier = Modifier.constrainAs(loading) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}