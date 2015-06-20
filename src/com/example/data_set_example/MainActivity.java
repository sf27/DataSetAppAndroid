package com.example.data_set_example;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

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
	public boolean onCreateOptionsMenu(
			Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar_menu, menu);

		// /boton de busqueda en la barra de acciones
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView mSearchView = (SearchView) searchItem.getActionView();
		System.out.println("Epale: " + mSearchView);
		searchData(mSearchView);// se encarga de buscar los datos en la lista
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			MenuItem item) {
		ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) listData.getAdapter();
		switch (item.getItemId()) {
			case R.id.ordenar_asc:
				Toast.makeText(this, "Datos ordenados en forma ascendiente", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.ordenar_desc:
				Toast.makeText(this, "Datos ordenados en forma descendiente", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
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

	/**
	 * Busca datos en la lista y los filtra.
	 * 
	 * @param mSearchView
	 */
	private void searchData(
			SearchView mSearchView) {
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			/**
			 * Metodo que se ejecuta cuando se presiona aceptar en el campo de
			 * busqueda de la barra de accion
			 */
			@Override
			public boolean onQueryTextSubmit(
					String query) {
				ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) listData.getAdapter();
				Filter filter = arrayAdapter.getFilter();
				filter.filter(query);
				arrayAdapter.notifyDataSetChanged();
				return false;
			}

			/**
			 * Metodo que se ejecuta cuando se va escribiendo en el campo de
			 * busqueda de la barra de accion
			 */
			@Override
			public boolean onQueryTextChange(
					String newText) {
				ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) listData.getAdapter();
				Filter filter = arrayAdapter.getFilter();
				filter.filter(newText);
				arrayAdapter.notifyDataSetChanged();
				return false;
			}

		});

	}
}
