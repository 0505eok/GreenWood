package com.example.greenwoodapp.Calendar;

import static com.example.greenwoodapp.Calendar.CalendarUtils.daysInMonthArray;
import static com.example.greenwoodapp.Calendar.CalendarUtils.monthYearFromDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenwoodapp.R;

import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener, View.OnClickListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    public CalendarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_main, container, false);
        calendarRecyclerView = v.findViewById(R.id.calendarRecyclerView);
        monthYearText = v.findViewById(R.id.monthYearTV);
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        Button backBtn = v.findViewById(R.id.backBtn);
        Button nextBtn = v.findViewById(R.id.nextBtn);
        Button weekBtn = v.findViewById(R.id.weekBtn);
        backBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        weekBtn.setOnClickListener(this);
        return v;
    }

    private void setMonthView(){
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    public void weeklyAction(View view) {
        Intent intent = new Intent(getActivity(), WeekViewActivity.class);
        startActivity(intent);
    }



    @Override
    public void onItemClick(int position, LocalDate date) {
        if(date != null){
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backBtn:
                previousMonthAction(v);
                break;
            case R.id.nextBtn:
                nextMonthAction(v);
                break;
            case R.id.weekBtn:
                weeklyAction(v);
                break;
        }
    }
}
