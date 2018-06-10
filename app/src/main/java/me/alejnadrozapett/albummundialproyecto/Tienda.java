package me.alejnadrozapett.albummundialproyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

    private RequestQueue request;
    private StringRequest stringRequest;
    private final int sobresTotal = 10;
    private int sobresComprados;
    private int[] estampasCompradas = new int[50];
    private int[] estampasRepetidas = new int[50];
    private int[] nuevasEstampas = new int[5];
    private String TAG = Tienda.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        ibSobre=(ImageButton) findViewById(R.id.ibSobre);

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
                //parseRequest(response.toString());
                setNuevasEstampas(parseRequest(response.toString()));
                setEstampasCompradas(getNuevasEstampas());
                setSobresComprados();
                //setPersistencia();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Tienda.this, "No se pudo completar la compra", Toast.LENGTH_LONG).show();
            }
        });

        request.add(stringRequest);
    }

    private void leerInfo(){

    }
    private void setPersistencia() {
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
    }

    public int[] getEstampasCompradas() {
        return estampasCompradas;
    }

    public void setEstampasCompradas(int[] nv) {
        for(int i=0; i<nv.length; i++){
            if(estampasCompradas[nv[i]-1] != nv[i]){
                estampasCompradas[nv[i]-1] = nv[i];
            } else{
                //setRepetidas
                setEstampasRepetidas(nv[i]);
            }
        }
    }

    public int[] getEstampasRepetidas() {
        return estampasRepetidas;
    }

    public void setEstampasRepetidas(int er) {
        estampasRepetidas[er-1] = estampasRepetidas[er-1] +1;
    }

    public int[] getNuevasEstampas() {
        return nuevasEstampas;
    }

    public void setNuevasEstampas(int[] nuevasEstampas) {
        this.nuevasEstampas = nuevasEstampas;
    }
}

