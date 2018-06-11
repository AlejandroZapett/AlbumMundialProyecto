package me.alejnadrozapett.albummundialproyecto;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Repetidas extends AppCompatActivity {

    SharedPreferences persistencia;
    //ImageButton estampa;
    TextView anterior;
    TextView siguiente;
    private final String listaJugadores = "1:Nawaf Al Abed,2:Mohamed Salah,3:Emil Forsberg,4:Aleksandr Kokorin," +
            "5:Victor Moses,6:Philippe Coutinho,7:Eden Hazard,8:Olivier Giroud,9:James Rodríguez," +
            "10:Sadio Mané,11:Hirving Lozano,12:Cristiano Ronaldo,13:Andrés Iniesta,14:Branislav Ivanovic," +
            "15:Gylfi Sigurdsson,16:Lionel Messi,17:Paolo Guerrero,18:Marcelo Vieira,19:Shinji Kagawa," +
            "20:Radamel Falcao,21:Mesut Özil,22:Moussa Konaté,23:Luca Modric,24:Hamza Mendyl," +
            "25:Romelu Lukaku,26:Ferjani Sassi,27:Mehdi Benatia,28:Francisco Alarcón,29:Toni Kroos," +
            "30:Christian Eriksen,31:Luis Suárez,32:Xherdan Shaqiri,33:Kylian Mbappé,34:Thomas Müller," +
            "35:Mile Jedinak,36:Alex Iwobi,37:Carlos Vela,38:Manuel Neuer,39:Javier Hernández," +
            "40:Sergio Agüero,41:Andrés Guardado,42:Harry Kane,43:Son Heung-min,44:Paulo Dybala," +
            "45:Antoine Griezmann,46:Neymar Jr.,47:Keylor Navas,48:Sergio Ramos,49:Robert Lewandowski," +
            "50:Edinson Cavani";
    private ArrayList<Jugadores> objetoJugadores = new ArrayList<Jugadores>();
    private ArrayList<JButton> botonesJugadores = new ArrayList<JButton>();
    private int[] estampasRepetidas = new int[50];
    private int idEstampaRepetida=0;
    private int idIntercambio;
    private int numEstampasRepetidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repetidas);
        persistencia = getSharedPreferences("persistencia", Context.MODE_PRIVATE);
        anterior = (TextView) findViewById(R.id.tbi);
        siguiente = (TextView) findViewById(R.id.tbd);
        leerInfo();
        crearBotonesEstampasRepetidas();
        colorcarEstampa();

        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] ler = new int[numEstampasRepetidas];
                idEstampaRepetida = obtenerAnterior(ler);
                Toast.makeText(Repetidas.this, Integer.toString(idEstampaRepetida), Toast.LENGTH_SHORT).show();
                colorcarEstampa();
            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] ler = new int[numEstampasRepetidas];
                idEstampaRepetida = obtenerSiguiente(ler);
                Toast.makeText(Repetidas.this, Integer.toString(idEstampaRepetida), Toast.LENGTH_SHORT).show();
                colorcarEstampa();
            }
        });

    }

    private int obtenerAnterior (int[] ler){
        int num = idEstampaRepetida;
        for (int i = 0; i < idEstampaRepetida-1; i++){
            if(estampasRepetidas[i] != 0){
                num = i+1;
            }
        }
        return num;
    }

    private int obtenerSiguiente (int[] ler){
        int num = idEstampaRepetida;
        for (int i = idEstampaRepetida; i<estampasRepetidas.length;i++){
            if(estampasRepetidas[i] != 0){
                num = i+1;
                return num;
            }
        }
        return num;
    }

    private void leerInfo(){

        if (persistencia.contains("repetidas")){
            // la informacion va a estar guardada de la siguiente forma: 1,2,3,0,5,0,0,8
            String repetidas = persistencia.getString("repetidas", "0");
            Toast.makeText(this, repetidas, Toast.LENGTH_SHORT).show();
            estampasRepetidas = parseoInfo(repetidas);
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

    private void crearBotonesEstampasRepetidas(){
        String[] datos = listaJugadores.split(",");
        for(int i = 0; i<50; i++){
            String[] subdatos = datos[i].split(":");
            objetoJugadores.add(new Jugadores(subdatos[1], Integer.parseInt(subdatos[0])));
        }
        int contador = 0;
        for (Jugadores j:objetoJugadores){
            if(estampasRepetidas[j.id-1] != 0){
                contador = contador +1;
                ImageButton estampa = new ImageButton(getApplicationContext());
                estampa.setId(j.id+300);
                String jugador = j.nombre.replace(" ", "").toLowerCase().replace("á", "a").replace("é","e").replace("í","i").replace("ó","o").replace("ú", "u");
                jugador = jugador.replace("-","").replace("ö","o").replace("ü", "u").replace(".","");
                estampa.setImageResource(getResources().getIdentifier("@drawable/"+jugador, null, getPackageName()));
                botonesJugadores.add(new JButton(j.id, j.nombre, estampa));
            }
        }
        numEstampasRepetidas = contador;
        Toast.makeText(this, Integer.toString(numEstampasRepetidas), Toast.LENGTH_SHORT).show();
        for (int i = 0; i< estampasRepetidas.length; i++){
            if ( estampasRepetidas[i] != 0){
                idEstampaRepetida = i+1;
            }
        }
    }

    private void colorcarEstampa(){
        FrameLayout frame = (FrameLayout) findViewById(R.id.frameRepetidas);
        for (JButton b:botonesJugadores){
            if (b.id == idEstampaRepetida){
                frame.addView(b.img);
                b.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setIdIntercambio(v.getId());
                        //pop over de intercambiar
                    }
                });
            }
        }
    }

    public class Jugadores {
        String nombre;
        int id;

        public Jugadores(String nombre, int id) {
            this.nombre = nombre;
            this.id = id;
        }
    }

    public class JButton {
        int id;
        String nombre;
        ImageButton img;

        public JButton(int id, String nombre, ImageButton img) {
            this.id = id;
            this.nombre = nombre;
            this.img = img;
        }
    }

    public void setIdIntercambio(int idIntercambio) {
        this.idIntercambio = idIntercambio-300;
    }

}
