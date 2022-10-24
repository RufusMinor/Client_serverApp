package com.template;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.util.TimeZone;

public class LoadingActivity extends AppCompatActivity {

    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;
    public EditText editText;
    public String URL;
    public String adress;
    public static String PACKAGE_NAME;
    private String usserid;
    private String timeZone;
    public String ssylka;
    public static final String APP_PREFERENCES_URL = "URL";
    public static final String APP_PREFERENCES_URL2 = "URL2";
    public static final String APP_PREFERENCES = "mysettings";
    public String init,init2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mDatabase= FirebaseDatabase.getInstance();
        mReference=mDatabase.getReference("db");
        editText=(EditText)findViewById(R.id.textView);
        PACKAGE_NAME=getApplicationContext().getPackageName();
        usserid=Uuid.result();
        TimeZone tz = TimeZone.getDefault();
        timeZone=tz.getID();

        SharedPreferences mURL2;
        mURL2 = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.child("link").getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                adress=String.valueOf(value);
                URL url1=new URL(adress,PACKAGE_NAME,usserid,timeZone );
                ssylka=String.valueOf(url1);
                if(value==null){
                    Intent intent1=new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent1);
                }
                else{
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            // получаем ответ
                            HttpURLConnection connection=null;
                            try{
                                connection=(HttpURLConnection) new java.net.URL(ssylka).openConnection();
                                connection.setRequestMethod("GET");
                                connection.setUseCaches(false);
                                connection.setConnectTimeout(2500);
                                connection.setReadTimeout(2500);

                                connection.connect();

                                StringBuilder ab=new StringBuilder();

                                if(HttpURLConnection.HTTP_FORBIDDEN==connection.getResponseCode()){
                                    BufferedReader in=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                    String line;
                                    while ((line=in.readLine())!=null){
                                        ab.append(line);
                                        ab.append("\n");
                                    }
                                    Log.d("log1","Вот оно работает вроде: "+ab);
                                    String i= String.valueOf(ab);
                                    Intent intent1=new Intent(LoadingActivity.this, MainActivity.class);
                                    startActivity(intent1);
                                    init2="403";

                                }
                                else {
                                    Log.d("log2","Все пропало");

                                    init="200";

                                    Intent intent2=new Intent(LoadingActivity.this, WebActivity.class);
                                    intent2.putExtra("String",ssylka);
                                    startActivity(intent2);
                                }

                            }
                            catch (Throwable cause){
                                cause.printStackTrace();
                            }
                            finally {
                                if(connection!=null){
                                    connection.disconnect();
                                }
                            }
                            // отображаем в текстовом поле
//                            text.post(new Runnable() {
//                                public void run() {
//                                    text.setText(i);
//                                }
//                            });
                        }
                    };
                    // Определяем объект Thread - новый поток
                    Thread thread = new Thread(runnable);
                    // Запускаем поток
                    thread.start();

//                    SharedPreferences mURL;
//                    mURL = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
//                    String strURL = ssylka.toString();
//                    SharedPreferences.Editor editor = mURL.edit();
//                    editor.putString(APP_PREFERENCES_URL, strURL);
//                    editor.apply();





                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


//        if(mURL.contains(APP_PREFERENCES_URL)) {
//            //strURL.setText(m.getString(APP_PREFERENCES_NAME, ""));
//            Log.d("Log3","Сохранено 200");
//
//        }
////        if  (mURL2.contains(APP_PREFERENCES_URL2)){
////            Log.d("Log3","Сохранено 403");
////        }
//        else {
//            Log.d("Log3","Не фартануло");
//        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences mURL;
        mURL = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String strURL = init.toString();
        SharedPreferences.Editor editor = mURL.edit();
        editor.putString(APP_PREFERENCES_URL2, strURL);
        editor.apply();

//        SharedPreferences mURL2;
//        mURL2 = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
//        String strURL2 = init2.toString();
//        SharedPreferences.Editor editor2 = mURL2.edit();
//        editor.putString(APP_PREFERENCES_URL2, strURL2);
//        editor2.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mURL;
        mURL = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(mURL.contains(APP_PREFERENCES_URL)) {
            //strURL.setText(m.getString(APP_PREFERENCES_NAME, ""));
            Log.d("Log3","Сохранено 200");

        }
//        if  (mURL2.contains(APP_PREFERENCES_URL2)){
//            Log.d("Log3","Сохранено 403");
//        }
        else {
            Log.d("Log3","Провал");
        }
    }
}
