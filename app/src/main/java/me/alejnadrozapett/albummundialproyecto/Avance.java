package me.alejnadrozapett.albummundialproyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Avance extends AppCompatActivity {

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
    private final int numEstampas = 50;
    private int[] estampasCompradas = new int[50];
    private ArrayList<Text> listaText = new ArrayList<Text>();

    Button atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avance);
        leerInfo();
        colocarCuadroEstampas();
        colocarListaEstampas();
        atras = (Button) findViewById(R.id.atras);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresarMenu();
            }
        });
    }

    public int getNumEstampas() {
        return numEstampas;
    }

    public int[] getEstampasCompradas() {
        return estampasCompradas;
    }

    private void setEstampasCompradas(int[] estampasCompradas) {
        for(int i = 0; i<estampasCompradas.length; i++)
            this.estampasCompradas[i] = estampasCompradas[i];
    }

    private void leerInfo(){
        SharedPreferences persistencia = getSharedPreferences("persistencia", Context.MODE_PRIVATE);
        if (persistencia.contains("repetidas")){
            // la informacion va a estar guardada de la siguiente forma: 1,2,3
            if (persistencia.contains("compradas")){
                String compradas = persistencia.getString("compradas", "0");
                setEstampasCompradas(parseoInfo(compradas));
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
    private void regresarMenu(){
        //regresa al main activity
        Intent intent = new Intent(Avance.this, MainActivity.class);
        startActivity(intent);
        //Toast.makeText(this, "regresa al menu", Toast.LENGTH_SHORT).show();
    }

    private void verJugador(String ventana, int jugador){
        // va al activity de ver jugador con la información de:
        // 1. que activity la invocó
        // 2. que id de jugador se quiere ver
        //Intent intent = new Intent(Avance.this, Jugador.class);
        //intent.putExtra("ventana", ventana);
        //intent.putExtra("jugador", jugador);
        //startActivity(intent);
        Toast.makeText(this, "ver jugador", Toast.LENGTH_SHORT).show();
    }
    private void colocarCuadroEstampas(){
        Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show();
        LinearLayout layoutH1 = (LinearLayout) findViewById(R.id.LOH1);
        LinearLayout layoutH2 = (LinearLayout) findViewById(R.id.LOH2);
        LinearLayout layoutH3 = (LinearLayout) findViewById(R.id.LOH3);
        LinearLayout layoutH4 = (LinearLayout) findViewById(R.id.LOH4);
        LinearLayout layoutH5 = (LinearLayout) findViewById(R.id.LOH5);
        LinearLayout[] listaLayout = {layoutH1, layoutH2, layoutH3, layoutH4, layoutH5};
        for(int i=0; i< getNumEstampas(); i++){
            listaText.add(new Text(Integer.toString(i+1), i));
        }

        for (Text t:listaText){
            //editar propiedades del frame y el text
            //frame de acuerdo a las compradas
            TextView text = new TextView(getApplicationContext());
            text.setText(t.text);
            text.setTextColor(Color.BLACK);
            text.setId(t.id);
            FrameLayout frame = new FrameLayout(getApplicationContext());
            frame.setId(t.id+50);
            frame.addView(text);
            int valor = (int)(t.id/10);
            switch (valor){
                case 0:
                    listaLayout[0].addView(frame);
                    break;
                case 1:
                    listaLayout[1].addView(frame);
                    break;
                case 2:
                    listaLayout[2].addView(frame);
                    break;
                case 3:
                    listaLayout[3].addView(frame);
                    break;
                case 4:
                    listaLayout[4].addView(frame);
                    break;
            }
        }
    }

    private void colocarListaEstampas(){
        String[] datos = listaJugadores.split(",");
        LinearLayout layputDer = (LinearLayout) findViewById(R.id.LayoutVerticalDer);
        for(int i = 0; i<50; i++){
            String[] subdatos = datos[i].split(":");
            objetoJugadores.add(new Jugadores(subdatos[1], Integer.parseInt(subdatos[0])));
        }
        int [] ec = getEstampasCompradas();
        for(int i=0; i< ec.length; i++){
            // las repetidas son los id de los jugadores
            if (ec[i] != 0){
                //crear boton y colocarlo en contenedor
                Button btnJ = new Button(getApplicationContext());
                for (Jugadores j:objetoJugadores){
                    if(j.id == ec[i]){
                        String textoBoton = Integer.toString(j.id)+" "+j.nombre;
                        btnJ.setText(textoBoton);
                        layputDer.addView(btnJ);
                        break;
                    }
                }

            }
        }
    }

    public class Text {
        String text;
        int id;

        public Text(String text, int id) {
            this.text = text;
            this.id = id;
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

}

