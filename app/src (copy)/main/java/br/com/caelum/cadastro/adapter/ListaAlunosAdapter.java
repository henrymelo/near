package br.com.caelum.cadastro.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.model.Aluno;

/**
 * Created by android7164 on 09/11/17.
 */

public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Activity activity;
    //private Context context;

    public ListaAlunosAdapter(Activity activity, List<Aluno> alunos) {
        this.activity = activity;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int pos) {
        return alunos.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return alunos.get(pos).getId();
    }

    @Override
    public View getView(int posicao, View convertView, ViewGroup parent) {
        // View view = activity.getLayoutInflater().inflate(R.layout.item, parent, false); // LENTO
        /**
         * IMPLEMENTAR PADRAO ViewHolder somente para ver a MELHORIA
         * http://blog.alura.com.br/utilizando-o-padrao-viewholder/
         *
         * O GOOGLE usa uma API que ja tem o ViewHolder encapsulado, e outras melhorias // MAIS USADO
         * http://blog.alura.com.br/criando-listas-com-recyclerview/
         */

        View view;
        if (convertView == null) { // MAIS RAPIDO
            view = LayoutInflater.from(activity).inflate(R.layout.item,parent, false);
        } else {
            view = convertView;
        }

        Aluno aluno = alunos.get(posicao);

        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());

        Bitmap bm;

        if (aluno.getCaminhoFoto() != null) {
            bm = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }
        bm = Bitmap.createScaledBitmap(bm, 100,100, true);

        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);
        foto.setImageBitmap(bm);
        foto.setScaleType(ImageView.ScaleType.FIT_XY);

        if (posicao % 2 == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setBackgroundColor(activity.getResources().getColor(R.color.linha_par, activity.getTheme()));
            } else {

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setBackgroundColor(activity.getResources().getColor(R.color.linha_impar, activity.getTheme()));
            } else {
                view.setBackgroundColor(activity.getResources().getColor(R.color.linha_impar));

            }
        }

        return view;
    }
}
