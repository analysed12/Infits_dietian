package com.ultimate.infits;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FetchData extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... strings) {
        System.out.println("Azar");
        try {
            URL url = new URL("https://192.168.182.1/get_from.php");
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String data = "";

            while ((line = bufferedReader.readLine()) != null) {
                data += line;
            }
            JSONObject jsonResponse = new JSONObject(data);
            String name = jsonResponse.getString("name");
            DataFromDatabase.name = name;
            System.out.println(name);
            Log.d("Name",name);
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return name;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println(s);
    }
}
