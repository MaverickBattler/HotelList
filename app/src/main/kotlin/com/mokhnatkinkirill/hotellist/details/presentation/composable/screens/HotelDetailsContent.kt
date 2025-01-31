package com.mokhnatkinkirill.hotellist.details.presentation.composable.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.network.HttpException
import coil.request.ImageRequest
import com.mokhnatkinkirill.hotellist.R
import com.mokhnatkinkirill.hotellist.details.presentation.composable.design.Dimen
import com.mokhnatkinkirill.hotellist.details.presentation.composable.getColorFromAttr
import com.mokhnatkinkirill.hotellist.details.presentation.state.HotelDetailsUiState
import com.mokhnatkinkirill.hotellist.details.presentation.transformations.CropBorderTransformation

@Composable
fun HotelDetailsContent(
    hotel: HotelDetailsUiState.Content, onMapClicked: (lat: String, lon: String) -> Unit
) {
    val context = LocalContext.current
    val textColorMain = getColorFromAttr(R.attr.textColorMain, context)
    val softBackgroundColor = getColorFromAttr(R.attr.softBackgroundColor, context)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        when (hotel.image) {
            HotelDetailsUiState.Content.Image.NoImage -> {
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

            is HotelDetailsUiState.Content.Image.ImageSource -> {
                AsyncCoilImage(imageUrl = hotel.image.imagePath)
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
fun AsyncCoilImage(imageUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val softBackgroundColor = getColorFromAttr(R.attr.softBackgroundColor, context)
    val textColorMain = getColorFromAttr(R.attr.textColorMain, context)
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .transformations(CropBorderTransformation()) // removing the 1 px border
            .build(),
        contentDescription = null,
        modifier = modifier.height(Dimen.hotelImageSize),
        contentScale = ContentScale.Crop,
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimen.hotelImageSize)
                    .background(softBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        error = { error ->
            val throwable = error.result.throwable
            val textOnImage = if (throwable is HttpException && throwable.response.code != 404) {
                stringResource(R.string.failed_to_load_image)
            } else {
                stringResource(R.string.no_image_available)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimen.hotelImageSize)
                    .background(softBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = textOnImage, color = textColorMain)
            }
        }
    )
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