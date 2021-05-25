package br.com.halsservice.utils.validator

import com.google.android.material.textfield.TextInputLayout

/**
 * Created by RubioAlves on 22/05/2021
 */
interface Validador {

    fun estaValido():Boolean
    fun validaCampoObrigatorio():Boolean
    fun removeError(textInputLayout: TextInputLayout)
}