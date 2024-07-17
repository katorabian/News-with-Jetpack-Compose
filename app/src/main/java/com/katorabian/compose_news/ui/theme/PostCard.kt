package com.katorabian.compose_news.ui.theme

import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.katorabian.compose_news.R

@Preview
@Composable
fun PostCard() {
    ComposeNewsTheme {
        Card(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.fillMaxWidth(),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                disabledContentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {

            Column(modifier = Modifier.padding(8.dp, 4.dp, 8.dp, 16.dp)) {
                PostCardHeader()
                Text(
                    text = "Description",
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.FillWidth
                )
                Row {
                    TextWithImage()
                    Spacer(modifier = Modifier.weight(1F))
                    TextWithImage()
                    Spacer(modifier = Modifier.width(12.dp))
                    TextWithImage()
                    Spacer(modifier = Modifier.width(12.dp))
                    TextWithImage()
                }
            }
        }
    }
}

@Composable
fun PostCardHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.post_comunity_thumbnail),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "/dev/null",
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "14:00",
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "more",
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview
@Composable
private fun PostCardHeaderLight() {
    ComposeNewsTheme(darkTheme = false) {
        PostCardHeader()
    }
}

@Preview
@Composable
private fun PostCardHeaderDark() {
    ComposeNewsTheme(darkTheme = true) {
        PostCardHeader()
    }
}

@Preview
@Composable
fun TextWithImage() {
    val tempText = "666"
    Row {
        Text(
            text = tempText,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = tempText,
            modifier = Modifier.height(16.sp.value.dp),
            contentScale = ContentScale.FillHeight,
//            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )
    }
}