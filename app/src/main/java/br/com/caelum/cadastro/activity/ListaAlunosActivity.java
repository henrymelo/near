package br.com.caelum.cadastro.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.adapter.ListaAlunosAdapter;
import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.permission.Permissao;
import br.com.caelum.cadastro.support.WebClient;
import br.com.caelum.cadastro.task.EnviaAlunosTask;

import static android.view.ContextMenu.*;
import static android.widget.AdapterView.*;

public class ListaAlunosActivity extends AppCompatActivity {
    private List<Aluno> alunos;
    private ListView listaAlunos;
    private ContextMenu menu;
    private ContextMenuInfo menuInfo;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Permissao.fazPermissao(this);

        ListView listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        registerForContextMenu(listaAlunos);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void onResume() {
        super.onResume();

        this.carregaLista();

        ListView listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        //listaAlunos.setOnItemClickListener(new OnItemClickListener() {
        //public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
        //Toast.makeText(ListaAlunosActivity.this, "Posição selecionada: " + posicao, Toast.LENGTH_LONG).show();
        //    }
        // });

        listaAlunos.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                Aluno aluno = (Aluno) adapter.getItemAtPosition(posicao);
                edicao.putExtra("aluno", aluno);
                startActivity(edicao);
            }
        });


        listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
                String aluno = adapter.getItemAtPosition(posicao).toString();
                Toast.makeText(ListaAlunosActivity.this, "Clique longo: " + aluno, Toast.LENGTH_LONG).show();
                return false;
            }
        });


        FloatingActionButton botaoAdiciona = (FloatingActionButton) findViewById(R.id.lista_alunos_floating_button);

        botaoAdiciona.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ListaAlunosActivity.this, "Floating button clicado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

    }

    private void carregaLista() {
        //String[] alunos = {"Anderson", "Filipe", "Guilherme"};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alunos);

        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();

        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos); //ListaCustomizadaFicouLentaScrool


        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        listaAlunos.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mapa:
                Intent mapa = new Intent(this, MostraAlunosActivity.class);
                startActivity(mapa);
                return true;
            case R.id.menu_enviar_notas:
                AlunoDAO dao = new AlunoDAO(this);
                List<Aluno> alunos = dao.getLista();
                dao.close();

                String json = new AlunoConverter().toJSON(alunos);

                EnviaAlunosTask task = new EnviaAlunosTask(ListaAlunosActivity.this);
                task.execute(json);


                //Toast.makeText(this, json, Toast.LENGTH_LONG).show();
                /**
                WebClient client = new WebClient();
                String resposta = null;
                try {
                    resposta = client.post(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, resposta, Toast.LENGTH_LONG).show();
                 */
                return true;
            case R.id.menu_receber_provas:
                Intent provas = new Intent(this, ProvasActivity.class);
                startActivity(provas);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);

        MenuItem ligar = menu.add("Ligar");
        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);

        MenuItem sms = menu.add("Enviar SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.putExtra("sms_body", "Um test do curso Android Caelum");
        sms.setIntent(intentSms);
        intentSms.setData(Uri.parse("sms:"+alunoSelecionado.getTelefone()));

        MenuItem map = menu.add("Achar no Mapa");
        Intent intentMap = new Intent(Intent.ACTION_VIEW);
        map.setIntent(intentMap);
        intentMap.setData(Uri.parse("geo:0,0?z=14&q="+alunoSelecionado.getEndereco()));

        MenuItem site = menu.add("Navegar no site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String siteUrl = alunoSelecionado.getSite();

        if (!siteUrl.startsWith("http://")) {
            siteUrl = "http://"+siteUrl;
        }
        site.setIntent(intentSite);
        intentSite.setData(Uri.parse(siteUrl));

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                new AlertDialog.Builder(ListaAlunosActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Deletar")
                        .setMessage("Deseja mesmo deletar?")
                        .setPositiveButton("Quero",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        AlunoDAO dao = new AlunoDAO(
                                                ListaAlunosActivity.this);
                                        dao.deletar(alunoSelecionado);
                                        dao.close();
                                        carregaLista();
                                    }

                                }).setNegativeButton("Não", null).show();
                return false;
            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ListaAlunos Page") // TODO: Define a title for the content shown.
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
