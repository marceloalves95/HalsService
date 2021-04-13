package br.com.halsservice.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Hals Service
 * @author Marcelo Alves
 * 09/03/2021
 */
class HelperDB (context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val cliente = "CREATE TABLE $TABLE_CLIENTE (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NOME TEXT NOT NULL," +
                "$ENDERECO TEXT NOT NULL," +
                "$NUMERO INT NOT NULL, " +
                "$BAIRRO TEXT NOT NULL, " +
                "$TELEFONE TEXT UNIQUE NOT NULL);"

        val servico =
                "CREATE TABLE $TABLE_SERVICO (" +
                        "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "$DESCRICAO TEXT NOT NULL, " +
                        "$TIPO_SERVICO TEXT NOT NULL ," +
                        "$GARANTIA TEXT NOT NULL, " +
                        "$VALOR_SERVICO TEXT NOT NULL, " +
                        "$DATA_REPARO TEXT NOT NULL, " +
                        "$FK_CLIENTE INT , " +
                        "FOREIGN KEY($FK_CLIENTE) REFERENCES $TABLE_CLIENTE($ID));"

        db?.execSQL(cliente)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        val dropTable = "DROP TABLE IF EXISTS $TABLE_CLIENTE"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    companion object Cliente{

        //Nome da versão e nome do banco de dados
        private const val DB_VERSION = 1
        private const val DB_NAME = "hals_service"

        //Aqui se define o(s) nome(s) da(s) tabela(s) do banco de dados
        private const val TABLE_CLIENTE = "Cliente"
        private const val TABLE_SERVICO = "Servico"

        //Nome do(s) campo(s) da(s) tabela(s)
        private const val ID = "id"
        private const val NOME = "nome"
        private const val ENDERECO = "endereco"
        private const val NUMERO = "numero"
        private const val BAIRRO = "bairro"
        private const val TELEFONE = "telefone"

        private const val DESCRICAO = "descricao"
        private const val TIPO_SERVICO = "tipo_servico"
        private const val GARANTIA = "garantia"
        private const val VALOR_SERVICO = "valor_servico"
        private const val DATA_REPARO = "data_reparo"
        private const val FK_CLIENTE = "fk_cliente"

    }


}