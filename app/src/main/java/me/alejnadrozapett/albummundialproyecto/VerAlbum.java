package me.alejnadrozapett.albummundialproyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class VerAlbum extends AppCompatActivity {

    private int EstampasPorFila = 25;
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
    private int[] estampasCompradas = new int[50];
    private ArrayList<VerAlbum.Jugadores> objetoJugadores = new ArrayList<VerAlbum.Jugadores>();
    private String nombreJugador;
    private int idJugador;
    private String jugador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_album);
        /*this.estampasCompradas[14] = 15;
        this.estampasCompradas[0] = 1;
        this.estampasCompradas[1] = 2;
        this.estampasCompradas[2] = 3;
        this.estampasCompradas[3] = 4;
        this.estampasCompradas[4] = 5;
        this.estampasCompradas[5] = 6;
        this.estampasCompradas[30] = 31;*/

        leerInfo();
        pegarEstampas();
    }


    public int[] getEstampasCompradas() {
        return estampasCompradas;
    }
    private void setEstampasCompradas(int[] estampasCompradas) {
        for(int i = 0; i<estampasCompradas.length; i++) {
            this.estampasCompradas[i] = estampasCompradas[i];
        }
    }

    private void leerInfo(){
        SharedPreferences persistencia = getSharedPreferences("persistencia", Context.MODE_PRIVATE);
        if (persistencia.contains("repetidas")){
            // la informacion va a estar guardada de la siguiente forma: 1,2,3,0,5,0,0,8
            if (persistencia.contains("compradas")){
                String compradas = persistencia.getString("compradas", "0");
                setEstampasCompradas(parseoInfo(compradas));
            }
        }
    }

    private int[] parseoInfo(String cadena) {
        String[] r = cadena.split(",");
        int[] regreso = new int[r.length];
        for (int i = 0; i < r.length; i++) {
            regreso[i] = Integer.parseInt(r[i]);
        }
        return regreso;
    }

    public class Jugadores {
        String nombre;
        int id;
        public Jugadores(String nombre, int id) {
            this.nombre = nombre;
            this.id = id;
        }
    }

    public FrameLayout estampaBloqueada(){
        TextView textView = new TextView(this);
        ImageView imageView = new ImageView(this);
        FrameLayout frame = new FrameLayout(getApplicationContext());
        String texto = getString(R.string.bloqueado);
        textView.setText(texto);
        textView.setTextColor(getResources().getColor(R.color.SplashScreen));
        imageView.setImageResource(R.drawable.bloqueado);
        frame.addView(imageView);
        frame.addView(textView);
        return frame;
    }


    private void pegarEstampas() {
        LinearLayout layout0 = (LinearLayout) findViewById(R.id.LinearLayout0);
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.LinearLayout1);
        String[] datos = listaJugadores.split(",");


        for(int i = 0; i<50; i++){
            String[] subdatos = datos[i].split(":");
            objetoJugadores.add(new Jugadores(subdatos[1], Integer.parseInt(subdatos[0])));
        }

        int [] ec = getEstampasCompradas();
        for(int i=0; i< ec.length; i++){
            if (ec[i] != 0){
                for (Jugadores js:objetoJugadores){
                    if(js.id == ec[i]) {
                        if(i<EstampasPorFila) {
                            TextView textView = new TextView(this);
                            ImageView imageView = new ImageView(this);
                            FrameLayout frame = new FrameLayout(getApplicationContext());
                            String texto = Integer.toString(js.id) + "\n" + js.nombre;
                            textView.setText(texto);
                            textView.setTextColor(getResources().getColor(R.color.SplashScreen));
                            jugador = js.nombre.replace(" ", "").toLowerCase().replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
                            jugador = jugador.replace("-", "").replace("ö", "o").replace("ü", "u");
                            imageView.setImageResource(getResources().getIdentifier("@drawable/" + jugador, null, getPackageName()));
                            frame.addView(imageView);
                            frame.addView(textView);
                            layout0.addView(frame);
                        }
                        else{
                            TextView textView = new TextView(this);
                            ImageView imageView = new ImageView(this);
                            FrameLayout frame = new FrameLayout(getApplicationContext());
                            String texto = Integer.toString(js.id) + "\n" + js.nombre;
                            textView.setText(texto);
                            textView.setTextColor(getResources().getColor(R.color.SplashScreen));
                            jugador = js.nombre.replace(" ", "").toLowerCase().replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
                            jugador = jugador.replace("-", "").replace("ö", "o").replace("ü", "u");
                            imageView.setImageResource(getResources().getIdentifier("@drawable/" + jugador, null, getPackageName()));
                            frame.addView(imageView);
                            frame.addView(textView);
                            layout1.addView(frame);
                        }
                    }
                }
            }
            else{
                if(i<EstampasPorFila) {
                    layout0.addView(estampaBloqueada());
                }
                else {
                    layout1.addView(estampaBloqueada());
                }
            }
        }
    }
}
