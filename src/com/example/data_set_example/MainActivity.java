package com.example.data_set_example;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private TextView					txtInfo;
	private Button						loadMore;
	private ListView					listData;
	private static ArrayList<String>	lista				= new ArrayList<String>();
	private static ArrayList<String>	temp				= new ArrayList<String>();
	private static int					pagination			= 10;
	private static int					numberOfItemsLoaded	= pagination;
	private ArrayAdapter<String>		arrayAdapter;

	@Override
	protected void onCreate(
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtInfo = (TextView) findViewById(R.id.txtInfo);
		listData = (ListView) findViewById(R.id.listData);
		loadMore = (Button) findViewById(R.id.btnLoadMore);
		GetJsonTask task = new GetJsonTask();
		task.execute(Variables.URL);

		loadMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(
					View v) {
				sliceArrayList(numberOfItemsLoaded + 1, numberOfItemsLoaded + pagination);
				numberOfItemsLoaded += pagination;
				arrayAdapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "Data loaded", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(
			Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) { return true; }
		return super.onOptionsItemSelected(item);
	}

	public class GetJsonTask extends AsyncTask<String, Void, String> {
		ProgressDialog		dialog	= new ProgressDialog(MainActivity.this);

		private UtilsFiles	utils;

		public GetJsonTask() {
			this.utils = new UtilsFiles(MainActivity.this);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// dialog.setIndeterminate(true);
			// dialog.setMessage("Please wait..");
			// dialog.show();
		}

		protected String doInBackground(
				String... urls) {

			String data;
			if (!Variables.DEBUG) {
				String url = urls[0];
				data = utils.requestWebService(url);
			} else {
				data = utils.requestFile();
			}
			return data;
		}

		protected void onPostExecute(
				String result) {
			dialog.dismiss();
			JsonProcess process = new JsonProcess(result);
			txtInfo.setText(process.getInformation());

			lista = (ArrayList<String>) process.getListData();

			sliceArrayList(0, numberOfItemsLoaded);
			arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, temp);

			listData.setAdapter(arrayAdapter);
		}
	}

	private ArrayList<String> sliceArrayList(
			int a,
			int b) {
		for (int i = a; i < b; i++) {
			String item = lista.get(i);
			if (item != null) {
				temp.add(item);
			}
		}
		return temp;
	}

}
