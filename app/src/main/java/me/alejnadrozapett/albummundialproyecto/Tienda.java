package me.alejnadrozapett.albummundialproyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Tienda extends AppCompatActivity {
    ImageButton ibSobre;
    //TextView sobres = (TextView) findViewById(R.id.tvTienda4);

    private SharedPreferences persistencia;
    private RequestQueue request;
    private StringRequest stringRequest;
    private final int sobresTotal = 10;
    private int sobresComprados=0;
    private int[] estampasCompradas = new int[50];
    private int[] estampasRepetidas = new int[50];
    private int[] nuevasEstampas = new int[5];
    private String TAG = Tienda.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        ibSobre=(ImageButton) findViewById(R.id.ibSobre);
        persistencia = getSharedPreferences("persistencia", Context.MODE_PRIVATE);
        leerInfo();
        ibSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSobresComprados() < getSobresTotal()){
                    numerosAleatorios();
                } else{
                    advertenciaDeCompra();
                }
            }
        });
    }

    public void advertenciaDeCompra() {
        Toast.makeText(Tienda.this, "Ya no puedes comprar más sobres!", Toast.LENGTH_SHORT).show();
    }
    private void numerosAleatorios(){
        request = Volley.newRequestQueue(this);
        String url="http://serverbpw.com/cm/cards.php?type=xml";
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, response.toString());
                Toast.makeText(Tienda.this, "Sobre comprado!", Toast.LENGTH_SHORT).show();
                setNuevasEstampas(parseRequest(response.toString()));
                setEstampasCompradas(getNuevasEstampas());
                setSobresComprados();
                setPersistencia();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Tienda.this, "No se pudo completar la compra", Toast.LENGTH_LONG).show();
            }
        });

        request.add(stringRequest);
    }

    private void setPersistencia() {
        SharedPreferences.Editor editorPersistencia = persistencia.edit();
        String compradas = "";
        for (int i = 0; i<getEstampasCompradas().length; i++){
            if (i < getEstampasCompradas().length-1){
                compradas = compradas+Integer.toString(getEstampasCompradas()[i])+",";
            } else if (i == getEstampasCompradas().length-1){
                compradas = compradas+Integer.toString(getEstampasCompradas()[i]);
            }
        }
        String repetidas = "";
        for (int i = 0; i<estampasRepetidas.length; i++){
            if (i < getEstampasRepetidas().length-1){
                repetidas = repetidas+Integer.toString(estampasRepetidas[i])+",";
            } else if (i == getEstampasRepetidas().length-1){
                repetidas = repetidas+Integer.toString(estampasRepetidas[i]);
            }
        }
        Toast.makeText(this, compradas,Toast.LENGTH_LONG ).show();
        Toast.makeText(this, repetidas,Toast.LENGTH_LONG ).show();
        Toast.makeText(this, Integer.toString(sobresComprados),Toast.LENGTH_LONG ).show();
        editorPersistencia.putString("compradas", compradas);
        editorPersistencia.putString("repetidas", repetidas);
        editorPersistencia.putString("sobresComprados", Integer.toString(sobresComprados));
        editorPersistencia.apply();
    }
    private void leerInfo(){
        //persistencia = getSharedPreferences("persistencia", Context.MODE_PRIVATE);
        if (persistencia.contains("repetidas")){
            // la informacion va a estar guardada de la siguiente forma: 1,2,3,0,5,0,0,8
            String repetidas = persistencia.getString("repetidas", "0");
            estampasRepetidas = parseoInfo(repetidas);
            if (persistencia.contains("compradas")){
                String compradas = persistencia.getString("compradas", "0");
                estampasCompradas = parseoInfo(compradas);
            }
            if(persistencia.contains("sobresComprados")){
                setSobresComprados(Integer.parseInt(persistencia.getString("sobresComprados", "0")));
            }
        }
    }
    private int[] parseoInfo(String cadena){
        String[] r = cadena.split(",");
        int[] regreso = new int[r.length];
        for(int i=0; i<r.length ; i++){
            regreso[i] = Integer.parseInt(r[i]);
        }
        return regreso;
    }

    private int[] parseRequest(String s) {
        int[] nv = new int[5];
        String values = s.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><result><card><id>", "").replace(" ", "");
        values = values.replace("</id>",",").replace("<card>","").replace("</card>","").replace("<id>", "");
        String[] list = values.split(",");
        for(int i=0; i<list.length-1; i++){
            nv[i] = Integer.parseInt(list[i]);
        }
        return nv;
    }


    public int getSobresTotal() {
        return sobresTotal;
    }

    public int getSobresComprados() {
        return sobresComprados;
    }

    public void setSobresComprados() {
        this.sobresComprados = this.sobresComprados +1;
        //sobres.setText("Sobre comprados: "+Integer.toString(this.sobresComprados));
    }
    private void setSobresComprados(int sc){
        this.sobresComprados = sc;
        //sobres.setText("Sobre comprados: "+Integer.toString(this.sobresComprados));
    }
    public int[] getEstampasCompradas() {
        return estampasCompradas;
    }

    private void setEstampasCompradas(int[] nv) {
        for(int i=0; i<nv.length; i++){
            if(estampasCompradas[nv[i]-1] != nv[i]){
                estampasCompradas[nv[i]-1] = nv[i];
            } else{
                setEstampasRepetidas(nv[i]);
            }
        }
    }
    private void setEstampasCompradas(int[] ec, String s){
        this.estampasCompradas = ec;
    }
    public int[] getEstampasRepetidas() {
        return estampasRepetidas;
    }

    private void setEstampasRepetidas(int er) {
        this.estampasRepetidas[er-1] = this.estampasRepetidas[er-1] +1;
    }
    private void setEstampasRepetidas(int[] er, String s){
        this.estampasRepetidas = er;
    }


    public int[] getNuevasEstampas() {
        return nuevasEstampas;
    }

    private void setNuevasEstampas(int[] nuevasEstampas) {
        this.nuevasEstampas = nuevasEstampas;
    }
}

