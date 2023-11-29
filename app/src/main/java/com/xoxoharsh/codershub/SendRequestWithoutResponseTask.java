package com.xoxoharsh.codershub;
import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class SendRequestWithoutResponseTask extends AsyncTask<String, Void, Integer> {
    private Callback callback;
    public interface Callback {
        void onTaskCompleted(int responseCode);
    }
    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    @Override
    protected Integer doInBackground(String... params) {
        if (params.length == 0) {
            return -1;
        }
        String email = params[0];
        Log.d("CodersHub_Errors", "Entered SendRequestWithoutResponseTask");
        String urlString = "https://minor-fcph.onrender.com//profile/" + email;
        Log.d("CodersHub_Errors", "Url string - " + urlString);
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.close();
            int responseCode = connection.getResponseCode();
            Log.d("CodersHub_Errors", "Response Code: " + responseCode);
            connection.disconnect();
            return responseCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicates a failure
        }
    }
    @Override
    protected void onPostExecute(Integer responseCode) {
        if (callback != null) {
            callback.onTaskCompleted(responseCode);
        }
    }
}


