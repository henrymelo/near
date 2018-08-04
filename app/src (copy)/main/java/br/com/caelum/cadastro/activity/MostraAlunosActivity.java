package br.com.caelum.cadastro.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.fragment.MapaFragment;

/**
 * Created by android7164 on 10/11/17.
 */

public class MostraAlunosActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mostra_alunos);

        MapaFragment mapaFragment = new MapaFragment();

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.mostra_alunos_mapa, mapaFragment);
        tx.commit();
    }
}
