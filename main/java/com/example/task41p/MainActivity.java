package com.example.task41p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;

    private boolean running;

    //private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
           // wasRunning
             //       = savedInstanceState
               //     .getBoolean("wasRunning");
        }
        runTimer();
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
       // wasRunning = true;
    }

    private void showOutput(View view) {
        TextView output = findViewById(R.id.outputText);
        TextView input = findViewById(R.id.workoutType);
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String out = String.format("You Spent %d:%d on %s", minutes, secs, input.getText());
        output.setText(out);

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