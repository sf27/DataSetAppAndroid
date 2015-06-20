package com.example.data_set_example;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonProcess {

	public static String processJsonResponse(
			String stringData) {
		JSONObject jsonResponse = null;

		String OutputData = "";
		try {
			jsonResponse = new JSONObject(stringData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			JSONObject meta = jsonResponse.getJSONObject("meta");
			JSONObject view = meta.getJSONObject("view");
			String name = view.getString("name");
			System.out.println(name);
			JSONArray data = jsonResponse.getJSONArray("data");
			System.out.println(data);
			ArrayList<String> lista = new ArrayList<String>();
			for (int i = 0; i < data.length(); i++) {
				JSONArray each = data.getJSONArray(i);
				lista.add(each.get(11).toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
