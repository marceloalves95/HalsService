package br.com.halsservice.utils.validator

import android.widget.EditText
import br.com.halsservice.utils.formatter.FormatadorTelefone
import br.com.halsservice.utils.others.Constants
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by RubioAlves on 24/05/2021
 */
class ValidadorTelefone(private val textInputCampoTelefone: TextInputLayout, private var campo: EditText) {

    private var validadorPadrao: ValidadorPadrao

    init {
        campo = textInputCampoTelefone.editText!!
        validadorPadrao = ValidadorPadrao(textInputCampoTelefone, campo)
    }

    private fun validaEntreDezouOnzeDigitos(telefone:String):Boolean{

        val digitos = telefone.length
        if(digitos < 10 || digitos > 11){

            textInputCampoTelefone.error = Constants.MENSAGEM_ERRO_DIGITOS_TELEFONE
            return false

        }

        return true

    }

    fun estaValido():Boolean{

        if(!validadorPadrao.estaValido()) {
            return false
        }
        val campoDDD = campo.text.toString()

        /*if (validaEntreDezouOnzeDigitos(campoDDD)) {
            return false
        }*/
        adicionarFormatacao(campoDDD)

        return true

    }

    fun adicionarFormatacao(telefone:String){

        val formatador = FormatadorTelefone()
        val telefoneFormatado = formatador.formata(telefone)
        campo.setText(telefoneFormatado)

    }



}