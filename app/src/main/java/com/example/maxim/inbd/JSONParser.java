package com.example.maxim.inbd;


import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by Maxim on 25.03.2016.
 */



class JSONParser extends AsyncTask<Void, Void, String> implements Subject {
         private ArrayList<Observer> observers;
         public static String LOG_TAG = "my_log";
         HttpURLConnection urlConnection = null;
         BufferedReader reader = null;
         String resultJson = "";
         String TrueQuestion ;
         String TrueAnswer ;
         String FalseAnswer;
         JSONObject dataJsonObj;
    JSONParser(){
        observers=new ArrayList<>();
    }


        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            try {
                URL url = new URL("http://chesnokow100.esy.es/example.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // выводим целиком полученную json-строку
            Log.d(LOG_TAG, strJson);
            try {

                dataJsonObj = new JSONObject(strJson);
                TrueQuestion = dataJsonObj.getString("TrueQuestion");
                TrueAnswer = dataJsonObj.getString("TrueAnswer");
                FalseAnswer = dataJsonObj.getString("FalseAnswer");
                Log.d(LOG_TAG, TrueQuestion);
                Log.d(LOG_TAG, TrueAnswer);
                Log.d(LOG_TAG, FalseAnswer);
                notifyObservers();
                }

            catch (JSONException e) {
                e.printStackTrace();
            }
}

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);

    }

    @Override
    public void removeObserver(Observer о) {

    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update(TrueQuestion, TrueAnswer, FalseAnswer);
        }

    }
}

