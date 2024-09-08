package com.example.googletasksclone.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.graphics.painter.Painter

object ImagePainterUtil {

    /**
     * Resolves the appropriate Painter for images based on the current environment.
     *
     * @param previewResourceId Resource ID of the drawable used in preview mode.
     * @param networkImageUrl URL of the image to load at runtime. If null, uses the drawable resource.
     * @return A Painter that can be used to display the image.
     */
    @Composable
    fun resolvePainter(
        @DrawableRes previewResourceId: Int,
        networkImageUrl: String? = null
    ): Painter {
        val isPreview = LocalInspectionMode.current
        return if (isPreview) {
            // Use painterResource in preview mode
            painterResource(id = previewResourceId)
        } else {
            // Use rememberAsyncImagePainter for runtime image loading
            rememberAsyncImagePainter(model = networkImageUrl ?: previewResourceId)
        }
    }
}
