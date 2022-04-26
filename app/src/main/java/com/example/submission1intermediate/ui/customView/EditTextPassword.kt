package com.example.submission1intermediate.ui.customView

import android.content.ContentValues.TAG
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText

class EditTextPassword : AppCompatEditText {

    constructor(context: Context):super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet):super(context,attrs){
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,attrs,defStyleAttr){
        init()
    }
    private fun init(){
        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    error = if(passwordValidation(s.toString())) null
                    else "Password must be at least 6 characters"
                }
            }
            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged: ")
            }
        })
    }
    private fun passwordValidation(pass: String) : Boolean{
        return pass.length >= 6
    }
}