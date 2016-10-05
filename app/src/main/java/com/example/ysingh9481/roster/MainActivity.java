package com.example.ysingh9481.roster;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.content.SharedPreferences;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    EditText dateOfBirth, userName;
    CheckBox steadyCheck;
    private int mYear, mMonth, mDay;


    Spinner eyeColorElem;
    String[] eyeColors;
    ArrayAdapter<String> eyeColorAdapter;
    String shirtRadio = "M";

    SeekBar seekPant;
    SeekBar seekShirt;
    SeekBar seekShoe;

    TextView textViewPant;
    TextView textViewShirt;
    TextView textViewShoe;

    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.roster_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("THE ROSTER");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);

        seekPant = (SeekBar) findViewById(R.id.pantSize);
        seekShirt = (SeekBar) findViewById(R.id.shirtSize);
        seekShoe = (SeekBar) findViewById(R.id.shoeSize);

        textViewPant= (TextView) findViewById(R.id.pantSizeValue);
        textViewShirt= (TextView) findViewById(R.id.shirtSizeValue);
        textViewShoe= (TextView) findViewById(R.id.shoeSizeValue);

        seekPant.setOnSeekBarChangeListener(this);
        seekShirt.setOnSeekBarChangeListener(this);
        seekShoe.setOnSeekBarChangeListener(this);

        eyeColorElem = (Spinner) findViewById(R.id.eyeColor);
        eyeColors = new String[]{
                "Black","Brown","Blue","Green"
        };
        eyeColorAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,eyeColors);
        eyeColorElem.setAdapter(eyeColorAdapter);

        userName = (EditText)findViewById(R.id.userName);
        dateOfBirth = (EditText)findViewById(R.id.dateofBirth);
        steadyCheck = (CheckBox)findViewById(R.id.checkBox);

        preferences = getSharedPreferences("RosterPreferences", MODE_PRIVATE);

        if (preferences.getBoolean("saved", false)) {
            loadPreferences();
        }

    }

    private void loadPreferences(){
        userName.setText(preferences.getString("userName", null));
        eyeColorElem.setSelection(eyeColorAdapter.getPosition(preferences.getString("eyeColor", null)));
        steadyCheck.setChecked(preferences.getBoolean("steadyCheck", false));
        dateOfBirth.setText(preferences.getString("dateOfBirth", null));
        String shirtSizeRadio = preferences.getString("shirtRadio", null);

        RadioButton setRadio;

        switch (shirtSizeRadio){
            case "XS":
                setRadio = (RadioButton)findViewById(R.id.xSmall);
                setRadio.setChecked(true);
                break;
            case "S":
                setRadio = (RadioButton)findViewById(R.id.small);
                setRadio.setChecked(true);
                break;
            case "M":
                setRadio = (RadioButton)findViewById(R.id.medium);
                setRadio.setChecked(true);
                break;
            case "L":
                setRadio = (RadioButton)findViewById(R.id.large);
                setRadio.setChecked(true);
                break;
            case "XL":
                setRadio = (RadioButton)findViewById(R.id.xLarge);
                setRadio.setChecked(true);
                break;
            case "XXL":
                setRadio = (RadioButton)findViewById(R.id.xXLarge);
                setRadio.setChecked(true);
                break;
        }

        seekPant.setProgress(preferences.getInt("pantSize", 0));
        textViewPant.setText(String.valueOf(preferences.getInt("pantSize", 0)));
        seekShirt.setProgress(preferences.getInt("shirtSize", 0));
        textViewShirt.setText(String.valueOf(preferences.getInt("shirtSize", 0)));
        seekShoe.setProgress(preferences.getInt("shoeSize", 0));
        textViewShoe.setText(String.valueOf(preferences.getInt("shoeSize", 0)));
    }

    public void getDateofBirth(View v) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateOfBirth.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (seekBar == seekPant){
            textViewPant.setText(String.valueOf(i));
        } else if (seekBar == seekShirt){
            textViewShirt.setText(String.valueOf(i));
        } else {
            textViewShoe.setText(String.valueOf(i+4));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void shirtSizeChanged(View v) {
        RadioButton shirtSize = (RadioButton) v;
        shirtRadio = shirtSize.getText().toString();
    }

    public void savePreferences(View v){
        SharedPreferences.Editor prefsEditor = preferences.edit();

        prefsEditor.putString("userName", userName.getText().toString());
        prefsEditor.putString("eyeColor", eyeColorElem.getSelectedItem().toString());
        prefsEditor.putBoolean("steadyCheck", steadyCheck.isChecked());
        prefsEditor.putString("dateOfBirth", dateOfBirth.getText().toString());
        prefsEditor.putString("shirtRadio", shirtRadio);
        prefsEditor.putInt("pantSize", seekPant.getProgress());
        prefsEditor.putInt("shirtSize", seekShirt.getProgress());
        prefsEditor.putInt("shoeSize", seekShoe.getProgress());
        prefsEditor.putBoolean("saved", true);

        prefsEditor.commit();
    }
}
