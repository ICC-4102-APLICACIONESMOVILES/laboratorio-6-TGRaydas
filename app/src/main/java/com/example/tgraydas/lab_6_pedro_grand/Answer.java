package com.example.tgraydas.lab_6_pedro_grand;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Question.class, parentColumns = "Question", childColumns = "questionId", onDelete = CASCADE))
public class Answer {
    @PrimaryKey
    public Integer questionId;
    public String Answer;
    public Double lat;
    public Double lon;
    public Answer(String Answer, Integer questionId, Double lat, Double lon)
    {
        this.questionId = questionId;
        this.Answer = Answer;
        this.lat = lat;
        this.lon = lon;
    }
}
