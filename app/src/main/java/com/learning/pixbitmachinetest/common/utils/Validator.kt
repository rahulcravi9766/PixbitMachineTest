package com.learning.pixbitmachinetest.common.utils

object Validator {

    /**
     * Validates if the name contains only characters a-z, A-Z and white spaces.
     */
    fun isNameValid(name: String): Boolean {
        return name.matches(Regex("^[a-zA-Z\\s]*$"))
    }

    /**
     * Validates if the email address is valid.
     */
    fun isEmailValid(email: String): Boolean {
        return email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
    }

    /**
     * Validates if the password meets the following criteria:
     * - Not less than 5 characters.
     * - Must contain any numbers (0-9).
     * - Must contain alphabets (a-z or A-Z), with both uppercase and lowercase.
     * - Must contain any of the characters (@, !, ?, _).
     */
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
