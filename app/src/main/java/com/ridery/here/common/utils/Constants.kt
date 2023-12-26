package com.ridery.here.common.utils

import java.util.regex.Pattern

object Constants {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    const val POSTS_PATH = "posts"

    const val NAME_PARAM = "name"
    const val LAST_NAME_PARAM = "lastName"
    const val EMAIL_PARAM = "email"
    const val PASSWORD_PARAM = "password"
    const val PHONE_PARAM = "phone"

    val PASSWORD_PATTERN: Pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d{2,})(?=.*[.,;:!@#\$%&*()_+=|<>?{}\\[\\]~-]).+$")
    val PHONE_PATTERN: Pattern = Pattern.compile("^\\+?[0-9]{10,15}$")

    const val ERROR_VALIDATE = "Error 00"
    const val ERROR_EXIST = "Error 01"
    const val ERROR_LENGTH = "Error 02"
    const val ERROR_UNKNOW = "unknow"
}