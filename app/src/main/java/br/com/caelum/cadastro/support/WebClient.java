package br.com.caelum.cadastro.support;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by android7164 on 09/11/17.
 */

public class WebClient {
    public String post(String json) throws IOException{
        //http://blog.alura.com.br/facilitando-as-requisicoes-utilizando-okhttp-no-android/
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.caelum.com.br/mobile";
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);
        builder.post(body);
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        String jsonDeResposta = response.body().string();
        return jsonDeResposta;
    }

    public String get() throws IOException{
        //http://blog.alura.com.br/facilitando-as-requisicoes-utilizando-okhttp-no-android/
        String urlGet = "http://umsite.com/fazGet";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlGet).build();
        Response response = client.newCall(request).execute();
        String jsonDeResposta = response.body().string();
        return  jsonDeResposta;
    }
}
