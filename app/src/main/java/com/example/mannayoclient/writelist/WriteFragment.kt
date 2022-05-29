package com.example.mannayoclient.writelist

import WriteRVAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View

import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.advertiselist.AdvertiseActivity
import com.example.mannayoclient.databinding.WriteFragBinding
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WriteFragment : Fragment(R.layout.write_frag) {
    private val viewModel: WriteModel by viewModels()
    lateinit var binding: WriteFragBinding
    lateinit var writeActivity: WriteActivity
    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_GALLERY = 102

    var path: Uri? = null
    var realPath: String? = null
    var file: File? = null


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var isVote: Boolean = false

        super.onViewCreated(view, savedInstanceState)
        binding = WriteFragBinding.bind(view)
        writeActivity = context as WriteActivity


        val sharedPreferences = writeActivity.getSharedPreferences("Pref", Context.MODE_PRIVATE)


        val adapter = WriteRVAdapter(viewModel)
        binding.voteRecyclerView.adapter = adapter
        binding.voteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.voteRecyclerView.setHasFixedSize(true)

        viewModel.itemsLiveData.observe(requireActivity()) {
            adapter.notifyDataSetChanged()
        }


        /*rvAdapter.itemClick = object : WriteRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                view.findViewById<TextView>(R.id.choice)
                    .addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {
                            println("작동중?")
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            println("작동중?")
                        }

                        override fun afterTextChanged(p0: Editable?) {
                            println("작동중?")
                            items[position].contents =
                                view.findViewById<TextView>(R.id.choice).text.toString()
                        }
                    })
            }
        }*/



        binding.plusVote.setOnClickListener {
            isVote = true
            binding.votetLayout.visibility = View.VISIBLE
            binding.voteClear.visibility = View.VISIBLE
        }

        binding.plusImage.setOnClickListener {
            binding.imageLayout.visibility = View.VISIBLE
            binding.imageClear.visibility = View.VISIBLE
        }

        binding.voteClear.setOnClickListener {
            isVote = false
            binding.votetLayout.visibility = View.GONE
            binding.voteClear.visibility = View.GONE
        }

        binding.imageClear.setOnClickListener {
            binding.imageLayout.visibility = View.GONE
            binding.imageClear.visibility = View.GONE
        }

        binding.wImage.setOnClickListener {
            showDialog()
        }



        binding.plus.setOnClickListener {

            if (viewModel.itemsSize < 4)
                showDialog2()
            else
                Toast.makeText(writeActivity, "투표 항목은 4개까지 입력 가능합니다!", Toast.LENGTH_SHORT).show()
        }


        /*binding.ok.setOnClickListener {

            val request = Board(
                memberId = sharedPreferences.getString("id", null)?.toLong(),
                contents = binding.context.text.toString(),
                isVote = isVote,
                type = sharedPreferences.getString("boardtype", null)
            )

            retrofitService.service.setBoard(request).enqueue(object : Callback<ReceiveOK> {
                override fun onResponse(call: Call<ReceiveOK>, response: Response<ReceiveOK>) {
                    val receive = response.body() as ReceiveOK
                    if (response.isSuccessful && receive.success) {
                        if (realPath != null) {
                            file = File(realPath)
                            println(realPath)
                            var requestBody: RequestBody =
                                RequestBody.create(MediaType.parse("multipart/form-data"), file)
                            var body: MultipartBody.Part = MultipartBody.Part.createFormData(
                                "multipartFile",
                                file?.name,
                                requestBody
                            )
                            retrofitService.service.setBoardImage(receive.response.toLong(), body)
                                .enqueue(object : Callback<ReceiveOK> {
                                    override fun onResponse(
                                        call: Call<ReceiveOK>,
                                        response: Response<ReceiveOK>
                                    ) {
                                        val receive = response.body() as ReceiveOK
                                        if (response.isSuccessful && receive.success) {
                                            Toast.makeText(
                                                writeActivity,
                                                "글쓰기 완료",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                                    }

                                })
                        }
                        if (isVote) {
                            for (i in items) {
                                retrofitService.service.setVote(
                                    receive.response.toLong(),
                                    i.contents
                                ).enqueue(object : Callback<ReceiveOK> {
                                    override fun onResponse(
                                        call: Call<ReceiveOK>,
                                        response: Response<ReceiveOK>
                                    ) {

                                    }

                                    override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {

                                    }

                                })
                            }
                        }
                        onActivityChange()
                    } else {
                        Toast.makeText(writeActivity, "글쓰기 실패", Toast.LENGTH_SHORT).show()
                        onActivityChange()
                    }
                }

                override fun onFailure(call: Call<ReceiveOK>, t: Throwable) {
                    Toast.makeText(writeActivity, "서버와 연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show()
                }

            })
        }*/


    }

    private fun showDialog2() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.vote_dialog, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)

        val alertDialog = mBuilder.show()

        alertDialog.findViewById<View>(R.id.buttonOK)?.setOnClickListener {
            val item = Item(alertDialog.findViewById<EditText>(R.id.vote_editText)?.text.toString())
            viewModel.addItem(item)
            alertDialog.dismiss()

        }

    }


    private fun showDialog() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.photo_dialog, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
            .setTitle("프로필 설정")

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

    fun saveImageFile(filename: String, mimeType: String, bitmap: Bitmap): Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )

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
        } catch (e: Exception) {

        }
        return null

    }

    fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return filename
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FLAG_REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data?.extras?.get("data") as Bitmap

                        //val filename = newFileName()
                        //val uri = saveImageFile(filename, "image/jpg", bitmap)
                        if (bitmap != null) {
                            val imagePath = getImageUri(writeActivity, bitmap)
                            path = imagePath
                            realPath = getRealPathFromURI(path!!)
                            binding.wImage.setImageBitmap(bitmap)
                        }

                    }
                }
                FLAG_REQ_GALLERY -> {
                    val uri = data?.data
                    binding.wImage.setImageURI(uri)
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

    fun getImageUri(inContext: Context?, inImage: Bitmap?): Uri? {
        val btyes = ByteArrayOutputStream()
        if (inImage != null) {
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, btyes)
        }
        val path = MediaStore.Images.Media.insertImage(
            inContext?.contentResolver,
            inImage,
            "Title" + " - " + Calendar.getInstance().time,
            null
        )
        return Uri.parse(path)
    }

    fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? =
            requireActivity().contentResolver.query(contentURI, null, null, null, null)

        if (cursor == null) {
            result = contentURI?.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }

        return result
    }

    fun onActivityChange() {
        val intent = Intent(activity, AdvertiseActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}