package life.league.challenge.kotlin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import life.league.challenge.kotlin.ui.post.PostScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Routes.PostFragmentScreen.route
                    ) {
                        composable(route = Routes.PostFragmentScreen.route) {
                            PostScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }

    sealed class Routes(val route: String) {
        object PostFragmentScreen : Routes("posts_screen")
        object ComicFragmentScreen : Routes("comic_screen")
    }

}
