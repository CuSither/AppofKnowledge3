package com.example.alex.appofknowledge3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    public static String theString = null;
    public static boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText questionInput = findViewById(R.id.questionInput);
        Button mainButton = findViewById(R.id.mainButton);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionInput.getText().toString();
                close();
                startAPICall(question);
            }
        });
    }

    public void close() {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.spooky_eye_closed);
    }

    public void open() {
        ImageView imageView = findViewById(R.id.imageView);
        try {
            System.out.println("Theoretically, this worked");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Whoops");
            e.printStackTrace();
        }
        imageView.setImageResource(R.drawable.spooky_eye_open);
        displayText();
    }

    public void displayText() {
        TextView textBox = findViewById(R.id.Answer);
        textBox.setText(theString);
    }


    public void startAPICall(String query) {
        theString = "";
        displayText();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "http://api.wolframalpha.com/v1/result?appid=V49X5Y-EEKK3UWH9U&i=" + query.replace(' ', '+') + "%3f",


                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        theString = response;
                        open();
                        Log.e("It worked", response);
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        theString = "Do not ask questions to which you are not prepared to hear the answer";
                        open();
                        Log.e("It didn't work", error.toString());
                    }
                });
        queue.add(stringRequest);
    }
}
