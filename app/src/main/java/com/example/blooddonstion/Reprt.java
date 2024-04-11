package com.example.blooddonstion;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class Reprt extends AppCompatActivity {
    List<ReportDataModel> list;
    Spinner spinner;
    String Paramter;
    ReportDb reportDb;
    CardView cardViewreport;
    Button buttonview, close;
    TextView serial,temper,date,progress,rfid;
    List<ReportDataModel> listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprt);
        Initcopmponet();
    }

    private void Initcopmponet() {

        List<String> stringList = new ArrayList<>();


        spinner = findViewById(R.id.spiiner);
        buttonview = findViewById(R.id.btnview);
        close = findViewById(R.id.ViewAllclose);
        serial=findViewById(R.id.Booktitle);
        temper=findViewById(R.id.SolarCell);
        date=findViewById(R.id.MonthPV);
        progress=findViewById(R.id.MonthSolar);
        rfid=findViewById(R.id.IECcertificate);
        close=findViewById(R.id.ViewAllclose);
        cardViewreport = findViewById(R.id.cardView3);
        reportDb = new ReportDb(getApplicationContext());
        list = new ArrayList<>();
        list = reportDb.getAllContacts();
        for (int i = 0; i < list.size(); i++) {
            stringList.add(list.get(i).getSerialNumber());
        }
        spinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, stringList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Paramter=String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    buttonview.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SetupData();
            cardViewreport.setVisibility(View.VISIBLE);

        }
    });


    close.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            cardViewreport.setVisibility(View.GONE);

        }
    });
    }

    private void SetupData() {
        reportDb = new ReportDb(getApplicationContext());
        listdata=new ArrayList<>();
        listdata=reportDb.getAllDetails(Paramter);
        for (int i = 0; i < listdata.size(); i++) {
            temper.setText(listdata.get(i).getDataTime());
            date.setText(listdata.get(i).getProcess());
            serial.setText(listdata.get(i).getSerialNumber());
            progress.setText(listdata.get(i).getTemperature());
            rfid.setText(listdata.get(i).getRfidnumber());

        }
    }
}