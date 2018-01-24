package notboot.ferreira.edson.br.bancopanex.CacheBD

/**
 * Created by edson.ferreira on 23/01/2018.
 */
import android.database.sqlite.SQLiteDatabase

class CriaBanco {

    internal var SQL: String = ""
    fun criaBanco(BancoDeDados: SQLiteDatabase?): Boolean {

        try {
            if (BancoDeDados != null) {

                //Tabela do Usuário
                SQL = " CREATE TABLE IF NOT EXISTS JOGO_TOP ( _id INTEGER PRIMARY KEY," +
                        "ID_JOGO NUMBER, NOME_JOGO TEXT, IMG_JOGO TEXT, QUANTIDADE_VISU NUMBER" +
                        ",QTD_CANAIS NUMBER);  "
                BancoDeDados.execSQL(SQL)


            }


            return true
        }catch (e:Exception){

            return false
        }

    }
}