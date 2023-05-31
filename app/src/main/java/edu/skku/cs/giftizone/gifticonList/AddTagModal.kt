package edu.skku.cs.giftizone.gifticonList

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import edu.skku.cs.giftizone.R

class AddTagModal(
    private val context: Context,
    private val tagList: ArrayList<String>,
    private val addTagHandler: (String) -> Unit
) {
    private val inflater = LayoutInflater.from(context)
    private val dialogLayout = inflater.inflate(R.layout.tag_add_modal, null)
    private val confirmTagBtn = dialogLayout.findViewById<Button>(R.id.confirmTagButton)
    private val cancelTagBtn = dialogLayout.findViewById<ImageButton>(R.id.cancelTagButton)
    private val editTextTag = dialogLayout.findViewById<EditText>(R.id.editTextTag)

    private val tagDialog = AlertDialog.Builder(context).create()

    init {
        tagDialog.setView(dialogLayout)
        confirmTagBtn.setOnClickListener {
            val tag = editTextTag.text.toString()

            if (!isValidTag(tag)) {
                return@setOnClickListener
            }

            addTagHandler(tag)
            tagDialog.dismiss()
        }
        cancelTagBtn.setOnClickListener {
            tagDialog.cancel()
        }
    }

    fun show() {
        tagDialog.show()
    }

    private fun isValidTag(tag: String): Boolean {
        if (tag.isEmpty()) {
            Toast.makeText(context, "태그를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (tagList.contains(tag)) {
            Toast.makeText(context, "이미 존재하는 태그입니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (tag.length > 6) {
            Toast.makeText(context, "태그는 6자 이내로 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (tagList.size >= 10) {
            Toast.makeText(context, "태그는 5개까지만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}