package com.ridery.here.common.utils

import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import com.ridery.here.R

fun validateName(text: EditText){
    text.doAfterTextChanged {
        if (it!!.length >= 50) {
            text.error = "El nombre o apellido puede contener max. 50 caracteres."
        }
    }
}

fun validateEmail(email: EditText){
    email.doAfterTextChanged {
        if (!Patterns.EMAIL_ADDRESS.matcher(it!!).matches()) {
            email.error = "Por favor, introduce un correo electrónico válido"
        }
    }
}

fun validatePassword(password: EditText){
    password.doAfterTextChanged {
        if (!Constants.PASSWORD_PATTERN.matcher(it!!).matches()) {
            password.error = "La contraseña debe contener al menos una letra mayúscula, dos números y un signo de puntuación"
        }
    }
}

fun validatePhone(phone: EditText){
    phone.doAfterTextChanged {
        if (!Constants.PHONE_PATTERN.matcher(it!!).matches()) {
            phone.error = "Por favor, introduce un número de teléfono válido"
        }
    }
}

fun getMsgErrorByCode(errorCode: String?): Int = when(errorCode){
    Constants.ERROR_VALIDATE -> R.string.error_validate
    Constants.ERROR_EXIST -> R.string.error_user_exists
    Constants.ERROR_LENGTH -> R.string.error_invalid_length
    else -> R.string.error_unknow
}

fun hideKeyboard(context: Context, view: View){
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}