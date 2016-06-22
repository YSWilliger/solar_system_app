package com.maza.oursolarsystem;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Quiz extends AppCompatActivity implements View.OnClickListener {

    public int chosenPlanetQ = 0;
    public int locationOfCorrectAnswer = 0;
    String[] planet_questions;
    String[] planet_answers;
    ArrayList<String> answers = new ArrayList<>();
    ArrayList<Integer> questions_asked = new ArrayList<>();
    TextView questionText;
    public Button button0, button1, button2, button3, quizAgainButton, backPLDataButton;
    MediaPlayer mp1, mp2, mp3;
    int incorrectAnswerLocation;
    TextView pointsText;
    int points;
    String correctAnsString, incorrectAns;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mp1 = MediaPlayer.create(getApplicationContext(), R.raw.buzzer);
        mp2 = MediaPlayer.create(getApplicationContext(), R.raw.bell);
        mp3 = MediaPlayer.create(getApplicationContext(), R.raw.tada);

        questionText = (TextView) findViewById(R.id.questionText);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        quizAgainButton = (Button) findViewById(R.id.quizAgainButton);
        backPLDataButton = (Button) findViewById(R.id.backPlDataButton);
        quizAgainButton.setVisibility(View.INVISIBLE);
        backPLDataButton.setVisibility(View.INVISIBLE);
        pointsText = (TextView) findViewById(R.id.pointsTextView);

        button0.setTag("0");
        button1.setTag("1");
        button2.setTag("2");
        button3.setTag("3");

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        planet_questions = getResources().getStringArray(R.array.planet_questions);
        planet_answers = getResources().getStringArray(R.array.planet_answers);

        createNewQuestion();

    }

    public void createNewQuestion() {

        answers.clear();
        Random random = new Random();

        try {

            if (points == 20) {
                questionText.setText("You won!\n Play again?");
                mp3.start();
            } else {

                //random number to choose correct answer from planey_answers array
                //random number to select where in answers arraylist the correct answer will go

                chosenPlanetQ = random.nextInt(planet_questions.length);
                locationOfCorrectAnswer = random.nextInt(4);

                //prevent same question being asked twice

                while (questions_asked.contains(chosenPlanetQ)) {
                    chosenPlanetQ = random.nextInt(planet_questions.length);
                }
                correctAnsString = planet_answers[chosenPlanetQ].toString();

                for (int i = 0; i < 4; i++) {

                    if (i == locationOfCorrectAnswer) {

                        questionText.setText(planet_questions[chosenPlanetQ]);
                        answers.add(i, correctAnsString);
                        Log.i("Correct Answer", correctAnsString);

                    } else {
                        incorrectAnswerLocation = random.nextInt(planet_answers.length);
                        incorrectAns = planet_answers[incorrectAnswerLocation];

                        while ((correctAnsString.equals(incorrectAns)) ||
                                (answers.contains(incorrectAns))) {
                            incorrectAnswerLocation = random.nextInt(planet_answers.length);
                            incorrectAns = planet_answers[incorrectAnswerLocation];
                        }
                        answers.add(i, incorrectAns);
                        Log.i("annswers arraylist", answers.get(i));

                    }
                }

                button0.setText(answers.get(0));
                button1.setText(answers.get(1));
                button2.setText(answers.get(2));
                button3.setText(answers.get(3));
                questions_asked.add(chosenPlanetQ);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {
        String tag = v.getTag().toString();


        if ((Integer.parseInt(tag)) == locationOfCorrectAnswer) {
            mp2.start();
            points++;
            pointsText.setText(Integer.toString(points) + getString(R.string.numQuestions));

            if (points == 20) {
                endGame();
            }
        } else {
            mp1.start();
        }
        createNewQuestion();
    }

    public void endGame() {
        button0.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        backPLDataButton.setVisibility(View.VISIBLE);
        pointsText.setVisibility(View.INVISIBLE);
        backPLDataButton.setText("PLANET DATA");
        quizAgainButton.setVisibility(View.VISIBLE);
        quizAgainButton.setText("QUIZ ME AGAIN!");
        questions_asked.clear();
    }

    public void quizAgain(View v) {
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        pointsText.setVisibility(View.VISIBLE);
        points = 0;
        pointsText.setText(Integer.toString(points) + getString(R.string.numQuestions));
        questionText.setText("");
        backPLDataButton.setVisibility(View.INVISIBLE);
        quizAgainButton.setVisibility(View.INVISIBLE);
        createNewQuestion();
    }

    public void dataAgain(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }



}
