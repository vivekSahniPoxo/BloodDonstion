package com.example.blooddonstion;

public class ReportDataModel {
    String SerialNumber,Temperature,DataTime,Id,Process,rfidnumber;

    public ReportDataModel() {
    }

    public ReportDataModel(String serialNumber, String temperature, String dataTime, String process, String rfidnumber) {
        SerialNumber = serialNumber;
        Temperature = temperature;
        DataTime = dataTime;
        Process = process;
        this.rfidnumber = rfidnumber;
    }

    public String getRfidnumber() {
        return rfidnumber;
    }

    public void setRfidnumber(String rfidnumber) {
        this.rfidnumber = rfidnumber;
    }

    public String getProcess() {
        return Process;
    }

    public void setProcess(String process) {
        Process = process;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getDataTime() {
        return DataTime;
    }

    public void setDataTime(String dataTime) {
        DataTime = dataTime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
