package com.mokhnatkinkirill.hotellist.data.repository

import android.app.Application
import android.graphics.Bitmap
import coil.ImageLoader
import coil.network.HttpException
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.mokhnatkinkirill.hotellist.data.network.HOTEL_IMAGE_ENDPOINT
import com.mokhnatkinkirill.hotellist.data.network.model.ImageLoadResult
import com.mokhnatkinkirill.hotellist.details.domain.repository.HotelImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class HotelImageRepositoryImpl(
    private val application: Application
) : HotelImageRepository {

    override suspend fun getHotelImage(imageName: String): ImageLoadResult =
        withContext(Dispatchers.IO) {
            val cachedFile = getCacheFile(imageName)

            if (cachedFile.exists()) {
                return@withContext ImageLoadResult.Image(cachedFile.absolutePath)
            }

            val imageUrl = "$HOTEL_IMAGE_ENDPOINT${imageName}"

            when (val imageLoadingResult = downloadAndProcessImage(imageUrl)) {
                is ImageLoadingResult.Image -> {
                    val bitmap = imageLoadingResult.bitmap
                    ImageLoadResult.Image(saveBitmapToFile(bitmap, imageName))
                }

                ImageLoadingResult.ErrorLoadingImage -> {
                    ImageLoadResult.FailedToLoad
                }

                ImageLoadingResult.ImageNotFound -> {
                    ImageLoadResult.ImageNotFound
                }
            }
        }

    private fun saveBitmapToFile(bitmap: Bitmap, imageName: String): String {
        val file = File(application.cacheDir, getHotelImageFileName(imageName))
        file.outputStream().use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        return file.absolutePath
    }

    private suspend fun downloadAndProcessImage(url: String): ImageLoadingResult {
        val imageLoader = ImageLoader(application)

        val request = ImageRequest.Builder(application)
            .data(url)
            .allowHardware(false)
            .build()

        val result = imageLoader.execute(request)
        if (result is SuccessResult) {
            val bitmap = (result.drawable as? android.graphics.drawable.BitmapDrawable)?.bitmap
            return bitmap?.let { ImageLoadingResult.Image(removeBorder(it)) }
                ?: ImageLoadingResult.ErrorLoadingImage
        } else if (result is ErrorResult) {
            val error = result.throwable
            if (error is HttpException && error.response.code == 404) {
                return ImageLoadingResult.ImageNotFound
            }
        }
        return ImageLoadingResult.ErrorLoadingImage
    }

    private fun getCacheFile(imageName: String): File {
        return File(application.cacheDir, getHotelImageFileName(imageName))
    }

    private fun removeBorder(bitmap: Bitmap): Bitmap {
        return Bitmap.createBitmap(
            bitmap, 1, 1, bitmap.width - 2, bitmap.height - 2
        )
    }

    private fun getHotelImageFileName(imageName: String): String {
        return "$HOTEL_IMAGE_FILE_PREFIX$imageName$HOTEL_IMAGE_FILE_EXT"
    }


    private sealed interface ImageLoadingResult {

        data object ImageNotFound : ImageLoadingResult

        data object ErrorLoadingImage : ImageLoadingResult

        data class Image(val bitmap: Bitmap) : ImageLoadingResult
    }

    private companion object {
        const val HOTEL_IMAGE_FILE_PREFIX = "hotel_"
        const val HOTEL_IMAGE_FILE_EXT = ".png"
    }
}