package com.example.faiz.idealweight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private SeekBar seekBar;
    private TextView textView;
    private SeekBar seekBar2;
    private TextView textView2;
    boolean isUnitInch;//global variable that is used to handle situation when height unit is changed.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isUnitInch=false;
        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
        mySpinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Cm");
        categories.add("Inch");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,categories);
        mySpinner.setAdapter(adapter);
        Button myButton = (Button) findViewById(R.id.button);
        myButton.requestFocus();
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView.setText("Height \n"+seekBar.getProgress() + "/" + seekBar.getMax());
        textView2.setText("Weight(Kg):\n"+seekBar2.getProgress() + "/" + seekBar2.getMax());
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress = 10;
                    @Override
                    public void onProgressChanged(SeekBar seekBar,

                                                  int progressValue, boolean fromUser)
                    {
                        progress = progressValue;
                        String ftIn;

                        if(isUnitInch==false)
                        {
                            textView.setText("Height \n" + progress + "");
                        }
                        else if(isUnitInch==true)
                        {
                            ftIn=InchesToFt(progress);
                            textView.setText("Height \n" +ftIn);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Do something here,
                        //if you want to do anything at the start of
                        // touching the seekbar
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Display the value in textview
                        //textView.setText(progress);

                    }
                });

        seekBar2.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {
                    int progress = 0;
                    @Override
                    public void onProgressChanged(SeekBar seekBar2,

                                                  int progresValue, boolean fromUser) {
                        progress = progresValue;
                        //Integer Getprog = seekBar2.getProgress();
                        textView2.setText("Weight(Kg):\n"+progress + "");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Do something here,
                        //if you want to do anything at the start of
                        // touching the seekbar
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar2) {
                        // Display the value in textview
                        //textView2.setText(progress );
                    }
                });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if (item == "Inch")
        {
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            isUnitInch=true;
            seekBar.setMax(100);
        }
        else if (item=="Cm")
        {
            isUnitInch=false;
        }
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String InchesToFt(int inch) {
        int inches;
        int feet;
        int remainder;
        String displayFtIn;
        inches = inch;
        feet = inches / 12;
        //Calculate the remaining number of inches
        remainder = inches % 12;
        if(remainder==0)
        {
            displayFtIn=(feet+"Ft");
        }
        else
        displayFtIn=(feet+"Ft"+" "+remainder+"In");
        return (displayFtIn);
    }
    public double GetIdealWeight(double height)
    {

        double idealWeight;
        double remainInch;
        if(height<=60)
        {
            idealWeight=50;
        }
        else
        {
            remainInch = (height - 60);
            idealWeight = 50 + (2.3 * remainInch);//ideal weight for men

        }
        return idealWeight;
    }
    public void GetBMI(View view)
    {
        double height;
        height=seekBar.getProgress();
        double heightForBMI;
        double BMI;
        heightForBMI = height/100;// this variable will be used in calculating BMI as it has to be converted in mm.
        double weight = seekBar2.getProgress();
        double heightInInch;
        double getIdealWeight;
        heightInInch=height;
        if(isUnitInch==true)
        {
            weight=weight* 2.2046;
            heightInInch=(heightInInch/12);
           BMI=(4.88*weight)/(heightInInch*heightInInch);
        }
        else
        {
            BMI = weight / (heightForBMI * heightForBMI);
        }
        DecimalFormat df = new DecimalFormat("#.#");
        BMI= Double.valueOf(df.format(BMI));
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        textView5.setText("BMI: "+"\n"+BMI + "");
       getIdealWeight= GetIdealWeight(heightInInch*0.39);
        getIdealWeight=Double.valueOf(df.format(getIdealWeight));
        TextView textView6 = (TextView) findViewById(R.id.textView6);
        textView6.setText("Ideal Weight:\n "+" "+getIdealWeight + "");
        if(BMI<=18.5) Toast.makeText(getApplicationContext(), "Underweight" , Toast.LENGTH_SHORT).show();
        if(BMI>18.5 && BMI<=24.9) Toast.makeText(getApplicationContext(), "Normal Weight" , Toast.LENGTH_SHORT).show();
        if(BMI>25 && BMI<=29.9) Toast.makeText(getApplicationContext(), "Overweight" , Toast.LENGTH_SHORT).show();
        if(BMI>=30) Toast.makeText(getApplicationContext(), "Obese" , Toast.LENGTH_SHORT).show();

    }

}
