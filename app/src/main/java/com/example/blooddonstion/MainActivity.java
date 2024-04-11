package com.example.blooddonstion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.mainGrid);
        setSingleEvent(gridLayout);

    }
    private void setSingleEvent(GridLayout gridLayout) {

        CardView Report = (CardView) gridLayout.findViewById(R.id.assetStock);
        CardView Procees1 = gridLayout.findViewById(R.id.Searchform);
        CardView Procees2 = gridLayout.findViewById(R.id.InventoryForm);
        CardView Procees3 = gridLayout.findViewById(R.id.RegisterAsset);

        CardView Procees4 = gridLayout.findViewById(R.id.Auditform);


        Procees4.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, progrss4.class)));
        Procees2.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Reprt.class)));
        Procees1.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Progress1.class)));
        Report.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Reprt.class)));
        Procees3.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, progress3.class)));

    }

}