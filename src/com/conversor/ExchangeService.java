package com.conversor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExchangeService {

    private static final String API_KEY = "72c3a0cdb7394da2916e0a89"; // ← Pega tu API Key aquí
    private static final String BASE = "USD"; // Usamos USD como base
    private static Map<String, Double> tasas = new HashMap<>();

    // Inicializar las tasas al iniciar la aplicación
    public static void inicializarTasas() {
        try {
            String urlStr = "https://v6.exchangerate-api.com/v6/" + "72c3a0cdb7394da2916e0a89" + "/latest/" + BASE;
            URL url = new URL(urlStr);

            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(request.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject rates = json.getAsJsonObject("conversion_rates");

            for (String key : rates.keySet()) {
                tasas.put(key, rates.get(key).getAsDouble());
            }

            System.out.println("Tasas cargadas correctamente.");

        } catch (Exception e) {
            System.out.println("Error al obtener las tasas: " + e.getMessage());
        }
    }

    // Obtener tasa entre cualquier par de monedas
    public static double obtenerTasa(String base, String destino) {
        if (base.equals(destino)) return 1;

        double tasaBaseUSD = base.equals(BASE) ? 1 : 1 / tasas.get(base);
        double tasaDestinoUSD = destino.equals(BASE) ? 1 : tasas.get(destino);

        return tasaBaseUSD * tasaDestinoUSD;
    }
}
