package au.com.geekfreak.questionnaire;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.com.geekfreak.questionnaire.questionnaire.R;

public class StudentInfoActivity extends AppCompatActivity {

    EditText editStudentCode;
    Button btnOK;
    static String studentCode;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        setTitle("Student Details");

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            value = extras.getInt("value");
        }

        editStudentCode = (EditText) findViewById(R.id.editStudentCode);
        btnOK = (Button) findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                studentCode = editStudentCode.getText().toString().toUpperCase();

                if(studentCode.isEmpty())
                {
                    editStudentCode.setError("Please enter student code!");
                }
                else {

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StudentInfoActivity.this);
                    alertDialogBuilder.setMessage("Are you sure " + studentCode.toUpperCase() + " is your student code?");
                    alertDialogBuilder.setIcon(R.drawable.warning_icon);
                    alertDialogBuilder.setTitle("Confirmation!");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (value == 0 && isInternetConnected()) {
                                finish();
                                Intent myIntent = new Intent(StudentInfoActivity.this, PerceivedActivity.class);
                                myIntent.putExtra("StudentCode", studentCode);
                                StudentInfoActivity.this.startActivity(myIntent);
                            } else if (value == 1 && isInternetConnected()) {
                                finish();
                                Intent myIntent = new Intent(StudentInfoActivity.this, MoodStatusActivity.class);
                                myIntent.putExtra("StudentCode", studentCode);
                                StudentInfoActivity.this.startActivity(myIntent);
                            } else if(value == 2 && isInternetConnected()){
                                finish();
                                Intent myIntent = new Intent(StudentInfoActivity.this, StrengthActivity.class);
                                myIntent.putExtra("StudentCode", studentCode);
                                StudentInfoActivity.this.startActivity(myIntent);
                            }else
                            {
                                Toast.makeText(StudentInfoActivity.this,"Please check internet connection!",Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }


            }//onClick
        });

    }//onCreate

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
