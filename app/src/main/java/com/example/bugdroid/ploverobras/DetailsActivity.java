package com.example.bugdroid.ploverobras;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView id;
    TextView obratext;
    TextView clientetext;
    TextView dataplevantamento;
    TextView datarlevantamento;
    TextView estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        id = (TextView)findViewById(R.id.idtext);
        obratext = (TextView)findViewById(R.id.obratext);
        clientetext = (TextView)findViewById(R.id.clientetext);
        dataplevantamento = (TextView)findViewById(R.id.dataplevantamento);
        datarlevantamento = (TextView)findViewById(R.id.datarlevantamento);
        estado = (TextView)findViewById(R.id.estado);

        id.setText(getIntent().getExtras().getString("id"));
        obratext.setText(getIntent().getExtras().getString("nomeobra"));
        clientetext.setText(getIntent().getExtras().getString("idCliente"));
        dataplevantamento.setText(getIntent().getExtras().getString("dataplevantamento"));
        datarlevantamento.setText(getIntent().getExtras().getString("datarlevantamento"));
        estado.setText(getIntent().getExtras().getString("estado"));

    }
}
