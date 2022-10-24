package com.template;

public class URL extends LoadingActivity{

    String timeZone;
    String usserID;
    String packageNAme;
    String adress;

    public URL(String adress,String packageNAme, String usserID,String timeZone) {

        this.adress=adress;
        this.usserID = usserID;
        this.packageNAme=packageNAme;
        this.timeZone=timeZone;


    }


    @Override
    public String toString() {
        return adress+"/?packageid="+packageNAme+"&userid="+usserID+"&getz="+timeZone+"&getr=utm_source=google-play&utm_medium=organic";
    }
}
