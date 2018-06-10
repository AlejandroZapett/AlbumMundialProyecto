package me.alejnadrozapett.albummundialproyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Tienda extends AppCompatActivity {
    ImageButton ibSobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);
        ibSobre=(ImageButton) findViewById(R.id.ibSobre);

        ibSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advertenciaDeCompra();
            }
        });
    }

    public void advertenciaDeCompra() {
        Toast.makeText(this, "Sobre comprado!", Toast.LENGTH_SHORT).show();
    }



}

