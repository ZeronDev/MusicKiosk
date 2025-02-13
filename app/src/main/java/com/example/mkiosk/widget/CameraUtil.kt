package com.example.mkiosk.widget

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.mkiosk.R
import com.example.mkiosk.cameraExecutor
import com.example.mkiosk.util.Changer
import com.example.mkiosk.util.ScanAnalyzer
import com.example.mkiosk.util.Util.toast
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object CameraUtil {
//    lateinit var analyzer: ScanAnalyzer
    @Composable
    fun CameraView(idChanger: Changer<String>, isLogined: Boolean, isAdmin: Boolean) {
        val lifecycleOwner = LocalLifecycleOwner.current
        val context = LocalContext.current
        val previewView = remember { PreviewView(context) }

        val cameraProviderF = ProcessCameraProvider.getInstance(context)
        LaunchedEffect(isLogined) {
            val cameraProvider = cameraProviderF.get()
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
            if (isAdmin) {
                Log.d("TEST", "isAdmin")
            }

            val imageAnalyzer = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(cameraExecutor, ScanAnalyzer(isLogined || isAdmin, context, idChanger))
            }
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalyzer)
            } catch (e: Exception) {
                Log.e("Camera Preview Problem", "Preview Failed : $e")
                context.toast(R.string.error)
            }
        }
        AndroidView(factory = { previewView }, modifier = Modifier.fillMaxHeight(0.75f).fillMaxWidth(0.25f).padding(15.dp).clip(RoundedCornerShape(20.dp)))
    }


//    fun takePhoto() : Account? {
//        var account: Account? = null
//        imageCapture ?: return account
//        val outputOption = ImageCapture.OutputFileOptions.Builder(File(context.getPath(), context.getString(R.string.processing))).build()
//        imageCapture!!.takePicture(outputOption, ContextCompat.getMainExecutor(context),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(exception: ImageCaptureException) {
//                    Log.e("Capture Problem", "Capture Failed : $exception")
//                    context.toast(R.string.error)
//                }
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    faceDetect(context, outputFileResults.savedUri!!)
//                }
//            })
//        return account
//    }
}