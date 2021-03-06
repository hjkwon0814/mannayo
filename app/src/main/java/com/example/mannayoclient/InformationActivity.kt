package com.example.mannayoclient

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.mannayoclient.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {
    lateinit var binding: ActivityInformationBinding

    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_GALLERY = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inforButton.setOnClickListener {
            showDialg()
        }


        binding.inforNick.addTextChangedListener {

            //닉네임 한글또는 영어로 일곱자 이내 혼용은 불가능
            fun isNickFormat(name: String): Boolean {
                return name.matches("^[가-힣]{1,7}|[a-zA-Z]{1,7}\$".toRegex())
            }

            //아무것도 입력안했을 떄
            if (binding.inforNick.text.isNullOrEmpty()) {
                binding.imageViewNick.visibility = View.GONE
                binding.inforNick.setBackgroundResource(R.drawable.information3)
                binding.nickButton.setImageResource(R.drawable.information5)
            }

            //닉네임 형식에 충족 ++ 중복은 아직 처리 못함
            /* 중복일 때
                binding.imageViewNick2.visibility = View.VISIBLE
                binding.inforNick.setBackgroundResource(R.drawable.information4)
                binding.nickButton.setImageResource(R.drawable.information5)
            */
            if (isNickFormat(binding.inforNick.text.toString()) && !binding.inforNick.text.isNullOrEmpty()) {
                binding.imageViewNick.visibility = View.GONE
                binding.inforNick.setBackgroundResource(R.drawable.information6)
                binding.nickButton.setImageResource(R.drawable.information7)
                binding.nickButton.setOnClickListener {
                    //어디로 이동할지
                }
            }

            //입력은 했지만 7자 초과
            if (!isNickFormat(binding.inforNick.text.toString()) && !binding.inforNick.text.isNullOrEmpty()) {
                binding.imageViewNick.visibility = View.VISIBLE
                binding.inforNick.setBackgroundResource(R.drawable.information4)
                binding.nickButton.setImageResource(R.drawable.information5)
            }


        }

    }


    private fun showDialg() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.photo_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("프로필 설정")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<View>(R.id.camera)?.setOnClickListener {
            if (isPermitted(CAMERA_PERMISSION)) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(this, CAMERA_PERMISSION, FLAG_PERM_CAMERA)
            }
            alertDialog.dismiss()
        }
        alertDialog.findViewById<View>(R.id.gallery)?.setOnClickListener {
            if (isPermitted(STORAGE_PERMISSION)) {
                openCallery()
            } else {
                ActivityCompat.requestPermissions(this, STORAGE_PERMISSION, FLAG_PERM_STORAGE)
            }
            alertDialog.dismiss()
        }

    }

    private fun openCallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_GALLERY)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, FLAG_REQ_CAMERA)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FLAG_REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data?.extras?.get("data") as Bitmap


                        binding.inforPhoto.setImageBitmap(bitmap)
                    }
                }
                FLAG_REQ_GALLERY -> {
                    val uri = data?.data
                    binding.inforPhoto.setImageURI(uri)
                }
            }
        }
    }

    private fun isPermitted(permissions: Array<String>): Boolean {

        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(
                this,
                permission
            )
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            FLAG_PERM_CAMERA -> {
                var checked = true
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        checked = false
                        break
                    }
                }
                if (checked) {
                    openCamera()
                }
            }
        }
    }
}