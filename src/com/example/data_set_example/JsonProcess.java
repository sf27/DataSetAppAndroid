package com.example.data_set_example;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonProcess {
	private JSONObject	jsonResponse	= null;

	public JsonProcess(String stringData) {

		ArrayList<String> lista = new ArrayList<String>();
		try {
			jsonResponse = new JSONObject(stringData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getListData() {
		ArrayList<String> lista = new ArrayList<String>();

		try {
			JSONArray data = this.jsonResponse.getJSONArray("data");
			System.out.println(data);
			for (int i = 0; i < data.length(); i++) {
				JSONArray each = data.getJSONArray(i);
				lista.add(each.get(11).toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	public String getInformation() {
		String information = "";
		try {
			JSONObject meta = this.jsonResponse.getJSONObject("meta");
			JSONObject view = meta.getJSONObject("view");
			information = view.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return information;
	}
}
