package com.example.blooddonstion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;
import com.speedata.libuhf.bean.SpdReadData;
import com.speedata.libuhf.interfaces.OnSpdReadListener;
import com.speedata.libuhf.utils.ErrorStatus;
import com.speedata.libuhf.utils.StringUtils;

import java.util.Date;

public class Progress1 extends AppCompatActivity {
    Spinner spinner;
    String Spinnervalue, result,epc;
    private String[] country = {"Process 1", "Process 2", "Process 3", "Process 4"};
    EditText serialtxt, temptxt;
    Button SaVEBTN;
    ReportDb reportDb;
    CardView ScanRfid;
    IUHFService iuhfService;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress1);
        serialtxt = findViewById(R.id.serialNumber);
        temptxt = findViewById(R.id.Temperature);
        SaVEBTN = findViewById(R.id.buttonSave);
        handler = new Handler();
        //Spinner Implementation
       // iuhfService = UHFManager.getUHFService(this);
//        iuhfService.setOnInventoryListener(var1 -> {
////                    tempList.add(var1.getEpc());
////                    System.out.println("List Data" + tempList);
//           epc = var1.getEpc();
////
//        });


        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, country));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinnervalue = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ScanRfid=findViewById(R.id.button_Scan);
        ScanRfid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scan();
            }
        });

        SaVEBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (serialtxt.length() == 0) {
                    serialtxt.setError("Please Enter ");

                }
                if (temptxt.length() == 0) {
                    temptxt.setError("Please Enter ");
                } else {
                    Date d = new Date();
                    CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                    reportDb = new ReportDb(getApplication());
                    reportDb.addContact(new ReportDataModel(serialtxt.getText().toString(), temptxt.getText().toString() + " Celsius", Spinnervalue, s.toString(),result));
                    Toast.makeText(Progress1.this, "Saved...", Toast.LENGTH_SHORT).show();
                    serialtxt.setText("");
                    temptxt.setText("");
                }

            }
        });

    }

    private void Scan() {
//        iuhfService.openDev();
//        result = iuhfService.read_area(1, "2", "6", "00000000");
//
//        if (result != null) {
//            Toast.makeText(this, "Reading Tag Data", Toast.LENGTH_SHORT).show();
//            iuhfService.inventoryStop();
//            iuhfService.closeDev();
//
//        }



        iuhfService.setOnReadListener(new OnSpdReadListener() {
            @Override
            public void getReadData(SpdReadData var1) {
                StringBuilder stringBuilder = new StringBuilder();
                byte[] epcData = var1.getEPCData();
                String hexString = StringUtils.byteToHexString(epcData, var1.getEPCLen());
                if (!TextUtils.isEmpty(hexString)) {
                    stringBuilder.append("EPC：").append(hexString).append("\n");
                } else if (var1.getStatus() == 0) {
                    byte[] readData = var1.getReadData();
                    String readHexString = StringUtils.byteToHexString(readData, var1.getDataLen());
                    stringBuilder.append("ReadData:").append(readHexString).append("\n");
                    Toast.makeText(Progress1.this, readHexString, Toast.LENGTH_SHORT).show();


                    result=readHexString;
                    epc = readHexString;
//                    resultValue.setVisibility(View.VISIBLE);
//                    resultValue.setText(resultV);

                } else {
                    stringBuilder.append(getResources().getString(R.string.read_fail))
                            .append(":").append(ErrorStatus.getErrorStatus(Progress1.this, var1.getStatus()))
                            .append("\n");
                    handler.sendMessage(handler.obtainMessage(1, stringBuilder.toString()));
                }
            }
        });

        Integer readArea = iuhfService.readArea(1, 2, 6, "00000000");
        if (readArea != null && readArea != 0) {
            String err = getResources().getString(R.string.read_fail) + ":" +
                    ErrorStatus.getErrorStatus(getApplicationContext(), readArea) + "\n";
            handler.sendMessage(handler.obtainMessage(1, err));
        }




    }


    @Override
    protected void onStop() {
        super.onStop();
        stopInventoryService();
    }

    private void stopInventoryService() {
        iuhfService.closeDev();

    }


    @Override
    protected void onStart() {
        super.onStart();
        initializeUHF();
    }

    @SuppressLint("ResourceAsColor")
    private void initializeUHF() {
        try {
            iuhfService = UHFManager.getUHFService(this);
            iuhfService.openDev();
            iuhfService.setAntennaPower(30);

            // iuhfService.setFrequency(2);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case  KeyEvent.KEYCODE_BUTTON_R2:
            case KeyEvent.KEYCODE_F1://KeyEvent { action=ACTION_UP, keyCode=KEYCODE_F1, scanCode=59, metaState=0, flags=0x8, repeatCount=0, eventTime=13517236, downTime=13516959, deviceId=1, source=0x101 }
                Scan();
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }
}