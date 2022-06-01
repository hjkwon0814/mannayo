package com.example.mannayoclient

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.mannayoclient.databinding.ActivityCancelMemberBinding


class CancelMemberActivity : AppCompatActivity() {
    lateinit var binding: ActivityCancelMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityCancelMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reason.setOnClickListener{
            showDialg()
        }
    }

    private fun showDialg() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.member_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<ImageView>(R.id.member_ok).setImageResource(R.drawable.ok)
        alertDialog.findViewById<ImageView>(R.id.member_ok).isEnabled=false
        alertDialog.findViewById<TextView>(R.id.text_1)?.setOnClickListener{
            alertDialog.findViewById<TextView>(R.id.text_1).setTextColor(Color.parseColor("#ff8b83"))
            alertDialog.findViewById<TextView>(R.id.text_2).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_3).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_4).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_5).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<ImageView>(R.id.member_ok).setImageResource(R.drawable.ok_fill)
            alertDialog.findViewById<ImageView>(R.id.member_ok).isEnabled= true
            alertDialog.findViewById<ImageView>(R.id.member_ok)?.setOnClickListener{
                binding.reason.text = alertDialog.findViewById<TextView>(R.id.text_1).text
                binding.memButton.setImageResource(R.drawable.member7)
                alertDialog.dismiss()
            }
            }

        alertDialog.findViewById<TextView>(R.id.text_2)?.setOnClickListener{
            alertDialog.findViewById<TextView>(R.id.text_1).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_2).setTextColor(Color.parseColor("#ff8b83"))
            alertDialog.findViewById<TextView>(R.id.text_3).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_4).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_5).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<ImageView>(R.id.member_ok).setImageResource(R.drawable.ok_fill)
            alertDialog.findViewById<ImageView>(R.id.member_ok).isEnabled=true
            alertDialog.findViewById<ImageView>(R.id.member_ok)?.setOnClickListener{
                binding.reason.text = alertDialog.findViewById<TextView>(R.id.text_2).text
                binding.memButton.setImageResource(R.drawable.member7)
                alertDialog.dismiss()
            }
        }

        alertDialog.findViewById<TextView>(R.id.text_3)?.setOnClickListener{
            alertDialog.findViewById<TextView>(R.id.text_1).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_2).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_3).setTextColor(Color.parseColor("#ff8b83"))
            alertDialog.findViewById<TextView>(R.id.text_4).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_5).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<ImageView>(R.id.member_ok).setImageResource(R.drawable.ok_fill)
            alertDialog.findViewById<ImageView>(R.id.member_ok).isEnabled=true
            alertDialog.findViewById<ImageView>(R.id.member_ok)?.setOnClickListener{
                binding.reason.text = alertDialog.findViewById<TextView>(R.id.text_3).text
                binding.memButton.setImageResource(R.drawable.member7)
                alertDialog.dismiss()
            }
        }
        alertDialog.findViewById<TextView>(R.id.text_4)?.setOnClickListener{
            alertDialog.findViewById<TextView>(R.id.text_1).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_2).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_3).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_4).setTextColor(Color.parseColor("#ff8b83"))
            alertDialog.findViewById<TextView>(R.id.text_5).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<ImageView>(R.id.member_ok).setImageResource(R.drawable.ok_fill)
            alertDialog.findViewById<ImageView>(R.id.member_ok).isEnabled=true
            alertDialog.findViewById<ImageView>(R.id.member_ok)?.setOnClickListener{
                binding.reason.text = alertDialog.findViewById<TextView>(R.id.text_4).text
                binding.memButton.setImageResource(R.drawable.member7)
                alertDialog.dismiss()
            }
        }
        alertDialog.findViewById<TextView>(R.id.text_5)?.setOnClickListener{
            alertDialog.findViewById<TextView>(R.id.text_1).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_2).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_3).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_4).setTextColor(Color.parseColor("#767676"))
            alertDialog.findViewById<TextView>(R.id.text_5).setTextColor(Color.parseColor("#ff8b83"))
            alertDialog.findViewById<ImageView>(R.id.member_ok).setImageResource(R.drawable.ok_fill)
            alertDialog.findViewById<ImageView>(R.id.member_ok).isEnabled=true
            alertDialog.findViewById<ImageView>(R.id.member_ok)?.setOnClickListener{
                binding.reason.text = alertDialog.findViewById<TextView>(R.id.text_5).text
                binding.memButton.setImageResource(R.drawable.member7)
                alertDialog.dismiss()
            }
        }

        alertDialog.findViewById<ImageView>(R.id.member_out)?.setOnClickListener{
            alertDialog.dismiss()
        }
        }
}