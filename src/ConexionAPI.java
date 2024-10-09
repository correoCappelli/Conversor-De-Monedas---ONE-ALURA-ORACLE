import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;

public class ConexionAPI {

    //conversor utilizando API Estandar que devuelve todas las conversiones de monedas

    public apiEXCHANGE buscarMonedas(String monedaBaseUno, String monedaBaseDos, Double importeAConvertir) {


        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/556aeb496dc30b82f4742201/pair/"
                        +monedaBaseUno+"/"+monedaBaseDos+"/"+importeAConvertir);

        System.out.println("*****accediendo a la API*****  : " + direccion);
        System.out.println("************************************");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();


        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String respuesta = response.body();
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(respuesta, JsonObject.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("*** error en el formato de la URI o del Json ***"+"   "+e);
        }

        JsonObject jsonObject = gson.fromJson(respuesta, JsonObject.class);
            //muestro el tipo de error que nos proporciona la API
            if (jsonObject.get("result").getAsString().equals("error")) {
                System.out.println(jsonObject.get("error-type").getAsString());
                throw new RuntimeException(jsonObject.get("error-type").getAsString());
            }

            return new Gson().fromJson(response.body(), apiEXCHANGE.class);

        }


    public JsonObject buscarMonedasBasico(String monedaBaseUno) {


        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/556aeb496dc30b82f4742201/latest/"
                +monedaBaseUno);

        System.out.println("*****accediendo a la API*****  : " + direccion);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();


        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String respuesta = response.body();
        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(respuesta, JsonObject.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("*** error en el formato de la URI o del Json ***"+"   "+e);
        }

        JsonObject jsonObject = gson.fromJson(respuesta, JsonObject.class);
        //muestro el tipo de error que nos proporciona la API
        if (jsonObject.get("result").getAsString().equals("error")) {
            System.out.println(jsonObject.get("error-type").getAsString());
            throw new RuntimeException(jsonObject.get("error-type").getAsString());
        }

        return jsonObject;

    }
        }
