package com.example.data_set_example;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class GetJsonTask extends AsyncTask<String, Void, String> {

	private UtilsFiles		utils;
	private ProgressDialog	dialog;

	public GetJsonTask(Activity activity) {
		utils = new UtilsFiles(activity);
		dialog = new ProgressDialog(activity);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog.setIndeterminate(true);
		dialog.setMessage("Please wait..");
		dialog.show();
	}

	protected String doInBackground(
			String... urls) {
		String url = urls[0];
		String data;
		if (!Variables.DEBUG) {
			data = utils.requestWebService(url);
		} else {
			data = utils.requestFile();
		}
		return data;
	}

	protected void onPostExecute(
			String result) {
		dialog.dismiss();
		JsonProcess.processJsonResponse(result);
	}
}
