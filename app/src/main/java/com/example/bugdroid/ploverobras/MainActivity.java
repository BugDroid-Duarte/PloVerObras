package com.example.bugdroid.ploverobras;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener{

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView list;

    private static String url = "http://ploran.gear.host/scriptobras6.php";

    ArrayList<HashMap<String, String>> obrasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obrasList = new ArrayList<HashMap<String, String>>();
        list = (ListView)findViewById(R.id.list1);

        new GetObras().execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long id) {
                Log.e("item clicks", "selected: " + position);

                Intent intent = new Intent (MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", obrasList.get(position).get("Id"));
                intent.putExtra("nomeobra", obrasList.get(position).get("nomeObra"));
                intent.putExtra("idCliente", obrasList.get(position).get("idCliente"));
                intent.putExtra("dataplevantamento", obrasList.get(position).get("DataLevantamento"));
                intent.putExtra("datarlevantamento", obrasList.get(position).get("DataRealizacao"));
                intent.putExtra("estado", obrasList.get(position).get("Estado"));
                intent.putExtra("DataRMateriais", obrasList.get(position).get("DataRMateriais"));
                intent.putExtra("DataInicioObra", obrasList.get(position).get("DataInicioObra"));
                intent.putExtra("DataConclusao", obrasList.get(position).get("DataConclusao"));
                intent.putExtra("DataVestoria\"", obrasList.get(position).get("DataVestoria"));
                intent.putExtra("Obs", obrasList.get(position).get("Obs"));
                intent.putExtra("Prompor", obrasList.get(position).get("Prompor"));
                intent.putExtra("Levantpor", obrasList.get(position).get("Levantpor"));
                intent.putExtra("executpor", obrasList.get(position).get("executpor"));
                startActivity(intent);
            }
        });
    }

    private class GetObras extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray obras = new JSONArray(jsonStr);
                    // Getting JSON Array node
                    //JSONArray obras = jsonObj.getJSONArray("obras");

                    // looping through All
                    for (int i = 0; i < obras.length(); i++) {
                        JSONObject c = obras.getJSONObject(i);

                        String id = c.getString("Id");
                        String nomeObra = c.getString("NomeObra");
                        String idCliente = c.getString("idCliente");
                        String DataLevantamento = c.getString("DataPLevantamento");
                        String DataRealizacao = c.getString("DataRLevantamento");
                        String Estado = c.getString("Estado");
                        String DataMateriais = c.getString("DataRMateriais");
                        String DataInicioObra = c.getString("DataInicioObra");
                        String DataConclusao = c.getString("DataConclusao");
                        String DataVestoria = c.getString("DataVestoria");
                        String Obs = c.getString("Obs");
                        String Prompor = c.getString("Prompor");
                        String Levantpor = c.getString("Levantpor");
                        String executpor = c.getString("executpor");

                        // tmp hash map for single contact
                        HashMap<String, String> obra = new HashMap<>();

                        // adding each child node to HashMap key => value
                        obra.put("Id", id);
                        obra.put("nomeObra", nomeObra);
                        obra.put("idCliente", idCliente);
                        obra.put("DataLevantamento", DataLevantamento);
                        obra.put("DataRealizacao", DataRealizacao);
                        obra.put("Estado", Estado);
                        obra.put("DataMateriais", DataMateriais);
                        obra.put("DataIncioObra", DataInicioObra);
                        obra.put("DataConclusao", DataConclusao);
                        obra.put("DataVestoria", DataVestoria);
                        obra.put("Obs", Obs);
                        obra.put("Prompor", Prompor);
                        obra.put("Levantpor", Levantpor);
                        obra.put("executpor", executpor);

                        // adding contact to contact list
                        obrasList.add(obra);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();

                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, obrasList,
                    R.layout.list_item, new String[]{"nomeObra", "idCliente",
                    "Estado"}, new int[]{R.id.name,
                    R.id.email, R.id.mobile});

            list.setAdapter(adapter);
        }
    }

    List<String> cities;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // User pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
    }
}