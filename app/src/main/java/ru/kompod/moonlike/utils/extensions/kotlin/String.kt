// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.kotlin

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.core.text.parseAsHtml
import ru.kompod.moonlike.utils.ImprovedBulletSpan
import java.io.UnsupportedEncodingException
import java.security.MessageDigest

val String.Companion.empty
    get() = ""

val String.Companion.space
    get() = " "

val String.Companion.lineSeparator
    get() = "\n"

fun String.md5(): String? {
    return try {
        MessageDigest.getInstance("MD5")
            .digest(this.toByteArray())
            .joinToString("") {
                Integer.toHexString(it.toInt() and 0xFF or 0x100).substring(1, 3)
            }

    } catch (e: java.security.NoSuchAlgorithmException) {
        null
    } catch (ex: UnsupportedEncodingException) {
        null
    }
}

fun String.parseHtmlToBullet() : SpannableStringBuilder{
    val htmlSpannable = this.parseAsHtml()
    val spannableBuilder = SpannableStringBuilder(htmlSpannable)
    val bulletSpans = spannableBuilder.getSpans(0, spannableBuilder.length, BulletSpan::class.java)
    bulletSpans.forEach {
        val start = spannableBuilder.getSpanStart(it)
        val end = spannableBuilder.getSpanEnd(it)
        spannableBuilder.removeSpan(it)
        spannableBuilder.setSpan(
            ImprovedBulletSpan(),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    return spannableBuilder
}