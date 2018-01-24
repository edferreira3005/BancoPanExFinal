package notboot.ferreira.edson.br.bancopanex.Internet

/**
 * Created by edson.ferreira on 23/01/2018.
 */

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.net.HttpURLConnection


class Download {

    fun getBitmapFromURL(src: String): Bitmap? {
        try {
            val url = java.net.URL(src)

            val conexao = url
                    .openConnection() as HttpURLConnection

            conexao.doInput = true

            conexao.connect()

            val input = conexao.inputStream

            val Imagem = BitmapFactory.decodeStream(input)

            return Imagem

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }
}