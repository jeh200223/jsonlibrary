package com.kbu.exam.jsonbylibrary;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

public class LottoThread extends Thread {
    private String lturl;
    private StringBuilder builder = new StringBuilder();
    private Context context;
    private String line = "";

    public LottoThread(String lturl, Context context) {
        this.lturl = lturl;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(lturl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            streamReader.close();
            inputStream.close();
            connection.disconnect();
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        super.run();
    }

    public String getResult() {
        return builder.toString();
    }
}
