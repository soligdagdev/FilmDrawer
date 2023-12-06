package com.soligdag.filmdrawer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.soligdag.filmdrawer.data.models.CastMember
import com.soligdag.filmdrawer.data.models.MediaPerson
import com.soligdag.filmdrawer.data.models.dummyCastMember
import com.soligdag.filmdrawer.data.models.dummyMediaPerson
import com.soligdag.filmdrawer.ui.theme.FilmDrawerTheme
import com.soligdag.filmdrawer.ui.theme.Typography

@Composable
fun CastItemComposable(castMember: CastMember) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(4.dp)) {
        Box() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w276_and_h350_face/"+castMember.profilePath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.mi_poster),
                contentDescription = "poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(80.dp)
                    .height(120.dp)
            )
        }

        Text(
            text =  castMember.name?:"",
            style = Typography.titleSmall.copy(fontSize = 12.sp, lineHeight = 13.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 2.dp, top = 4.dp)
                .width(80.dp)
                .align(Alignment.Start)
        )
        Text(
            text =  castMember.character?:"",
            style = Typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 12.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(80.dp)
                .padding(start = 2.dp)
        )

    }
}


@Composable
fun MediaPersonComposable(mediaPerson: MediaPerson, onMediaPersonClicked : () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(4.dp).clickable { onMediaPersonClicked() }) {
        Box() {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w276_and_h350_face/"+mediaPerson.profilePath)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.mi_poster),
                contentDescription = "poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(80.dp)
                    .height(120.dp)
            )
        }

        Text(
            text =  mediaPerson.name?:"",
            style = Typography.titleSmall.copy(fontSize = 12.sp, lineHeight = 13.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 2.dp, top = 4.dp)
                .width(80.dp)
                .align(Alignment.Start)
        )
        Text(
            text =  mediaPerson.knownForDepartment?:"",
            style = Typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 12.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(80.dp)
                .padding(start = 2.dp)
        )

    }
}
@Preview
@Composable
fun castMemberPreview() {
    FilmDrawerTheme {
        CastItemComposable(castMember = dummyCastMember)
    }
}


@Preview
@Composable
fun mediaPersonPreview() {
    FilmDrawerTheme {
        MediaPersonComposable(mediaPerson = dummyMediaPerson,{})
    }
}