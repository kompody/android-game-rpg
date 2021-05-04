// Copyright (c) 2021 Kompod. All rights reserved
// Description: todo

package ru.kompod.moonlike.presentation.custom

import android.content.Context
import android.graphics.Color
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.custom_edit_text.view.*
import ru.kompod.moonlike.R
import ru.kompod.moonlike.utils.extensions.android.setVisibleOrGone
import ru.kompod.moonlike.utils.extensions.android.showKeyboard
import ru.kompod.moonlike.utils.extensions.kotlin.empty

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    enum class InputType(
        val value: Int,
        val inputType: Int,
        val rawInputType: Int?
    ) {
        EMAIL(0, android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS,null),
        PASSWORD(1, android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD, null),
        PHONE(2, android.text.InputType.TYPE_CLASS_TEXT, android.text.InputType.TYPE_CLASS_NUMBER),
        TEXT(3, android.text.InputType.TYPE_CLASS_TEXT, null),
        DIGITPASSWORD(4, android.text.InputType.TYPE_CLASS_PHONE, null),
        NOINPUT(5, android.text.InputType.TYPE_NULL, android.text.InputType.TYPE_NULL)
    }

    var text: String
        get() = editText.text?.toString() ?: String.empty
        set(value) {
            editText.setText(value)
        }

    var isErrorEnabled = false
        set(value) {
            if (errorText.isNullOrBlank()) return

            if (value) {
                labelTextView.setTextColor(labelErrorTextColor)
            } else {
                labelTextView.setTextColor(labelTextColor)
            }
            errorTextView.setVisibleOrGone(value)
            field = value
        }

    var errorText
        get() = errorTextView.text
        set(value) {
            errorTextView.text = value
        }

    var errorTextColor
        get() = errorTextView.textColors.defaultColor
        set(value) {
            errorTextView.setTextColor(value)
        }

    var hintText
        get() = editText.hint
        set(value) {
            editText.hint = value
        }

    var hintTextColor
        get() = editText.hintTextColors.defaultColor
        set(value) {
            editText.setHintTextColor(value)
        }

    var labelText
        get() = labelTextView.text
        set(value) {
            labelTextView.text = value
        }

    var labelTextColor = Color.BLACK
        set(value) {
            field = value
            labelTextView.setTextColor(value)
        }

    var labelErrorTextColor = Color.BLACK

    var inputType = InputType.EMAIL
        set(value) {
            field = value
            setInputTypeInternal()
        }

    var clickListener: ((View) -> Unit)? = null
        set(value) {
            field = value
            labelTextView.setOnClickListener(field)
            setOnClickListener(field)
            editText.setOnClickListener(field)
        }

    internal val innerEditText
        get() = editText

    private var textWatcher: TextWatcher? = null
    private var isPasswordToggleEnabled = false

    init {
        View.inflate(context, R.layout.custom_edit_text, this)
        orientation = VERTICAL

        context.withStyledAttributes(attrs, R.styleable.CustomEditText) {
            text = getString(R.styleable.CustomEditText_text) ?: String.empty
            isErrorEnabled = getBoolean(R.styleable.CustomEditText_errorEnabled, false)
            errorTextColor = getColor(R.styleable.CustomEditText_errorColor, Color.WHITE)
            errorText = getString(R.styleable.CustomEditText_errorText) ?: String.empty

            hintText = getString(R.styleable.CustomEditText_hintText) ?: String.empty
            hintTextColor = getColor(R.styleable.CustomEditText_hintTextColor, Color.BLACK)

            labelText = getString(R.styleable.CustomEditText_labelText) ?: String.empty
            labelTextColor = getColor(R.styleable.CustomEditText_labelTextColor, Color.BLACK)
            labelErrorTextColor = getColor(R.styleable.CustomEditText_labelErrorTextColor, Color.BLACK)

            inputType = convertInputType(getInt(R.styleable.CustomEditText_inputType, InputType.EMAIL.value))

            val maxLength = getInt(R.styleable.CustomEditText_maxLength, 0)
            if (maxLength > 0) {
                innerEditText.filters = arrayOf(InputFilter.LengthFilter(maxLength))
            }
        }

        initListeners()
        setInputTypeInternal()
    }

    private fun initListeners() {
        labelTextView.setOnClickListener {
            editText.showKeyboard()
        }

        passwordToggle.setOnCheckedChangeListener { _, isChecked ->
            setPasswordTransformationMethod(isChecked)
        }

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                editText.setSelection(editText.text?.length ?: 0)
            }
        }
    }

    private fun setInputTypeInternal() {
        when (inputType) {
            InputType.EMAIL -> configureEmailInputType()
            InputType.PASSWORD -> configurePasswordInputType()
            InputType.PHONE -> configurePhoneInputType()
            InputType.TEXT -> configureTextInputType()
            InputType.DIGITPASSWORD -> configureDigitPasswordInputType()
            InputType.NOINPUT -> configureNoInputInputType()
        }

        editText.inputType = inputType.inputType
        inputType.rawInputType?.let(editText::setRawInputType)
    }

    private fun setPasswordTransformationMethod(showChars: Boolean) {
        if (showChars) {
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }
        editText.setSelection(text.length)
    }

    private fun configureEmailInputType() {
        editText.transformationMethod = null
        isPasswordToggleEnabled = false
    }

    private fun configurePasswordInputType() {
        setPasswordTransformationMethod(false)
        isPasswordToggleEnabled = true
    }

    private fun configurePhoneInputType() {
        editText.transformationMethod = null
        isPasswordToggleEnabled = false
    }

    private fun configureTextInputType() {
        editText.transformationMethod = null
        isPasswordToggleEnabled = false
    }

    private fun configureDigitPasswordInputType() {
        setPasswordTransformationMethod(false)
        isPasswordToggleEnabled = true
    }

    private fun configureNoInputInputType() {
        editText.transformationMethod = null
        isPasswordToggleEnabled = false
        editText.isFocusable = false
    }

    private fun convertInputType(rawValue: Int): InputType {
        return when(rawValue) {
            InputType.EMAIL.value -> InputType.EMAIL
            InputType.PASSWORD.value -> InputType.PASSWORD
            InputType.PHONE.value -> InputType.PHONE
            InputType.TEXT.value -> InputType.TEXT
            InputType.DIGITPASSWORD.value -> InputType.DIGITPASSWORD
            InputType.NOINPUT.value -> InputType.NOINPUT
            else -> throw Exception("wrong input type: $rawValue")
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        textWatcher = editText.addTextChangedListener { text ->
            if (!isPasswordToggleEnabled) return@addTextChangedListener

            passwordToggle.setVisibleOrGone(!text.isNullOrEmpty())
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        textWatcher?.let(editText::removeTextChangedListener)
        textWatcher = null
    }
}