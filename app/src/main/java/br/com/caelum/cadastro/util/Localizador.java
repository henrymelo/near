package br.com.caelum.cadastro.util;

import android.content.Context;
import android.location.Address;import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by android7164 on 10/11/17.
 */

public class Localizador {
    private Geocoder geo;

    public Localizador (Context ctx) {
        geo = new Geocoder(ctx, Locale.getDefault());
    }

    public LatLng getCoordenada (String endereco) {
        try {
            List<android.location.Address> listaEnderecos;
            listaEnderecos = geo.getFromLocationName(endereco,1);
            if (!listaEnderecos.isEmpty()) {
                android.location.Address address = listaEnderecos.get(0);
                return new LatLng(address.getLatitude(), address.getLongitude());
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }


    }
}
