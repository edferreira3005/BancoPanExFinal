package notboot.ferreira.edson.br.bancopanex.CacheBD

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.util.Base64
import notboot.ferreira.edson.br.bancopanex.Internet.ConexaoUrls
import notboot.ferreira.edson.br.bancopanex.Internet.Download
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream



@Suppress("DEPRECATION")

class ManipulaBanco {
    var idt:Int = 0

    fun InsereBanco(BancoDeDados: SQLiteDatabase , c:Context) :Boolean{

        try {

        val ar = ConexaoUrls()
        //Se conectando com meu ID ao Twitch
        val jsonStr = ar.chamadaGet("https://api.twitch.tv/kraken/games/top?limit=100&client_id=6b6l8pfhm2oec7dngvdjw95l5tffq3")
            var byteArrayOutputStream : ByteArrayOutputStream?
            var imgb: Bitmap?
            var byteArray1 : ByteArray?
            var img64 : String?

            val jsonObj = JSONObject(jsonStr)
            if(jsonObj.length() > 0) {
                var SQL: String = "DELETE FROM JOGO_TOP"
                BancoDeDados.execSQL(SQL)

                val ja = jsonObj.getJSONArray("top")

                val count = ja.length()

                for (i in 0..count - 1) {
                    var jsonObject = ja.getJSONObject(i)

                    println(jsonObject.getString("game"))

                    var ja = JSONObject(jsonObject.getString("game"))

                    var imgjs = JSONObject(ja.getString("logo"))

                    var down = Download()
                    imgb = down.getBitmapFromURL(imgjs.getString("large").trim())!!
                    byteArrayOutputStream = ByteArrayOutputStream()

                    imgb.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

                    byteArray1 = byteArrayOutputStream.toByteArray()

                    img64 = Base64.encodeToString(byteArray1, Base64.DEFAULT)



                    SQL = "INSERT INTO JOGO_TOP (NOME_JOGO,QUANTIDADE_VISU,QTD_CANAIS,IMG_JOGO)" +
                            " VALUES ('" + ja.getString("name").replace("'", "") + "','" +
                            "" + jsonObject.getString("viewers") + "','" + jsonObject.getString("channels") + "','" +
                            img64 + "');"
                    BancoDeDados.execSQL(SQL)


                    byteArrayOutputStream = null
                    imgb = null
                    byteArray1 = null
                    img64 = null

                }

                return true
            }else{

               return true

            }




        } catch (e: JSONException) {
            e.printStackTrace()

            return true
        }



    }


    fun SelectInfoJogo(id_jogo: Int, BancoDeDados: SQLiteDatabase): Cursor {

        val cursor: Cursor

        cursor = BancoDeDados.rawQuery("SELECT " +
                "                            A._id," +
                "                            NOME_JOGO, " +
                "                            IMG_JOGO, " +
                "                            QUANTIDADE_VISU, " +
                "                            QTD_CANAIS" +
                "                        FROM " +
                "                           JOGO_TOP A" +
                "                        WHERE " +
                "                             A._id = " + id_jogo +
                "                        ORDER " +
                "                            BY A._id", null)

        return cursor
    }

}