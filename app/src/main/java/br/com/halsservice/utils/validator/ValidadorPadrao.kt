package br.com.halsservice.utils.validator

import android.widget.EditText
import br.com.halsservice.utils.others.Constants.CAMPO_OBRIGATORIO
import com.google.android.material.textfield.TextInputLayout
/**
 * Created by RubioAlves on 22/05/2021
 */
class ValidadorPadrao(private val textInpuCampo:TextInputLayout, private var campo:EditText):Validador  {

    init {
        campo = textInpuCampo.editText!!
    }

    override fun removeError(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.isErrorEnabled = false
    }

    override fun validaCampoObrigatorio(): Boolean {

        val texto = campo.text.toString()

        if (texto.isEmpty()){
            textInpuCampo.error = CAMPO_OBRIGATORIO
            return false
        }

        return true

    }

    override fun estaValido(): Boolean = validaCampoObrigatorio()

}