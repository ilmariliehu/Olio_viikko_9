package com.example.week9_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    EditText date;
    EditText Time_start;
    EditText Time_end;

    TextView print;
    Button button;
    Spinner spinner;

    String choice;
    String Date = null;
    String start;
    String end;



    readXML XML = readXML.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        date = (EditText) findViewById(R.id.editText);
        Time_start = (EditText) findViewById(R.id.editText2);
        Time_end = (EditText) findViewById(R.id.editText3);

        print = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);


        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, XML.teatterit());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                start = null;
                end = null;

                Date = date.getText().toString();
                if (!Time_start.getText().toString().equals("")) {
                    start = Time_start.getText().toString();
                }

                if (!Time_end.getText().toString().equals("")) {
                    end = Time_end.getText().toString();
                }

                XML.schedule(Date, print, choice, start, end);

            }
        });

    }


}

