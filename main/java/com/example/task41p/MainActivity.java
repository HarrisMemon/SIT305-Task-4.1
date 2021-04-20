package com.example.task41p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    String workouttype;
    private boolean running;
    SharedPreferences sharedPreferences;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = findViewById(R.id.outputText);
        sharedPreferences = getSharedPreferences("com.example.task41p", MODE_PRIVATE);
        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds
                    = savedInstanceState
                    .getInt("seconds");
            running
                    = savedInstanceState
                    .getBoolean("running");

        }
        checkpref();
        runTimer();

    }

    private void checkpref() {
        int secs = sharedPreferences.getInt("Secs", 0);
        String type = sharedPreferences.getString("type", "");
        boolean isrunning = sharedPreferences.getBoolean("run", false);
        seconds = secs;
        output.setText(type);
        running = isrunning;

    }

    @Override
    protected void onStop() {

        super.onStop();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Secs", seconds);
        editor.putString("type", workouttype);
        editor.putBoolean("run", running);
        editor.apply();

    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("running", running);

        savedInstanceState.putInt("seconds", seconds);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void startTimer(View view){

        running = true;
    }

    public void stopTimer(View view){
        running = false;
        showOutput(view);
        seconds = 0;

    }



    public void pauseTimer(View view){
        running = false;

    }

    private void showOutput(View view) {
        TextView output = findViewById(R.id.outputText);
        TextView input = findViewById(R.id.workoutType);
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String out = String.format("You Spent %d:%d on %s", minutes, secs, input.getText());

        output.setText(out);
        workouttype = output.getText().toString();

    }

    private void runTimer() {
        TextView timerview = findViewById(R.id.timerView);

        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                if (running){
                    seconds++;
                }
                String time = String.format("%02d:%02d",
                        minutes, secs);
                timerview.setText(time);

                handler.postDelayed(this, 1000);
            }
        });


    }
}