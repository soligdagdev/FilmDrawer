package com.soligdag.filmdrawer.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.soligdag.filmdrawer.FilmDrawerApplication
import com.soligdag.filmdrawer.R
import com.soligdag.filmdrawer.data.models.CastMember
import com.soligdag.filmdrawer.data.models.CrewMember
import com.soligdag.filmdrawer.data.models.MovieDetail
import com.soligdag.filmdrawer.data.models.dummyCrewMemberDirector
import com.soligdag.filmdrawer.data.models.dummyListOfCastMembers
import com.soligdag.filmdrawer.data.models.dummyMovieDetail
import com.soligdag.filmdrawer.ui.components.BackBtn
import com.soligdag.filmdrawer.ui.components.CastItemComposable
import com.soligdag.filmdrawer.ui.components.Dropdown
import com.soligdag.filmdrawer.ui.components.LoadingDialog
import com.soligdag.filmdrawer.ui.components.RatingCircleBig
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography
import com.soligdag.filmdrawer.ui.util.Utility
import com.soligdag.filmdrawer.ui.viewmodels.MovieDetailViewModel
import com.soligdag.filmdrawer.ui.viewmodels.viewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int = 0,
    onAddedToWishList : () -> Unit = {},
    onBackBtnPressed: () -> Unit = {},
    onShareBtnPressed : () -> Unit = {},
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    var showShareScreen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    if (uiState.isLoading) {
        LoadingDialog()
    }
    if(uiState.addedToWishlist) {
        LaunchedEffect(Unit) {
            viewModel.resetState()
            onAddedToWishList()
        }
    }
    if (uiState.movieDetail != null && uiState.castList != null) {
        movieDetailContent(
            movieDetail = uiState.movieDetail!!,
            castMembers = uiState.castList!!.castMembers,
            onAddToWishlistClicked = {
                viewModel.addMovieToWishList(uiState.movieDetail!!)
            }, onBackBtnPressed = { onBackBtnPressed() },
            onShareAsRecommendationClicked = {
                //onShareBtnPressed()
                showShareScreen = true
            }
        )
    }

    if(showShareScreen) {
        ModalBottomSheet(
            onDismissRequest = {
                showShareScreen = false
            },
            sheetState = sheetState
        ) {
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showShareScreen = false
                    }
                }
            }) {
                Text("Hide bottom sheet")
            }

        }
    }
}

@Composable
private fun movieDetailContent(
    movieDetail: MovieDetail = dummyMovieDetail,
    castMembers: List<CastMember> = dummyListOfCastMembers,
    director: CrewMember = dummyCrewMemberDirector,
    onMarkAsFavouriteClicked: () -> Unit = {},
    onShareAsRecommendationClicked: () -> Unit = {},
    onAddToWishlistClicked: () -> Unit = {},
    onBackBtnPressed : () -> Unit = {}
) {
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
    Box(
        Modifier
            .width(1700.dp)
            .height(350.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/original/" + movieDetail.backdropPath)
                .crossfade(true)
                .build(),
            //placeholder = painterResource(R.drawable.mi_poster),
            contentDescription = "poster",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(350.dp)
                .scale(2f)
                .onGloballyPositioned { sizeImage = it.size }
        )


    }

    Box(
        modifier = Modifier
            .size(width = sizeImage.width.dp, 420.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background.copy(
                            alpha = 0.7f
                        ),
                        MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {


        Row(modifier = Modifier.padding(start = 16.dp, top = 48.dp, end = 16.dp)) {
            BackBtn(modifier = Modifier) {
                onBackBtnPressed()
            }
            Spacer(modifier = Modifier.weight(1f))
            Dropdown(
                menuItems = listOf(
                    "Add to Wish list",
                    "Mark as favourite",
                    "Share as recommendation"
                ),
                onItemSelected = { selectedItemIndex ->
                    when (selectedItemIndex) {
                        0 -> {
                            onAddToWishlistClicked()
                        }

                        1 -> {
                            onMarkAsFavouriteClicked()
                        }

                        2 -> {
                            onShareAsRecommendationClicked()
                        }
                    }

                })
        }

        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            Log.d(
                "Loading Image",
                "https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + movieDetail.posterPath
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w300_and_h450_bestv2/" + movieDetail.posterPath)
                    .crossfade(true)
                    .build(),
                //placeholder = painterResource(R.drawable.mi_poster),
                contentDescription = "poster",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(100.dp)
                    .height(150.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = movieDetail.title ?: "",
                    style = Typography.titleMedium.copy(fontSize = 20.sp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = movieDetail.tagline ?: "",
                    style = Typography.titleSmall.copy(
                        fontSize = 12.sp,
                        lineHeight = 13.sp,
                        fontStyle = FontStyle.Italic
                    )
                )

                Row(modifier = Modifier.padding(top = 16.dp)) {
                    Row(
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "backItem", modifier = Modifier
                                .padding(start = 6.dp, top = 4.dp, bottom = 4.dp)
                                .size(18.dp)
                        )
                        Text(
                            text = movieDetail.releaseDate ?: "",
                            modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp),
                            style = Typography.titleMedium.copy(fontSize = 14.sp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Filled.Timelapse,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = "backItem", modifier = Modifier
                                .padding(start = 6.dp, top = 4.dp, bottom = 4.dp)
                                .size(18.dp)
                        )
                        Text(
                            text = Utility.GetDisplayTimeFromMinutes(movieDetail.runtime ?: 0),
                            modifier = Modifier.padding(start = 6.dp, top = 4.dp, end = 6.dp),
                            style = Typography.titleMedium.copy(fontSize = 14.sp)
                        )
                    }
                }

                Row(modifier = Modifier.padding(top = 16.dp)) {

                    for (i in 0..(movieDetail.genres.size - 1).coerceIn(0, 2)) {
                        Row(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .border(
                                    border = BorderStroke(
                                        width = 1.dp,
                                        MaterialTheme.colorScheme.primary
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                )
                        ) {
                            Text(
                                text = movieDetail.genres[i].name ?: "",
                                style = Typography.titleMedium.copy(fontSize = 12.sp),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        Text(
                            text = "User score",
                            style = Typography.titleSmall.copy(fontSize = 14.sp)
                        )
                        Text(
                            text = "(" + movieDetail.voteCount + " votes)",
                            style = Typography.bodyMedium.copy(fontSize = 10.sp)
                        )
                    }
                    RatingCircleBig(
                        fillValue = movieDetail.voteAverage?.toFloat() ?: 0f,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }


            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = "Overview",
                style = Typography.titleMedium.copy(fontSize = 16.sp),
                modifier = Modifier.padding(start = 6.dp, top = 8.dp)
            )
            Text(
                text = movieDetail.overview ?: "",
                style = Typography.bodyMedium.copy(fontSize = 14.sp, lineHeight = 15.sp),
                modifier = Modifier.padding(start = 6.dp, top = 1.dp, end = 6.dp, bottom = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = "Credits",
                style = Typography.titleMedium.copy(fontSize = 16.sp),
                modifier = Modifier.padding(start = 6.dp, top = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                itemsIndexed(castMembers) { index, item ->
                    CastItemComposable(item)
                }
            }
            Text(
                text = "Director",
                modifier = Modifier.padding(start = 6.dp, top = 6.dp),
                style = Typography.titleMedium.copy()
            )
            Text(
                text = director.name ?: "",
                modifier = Modifier.padding(start = 6.dp, top = 2.dp, bottom = 8.dp),
                style = Typography.bodyMedium.copy()
            )
        }
        Spacer(modifier = Modifier.padding(48.dp))
    }


}

@Preview(showBackground = true)
@Composable
private fun MediaDetailPreview() {
    FilmDrawerTheme() {
        movieDetailContent()
    }

}


private fun getDirectorFromCrewList(crewMembers: List<CrewMember>): CrewMember {
    for (member in crewMembers) {
        if (member.job == "Director")
            return member
    }
    return CrewMember()
}