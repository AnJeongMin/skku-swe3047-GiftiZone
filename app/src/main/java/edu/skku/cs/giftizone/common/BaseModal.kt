package edu.skku.cs.giftizone.common

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog

open class BaseModal(context: Context, layoutId: Int) {
    protected val inflater = LayoutInflater.from(context)
    protected val dialogLayout = inflater.inflate(layoutId, null)
    protected val dialog = AlertDialog.Builder(context).create()

    fun show() {
        dialog.show()
    }
}