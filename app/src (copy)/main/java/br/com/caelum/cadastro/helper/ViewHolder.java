package br.com.caelum.cadastro.helper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.caelum.cadastro.R;

/**
 * Created by android7164 on 09/11/17.
 */

public class ViewHolder {
    final public TextView nome;
    final public ImageView foto;

    public ViewHolder(View view) {
        nome = (TextView) view.findViewById(R.id.item_nome);
        foto = (ImageView) view.findViewById(R.id.item_foto);
    }
}
