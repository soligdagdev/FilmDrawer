package com.soligdag.filmdrawer.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.soligdag.filmdrawer.R
import com.soligdag.filmdrawer.data.models.MediaItem
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.models.WishlistItem
import com.soligdag.filmdrawer.data.models.dummyMovie
import com.soligdag.filmdrawer.data.models.dummyMovieRecommendation
import com.soligdag.filmdrawer.data.models.dummyWishlist
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography

@Composable
fun MovieItemComposable(
    mediaItem: MediaItem,
    onMediaItemClicked: (mediaItem: MediaItem) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .clickable { onMediaItemClicked(mediaItem) }) {
        Box {
            Log.d(
                "Loading Image",
                "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + mediaItem.posterPath
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + mediaItem.posterPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.mi_poster),
                contentDescription = "poster",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(100.dp)
                    .height(150.dp)
            )
            RatingCircle(
                fillValue = mediaItem.voteAverage.toFloat(),
                modifier = Modifier.padding(top = 130.dp)
            )
        }

        Text(
            text = mediaItem.title,
            style = Typography.titleSmall.copy(fontSize = 14.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 2.dp, top = 4.dp)
                .width(100.dp)
                .align(Alignment.Start)
        )
        Text(
            text = mediaItem.releaseDate ,
            style = Typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(100.dp)
                .padding(start = 2.dp)
        )

    }
}

@Composable
fun SeriesItemComposable(
    mediaItem: MediaItem,
    onMediaItemClicked: (mediaItem: MediaItem) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .clickable { onMediaItemClicked(mediaItem) }) {
        Box {
            Log.d(
                "Loading Image",
                "https://image.tmdb.org/t/p/w300_and_h450_bestv2" + mediaItem.posterPath
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + mediaItem.posterPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.mi_poster),
                contentDescription = "poster",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(100.dp)
                    .height(150.dp)
            )
            RatingCircle(
                fillValue = mediaItem.voteAverage.toFloat(),
                modifier = Modifier.padding(top = 130.dp)
            )
        }

        Text(
            text = mediaItem.name,
            style = Typography.titleSmall.copy(fontSize = 14.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 2.dp, top = 4.dp)
                .width(100.dp)
                .align(Alignment.Start)
        )
        Text(
            text = mediaItem.releaseDate ,
            style = Typography.bodySmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(100.dp)
                .padding(start = 2.dp)
        )

    }
}


@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    FilmDrawerTheme {
        MovieItemComposable(dummyMovie)
    }
}

@Preview
@Composable
fun RatingCirclePreview() {
    FilmDrawerTheme {
        RatingCircle(6.3f, modifier = Modifier)
    }
}

@Preview
@Composable
fun RatingCircleBigPreview() {
    FilmDrawerTheme {
        RatingCircleBig(6.3f, modifier = Modifier)
    }
}


@Composable
fun RatingCircle(fillValue: Float, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background),
    ) {
        CircularProgressIndicator(
            progress = 1f, modifier = Modifier
                .size(34.dp)
                .padding(3.dp), color = Color.White.copy(alpha = 0.2f), strokeWidth = 1.dp
        )
        CircularProgressIndicator(
            progress = fillValue / 10, modifier = Modifier
                .size(34.dp)
                .padding(3.dp), color = getColorFromPercentageNumber(fillValue), strokeWidth = 2.dp
        )
        Text(
            text = getPercentageNumber(fillValue),
            style = Typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp
            ),
            textAlign = TextAlign.Center, modifier = Modifier
                .width(34.dp)
                .height(34.dp)
                .wrapContentHeight()
        )
    }
}

@Composable
fun RatingCircleBig(fillValue: Float, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(55.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background),
    ) {
        CircularProgressIndicator(
            progress = 1f, modifier = Modifier
                .size(55.dp)
                .padding(3.dp), color = Color.White.copy(alpha = 0.2f), strokeWidth = 2.dp
        )
        CircularProgressIndicator(
            progress = fillValue / 10, modifier = Modifier
                .size(55.dp)
                .padding(3.dp), color = getColorFromPercentageNumber(fillValue), strokeWidth = 4.dp
        )
        Text(
            text = getPercentageNumber(fillValue),
            style = Typography.titleSmall.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            ),
            textAlign = TextAlign.Center, modifier = Modifier
                .width(55.dp)
                .height(55.dp)
                .wrapContentHeight()
        )
    }
}


@Composable
fun RecentRecommendationItemComposable(
    recommendation: Recommendation,
    onMediaItemClicked: () -> Unit = {}
) {
    Column(modifier = Modifier.clickable { onMediaItemClicked() }) {
        Row(Modifier.padding(4.dp)) {
            Text(
                text = "by " + recommendation.recommenderName,
                style = Typography.bodySmall.copy(fontSize = 10.sp)
            )
        }
        MovieItemComposable(mediaItem = recommendation.mediaItem)
    }
}


@Preview
@Composable
private fun RecentRecommendationItemPreview() {
    FilmDrawerTheme {
        RecentRecommendationItemComposable(dummyMovieRecommendation)
    }

}


@Composable
fun WatchListMediaItemComposable(
    modifier: Modifier,
    wishlistItem: WishlistItem,
    onItemClicked: () -> Unit = {},
    onRemoveFromListClicked: () -> Unit = {},
    onSendAsRecommendationClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .height(160.dp)
            .fillMaxWidth()
            .clickable { onItemClicked() }
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + wishlistItem.posterPath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.mi_poster),
                contentDescription = "poster",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(100.dp)
                    .height(150.dp)
            )
            RatingCircle(
                fillValue = wishlistItem.voteAverage.toFloat(),
                modifier = Modifier.padding(top = 130.dp)
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = wishlistItem.title,
                    //text = "Three billboards outside ebbing missouri",
                    style = Typography.titleMedium.copy(fontSize = 18.sp),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(0.85f)
                )
                Dropdown(
                    menuItems = listOf(
                        "Remove from list",
                        "Share as recommendation"
                    ),
                    onItemSelected = { selectedItemIndex ->
                        when (selectedItemIndex) {
                            0 -> {onRemoveFromListClicked()}
                            1 -> {onSendAsRecommendationClicked()}
                        }

                    })


            }

            Row(modifier = Modifier.padding()) {
                if (wishlistItem.mediaType == "movie") {
                    Icon(
                        Icons.Default.Movie,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                    )
                } else {
                    Icon(
                        Icons.Default.Tv,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                Text(text = wishlistItem.mediaType, Modifier.padding(start = 8.dp))
            }

            Text(
                text = wishlistItem.overview,
                style = Typography.bodyMedium.copy(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    lineHeight = 16.sp
                ),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )


        }


    }
}

@Preview(showBackground = true)
@Composable
fun WatchlistMediaItemPreview() {
    FilmDrawerTheme {
        WatchListMediaItemComposable(modifier = Modifier, wishlistItem = dummyWishlist[0])
    }
}


private fun getPercentageNumber(fillValue: Float): String {
    val percentage = (fillValue * 10).toInt()
    return "$percentage"
}

private fun getColorFromPercentageNumber(fillValue: Float): Color {
    return if (fillValue > 7f)
        Color(red = 0.2f, green = 0.8f, blue = 0f)
    else if (fillValue > 5f)
        Color(red = 0.8f, green = 0.8f, blue = 0f)
    else if (fillValue > 3f)
        Color(red = 0.8f, green = 0.5f, blue = 0f)
    else
        Color(red = 0.8f, green = 0.2f, blue = 0f)
}

