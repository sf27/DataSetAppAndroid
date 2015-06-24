package com.example.data_set_example;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import android.app.Activity;

public class UtilsFiles {

	Activity	activity;

	public UtilsFiles(Activity activity) {
		this.activity = activity;
	}

	public String requestFile() {
		try {

			InputStream is = activity.getAssets().open("data.json");

			int size = is.available();

			byte[] buffer = new byte[size];

			is.read(buffer);

			is.close();

			return new String(buffer, "UTF-8");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String requestWebService(
			String serviceUrl) {

		HttpURLConnection urlConnection = null;
		try {
			// create connection
			URL urlToRequest = new URL(serviceUrl);
			urlConnection = (HttpURLConnection) urlToRequest.openConnection();
			// urlConnection.setConnectTimeout(3200);
			// urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);

			// handle issues
			int statusCode = urlConnection.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
				// handle unauthorized (if service requires user login)
			} else if (statusCode != HttpURLConnection.HTTP_OK) {
				// handle any other errors, like 404, 500,..
			}

			// create JSON object from content
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line = null;

			// Read Server Response
			while ((line = reader.readLine()) != null) {
				// Append server response in string
				sb.append(line + "");
			}

			return sb.toString();

		} catch (MalformedURLException e) {
			// URL is invalid
		} catch (SocketTimeoutException e) {
			// data retrieval or connection timed out
		} catch (IOException e) {
			// could not read response body
			// (could not create input stream)
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		return null;
	}
}
