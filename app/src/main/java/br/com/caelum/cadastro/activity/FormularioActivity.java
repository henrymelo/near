package br.com.caelum.cadastro.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.helper.FormularioHelper;
import br.com.caelum.cadastro.model.Aluno;

public class FormularioActivity extends AppCompatActivity {
    private FormularioHelper helper;
    public static final String ALUNO_SELECIONADO = "alunoSelecionado";
    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        /**
         Button botao =  (Button) findViewById(R.id.formulario_botao);
         botao.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        Toast.makeText(FormularioActivity.this, "Você clicou no botão", Toast.LENGTH_LONG).show();
        finish();
        }
        });
         */

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        this.helper = new FormularioHelper(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Intent intent = getIntent();
        if (intent.hasExtra("aluno")) {
            Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
            helper.colocaNoFormulario(aluno);
        }

        FloatingActionButton foto = helper.getFotoButton();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Uri localFoto = Uri.fromFile(new File(localArquivoFoto));
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
                startActivityForResult(irParaCamera, 123);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return false;
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                //Toast.makeText(this, "Ok clicado", Toast.LENGTH_LONG).show();
                //helper.pegaAlunoDoFormulario();
                Aluno aluno = helper.pegaAlunoDoFormulario();
                AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
                dao.close();
                if (helper.temNome()) {
                    if (aluno.getId() == null) {
                        //salva aluno...
                        dao.insere(aluno);
                    } else {
                        dao.altera(aluno);
                    }
                    finish();
                } else {
                    helper.mostraErro();
                }

                return false;
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == TIRA_FOTO) {
            if (resultCode == Activity.RESULT_OK) {
                helper.carregaImagem(this.localArquivoFoto);
            } else {
                this.localArquivoFoto = null;
            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Formulario Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
