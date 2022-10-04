package com.example.mannayoclient

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.mannayoclient.databinding.ActivityInformationBinding
import com.example.mannayoclient.dto.ReceiveOK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class InformationActivity : AppCompatActivity() {
    lateinit var binding: ActivityInformationBinding


    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var path: Uri? = null
    var realPath : String? = null
    var file: File? = null
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_GALLERY = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val shared = getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val memberId = shared.getString("id", null)?.toLong()
        val editor = shared.edit()




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
            }

            //입력은 했지만 7자 초과
            if (!isNickFormat(binding.inforNick.text.toString()) && !binding.inforNick.text.isNullOrEmpty()) {
                binding.imageViewNick.visibility = View.VISIBLE
                binding.inforNick.setBackgroundResource(R.drawable.information4)
                binding.nickButton.setImageResource(R.drawable.information5)
            }


        }


        binding.nickButton.setOnClickListener {
            Toast.makeText(this, "check?", Toast.LENGTH_SHORT).show()
            //creating a file
            if(realPath != null) {
                println("add image")
                file = File(realPath)
                if(!binding.inforNick.text.isNullOrEmpty()) {
                    sendNickNameAndImage(memberId, realPath)
                    editor.putString("nickname", binding.inforNick.text.toString())
                    editor.commit()
                    onActivityChange()
                }else {
                    Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                println("only nickname")
                if(!binding.inforNick.text.isNullOrEmpty()) {
                    sendNickNameOnly(memberId, binding.inforNick.text.toString())
                    editor.putString("nickname", binding.inforNick.text.toString())
                    editor.commit()
                    onActivityChange()
                }else {
                    Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
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
                    val myBitmap = data?.extras?.get("data") as Bitmap
                    if(myBitmap != null) {
                        val imagePath = getImageUri(this, myBitmap)
                        path = imagePath
                        realPath = getRealPathFromURI(path!!)
                        binding.inforPhoto.setImageBitmap(myBitmap)
                    }
                }
                FLAG_REQ_GALLERY -> {
                    val uri = data?.data
                    // 이미지 뷰에 선택한 이미지 출력
                    val imageview: ImageView = binding.inforPhoto
                    imageview.setImageURI(uri)
                    realPath = getRealPathFromURI(uri!!)
                }
            }
        }else if(resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this,"사진 선택 취소", Toast.LENGTH_LONG).show()
        }
    }

    fun getImageUri(inContext : Context?, inImage: Bitmap?): Uri? {
        val btyes = ByteArrayOutputStream()
        if(inImage != null) {
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, btyes)
        }
        val path = MediaStore.Images.Media.insertImage(inContext?.contentResolver, inImage, "Title" + " - " + Calendar.getInstance().time,null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(contentURI : Uri) : String? {
        val result : String?
        val cursor : Cursor? = contentResolver.query(contentURI,null,null,null,null)

        if(cursor == null) {
            result = contentURI?.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }

        return result
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

    fun sendNickNameAndImage(id : Long? ,path : String?) {
        var requestBody : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("multipartFile",file?.name,requestBody)

        retrofitService.service.setNickname(id ,binding.inforNick.text.toString()).enqueue(object :
            Callback<ReceiveOK> {
            override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                if(response.isSuccessful) {
                    println("닉네임등록 성공")
                    retrofitService.service.setMyProfileImage(id, body).enqueue(object :
                        Callback<ReceiveOK> {
                        override fun onResponse(
                            call: Call<ReceiveOK>,
                            response: Response<ReceiveOK>
                        ) {
                            val receive = response.body() as ReceiveOK
                            if(response.isSuccessful) {
                                onActivityChange()
                            }
                        }

                        override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {
                            println("프로필등록 실패")
                        }

                    })
                }
            }

            override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {
                println("닉네임등록 실패")
            }

        })
    }

    fun sendNickNameOnly(id : Long?, nickname : String) {
        retrofitService.service.setNickname(id ,binding.inforNick.text.toString()).enqueue(object : Callback<ReceiveOK> {
            override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                if(response.isSuccessful) {
                    println("닉네임등록 성공")
                }
            }

            override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {
                println("닉네임등록 실패")
            }

        })
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

    fun onActivityChange() {
        val intent = Intent(this, MypageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}