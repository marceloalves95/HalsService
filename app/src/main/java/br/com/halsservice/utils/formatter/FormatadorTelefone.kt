package br.com.halsservice.utils.formatter

/**
 * Created by RubioAlves on 24/05/2021
 */
class FormatadorTelefone {

    private val regex = "([0-9]{2})([0-9]{4,5})([0-9]{4})".toRegex()
    private val replacement = "($1) $2-$3"

    fun formata(telefone:String):String = telefone.replace(regex,replacement)
    fun remove(telefone: String):String {

        return telefone
            .replace("(", "")
            .replace(")", "")
            .replace(" ", "")
            .replace("-", "")
    }


}