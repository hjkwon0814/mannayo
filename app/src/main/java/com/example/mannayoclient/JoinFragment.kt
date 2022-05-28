package com.example.mannayoclient

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mannayoclient.databinding.JoinFragBinding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class JoinFragment : Fragment(R.layout.join_frag) {
    lateinit var binding: JoinFragBinding
    lateinit var mainActivity: MainActivity

    lateinit var editTextEmail: EditText
    lateinit var editTextEmailDotCom: Spinner
    lateinit var editTextPassword: EditText
    lateinit var editTextPasswordRe: EditText
    lateinit var editTextName: EditText
    lateinit var editTextYear: EditText
    lateinit var editTextMonth: EditText
    lateinit var editTextDate: EditText
    lateinit var editTextPhone: EditText

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = JoinFragBinding.bind(view)
        binding.editTextDate.setEnabled(false)
        binding.editTextDate2.setEnabled(false)
        binding.editTextDate3.setEnabled(false)


        editTextEmail = binding.editTextTextEmailAddress
        editTextEmailDotCom = binding.spinner
        editTextPassword = binding.editTextTextPassword
        editTextPasswordRe = binding.editTextTextPassword2
        editTextName = binding.editTextTextPersonName
        editTextYear = binding.editTextDate
        editTextMonth = binding.editTextDate2
        editTextDate = binding.editTextDate3
        editTextPhone = binding.editTextNumber


        binding.layout.setOnClickListener{
            hideKeyboard()
        }


        //이메일 콤보박스
        val items = resources.getStringArray(R.array.my_array)

        val myAapter = object : ArrayAdapter<String>(requireContext(), R.layout.item_spinner) {

            @SuppressLint("CutPasteId")
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

                val v = super.getView(position, convertView, parent)

                if (position == count) {
                    (v.findViewById<View>(R.id.tvItemSpinner) as TextView).text = ""
                    (v.findViewById<View>(R.id.tvItemSpinner) as TextView).hint = getItem(count)
                }

                return v
            }

            override fun getCount(): Int {
                return super.getCount() - 1
            }

        }


        myAapter.addAll(items.toMutableList())

        myAapter.add("선택")

        binding.spinner.adapter = myAapter

        binding.spinner.setSelection(myAapter.count)


        fun dipToPixels(dipValue: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dipValue,
                resources.displayMetrics
            )
        }
        binding.spinner.dropDownVerticalOffset = dipToPixels(52f).toInt()


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                when (position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    //...
                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("MyTag", "onNothingSelected")
            }
        }



        //달력 그림 클릭시 날짜선택하기
        binding.dateclick.setOnClickListener{

            val birth = GregorianCalendar()
            val year: Int = birth.get(Calendar.YEAR)
            val month: Int = birth.get(Calendar.MONTH)
            val date : Int = birth.get(Calendar.DAY_OF_MONTH)


            val dlg = DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                @SuppressLint("SetTextI18n")
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayofMonth: Int
                ) {
                    birth.set(Calendar.YEAR, year)
                    birth.set(Calendar.MONTH, month)
                    birth.set(Calendar.DAY_OF_MONTH, dayofMonth)
                    val selectedMonth = SimpleDateFormat("MM", Locale.getDefault()).format(birth.time)
                    val selectedDate = SimpleDateFormat("dd", Locale.getDefault()).format(birth.time)

                    binding.editTextDate.setText("${year}")
                    binding.editTextDate2.setText(selectedMonth)
                    binding.editTextDate3.setText(selectedDate)

                }
            }, year, month, date)
            dlg.show()
        }



        //제목 변경
        val title = mainActivity.findViewById<TextView>(R.id.textview)
        title.setText("회원가입")

        binding.joinSubmit.setOnClickListener {

            val request = signUpRequest(
                email = binding.editTextTextEmailAddress.text.toString() + "@" + binding.spinner.selectedItem.toString(),
                password = binding.editTextTextPassword.text.toString(),
                realname = binding.editTextTextPersonName.text.toString(),
                birth = binding.editTextDate.text.toString() + "-" + binding.editTextDate2.text.toString() + "-" + binding.editTextDate3.text.toString(),
                phoneNumber = binding.editTextNumber.text.toString(),
                image = "",
                accountStatus = "OPEN",
                accountTypeEnum = "MEMBER",
                loginTypeEnum = "EMAIL"
            )



            if (binding.editTextTextPassword.text.toString() != binding.editTextTextPassword2.text.toString() && !binding.editTextTextPassword.text.isNullOrEmpty() &&
                !binding.editTextTextPassword2.text.isNullOrEmpty()) {
                Toast.makeText(mainActivity, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT)
                    .show()

                binding.view2.visibility = View.VISIBLE
                binding.textView6.visibility = View.VISIBLE

                binding.view.visibility = View.GONE
                binding.textView4.visibility = View.GONE

                binding.view3.visibility = View.GONE
                binding.textView8.visibility = View.GONE


            }else  {
                binding.view2.visibility = View.GONE
                binding.textView6.visibility = View.GONE
            }
            if (!isPasswordFormat(binding.editTextTextPassword.text.toString()) && !binding.editTextTextPassword.text.isNullOrEmpty()) {
                Toast.makeText(mainActivity, "비밀번호가 올바르지 않은 형식입니다.!", Toast.LENGTH_SHORT)
                    .show()

                binding.view.visibility = View.VISIBLE
                binding.textView4.visibility = View.VISIBLE

                binding.view2.visibility = View.GONE
                binding.textView6.visibility = View.GONE

                binding.view3.visibility = View.GONE
                binding.textView8.visibility = View.GONE

            }
            if (!isNameFormat(binding.editTextTextPersonName.text.toString()) && !binding.editTextTextPersonName.text.isNullOrEmpty()){
                Toast.makeText(mainActivity, "이름이 올바르지 않은 형식니다.!", Toast.LENGTH_SHORT)
                    .show()

                binding.view3.visibility = View.VISIBLE
                binding.textView8.visibility = View.VISIBLE

                binding.view.visibility = View.GONE
                binding.textView4.visibility = View.GONE

                binding.view2.visibility = View.GONE
                binding.textView6.visibility = View.GONE

            }

            if(!binding.editTextTextEmailAddress.text.isNullOrEmpty() && !binding.editTextTextPassword.text.isNullOrEmpty()
                && !binding.editTextTextPassword2.text.isNullOrEmpty() && !binding.editTextTextPersonName.text.isNullOrEmpty()
                && !binding.editTextDate.text.isNullOrEmpty() && !binding.editTextDate2.text.isNullOrEmpty() && !binding.editTextDate3.text.isNullOrEmpty()
                && !binding.editTextNumber.text.isNullOrEmpty() && isNameFormat(binding.editTextTextPersonName.text.toString())
                && !binding.editTextTextPersonName.text.isNullOrEmpty() && isPasswordFormat(binding.editTextTextPassword.text.toString())
                && !binding.editTextTextPassword.text.isNullOrEmpty() && !binding.editTextTextPassword.text.isNullOrEmpty()
                && binding.editTextTextPassword.text.toString() == binding.editTextTextPassword2.text.toString()) {

                binding.view.visibility = View.GONE
                binding.textView4.visibility = View.GONE

                binding.view2.visibility = View.GONE
                binding.textView6.visibility = View.GONE

                binding.view3.visibility = View.GONE
                binding.textView8.visibility = View.GONE
                retrofitService.service.signUp(request).enqueue(object : Callback<resSignUpData> {

                    override fun onResponse(
                        call: Call<resSignUpData>,
                        response: Response<resSignUpData>
                    ) {
                        val reqresponse = response.body() as resSignUpData

                        if (response.isSuccessful && reqresponse.response == "성공") {
                            findNavController().navigate(R.id.action_joinFragment_to_join2Fragment)
                        } else {

                        }
                    }

                    override fun onFailure(call: Call<resSignUpData>, t: Throwable) {
                        Toast.makeText(mainActivity, "인터넷 연결을 확인해 주세요!", Toast.LENGTH_SHORT)
                            .show()
                    }

                })

            }

        }
    }


    fun buttonWatcher() {
        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(editTextEmail.text.toString() != "" && editTextEmailDotCom.selectedItem.toString() != ""
                    && editTextPassword.toString() != "" && editTextPasswordRe.toString() != "" && editTextName.toString() != ""
                    && editTextYear.toString() != "" &&editTextMonth.toString() != "" && editTextDate.toString() != ""
                    && editTextPhone.toString() != "")

                    return

            }

        })
    }

    //비밀번호 체크하는 함수 (숫자, 문자, 특수문자 포함 8~12자리 이내)
    fun isPasswordFormat(password: String): Boolean {
        return password.matches("^.*(?=^.{8,12}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$".toRegex())
    }

    //한글 이름 2~4자 이내, 영문 이름 2~10자 이내 : 띄어쓰기(\s)가 들어가며 First, Last Name 형식, 한글 또는 영문 사용하기(혼용X)
    fun isNameFormat(name: String): Boolean {
        return name.matches("^[가-힣]{2,4}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}\$".toRegex())
    }

    //EditText 외에 다른곳을 누르면 키보드 내려가기 (MainActivity에서 설정하였으나 JoinFragment에서는 먹히지 않아 따로 다시 작성함)
    fun hideKeyboard() {
        val mInputMethodManager =
            requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        mInputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }


}



