package br.com.halsservice.ui.cliente

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by RubioAlves on 17/04/2021
 */
@Parcelize
data class ClienteArgs(
        val nome:String,
        val endereco:String,
        val bairro:String,
        val numero:Int,
        val estado:String,
        val telefone:String
):Parcelable