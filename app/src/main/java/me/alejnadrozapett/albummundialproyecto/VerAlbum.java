package me.alejnadrozapett.albummundialproyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class VerAlbum extends AppCompatActivity {

    private int EstampasPorFila = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_album);
        pegarEstampas();
    }

    private void pegarEstampas() {
        LinearLayout layout0 = (LinearLayout) findViewById(R.id.LinearLayout0);
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.LinearLayout1);


        for (int i = 0; i < EstampasPorFila; i++) {
            //codicional que ve si existe la estampa superior, sino ppner imagen genérica sin cuadro de texto
            ImageView imageViewArriba = new ImageView(this);
            imageViewArriba.setImageResource(R.drawable.logo2018);
            TextView textViewtArriba = new TextView(this);
            textViewtArriba.setText("Jugador\n"+(i+1)+"/"+EstampasPorFila);
            textViewtArriba.setTextColor(getResources().getColor(R.color.SplashScreen));
            //poner index a imagenes
            //para poner 1-25 arriba y 25-50 abajo
            FrameLayout frameArriba = new FrameLayout(getApplicationContext());
            frameArriba.addView(imageViewArriba);
            frameArriba.addView(textViewtArriba);
            layout0.addView(frameArriba);


            ImageView imageViewAbajo = new ImageView(this);
            imageViewAbajo.setImageResource(R.drawable.logo2018);
            TextView textViewtAbajo = new TextView(this);
            textViewtAbajo.setText("Jugador\n"+(i+26)+"/"+EstampasPorFila);
            textViewtAbajo.setTextColor(getResources().getColor(R.color.SplashScreen));
            FrameLayout frameAbajo = new FrameLayout(getApplicationContext());
            frameAbajo.addView(imageViewAbajo);
            frameAbajo.addView(textViewtAbajo);
            layout1.addView(frameAbajo);
        }

    }
}
