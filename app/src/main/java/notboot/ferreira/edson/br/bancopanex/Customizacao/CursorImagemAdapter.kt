package notboot.ferreira.edson.br.bancopanex.Customizacao

/**
 * Created by edson.ferreira on 23/01/2018.
 */
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.widget.CursorAdapter
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import notboot.ferreira.edson.br.bancopanex.R


class CursorImagemAdapter(context: Context, cursor: Cursor) : CursorAdapter(context, cursor, 0) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {


        return LayoutInflater.from(context).inflate(R.layout.lista_imagem, parent, false)

    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {

        val image = view.findViewById(R.id.imagemJogo) as ImageView
        val descricao = view.findViewById(R.id.nomeJogo) as TextView


        try {
            var decodedString: ByteArray? = Base64.decode(cursor.getString(2), Base64.DEFAULT)
            val myBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString!!.size)

            if (myBitmap != null) {
                image.setImageBitmap(Bitmap.createScaledBitmap(myBitmap, 800, 800, true))
                descricao.text = cursor.getString(1)

            }

            decodedString = null


        } catch (e: NullPointerException) {


        }


    }

}