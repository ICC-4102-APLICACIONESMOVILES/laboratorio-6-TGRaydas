package com.example.tgraydas.lab_6_pedro_grand;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;


public class FormAnswers extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_form_answers);
        String DATABASE_NAME = "movies_db";
        final Database_PJ database_pj;
        database_pj = Room.databaseBuilder(this, Database_PJ.class, DATABASE_NAME).build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Question> questions = database_pj.daoAccess().fetchAllQuestions();
                if (questions.size() == 0)
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Question question = new Question("Cuantos Mundiales tiene Brazil");
                            database_pj.daoAccess().newQuestion(question);
                        }
                    });
                }
            }
        });

        Button button = findViewById(R.id.finish_answers_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity(database_pj);
            }
        });
    }


    private void finishActivity(Database_PJ database_pj) {
        final Database_PJ db = database_pj;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }System.out.println("WenaPeter");
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Answer answer = new Answer("Respuesta", 1, location.getLatitude(), location.getLongitude());
                        db.daoAccess().InsertNewAnswer(answer);
                    }
                });
            }
        });
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
        finish();

    }
}
