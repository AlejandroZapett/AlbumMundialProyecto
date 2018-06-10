package me.alejnadrozapett.albummundialproyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int estampasRepetidas [] = new int[50];
    private int estampasCompradas [] = new int[50];
    private int sobresComprados;
    private final int sobresTotal = 50;

    ImageButton ibAvance;
    ImageButton ibRepetidas;
    ImageButton ibAlbum;
    ImageButton ibTienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //leerInfo();
        SharedPreferences persistencia = getSharedPreferences("persistencia", Context.MODE_PRIVATE);
        persistencia.edit().clear().apply();
        ibAvance = (ImageButton) findViewById(R.id.ibAvance);
        ibRepetidas = (ImageButton) findViewById(R.id.ibRepetidas);
        ibAlbum = (ImageButton) findViewById(R.id.ibAlbum);
        ibTienda = (ImageButton) findViewById(R.id.ibTienda);

        ibAvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verAvance();
            }
        });
        ibRepetidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repetidas();
            }
        });
        ibAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verAlbum();
            }
        });
        ibTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprarSobre();
            }
        });
    }

    private void leerInfo(){
        SharedPreferences persistencia = getSharedPreferences("persistencia", Context.MODE_PRIVATE);
            // la informacion va a estar guardada de la siguiente forma: 1,2,3,4
        if (persistencia.contains("repetidas")) {
            String repetidas = persistencia.getString("repetidas", "0");
            Toast.makeText(this, repetidas,Toast.LENGTH_LONG ).show();
            setEstampasRepetidas(parseoInfo(repetidas));
        } else {
            for (int i = 0; i < estampasRepetidas.length; i++) {
                estampasRepetidas[i] = 0;
            }
        }
        if (persistencia.contains("compradas")){
            String compradas = persistencia.getString("compradas", "0");
            Toast.makeText(this, compradas,Toast.LENGTH_LONG ).show();
            setEstampasCompradas(parseoInfo(compradas));
        }  else {
            for (int i = 0; i < getEstampasCompradas().length; i++) {
                estampasCompradas[i] = 0;
            }
        }
    }

    public int[] parseoInfo(String cadena){
        String[] r = cadena.split(",");
        int[] regreso = new int[r.length];
        for(int i=0; i<r.length ; i++){
            regreso[i] = Integer.parseInt(r[i]);
        }
        return regreso;
    }

    public int[] getEstampasRepetidas() {
        return estampasRepetidas;
    }

    protected void setEstampasRepetidas(int[] estampasRepetidas) {
        this.estampasRepetidas = estampasRepetidas;
    }

    public int[] getEstampasCompradas() {
        return estampasCompradas;
    }

    protected void setEstampasCompradas(int[] estampasCompradas) {
        this.estampasCompradas = estampasCompradas;
    }

    public int getSobresComprados() {
        return sobresComprados;
    }

    protected void setSobresComprados(int sobresComprados) {
        this.sobresComprados = sobresComprados;
    }

    public void comprarSobre(){
        //cambiar a pagina de compras
        Intent tienda= new Intent(this, Tienda.class);
        startActivity(tienda);
    }
    public void verAlbum(){
        //cambiar a página de album
        Intent intent = new Intent(MainActivity.this, VerAlbum.class);
        startActivity(intent);
        //Toast.makeText(this, "ver album", Toast.LENGTH_SHORT).show();
    }
    public void verAvance(){
        //cambiar a página de Avance
        Intent intent = new Intent(MainActivity.this, Avance.class);
        startActivity(intent);
        //Toast.makeText(this, "avance", Toast.LENGTH_SHORT).show();
    }
    public void repetidas(){
        //cambiar a página de repetidas
        Toast.makeText(this, "repetidas", Toast.LENGTH_SHORT).show();
    }

}
