package com.example.blooddonstion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;
import com.speedata.libuhf.bean.SpdReadData;
import com.speedata.libuhf.interfaces.OnSpdReadListener;
import com.speedata.libuhf.utils.ErrorStatus;
import com.speedata.libuhf.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class progress3 extends AppCompatActivity {
    ReportDb reportDb;
    CardView ScanRfid,infocard;
    IUHFService iuhfService;
    String result, epc;
    List<ReportDataModel> list;
    TextView serial, temper, date, progress, rfid;
    Button close;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress3);
        ScanRfid = findViewById(R.id.button_Scan);
        //iuhfService = UHFManager.getUHFService(this);
        serial = findViewById(R.id.Booktitle);
        temper = findViewById(R.id.SolarCell);
        date = findViewById(R.id.MonthPV);
        progress = findViewById(R.id.MonthSolar);
        rfid = findViewById(R.id.IECcertificate);
        close = findViewById(R.id.ViewAllclose);
        infocard=findViewById(R.id.cardView4);
        reportDb = new ReportDb(this);
//        iuhfService.setOnInventoryListener(var1 -> {
////                    tempList.add(var1.getEpc());
////                    System.out.println("List Data" + tempList);
//            epc = var1.getEpc();
////
//        });
        ScanRfid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scan(result);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infocard.setVisibility(View.GONE);
            }
        });
    }

    private void Scan(String epc) {
        result = epc;
//        iuhfService.openDev();
//        result = iuhfService.read_area(1, "2", "6", "00000000");
        list = new ArrayList<>();
        if (result != null) {

            list = reportDb.getAllDetails1(result);
            if (list.size()>0)
            {
                infocard.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(this, "No Data Available for this RFID...", Toast.LENGTH_SHORT).show();
            }
//            iuhfService.inventoryStop();
//            iuhfService.closeDev();
            for (int i = 0; i < list.size(); i++) {
                temper.setText(list.get(i).getDataTime());
                date.setText(list.get(i).getProcess());
                serial.setText(list.get(i).getSerialNumber());
                progress.setText(list.get(i).getTemperature());
                rfid.setText(list.get(i).getRfidnumber());

            }

        }
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case    KeyEvent.KEYCODE_BUTTON_R2:
            case KeyEvent.KEYCODE_F1://KeyEvent { action=ACTION_UP, keyCode=KEYCODE_F1, scanCode=59, metaState=0, flags=0x8, repeatCount=0, eventTime=13517236, downTime=13516959, deviceId=1, source=0x101 }
//                Scan();
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
                            Toast.makeText(progress3.this, readHexString, Toast.LENGTH_SHORT).show();


                            result=readHexString;
                            epc = readHexString;
                            Scan(result);
//                    resultValue.setVisibility(View.VISIBLE);
//                    resultValue.setText(resultV);

                        } else {
                            stringBuilder.append(getResources().getString(R.string.read_fail))
                                    .append(":").append(ErrorStatus.getErrorStatus(progress3.this, var1.getStatus()))
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

                return true;
            default:
                return super.onKeyUp(keyCode, event);
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
}