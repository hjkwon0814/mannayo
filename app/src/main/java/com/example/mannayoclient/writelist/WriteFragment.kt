package com.example.mannayoclient.writelist

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mannayoclient.R
import com.example.mannayoclient.databinding.WriteFragBinding
import java.io.FileOutputStream
import java.text.SimpleDateFormat

class WriteFragment : Fragment(R.layout.write_frag) {
    lateinit var binding: WriteFragBinding

    val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_GALLERY = 102


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = WriteFragBinding.bind(view)


        val rv: RecyclerView = binding.voteRecyclerView

        val items = ArrayList<WriteModel>()

        //test 용
        items.add(WriteModel())
        items.add(WriteModel())


        val rvAdapter = WriteRVAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(requireContext())




        binding.plusVote.setOnClickListener{
            binding.votetLayout.visibility = View.VISIBLE
            binding.voteClear.visibility = View.VISIBLE
        }

        binding.plusImage.setOnClickListener{
            binding.imageLayout.visibility = View.VISIBLE
            binding.imageClear.visibility = View.VISIBLE
        }

        binding.voteClear.setOnClickListener{
            binding.votetLayout.visibility = View.GONE
            binding.voteClear.visibility = View.GONE
        }

        binding.imageClear.setOnClickListener{
            binding.imageLayout.visibility = View.GONE
            binding.imageClear.visibility = View.GONE
        }

        binding.wImage.setOnClickListener{
            showDialg()
        }



        binding.plus.setOnClickListener {
            val plus = WriteModel()
            items.add(plus)

            binding.voteRecyclerView.adapter?.notifyDataSetChanged()

        }



    }
    private fun showDialg() {
        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.photo_dialog, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
            .setTitle("프로필 설정")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<View>(R.id.camera)?.setOnClickListener{
            if(isPermitted(CAMERA_PERMISSION)){
                openCamera()
            }else {
                ActivityCompat.requestPermissions(requireActivity(), CAMERA_PERMISSION, FLAG_PERM_CAMERA)
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
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
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
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data?.extras?.get("data") as Bitmap

                        //val filename = newFileName()
                        //val uri = saveImageFile(filename, "image/jpg", bitmap)

                        binding.wImage.setImageBitmap(bitmap)
                    }
                }
                FLAG_REQ_GALLERY -> {
                    val uri =data?.data
                    binding.wImage.setImageURI(uri)
                }
            }
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
}