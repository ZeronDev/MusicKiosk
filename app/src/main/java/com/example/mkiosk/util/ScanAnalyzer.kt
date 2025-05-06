package com.example.mkiosk.util

import android.content.Context
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.ImageProxy
import com.example.mkiosk.util.BarcodeScanner.scan
import com.google.mlkit.vision.common.InputImage

class ScanAnalyzer(var stop: Boolean, val context: Context, val idChanger: Changer<String>) : Analyzer {
    companion object {
        var isProcessing = false
    }
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        if (stop || isProcessing) {
            imageProxy.close()
            return
        }
        imageProxy.image?.let {
            isProcessing = true
            val image = InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
            scan(image, context, idChanger, {_ -> isProcessing = false})
        }

        imageProxy.close()
    }
}