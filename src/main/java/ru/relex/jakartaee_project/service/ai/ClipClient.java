package ru.relex.jakartaee_project.service.ai;


import okhttp3.*;
import com.google.gson.*;

import java.io.IOException;

public class ClipClient {

    private static final String API_URL = "http://localhost:5005";

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    // =============== 1. Получить embedding картинки ===============
    public float[] getImageEmbedding(byte[] imageBytes) throws IOException {

        RequestBody fileBody = RequestBody.create(imageBytes, MediaType.parse("image/jpeg"));

        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "uploaded.jpg", fileBody)
                .build();

        Request request = new Request.Builder()
                .url(API_URL + "/embed_image")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();

        JsonObject obj = gson.fromJson(responseJson, JsonObject.class);
        JsonArray arr = obj.getAsJsonArray("embedding");

        float[] vector = new float[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            vector[i] = arr.get(i).getAsFloat();
        }

        return vector;
    }

    // =============== 2. Найти похожие ===============
    public double[] searchSimilar(float[] queryVec, float[][] dbVectors) throws IOException {
        // Return empty if no database vectors to compare
        if (dbVectors == null || dbVectors.length == 0) {
            return new double[0];
        }

        JsonObject req = new JsonObject();
        req.add("query_vector", gson.toJsonTree(queryVec));
        req.add("db_vectors", gson.toJsonTree(dbVectors));

        RequestBody body = RequestBody.create(
                req.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(API_URL + "/search_similar")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();

        if (!response.isSuccessful()) {
            throw new IOException("CLIP API error: " + responseJson);
        }

        JsonObject resp = gson.fromJson(responseJson, JsonObject.class);
        JsonArray scoresJson = resp.getAsJsonArray("scores");

        double[] scores = new double[scoresJson.size()];
        for (int i = 0; i < scoresJson.size(); i++) {
            scores[i] = scoresJson.get(i).getAsDouble();
        }

        return scores;
    }
}

