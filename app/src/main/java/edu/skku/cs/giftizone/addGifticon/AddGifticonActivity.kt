package edu.skku.cs.giftizone.addGifticon

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import edu.skku.cs.giftizone.R
import edu.skku.cs.giftizone.common.Gifticon
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.util.*

class AddGifticonActivity : AppCompatActivity() {
    private var tagList: ArrayList<String>? = null
    private var localImageUrl: Uri? = null
    private var selectedTag: String? = null
    private var expiredDate: LocalDate? = null
    private val pickImageResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        val gifticonImage = findViewById<ImageView>(R.id.addGifticonImage)
        if (uri != null) {
            Glide.with(this)
                .load(uri)  // or Uri or File
                .into(gifticonImage)
            localImageUrl = uri
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gifticon)

        setupSelectImageBtn()
        setupSelectExpireDate()
        setupTagSelectDropdown()
        setupSaveGifticonBtn()
    }

    private fun pickImageFromGallery() {
        pickImageResultLauncher.launch("image/*")
    }

    private fun setupSelectImageBtn() {
        val gifticonImage = findViewById<ImageView>(R.id.addGifticonImage)
        gifticonImage.setImageResource(R.drawable.baseline_image_search_24)
        gifticonImage.setOnClickListener {
            pickImageFromGallery()
        }
    }

    private fun setupSelectExpireDate() {
        val selectExpireDateBtn = findViewById<ImageView>(R.id.calenderButton)
        val expireDateText = findViewById<TextView>(R.id.addExpireText)
        selectExpireDateBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                expireDateText.text = "$selectedYear/${selectedMonth+1}/$selectedDay"
                expiredDate = LocalDate.of(selectedYear, selectedMonth+1, selectedDay)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun setupTagSelectDropdown() {
        tagList = intent.getStringArrayListExtra("tagList")
        val addTagDropdown: Spinner = findViewById(R.id.addTagDropdown)

        addTagDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedTag = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedTag = parent.getItemAtPosition(0).toString()
            }
        }

        ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            tagList!!)
            .also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            addTagDropdown.adapter = adapter
        }
    }

    private fun setupSaveGifticonBtn() {
        val saveGifticonBtn = findViewById<Button>(R.id.addGifticonConfirmButton)
        saveGifticonBtn.setOnClickListener {
            if (!isValidGifticon())
                return@setOnClickListener

            val barcode = findViewById<EditText>(R.id.barcodeEdit).text.toString()
            val gifticonProvider = findViewById<EditText>(R.id.providerEdit).text.toString()
            val gifticonContent = findViewById<EditText>(R.id.contentEdit).text.toString()

            val imagePath = if (localImageUrl == null) "" else saveBitmapImage(uri2bitmap(localImageUrl!!)!!)!!
            val gifticon = Gifticon(imagePath, barcode, selectedTag!!, gifticonProvider, gifticonContent, expiredDate!!)
            val intent = Intent()
            intent.putExtra("gifticon", gifticon)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun uri2bitmap(uri: Uri): Bitmap? {
        return try {
            val pfd = contentResolver.openFileDescriptor(uri, "r")
            val fd = pfd?.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fd)
            pfd?.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
            toast("이미지를 불러오는데 실패했습니다.")
            return null
        }
    }

    private fun saveBitmapImage(bitmap: Bitmap): String? {
        val filename = "${System.currentTimeMillis()}.jpg"
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(dir, filename)

        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
            toast("이미지를 저장하는데 실패했습니다.")
            return null
        }

        return file.absolutePath
    }

    private fun isValidGifticon(): Boolean {
        val gifticonProvider = findViewById<EditText>(R.id.providerEdit).text.toString()
        if (gifticonProvider.isEmpty()) {
            toast("사용처를 입력해주세요.")
            return false
        }

        val gifticonContent = findViewById<EditText>(R.id.contentEdit).text.toString()
        if (gifticonContent.isEmpty()) {
            toast("상품명을 입력해주세요.")
            return false
        }

        val barcode = findViewById<EditText>(R.id.barcodeEdit).text.toString()
        if (barcode.isEmpty()) {
            toast("바코드를 입력해주세요.")
            return false
        }

        if (expiredDate == null) {
            toast("유효기간을 선택해주세요.")
            return false
        }
        
        if (selectedTag == null) {
            toast("태그를 선택해주세요.")
            return false
        }
        return true
    }

    private fun toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}