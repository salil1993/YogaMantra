package com.deftminds.coronavirusapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.deftminds.coronavirusapp.R;
import com.deftminds.coronavirusapp.activity.MainActivity;
import com.deftminds.coronavirusapp.activity.Yogasana;

public class TrainingFragment extends Fragment implements View.OnClickListener {
    CardView day1,day2,day3,day4,day5,day6,day7,day8,day9,day10,day11,day12,day13,day14,day15,
            day16,day17,day18,day19,day20,
    day21,day22,day23,day24,day25,day26,day27,day28,day29,day30;
    Intent i;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        MainActivity.title.setText("Training");
        View root = inflater.inflate(R.layout.fragment_training, container, false);
        day1=(CardView)root.findViewById(R.id.day1);
        day2=(CardView)root.findViewById(R.id.day2);
        day3=(CardView)root.findViewById(R.id.day3);
        day5=(CardView)root.findViewById(R.id.day5);
        day6=(CardView)root.findViewById(R.id.day6);
        day7=(CardView)root.findViewById(R.id.day7);
        day9=(CardView)root.findViewById(R.id.day9);
        day10=(CardView)root.findViewById(R.id.day10);
        day11=(CardView)root.findViewById(R.id.day11);
        day13=(CardView)root.findViewById(R.id.day13);
        day14=(CardView)root.findViewById(R.id.day14);
        day15=(CardView)root.findViewById(R.id.day15);
        day17=(CardView)root.findViewById(R.id.day17);
        day18=(CardView)root.findViewById(R.id.day18);
        day19=(CardView)root.findViewById(R.id.day19);
        day21=(CardView)root.findViewById(R.id.day21);
        day22=(CardView)root.findViewById(R.id.day22);
        day23=(CardView)root.findViewById(R.id.day23);
        day25=(CardView)root.findViewById(R.id.day25);
        day26=(CardView)root.findViewById(R.id.day26);
        day27=(CardView)root.findViewById(R.id.day27);
        day29=(CardView)root.findViewById(R.id.day29);
        day30=(CardView)root.findViewById(R.id.day30);
        day1.setOnClickListener(this);
        day2.setOnClickListener(this);
        day3.setOnClickListener(this);
        day5.setOnClickListener(this);
        day6.setOnClickListener(this);
        day7.setOnClickListener(this);
        day9.setOnClickListener(this);
        day10.setOnClickListener(this);
        day11.setOnClickListener(this);
        day13.setOnClickListener(this);
        day14.setOnClickListener(this);
        day15.setOnClickListener(this);
        day17.setOnClickListener(this);
        day18.setOnClickListener(this);
        day19.setOnClickListener(this);
        day21.setOnClickListener(this);
        day22.setOnClickListener(this);
        day23.setOnClickListener(this);
        day25.setOnClickListener(this);
        day26.setOnClickListener(this);
        day27.setOnClickListener(this);
        day29.setOnClickListener(this);
        day30.setOnClickListener(this);
        return root;
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.day1:
             i =new Intent(getContext(), Yogasana.class);
            i.putExtra("day","Day1");
            startActivity(i);
            break;

            case R.id.day2:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day2");
                startActivity(i);
                break;

            case R.id.day3:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day3");
                startActivity(i);
                break;

            case R.id.day5:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day5");
                startActivity(i);
                break;

            case R.id.day6:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day6");
                startActivity(i);
                break;

            case R.id.day7:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day7");
                startActivity(i);
                break;

            case R.id.day9:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day9");
                startActivity(i);
                break;

            case R.id.day10:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day10");
                startActivity(i);
                break;

            case R.id.day11:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day11");
                startActivity(i);
                break;



            case R.id.day13:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day13");
                startActivity(i);
                break;

            case R.id.day14:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day14");
                startActivity(i);
                break;

            case R.id.day15:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day15");
                startActivity(i);
                break;

            case R.id.day17:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day17");
                startActivity(i);
                break;

            case R.id.day18:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day18");
                startActivity(i);
                break;

            case R.id.day19:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day19");
                startActivity(i);
                break;

            case R.id.day21:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day21");
                startActivity(i);
                break;

            case R.id.day22:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day22");
                startActivity(i);
                break;

            case R.id.day23:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day23");
                startActivity(i);
                break;
            case R.id.day25:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day25");
                startActivity(i);
                break;
            case R.id.day26:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day26");
                startActivity(i);
                break;
            case R.id.day27:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day27");
                startActivity(i);
                break;
            case R.id.day29:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day29");
                startActivity(i);
                break;
            case R.id.day30:
                i =new Intent(getContext(), Yogasana.class);
                i.putExtra("day","Day30");
                startActivity(i);
                break;
        }


    }
}
