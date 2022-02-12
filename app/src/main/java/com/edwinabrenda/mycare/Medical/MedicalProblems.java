package com.edwinabrenda.mycare.Medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.edwinabrenda.mycare.adapter.CustomMedicalProblemsAdapter;
import com.edwinabrenda.mycare.HomePage;
import com.edwinabrenda.mycare.R;

import java.util.ArrayList;

public class MedicalProblems extends AppCompatActivity {

    ArrayList<Integer> problemsImages;
    ArrayList<String> problemsText;
    ListView medicalListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_problems);

        medicalListView = (ListView) findViewById(R.id.listOfProblems);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fillProblemsImages();
                fillProblemsText();
                com.edwinabrenda.mycare.adapter.CustomMedicalProblemsAdapter customMedicalProblemsAdapter = new CustomMedicalProblemsAdapter(MedicalProblems.this, problemsImages, problemsText);
                medicalListView.setAdapter(customMedicalProblemsAdapter);
            }
        });

        medicalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listItemSelector(i);
            }
        });

        if(HomePage.progressDialog!=null) HomePage.progressDialog.cancel();
    }

    void listItemSelector(int i) {

        MedicinesController.index = (byte) i;

        Intent intent = new Intent(MedicalProblems.this, RequiredMedicines.class);
        startActivity(intent);
    }

    void fillProblemsImages() {
        problemsImages = new ArrayList();
        problemsImages.add(R.drawable.fever_face);
        problemsImages.add(R.drawable.headache);
        problemsImages.add(R.drawable.cold);
        problemsImages.add(R.drawable.vomiting);
        problemsImages.add(R.drawable.constipation);
        problemsImages.add(R.drawable.blood_presure);
        problemsImages.add(R.drawable.daily_health_care);
    }

    void fillProblemsText() {
        problemsText = new ArrayList();
        String[] problems = getResources().getStringArray(R.array.medicalProblems);

        for(int i=0; i<problems.length; i++)
            problemsText.add(problems[i]);
    }
}