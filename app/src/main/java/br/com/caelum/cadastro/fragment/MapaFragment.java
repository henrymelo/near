package br.com.caelum.cadastro.fragment;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.util.Localizador;

/**
 * Created by android7164 on 10/11/17.
 */

public class MapaFragment extends SupportMapFragment {
    private Geocoder geo;

    @Override
    public void onResume() {
        super.onResume();
        //Localizador localizador = new Localizador(getActivity());
        //LatLng local = localizador.getCoordenada("Rua Vergueiro 3185 Vila Mariana");
        //Log.i("MAPA", "Coordenadas da Caelum: " + local);

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Localizador localizador = new Localizador(getActivity());
                LatLng local = localizador.getCoordenada("Rua Vergueiro 3185 Vila Mariana");

                centralizaNo(local, googleMap);

                AlunoDAO dao = new AlunoDAO(getActivity());
                List<Aluno> alunos = dao.getLista();
                dao.close();

                for (Aluno aluno : alunos) {
                    MarkerOptions marcador = new MarkerOptions();
                    marcador.title(aluno.getNome());
                    marcador.position(localizador.getCoordenada(aluno.getEndereco()));
                    googleMap.addMarker(marcador);
                }

            }


        });

    }


    private void centralizaNo(LatLng local, GoogleMap mapa) {

    }
}
