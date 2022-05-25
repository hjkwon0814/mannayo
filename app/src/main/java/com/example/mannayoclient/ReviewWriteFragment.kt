package com.example.mannayoclient

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.ReviewwriteFragBinding
import com.example.mannayoclient.databinding.SearchFragBinding
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
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.locks.ReentrantLock

class ReviewWriteFragment : Fragment(R.layout.reviewwrite_frag) {
    lateinit var binding: ReviewwriteFragBinding
    lateinit var activity : SecondActivity
    var path: Uri? = null
    var realPath : String? = null
    var file: File? = null
    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_GALLERY = 102


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ReviewwriteFragBinding.bind(view)
        activity = context as SecondActivity

        val sharedPreferences = activity.getSharedPreferences("Pref", Context.MODE_PRIVATE)

        binding.photoButton2.setOnClickListener {
            showDialg()
        }



        binding.completion.setOnClickListener {
            val request = review(
                memberId = sharedPreferences.getString("id", null)?.toLong(),
                restaurantId = sharedPreferences.getString("restaurantId", null)?.toLong(),
                content = binding.editTextTextPersonName3.text.toString(),
                starPoint = binding.ratingBar.rating
            )
            retrofitService.service.setReview(request).enqueue(object : Callback<ReceiveOK> {
                override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                    val receive = response.body() as ReceiveOK
                    if(response.isSuccessful && receive.success) {
                        if(realPath != null) {
                            file = File(realPath)
                            var requestBody : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
                            var body : MultipartBody.Part = MultipartBody.Part.createFormData("multipartFile",file?.name,requestBody)
                            retrofitService.service.setReviewImage(receive.response.toLong(),body).enqueue(object : Callback<ReceiveOK> {
                                override fun onResponse(
                                    call: Call<ReceiveOK>,
                                    response: Response<ReceiveOK>
                                ) {
                                    val receive = response.body() as ReceiveOK
                                    if(response.isSuccessful && receive.success) {
                                        Toast.makeText(
                                            activity,
                                            "리뷰 작성 완료되었습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        findNavController().navigate(R.id.action_reviewWriteFragment_to_storeReviewFragment)
                                    }
                                }

                                override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                                }

                            })

                        }
                        findNavController().navigate(R.id.action_reviewWriteFragment_to_storeReviewFragment)
                    } else {
                        Toast.makeText(activity,"리뷰 작성 실패되었습니다.", Toast.LENGTH_SHORT).show()
                    }


                }

                override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {
                    Toast.makeText(activity,"서버와 연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show()

                }

            })
        }

    }

    private fun showDialg() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.photo_dialog, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<View>(R.id.camera)?.setOnClickListener {
            if (isPermitted(CAMERA_PERMISSION)) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    CAMERA_PERMISSION,
                    FLAG_PERM_CAMERA
                )
            }
            alertDialog.dismiss()
        }
        alertDialog.findViewById<View>(R.id.gallery)?.setOnClickListener {
            if (isPermitted(STORAGE_PERMISSION)) {
                openCallery()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    STORAGE_PERMISSION,
                    FLAG_PERM_STORAGE
                )
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
                        val myBitmap = data?.extras?.get("data") as Bitmap
                        if(myBitmap != null) {
                            val imagePath = getImageUri(activity, myBitmap)
                            path = imagePath
                            realPath = getRealPathFromURI(path!!)
                            binding.photoButton2.setImageBitmap(myBitmap)
                        }
                    }
                }
                FLAG_REQ_GALLERY -> {
                    val uri = data?.data
                    // 이미지 뷰에 선택한 이미지 출력
                    val imageview: ImageView = binding.photoButton2
                    imageview.setImageURI(uri)
                    realPath = getRealPathFromURI(uri!!)
                }
            }
        }
    }

    private fun isPermitted(permissions: Array<String>): Boolean {

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

    fun sendReviewAndImage(id : Long? ,path : String?, request: review) {
        var requestBody : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("multipartFile",file?.name,requestBody)

    }

    fun sendNickNameOnly(id : Long?, nickname : String) {

    }


}

data class review(
    @SerializedName("memberId")
    @Expose
    val memberId : Long?,

    @SerializedName("restaurantId")
    @Expose
    val restaurantId : Long?,

    @SerializedName("content")
    @Expose
    val content : String,

    @SerializedName("starPoint")
    @Expose
    val starPoint : Float
)


