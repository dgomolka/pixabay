@file:OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)

package com.psycodeinteractive.pixabay.presentation.feature.launch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.TopCenter
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.psycodeinteractive.pixabay.presentation.PixabayTheme
import com.psycodeinteractive.pixabay.presentation.feature.NavGraphs
import com.psycodeinteractive.pixabay.presentation.feature.destinations.ImageDetailsScreenDestination
import com.psycodeinteractive.pixabay.presentation.feature.destinations.ImageListScreenDestination
import com.psycodeinteractive.pixabay.presentation.feature.destinations.SplashScreenDestination
import com.psycodeinteractive.pixabay.presentation.feature.imagedetails.ImageDetailsScreen
import com.psycodeinteractive.pixabay.presentation.feature.imagelist.ImageListScreen
import com.psycodeinteractive.pixabay.presentation.feature.splash.SplashScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations.Companion.ACCOMPANIST_FADING
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PixabayTheme {
                AppEntryPoint()
            }
        }
    }
}

@Composable
fun AppEntryPoint() {
    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = TopCenter,
        rootDefaultAnimations = ACCOMPANIST_FADING
    )

    DestinationsNavHost(
        navGraph = NavGraphs.root,
        engine = navHostEngine
    ) {
        composable(SplashScreenDestination) {
            SplashScreen {
                destinationsNavigator.popBackStack()
                destinationsNavigator.navigate(ImageListScreenDestination) {
                    launchSingleTop = true
                }
            }
        }
        composable(ImageListScreenDestination) {
            ImageListScreen { imageId ->
                destinationsNavigator.navigate(ImageDetailsScreenDestination(imageId = imageId))
            }
        }
        composable(ImageDetailsScreenDestination) {
            ImageDetailsScreen(
                imageId = navArgs.imageId
            ) {
                destinationsNavigator.popBackStack()
            }
        }
    }
}
