package com.fitlog.app.data.security

import java.security.MessageDigest

/**
 * Encrypts the user's password before it ever leaves the device.
 *
 * Design note (see README "Security" section for the full write-up): the
 * plain-text password typed by the user is immediately hashed with SHA-256
 * and a per-app pepper here on the client, so the raw password is never
 * held in memory longer than necessary and is never logged or written to
 * disk. The resulting hash is sent to the REST API over HTTPS, where it is
 * hashed *again* with bcrypt (which adds a random per-user salt and a
 * deliberately slow work factor) before being stored in the database — see
 * backend/src/routes/auth.js. Neither the client hash nor the stored bcrypt
 * hash can be reversed to recover the original password.
 */
object PasswordHasher {

    // In production this should be pulled from a secure config, not hard-coded.
    private const val CLIENT_PEPPER = "fitlog-diploma-project-2026"

    fun hash(rawPassword: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest((rawPassword + CLIENT_PEPPER).toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
