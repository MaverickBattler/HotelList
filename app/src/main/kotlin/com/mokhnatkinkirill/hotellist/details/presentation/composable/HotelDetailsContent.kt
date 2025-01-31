package com.mokhnatkinkirill.hotellist.details.presentation.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.details.presentation.state.HotelDetailsUiState

@Composable
fun HotelDetailsContent(
    hotel: HotelDetailsUiState.Content, onMapClicked: (lat: String, lon: String) -> Unit
) {
    val context = LocalContext.current
    val textColorMain = getColorFromAttr(R.attr.textColorMain, context)
    val primaryColor = getColorFromAttr(com.google.android.material.R.attr.colorPrimary, context)
    val softBackgroundColor = getColorFromAttr(R.attr.softBackgroundColor, context)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        when (hotel.image) {
            is HotelDetailsUiState.Content.Image.NoImage -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.hotelImageSize)
                        .background(softBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.no_image_available), color = textColorMain)
                }
            }

            is HotelDetailsUiState.Content.Image.ImageLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.hotelImageSize)
                        .background(softBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = primaryColor)
                }
            }

            is HotelDetailsUiState.Content.Image.ImageSource -> {
                CoilImage(imagePath = hotel.image.imagePath, imageHeight = Dimen.hotelImageSize)
            }
        }

        Spacer(modifier = Modifier.height(Dimen.distanceSmall))

        Column(modifier = Modifier.padding(Dimen.distanceMedium)) {
            // Hotel Name
            Text(
                text = hotel.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = textColorMain
            )

            // Address
            TextWithIcon(R.drawable.link_arrow, hotel.address) {
                onMapClicked(hotel.lat, hotel.lon)
            }

            // Rating Stars
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = hotel.star1),
                    contentDescription = stringResource(R.string.star_1),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(Dimen.starIconSize)
                )
                Icon(
                    painter = painterResource(id = hotel.star2),
                    contentDescription = stringResource(R.string.star_2),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(Dimen.starIconSize)
                )
                Icon(
                    painter = painterResource(id = hotel.star3),
                    contentDescription = stringResource(R.string.star_3),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(Dimen.starIconSize)
                )
                Icon(
                    painter = painterResource(id = hotel.star4),
                    contentDescription = stringResource(R.string.star_4),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(Dimen.starIconSize)
                )
                Icon(
                    painter = painterResource(id = hotel.star5),
                    contentDescription = stringResource(R.string.star_5),
                    tint = Color.Unspecified,
                    modifier = Modifier.size(Dimen.starIconSize)
                )
                Text(
                    style = MaterialTheme.typography.labelLarge,
                    text = hotel.starsValue,
                    modifier = Modifier.padding(start = Dimen.distanceMinimal),
                    color = textColorMain,
                )
            }

            Spacer(modifier = Modifier.height(Dimen.distanceSmall))

            // Distance
            Text(
                text = hotel.distance,
                style = MaterialTheme.typography.bodyMedium,
                color = textColorMain
            )

            Spacer(modifier = Modifier.height(Dimen.distanceSmall))

            // Suites Availability
            Text(
                text = hotel.suitesAvailability,
                style = MaterialTheme.typography.bodyMedium,
                color = textColorMain
            )
        }
    }
}

@Composable
fun CoilImage(imagePath: String, imageHeight: Dp) {
    val context = LocalContext.current
    val softBackgroundColor = getColorFromAttr(R.attr.softBackgroundColor, context)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(imageHeight)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(softBackgroundColor)
        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun TextWithIcon(@DrawableRes drawableRes: Int, text: String, onClickListener: () -> Unit) {
    val icon = painterResource(id = drawableRes)
    val context = LocalContext.current
    val textColorSecondary = getColorFromAttr(R.attr.textColorSecondary, context)
    Text(
        color = textColorSecondary,
        modifier = Modifier
            .padding(top = Dimen.distanceMinimal, bottom = Dimen.distanceSmall)
            .clickable {
                onClickListener()
            },
        style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline),
        text = buildAnnotatedString {
            append(text)
            appendInlineContent("arrow", "[arrow]")
        },
        inlineContent = mapOf(
            "arrow" to InlineTextContent(
                Placeholder(
                    width = 18.sp,
                    height = 18.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = textColorSecondary,
                )
            }
        )
    )
}