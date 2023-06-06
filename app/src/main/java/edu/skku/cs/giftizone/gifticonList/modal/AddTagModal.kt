package edu.skku.cs.giftizone.gifticonList.modal

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.common.BaseModal
import edu.skku.cs.giftizone.common.toast

class AddTagModal(
    private val context: Context,
    private val tagList: ArrayList<String>,
    private val addTagHandler: (String) -> Unit
): BaseModal(context, R.layout.tag_add_modal) {
    private val MAX_TAG_COUNT = 10
    private val MAX_TAG_LENGTH = 5

    private val confirmTagBtn = dialogLayout.findViewById<Button>(R.id.confirmTagButton)
    private val editTextTag = dialogLayout.findViewById<EditText>(R.id.editTextTag)

    init {
        confirmTagBtn.setOnClickListener {
            val tag = editTextTag.text.toString()

            if (!isValidTag(tag)) {
                return@setOnClickListener
            }
            addTagHandler(tag)
            dialog.dismiss()
        }

        dialog.setView(dialogLayout)
    }

    private fun isValidTag(tag: String): Boolean {
        if (tag.isEmpty()) {
            toast(context, "태그를 입력해주세요.")
            return false
        }
        if (tagList.contains(tag)) {
            toast(context, "이미 존재하는 태그입니다.")
            return false
        }
        if (tag.length > MAX_TAG_LENGTH) {
            toast(context, "태그는 ${MAX_TAG_LENGTH}자 이내로 입력해주세요.")
            return false
        }
        if (tagList.size >= MAX_TAG_COUNT) {
            toast(context, "태그는 ${MAX_TAG_COUNT}개 까지만 추가할 수 있습니다.")
            return false
        }
        return true
    }
}