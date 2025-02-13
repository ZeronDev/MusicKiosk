package com.example.mkiosk.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.example.mkiosk.R
import com.example.mkiosk.util.Util.accountMap
import com.example.mkiosk.util.Util.recommendationMap
import com.example.mkiosk.util.Util.toast
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object BarcodeScanner {
    val options = BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_CODE_39).build()
    val scanner = BarcodeScanning.getClient(options)
    fun scan(image: InputImage, context: Context, idChanger: Changer<String>) {

        val result = scanner.process(image).addOnSuccessListener { barcodes ->
            if (barcodes.size != 1) {
                return@addOnSuccessListener
            }

            val barcode = barcodes.first()

            try {
                var id: String? = null
                if (barcode.valueType == Barcode.TYPE_TEXT && barcode.displayValue?.isDigitsOnly() == true) {
                    id = barcode.displayValue
                }
                Log.d("TEST", barcode.rawValue ?: "ERROR")
                id?.let {
                    accountMap[id] = accountMap[id] ?: mutableListOf()
                    idChanger(id)
                    context.toast(R.string.success)

                    val toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 1000)
                    toneGen.startTone(ToneGenerator.TONE_DTMF_S, 500) // 500ms "삐" 소리

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}