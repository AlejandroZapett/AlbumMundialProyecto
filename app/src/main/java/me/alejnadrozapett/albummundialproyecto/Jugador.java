package me.alejnadrozapett.albummundialproyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class Jugador extends AppCompatActivity {

    private int numJugador;
    private String ventanaAnterior;
    String datos; //1:nombre:peso:altura:club,
    ArrayList<DatosJugadores> listaDatos = new ArrayList<DatosJugadores>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);
        iniciarDatos();
    }

    private void iniciarDatos(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            setVentanaAnterior(bundle.getString("ventana"));
            setNumJugador(Integer.parseInt(bundle.getString("jugador")));
        }
        //String[] subdatos = datos.split(",");
        //for(int i=0; i<50; i++){
        //    String[] datosJugador = subdatos[i].split(":");
        //    listaDatos.add(new Jugador(Integer.parseInt(datosJugador[0]), datosJugador[1], Integer.parseInt(datosJugador[2]), Integer.parseInt(datosJugador[3]), datosJugador[4]));
        //}
    }

    private void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }

    private void setVentanaAnterior(String ventanaAnterior) {
        this.ventanaAnterior = ventanaAnterior;
    }

    public int getNumJugador() {
        return numJugador;
    }

    public String getVentanaAnterior() {
        return ventanaAnterior;
    }

    public String getDatos() {
        return datos;
    }

    public class DatosJugadores {
        int id;
        String nombre;
        int altura;
        int peso;
        String club;

        public DatosJugadores(int id, String nombre, int altura, int peso, String club) {
            this.id = id;
            this.nombre = nombre;
            this.altura = altura;
            this.peso = peso;
            this.club = club;
        }
        public DatosJugadores(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }
    }
}
