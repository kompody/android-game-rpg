// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.utils.extensions.rxpm

import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.jakewharton.rxbinding3.widget.textChanges
import com.redmadrobot.inputmask.MaskedTextChangedListener
import me.dmdev.rxpm.PresentationModel
import me.dmdev.rxpm.action
import me.dmdev.rxpm.bindTo
import me.dmdev.rxpm.state
import ru.kompod.moonlike.presentation.custom.CustomEditText
import ru.kompod.moonlike.utils.extensions.android.showKeyboard
import ru.kompod.moonlike.utils.extensions.kotlin.empty
import ru.kompod.moonlike.utils.extensions.kotlin.isNotNull
import ru.kompod.moonlike.utils.extensions.kotlin.isNull
import ru.kompod.moonlike.utils.extensions.kotlin.unsafeCastTo

class CustomInputControl internal constructor(
    initialText: String,
    private val hideErrorOnUserInput: Boolean = true,
    val mask: String?,
    private val validationCallback: ((CustomInputControl) -> ValidationResult)?
) : PresentationModel() {

    sealed class ValidationResult {
        object Success: ValidationResult()
        class Failed(val message: String): ValidationResult()
    }

    val text = state(initialText)

    val error = state<String>(diffStrategy = null)

    val textChanges = action<String>()

    val errorVisible = state<Boolean>(diffStrategy = null)

    var extractedValue: String = initialText

    var isMaskFilled: Boolean = mask.isNull()

    val isValid = state<ValidationResult>()

    override fun onCreate() {

        text
            .observable
            .filter { validationCallback.isNotNull() }
            .subscribe {
                isValid.consumer.accept(validationCallback!!(this))
                if (hideErrorOnUserInput) errorVisible.consumer.accept(false)
            }
            .untilDestroy()

        textChanges
            .observable
            .filter { it != text.value }
            .subscribe {
                text.consumer.accept(it)
                if (hideErrorOnUserInput) errorVisible.consumer.accept(false)
            }
            .untilDestroy()
    }
}

fun PresentationModel.customInputControl(
    initialText: String = String.empty,
    hideErrorOnUserInput: Boolean = true,
    mask: String? = null,
    validationCallback: ((inputControl: CustomInputControl) -> CustomInputControl.ValidationResult)? = null
): CustomInputControl {
    return CustomInputControl(initialText, hideErrorOnUserInput, mask, validationCallback).apply {
        attachToParent(this@customInputControl)
    }
}

infix fun CustomInputControl.bindTo(customEditText: CustomEditText) {

    var editing = false

    var maskedListener: MaskedTextChangedListener? = null

    text bindTo {
        val editable = customEditText.innerEditText.unsafeCastTo<EditText>().text
        if (!it.contentEquals(editable)) {
            editing = true
            if (editable is Spanned) {
                val string = SpannableString(it)
                TextUtils.copySpansFrom(editable, 0, string.length, null, string, 0)
                editable.replace(0, editable.length, string)
            } else {
                editable.replace(0, editable.length, it)
            }

            maskedListener?.setText(editable.toString())?.let { result ->
                extractedValue = result.extractedValue
                isMaskFilled = result.complete
            }

            editing = false
        }
    }

    mask?.let {
        maskedListener = MaskedTextChangedListener.installOn(
            editText = customEditText.innerEditText,
            primaryFormat = mask,
            valueListener = object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String) {
                    this@bindTo.extractedValue = extractedValue
                    isMaskFilled = maskFilled
                }
            }
        )

        customEditText.innerEditText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus)
                view.showKeyboard()
            maskedListener?.onFocusChange(view, hasFocus)
        }
    }

    customEditText
        .innerEditText
        .textChanges()
        .skipInitialValue()
        .filter { !editing }
        .map { it.toString() }
        .bindTo(textChanges)

    error bindTo { error ->
        if (error != String.empty) {
            customEditText.errorText = error
            customEditText.isErrorEnabled = true
        } else {
            customEditText.isErrorEnabled = false
        }
    }

    errorVisible bindTo { isVisible ->
        if (customEditText.errorText != String.empty) {
            customEditText.isErrorEnabled = isVisible
        }
    }
}