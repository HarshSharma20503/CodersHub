package com.xoxoharsh.codershub;
import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendRequestWithoutResponseTask extends AsyncTask<String, Void, Integer> {

    private Callback callback;

    // Callback interface
    public interface Callback {
        void onTaskCompleted(int responseCode);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
    @Override
    protected Integer doInBackground(String... params) {
        if (params.length == 0) {
            return -1; // No email provided
        }
        String email = params[0];
        Log.d("CodersHub_Errors", "Entered SendRequestWithoutResponseTask");
        // Replace "your_base_url_here" with the base URL, and append the email parameter
        String urlString = "https://minor-api.onrender.com/profile/" + email;
        Log.d("CodersHub_Errors", "Url string - " + urlString);
        try {
            // Create a URL object with the specified URL
            URL url = new URL(urlString);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST or GET, depending on your requirements
            connection.setRequestMethod("POST"); // or "GET"

            // Enable input/output streams, since you are not interested in the response
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // Get the output stream of the connection
            OutputStream outputStream = connection.getOutputStream();

            // Write any data you want to send in the request body (if needed)
            // For example, if sending JSON data:
            // String jsonInputString = "{\"key\":\"value\"}";
            // outputStream.write(jsonInputString.getBytes());

            // Close the output stream
            outputStream.close();

            // Get the response code
            int responseCode = connection.getResponseCode();
            Log.d("CodersHub_Errors", "Response Code: " + responseCode);

            // Close the connection
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


