package com.example.kevinpluck.agecategorizer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ll = (LinearLayout) findViewById(R.id.yearButtonLayout);
        generateButtons(Calendar.getInstance(), "14 16 20");
    }

    private void generateButtons(Calendar date, String ageCategories) {

        ll.removeAllViews();

        int[] ageCategoriesArray = parseAgeCategories(ageCategories);

        int theYear = date.get(Calendar.YEAR);
        int numberOfCategories = ageCategoriesArray.length;

        if(numberOfCategories <= 1) return;

        int oldestCategory = ageCategoriesArray[numberOfCategories - 1];
        int yearForButton = theYear - oldestCategory;

        addDefiniteYearButton("-" + (yearForButton-1),numberOfCategories);
        addYearButton(String.valueOf(yearForButton), numberOfCategories - 1);

        String years = "";
        for(int i = numberOfCategories - 2; i >= 0; i--){

            while(yearForButton <= (theYear - ageCategoriesArray[i] - 1)) {

                if (i >= 0 && yearForButton == (theYear - ageCategoriesArray[i] - 1)) {
                    addDefiniteYearButton(years, i + 1);
                    years = "";
                }

                yearForButton++;
                years += yearForButton + "\n";
            }
            addYearButton(years, i);
            years="";
        }
        addDefiniteYearButton((yearForButton+1) + "+",0);
    }

    private void addYearButton(String years, int categoryIndex) {
        Button yearButton = new Button(this);
        yearButton.setText(years.trim());
        ll.addView(yearButton);
    }

    private void addDefiniteYearButton(String years, int categoryIndex) {
        Button yearButton = new Button(this);
        yearButton.setText(years.trim());
        ll.addView(yearButton);
    }

    private int[] parseAgeCategories(String ageCategories) {
        String[] strArray = ageCategories.split(" ");
        int[] intArray = new int[strArray.length];
        for(int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        return intArray;
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

    public void testbuttonClick(View v){
        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText("Hello");
        Button myButton = getButton();

        LinearLayout ll = (LinearLayout) findViewById(R.id.yearButtonLayout);
        ll.removeAllViews();
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ll.addView(myButton,lp);
    }

    @NonNull
    private Button getButton() {
        Button myButton = new Button(this);
        myButton.setText("Push Me");


        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tv = (TextView) findViewById(R.id.textView2);
                tv.setText("Pushed");
            }
        });
        return myButton;
    }
}
