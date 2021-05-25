package br.com.halsservice.utils.relatorio

import java.io.File

/**
 * Created by RubioAlves on 25/05/2021
 */
interface PDF {

    fun createPdf()
    fun previewPDF(file: File)
}