package com.example.greenwoodapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greenwoodapp.Calendar.CalendarActivity;
import com.example.greenwoodapp.Call.CallActivity;

@RequiresApi(api = Build.VERSION_CODES.O)
public class main_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void changePage(Class activity){
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
    }

    public void moveToCalendar(View view){
        changePage(CalendarActivity.class);
    }

    public void moveToCall(View view){
        changePage(CallActivity.class);
    }
}
