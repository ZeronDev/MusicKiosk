package com.example.mkiosk.util

import android.content.Context
import com.example.mkiosk.R
import com.example.mkiosk.data.DataStorage.grant
import com.example.mkiosk.util.Util.toast
import com.gun0912.tedpermission.PermissionListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PermissionListen(val context: Context, val grantedChanger: Changer<Boolean>) : PermissionListener {
    override fun onPermissionGranted() {
        grantedChanger(true)
        CoroutineScope(Dispatchers.IO).launch { grant(context) }
    }
    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        context.toast(R.string.permission_denied)
    }
}