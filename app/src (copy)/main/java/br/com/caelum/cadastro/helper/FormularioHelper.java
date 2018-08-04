package br.com.caelum.cadastro.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.activity.FormularioActivity;
import br.com.caelum.cadastro.model.Aluno;

/**
 * Created by android7164 on 07/11/17.
 */
public class FormularioHelper {
    private Aluno aluno;
    private ImageView foto;
    private FloatingActionButton fotoButton;

    private EditText nome;
    private EditText telefone;
    private EditText site;
    private EditText endereco;
    private RatingBar nota;

    public FormularioHelper(FormularioActivity activity) {
        this.aluno = new Aluno();

        this.nome = (EditText) activity.findViewById(R.id.formulario_nome);
        this.telefone = (EditText) activity.findViewById(R.id.formulario_tefefone);
        this.site = (EditText) activity.findViewById(R.id.formulario_site);
        this.endereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        this.nota = (RatingBar) activity.findViewById(R.id.formulario_nota);

        foto = (ImageView) activity.findViewById(R.id.formulario_foto);
        fotoButton = (FloatingActionButton) activity.findViewById(R.id.formulario_foto_button);

    }

    public FloatingActionButton getFotoButton() {
        return fotoButton;
    }
    public Aluno pegaAlunoDoFormulario() {
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getRating()));
        aluno.setCaminhoFoto((String) foto.getTag());
        return aluno;
    }

    public boolean temNome() {
        return !nome.getText().toString().isEmpty();
    }

    public void mostraErro() {
        nome.setError("Campo nome não pode ser vazio");
    }

    public void colocaNoFormulario(Aluno aluno) {
        nome.setText(aluno.getNome());
        endereco.setText(aluno.getEndereco());
        site.setText(aluno.getSite());
        telefone.setText(aluno.getTelefone());
        nota.setRating(aluno.getNota().floatValue());
        carregaImagem(aluno.getCaminhoFoto());

        this.aluno = aluno;
    }

    public void carregaImagem(String localArquivoFoto) {
        if (localArquivoFoto != null) {
            Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
            Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, 300, 300, true);
            foto.setImageBitmap(imagemFotoReduzida);
            foto.setTag(localArquivoFoto);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
