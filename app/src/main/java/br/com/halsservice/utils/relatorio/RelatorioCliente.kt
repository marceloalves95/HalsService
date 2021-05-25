package br.com.halsservice.utils.relatorio

import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.OutputStream

/**
 * Created by RubioAlves on 25/05/2021
 */
class RelatorioCliente {

    private val fontArial: BaseFont = BaseFont.createFont("assets/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)

    private val tipoPapel = PageSize.A4
    private val widthPercentage = 100F
    private val borderWidth = 1.0F
    private val padding = 10F
    private val rowspan = 2
    private val colspan = 4
    private val numColumns = 20
    private val margins = 18F
    lateinit var document:Document

    fun criarDocumento(output: OutputStream){

        document = Document(tipoPapel, margins, margins, margins, margins)
        PdfWriter.getInstance(document, output)
        document.open()
        document.add(tabelaCliente())
        document.close()

    }
    fun alterarDocumento(output: OutputStream, tabela:PdfPTable){

        document = Document(tipoPapel, margins, margins, margins, margins)
        PdfWriter.getInstance(document, output)
        document.open()
        document.add(tabela)
        document.close()

    }

    fun tabelaCliente() :PdfPTable{

        val listaTitulos = listOf("Nome", "Endereço", "Número", "Bairro", "Telefone")

        //Criação de Tabela
        val tabela = PdfPTable(numColumns)
        tabela.widthPercentage = widthPercentage
        tabela.spacingBefore()

        tabela.addCell(tituloRelatorio())
        listaTitulos.forEach { titulos-> tabela.addCell(tituloSecundario(titulos)) }


        return tabela
    }



    private fun tituloRelatorio(): PdfPCell {

        val font = Font(fontArial, 20F, Font.BOLD, BaseColor.BLACK)
        val celula = PdfPCell(Paragraph("Relatório de Clientes", font))
        celula.borderWidth = borderWidth
        celula.setPadding(padding)
        celula.colspan = 20
        celula.rowspan = rowspan
        celula.horizontalAlignment = Element.ALIGN_CENTER
        celula.verticalAlignment = Element.ALIGN_MIDDLE

        return celula

    }

    private fun tituloSecundario(content: String): PdfPCell {

        val font = Font(fontArial, 18F, Font.NORMAL, BaseColor.WHITE)
        val celula = PdfPCell(Phrase(content, font))

        celula.borderWidth = borderWidth
        celula.setPadding(padding)
        celula.colspan = colspan
        celula.backgroundColor = BaseColor(26, 35, 126)
        celula.horizontalAlignment = Element.ALIGN_CENTER
        celula.verticalAlignment = Element.ALIGN_MIDDLE

        return celula
    }

    fun linhasRelatorio(content: String): PdfPCell {

        val font = Font(fontArial, 12F, Font.NORMAL, BaseColor.BLACK)
        val celula = PdfPCell(Phrase(content, font))

        celula.borderWidth = borderWidth
        celula.setPadding(padding)
        celula.colspan = colspan
        celula.horizontalAlignment = Element.ALIGN_CENTER
        celula.verticalAlignment = Element.ALIGN_MIDDLE

        return celula
    }


}