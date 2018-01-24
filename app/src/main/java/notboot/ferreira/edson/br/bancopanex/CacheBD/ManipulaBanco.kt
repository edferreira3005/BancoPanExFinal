package notboot.ferreira.edson.br.bancopanex.CacheBD

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.util.Base64
import android.view.View
import android.widget.*
import notboot.ferreira.edson.br.bancopanex.Customizacao.CursorImagemAdapter
import notboot.ferreira.edson.br.bancopanex.Internet.ConexaoUrls
import notboot.ferreira.edson.br.bancopanex.Internet.Download
import notboot.ferreira.edson.br.bancopanex.Telas.InfoGames
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream



@Suppress("DEPRECATION")

class ManipulaBanco {
    var idt:Int = 0

    fun InsereBanco(BancoDeDados: SQLiteDatabase , g:ListView, c:Context){

        try {

        val ar = ConexaoUrls()
        //Se conectando com meu ID ao Twitch
        val jsonStr = ar.chamadaGet("https://api.twitch.tv/kraken/games/top?limit=100&client_id=6b6l8pfhm2oec7dngvdjw95l5tffq3")


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
                    var imgb: Bitmap = down.getBitmapFromURL(imgjs.getString("medium").trim())!!
                    var byteArrayOutputStream = ByteArrayOutputStream()

                    imgb.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                    var byteArray1 = byteArrayOutputStream.toByteArray()

                    var img64 = Base64.encodeToString(byteArray1, Base64.DEFAULT)



                    SQL = "INSERT INTO JOGO_TOP (NOME_JOGO,QUANTIDADE_VISU,QTD_CANAIS,IMG_JOGO)" +
                            " VALUES ('" + ja.getString("name").replace("'", "") + "','" +
                            "" + jsonObject.getString("viewers") + "','" + jsonObject.getString("channels") + "','" +
                            img64 + "');"
                    BancoDeDados.execSQL(SQL)




                }


            }else{

                Toast.makeText(c, "Falha na Internet",
                        Toast.LENGTH_SHORT).show()

            }




        } catch (e: JSONException) {
            e.printStackTrace()
            Toast.makeText(c, "Falha na Internet",
                    Toast.LENGTH_SHORT).show()
        }

        val cursor: Cursor

        cursor = BancoDeDados.rawQuery("SELECT " +
                "                            A._id," +
                "                            NOME_JOGO, " +
                "                            IMG_JOGO " +
                "                        FROM " +
                "                           JOGO_TOP A" +
                "                        ORDER " +
                "                            BY A._id", null)

        if (cursor.getCount() > 0) {


            var AdapterImagem = CursorImagemAdapter(c, cursor)

            g.setAdapter(AdapterImagem)


            g.onItemClickListener = object : AdapterView.OnItemClickListener {


                override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {


                    idt = position
                    c.startActivity(Intent(c, InfoGames::class.java))

                }
            }

        }

    }

    fun SelectTop100(g:GridView, BancoDeDados: SQLiteDatabase, c:Context): Boolean {

        val cursor: Cursor

        cursor = BancoDeDados.rawQuery("SELECT " +
                "                            A._id," +
                "                            NOME_JOGO, " +
                "                            IMG_JOGO " +
                "                        FROM " +
                "                           JOGO_TOP A" +
                "                        ORDER " +
                "                            BY A._id", null)

        val coluna = arrayOf("IMG_JOGO", "NOME_JOGO")
        val AdapterLista: SimpleCursorAdapter

        if (cursor.getCount() > 0) {


            var AdapterImagem = CursorImagemAdapter(c, cursor)

            g.setAdapter(AdapterImagem);
            g.onItemClickListener = object : AdapterView.OnItemClickListener {


                override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    idt = position
                    c.startActivity(Intent(c, InfoGames::class.java))

                }
            }



        }
        return true

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
                "                             A._id = '" + id_jogo + "'" +
                "                        ORDER " +
                "                            BY A._id", null)

        return cursor
    }

}