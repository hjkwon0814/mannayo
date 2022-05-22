package com.example.mannayoclient

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64.NO_WRAP
import android.util.Base64.encodeToString
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.ProfileFragBinding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment(R.layout.profile_frag) {
    lateinit var binding: ProfileFragBinding
    lateinit var mainActivity: SecondActivity
    lateinit var sharedPreferences: SharedPreferences
    var path: Uri? = null
    var realPath : String? = null
    var file: File? = null
    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_GALLERY = 102



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = ProfileFragBinding.bind(view)

        mainActivity = context as SecondActivity


        sharedPreferences = mainActivity.getSharedPreferences("Pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val id = sharedPreferences.getString("id", "0")?.toLongOrNull()



        binding.photoButton.setOnClickListener() {
            showDialg()
        }
        //완료누르면 메인홈으로
        binding.completion.setOnClickListener {
            //creating a file
            if(realPath != null) {
                file = File(realPath)
                if(!binding.editTextTextPersonName2.text.isNullOrEmpty()) {
                    sendNickNameAndImage(id, realPath)
                    editor.putString("nickname", binding.editTextTextPersonName2.text.toString())
                    editor.commit()
                    mainActivity.FragmentViewFromProfileToMainHome()
                }else {
                    Toast.makeText(activity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                if(!binding.editTextTextPersonName2.text.isNullOrEmpty()) {
                    sendNickNameOnly(id, binding.editTextTextPersonName2.text.toString())
                    editor.putString("nickname", binding.editTextTextPersonName2.text.toString())
                    editor.commit()
                    mainActivity.FragmentViewFromProfileToMainHome()
                }else {
                    Toast.makeText(activity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }

        }


        //닉네임 글자수에 따라 밑에 텍스트 바꾸기
        binding.editTextTextPersonName2.addTextChangedListener(object: TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.textView13.text = "0/7"
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var userinput = binding.editTextTextPersonName2.text.toString()
                binding.textView13.text = userinput.length.toString() + "/7"
            }

            override fun afterTextChanged(s: Editable?) {
                var userinput = binding.editTextTextPersonName2.text.toString()
                binding.textView13.text = userinput.length.toString() + "/7"
            }

        })



    }

    private fun showDialg() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.photo_dialog, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
            .setTitle("프로필 설정")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<View>(R.id.camera)?.setOnClickListener{
            if(isPermitted(CAMERA_PERMISSION) && isPermitted(STORAGE_PERMISSION)){
                openCamera()
            }else {
                ActivityCompat.requestPermissions(requireActivity(), CAMERA_PERMISSION, FLAG_PERM_CAMERA)
                if(isPermitted(CAMERA_PERMISSION)) {
                    openCamera()
                }
            }
            alertDialog.dismiss()
        }

        alertDialog.findViewById<View>(R.id.gallery)?.setOnClickListener{
            if(isPermitted(STORAGE_PERMISSION)) {
                openCallery()
            }else {
                ActivityCompat.requestPermissions(requireActivity(), STORAGE_PERMISSION, FLAG_PERM_STORAGE)
            }
            alertDialog.dismiss()
        }

    }

    private fun openCallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, FLAG_REQ_GALLERY)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, FLAG_REQ_CAMERA)
    }

    fun saveImageFile(filename:String, mimeType:String, bitmap: Bitmap) : Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        try {
            if (uri != null) {
                var descriptor = requireActivity().contentResolver.openFileDescriptor(uri, "w")

                if (descriptor != null) {
                    val fos = FileOutputStream(descriptor.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.close()
                    return uri
                }
            }
        } catch (e:Exception) {

        }
        return null

    }

    fun newFileName() :String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename =sdf.format(System.currentTimeMillis())
        return filename
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FLAG_REQ_CAMERA -> {
                    val myBitmap = data?.extras?.get("data") as Bitmap
                    if(myBitmap != null) {
                        val uri = data?.data
                        val imagePath = getImageUri(mainActivity, myBitmap)
                        path = imagePath
                        binding.photo.setImageBitmap(myBitmap)
                    }
                }
                FLAG_REQ_GALLERY -> {
                    val uri = data?.data
                    // 이미지 뷰에 선택한 이미지 출력
                    val imageview: ImageView = binding.photo
                    imageview.setImageURI(uri)
                    realPath = getRealPathFromURI(uri!!)
                }
            }
        }else if(resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(mainActivity,"사진 선택 취소",Toast.LENGTH_LONG).show()
        }
    }

    private fun isPermitted(permissions:Array<String>): Boolean {

        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(
                requireContext(),
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
        when (requestCode) {
            FLAG_PERM_CAMERA -> {
                var checked =true
                for(grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED){
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
        val cursor : Cursor? = requireActivity().contentResolver.query(contentURI,null,null,null,null)

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

    fun sendNickNameAndImage(id : Long? ,path : String?) {
        var requestBody : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("multipartFile",file?.name,requestBody)

        retrofitService.service.setNickname(id ,binding.editTextTextPersonName2.text.toString()).enqueue(object : Callback<ReceiveOK> {
            override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                if(response.isSuccessful) {
                    println("닉네임등록 성공")
                    retrofitService.service.setMyProfileImage(id, body).enqueue(object : Callback<ReceiveOK> {
                        override fun onResponse(
                            call: Call<ReceiveOK>,
                            response: Response<ReceiveOK>
                        ) {
                            val receive = response.body() as ReceiveOK
                            if(response.isSuccessful) {
                                mainActivity.FragmentViewFromProfileToMainHome()
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
        retrofitService.service.setNickname(id ,binding.editTextTextPersonName2.text.toString()).enqueue(object : Callback<ReceiveOK> {
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


}

data class ReceiveOK (
    @SerializedName("success")
    @Expose
    val success : Boolean,

    @SerializedName("code")
    @Expose
    val code : Int,

    @SerializedName("msg")
    @Expose
    val response: String,
)