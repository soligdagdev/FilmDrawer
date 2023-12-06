package com.soligdag.filmdrawer.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filmdrawer.R
import com.soligdag.filmdrawer.data.models.Recommendation
import com.soligdag.filmdrawer.data.models.dummyMovieRecommendation
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography


@Composable
fun RecommendationItemComposable(
    modifier: Modifier,
    recommendation: Recommendation
) {
    Row(modifier = modifier.padding(16.dp).height(160.dp).fillMaxWidth(1f)) {
        Box() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + recommendation.mediaItem.posterPath)
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
                fillValue = recommendation.mediaItem.voteAverage.toFloat(),
                modifier = Modifier.padding(top = 130.dp)
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp).align(CenterVertically)) {
            if(recommendation.isReceivedRecommendation) {
                Text(
                    text = "by " + recommendation.recommenderName,
                    style = Typography.bodyMedium.copy()
                )
            }
            else {
                Text(
                    text = "to " + recommendation.receiverName,
                    style = Typography.bodyMedium.copy()
                )
            }

            Text(
                text = "on " + recommendation.date,
                style = Typography.bodyMedium.copy()
            )
            Text(
                text = if(recommendation.mediaItem.mediaType == "movie") recommendation.mediaItem.title else recommendation.mediaItem.name,
                style = Typography.titleMedium.copy(fontSize = 18.sp),
                modifier = Modifier.padding(top = 4.dp)
            )
            Row(modifier = Modifier.padding()) {
                if(recommendation.mediaItem.mediaType == "movie") {
                    Icon(
                        Icons.Default.Movie,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                else {
                    Icon(
                        Icons.Default.Tv,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                Text(text = recommendation.mediaItem.mediaType, Modifier.padding(start = 8.dp))
            }

            Text(text = recommendation.recommendationText, style = Typography.bodyMedium.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, lineHeight = 16.sp),
            maxLines = 4, overflow = TextOverflow.Ellipsis)


        }

    }
}

@Preview(showBackground = true)
@Composable
private fun RecommendationItemComposablePreview() {
    FilmDrawerTheme {
        RecommendationItemComposable(modifier = Modifier, dummyMovieRecommendation)
    }

}