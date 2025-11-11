package com.learning.pixbitmachinetest.common.utils

object Validator {


    fun isNameValid(name: String): Boolean {
        return name.matches(Regex("^[a-zA-Z\\s]*$"))
    }

    fun isEmailValid(email: String): Boolean {
        return email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
    }

    fun isPhoneValid(phone: String): Boolean {
        return phone.matches(Regex("^[0-9]{10}$"))
    }


    fun isPasswordValid(password: String): Boolean {
        if (password.length < 5) {
            return false
        }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasNumber = password.any { it.isDigit() }
        val hasSpecialChar = password.any { it in setOf('@', '!', '?', '_') }

        return hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar
    }
}
