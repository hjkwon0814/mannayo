package com.example.mannayoclient

import android.content.Context
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import java.lang.Exception

class TextClear :AppCompatEditText, TextWatcher, View.OnTouchListener, View.OnFocusChangeListener {


    private lateinit var clearDrawable : Drawable
    private lateinit var onfocuschangelistener: OnFocusChangeListener
    private lateinit var  onTouchListener : OnTouchListener
    private var count: Int = 0

    constructor(context: Context?):super(context!!){
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super (context!!, attrs){
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr:Int) : super(context!!, attrs, defStyleAttr) {
        init()
    }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener) {
        onfocuschangelistener = l
    }

    override fun setOnTouchListener(l: OnTouchListener) {
        onTouchListener = l
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {

        if(hasFocus && text != null) setClearIconVisible(text!!.isNotEmpty())
        else setClearIconVisible(false)

        if(count == 1) onfocuschangelistener.onFocusChange(view, hasFocus)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        try {
            val x = event.x.toInt()
            if (clearDrawable.isVisible && x > width - paddingRight - clearDrawable.intrinsicWidth) {
                if (event.action == MotionEvent.ACTION_UP) {
                    error = null
                    text = null
                }
                return true
            }
            return onTouchListener.onTouch(v, event)
        }catch (i:Exception){
            return false
        }

    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (isFocused) setClearIconVisible(text.isNotEmpty())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
    override fun afterTextChanged(s: Editable?) {}

    private fun setClearIconVisible(visible:Boolean){
        clearDrawable.setVisible(visible, false)
        setCompoundDrawables(null, null,
        if (visible) clearDrawable else null, null)
    }

    private fun init(){
        clearDrawable = DrawableCompat.wrap(
            (ResourcesCompat.getDrawable(resources,R.drawable.close_ring_duotone,null) as Drawable))
        clearDrawable.setBounds(0,0,clearDrawable.intrinsicWidth,clearDrawable.intrinsicHeight)


       setClearIconVisible(false)
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(this)
    }



}
