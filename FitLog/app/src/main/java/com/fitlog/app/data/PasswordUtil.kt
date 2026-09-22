package com.fitlog.app.data

import java.security.MessageDigest

 //Hashes passwords before they're stored in Room, so a rooted/leaked

object PasswordUtil {
    private const val PEPPER = "fitlog-local-demo"

    fun hash(rawPassword: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest((rawPassword + PEPPER).toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}