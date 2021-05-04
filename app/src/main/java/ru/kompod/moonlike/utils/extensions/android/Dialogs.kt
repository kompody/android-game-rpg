// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.android

import android.app.AlertDialog.*
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import ru.kompod.moonlike.R
import ru.kompod.moonlike.utils.ConfirmDialogAction
import java.util.*

private fun AlertDialog.Builder.build(block: AlertDialog.Builder.() -> Unit): AlertDialog {
    return this
        .apply(block)
        .create()
}

fun buildSupportAlertDialog(context: Context, themeResId: Int? = null, block: AlertDialog.Builder.() -> Unit): AlertDialog {
    return if (themeResId == null)
        AlertDialog
            .Builder(context)
            .build(block)
    else
        AlertDialog
            .Builder(context, themeResId)
            .build(block)
}

private fun getDialogListener(callback: (() -> Unit)?): DialogInterface.OnClickListener? {
    return if (callback != null)
        DialogInterface.OnClickListener { _, _ -> callback() }
    else
        null
}

fun AlertDialog.Builder.setNegativeButtonExt(textId: Int, callback: (() -> Unit)? = null) {
    setNegativeButton(
        textId,
        getDialogListener(callback)
    )
}

fun AlertDialog.Builder.setNegativeButtonExt(text: String, callback: (() -> Unit)? = null) {
    setNegativeButton(
        text,
        getDialogListener(callback)
    )
}

fun AlertDialog.Builder.setPositiveButtonExt(textId: Int, callback: (() -> Unit)? = null) {
    setPositiveButton(
        textId,
        getDialogListener(callback)
    )
}

fun AlertDialog.Builder.setPositiveButtonExt(text: String, callback: (() -> Unit)? = null) {
    setPositiveButton(
        text,
        getDialogListener(callback)
    )
}

fun datePickerDialogOf(
    context: Context,
    calendar: Calendar = Calendar.getInstance(),
    datePickerInitBlock: DatePicker.() -> Unit = {},
    onDateSet: (resultMillis: Long) -> Unit = {}
): Dialog {
    return with (calendar) {
        DatePickerDialog(
            context,
            R.style.AppTheme_DatePicker,
            { _, year, month, dayOfMonth -> onDateSet(
                Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }.timeInMillis)
            },
            get(Calendar.YEAR),
            get(Calendar.MONTH),
            get(Calendar.DAY_OF_MONTH)
        ).apply { datePicker.apply(datePickerInitBlock) }
    }
}