package au.com.geekfreak.questionnaire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import au.com.geekfreak.questionnaire.questionnaire.R;

public class MenuActivity extends AppCompatActivity {

    Button buttonCY,buttonSD, buttonPOMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonCY = (Button) findViewById(R.id.buttonCY);
        buttonPOMS = (Button) findViewById(R.id.buttonPOMS);
        buttonSD = (Button) findViewById(R.id.buttonSD);

        buttonCY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, StudentInfoActivity.class);
                myIntent.putExtra("value", 0);
                MenuActivity.this.startActivity(myIntent);
            }
        });


        buttonPOMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MenuActivity.this, StudentInfoActivity.class);
                myIntent.putExtra("value", 1);
                MenuActivity.this.startActivity(myIntent);
            }
        });


        buttonSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, StudentInfoActivity.class);
                myIntent.putExtra("value", 2);
                MenuActivity.this.startActivity(myIntent);
            }
        });


    }

}
