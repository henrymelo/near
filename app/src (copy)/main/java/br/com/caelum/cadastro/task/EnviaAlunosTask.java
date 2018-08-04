package br.com.caelum.cadastro.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import br.com.caelum.cadastro.activity.ListaAlunosActivity;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.support.WebClient;

/**
 * Created by android7164 on 09/11/17.
 */

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {
    private final Context context;
    private ProgressDialog progress;

    public EnviaAlunosTask(Context context) {
            this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(context, "Aguarde...", "Envio de dados para a web", true, true);
    }

    @Override
    protected String doInBackground(Object... jsons) {
        String json = (String) jsons[0];
        WebClient client = new WebClient();
        String media = null;
        try {
            media = client.post(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return media;
    }

    @Override
    protected void onPostExecute(String rsMedia) {
        progress.dismiss();
        Toast.makeText(context, rsMedia, Toast.LENGTH_LONG).show();

    }
}
