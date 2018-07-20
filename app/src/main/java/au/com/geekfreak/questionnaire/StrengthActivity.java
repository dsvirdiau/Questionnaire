package au.com.geekfreak.questionnaire;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import au.com.geekfreak.questionnaire.questionnaire.R;

public class StrengthActivity extends AppCompatActivity {

    TextView txtSituation;
    RadioGroup rdGroup;
    RadioButton nottrueRDButton, somewhattrueRDButton, certainlytrueRDButton;
    Integer rdButtonValue = null;
    Button btnNext;
    String studentCode;
    String[] arraySituations = new String[24];
    int i = 0;
    static int j = 2, k = 2;

    private Sheet sheet = null;
    private Workbook wb;
    private Cell c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strength);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            studentCode = extras.getString("StudentCode");
        }

        File file = new File(this.getExternalFilesDir(null), studentCode + "STRENGTH" + ".xls");

        DownloadFileTask fileDownload = new DownloadFileTask(DropboxClient.getClient(LoginActivity.ACCESS_TOKEN), file, this);
        fileDownload.execute();

        txtSituation = (TextView) findViewById(R.id.txtSituation2);
        rdGroup = (RadioGroup) findViewById(R.id.rdGroup2);
        nottrueRDButton = (RadioButton) findViewById(R.id.rdNottrue);
        somewhattrueRDButton = (RadioButton) findViewById(R.id.rdSomewhattrue);
        certainlytrueRDButton = (RadioButton) findViewById(R.id.rdCertainlyTrue);
        btnNext = (Button) findViewById(R.id.btnNext2);

        arraySituations = getResources().getStringArray(R.array.strength);
        txtSituation.setText(arraySituations[i]);


        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("FILE", "Storage not available or read only");
        }

        wb = new HSSFWorkbook();

        sheet = wb.getSheet("STRENGTH");

        if(sheet == null)
        {
            //Cell style for header row
            CellStyle cs = wb.createCellStyle();
            cs.setFillForegroundColor(HSSFColor.LIME.index);
            cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            sheet = wb.createSheet("STRENGTH");

            // Generate column headings
            Row row = sheet.createRow(0);

            c = row.createCell(0);
            c.setCellValue("Date");
            c.setCellStyle(cs);

            c = row.createCell(1);
            c.setCellValue("Student Code");
            c.setCellStyle(cs);

            c = row.createCell(2);
            c.setCellValue("I try to be nice to other people. I care about their feelings");
            c.setCellStyle(cs);

            c = row.createCell(3);
            c.setCellValue("I am restless, I cannot stay still for long");
            c.setCellStyle(cs);

            c = row.createCell(4);
            c.setCellValue("I get a lot of headaches, stomach-aches or sickness");
            c.setCellStyle(cs);

            c = row.createCell(5);
            c.setCellValue("I usually share with others, for example CD’s, games, food");
            c.setCellStyle(cs);

            c = row.createCell(6);
            c.setCellValue("I get very angry and often lose my temper");
            c.setCellStyle(cs);

            c = row.createCell(7);
            c.setCellValue("I would rather be alone than with people of my age");
            c.setCellStyle(cs);

            c = row.createCell(8);
            c.setCellValue("I usually do as I am told");
            c.setCellStyle(cs);

            c = row.createCell(9);
            c.setCellValue("I worry a lot");
            c.setCellStyle(cs);

            c = row.createCell(10);
            c.setCellValue("I am helpful if someone is hurt, upset or feeling ill");
            c.setCellStyle(cs);

            c = row.createCell(11);
            c.setCellValue("I am constantly fidgeting or squirming");
            c.setCellStyle(cs);

            c = row.createCell(12);
            c.setCellValue("I have one good friend or more");
            c.setCellStyle(cs);

            c = row.createCell(13);
            c.setCellValue("I fight a lot. I can make other people do what I want");
            c.setCellStyle(cs);

            c = row.createCell(14);
            c.setCellValue("I am often unhappy, depressed or tearful");
            c.setCellStyle(cs);

            c = row.createCell(15);
            c.setCellValue("Other people my age generally like me");
            c.setCellStyle(cs);

            c = row.createCell(16);
            c.setCellValue("I am easily distracted, I find it difficult to concentrate");
            c.setCellStyle(cs);

            c = row.createCell(17);
            c.setCellValue("I am nervous in new situations. I easily lose confidence");
            c.setCellStyle(cs);

            c = row.createCell(18);
            c.setCellValue("I am kind to younger children");
            c.setCellStyle(cs);

            c = row.createCell(19);
            c.setCellValue("I am often accused of lying or cheating");
            c.setCellStyle(cs);

            c = row.createCell(20);
            c.setCellValue("Other children or young people pick on me or bully me");
            c.setCellStyle(cs);

            c = row.createCell(21);
            c.setCellValue("I often volunteer to help others (parents, teachers, children)");
            c.setCellStyle(cs);

            c = row.createCell(22);
            c.setCellValue("I think before I do things");
            c.setCellStyle(cs);

            c = row.createCell(23);
            c.setCellValue("I take things that are not mine from home, school or elsewhere");
            c.setCellStyle(cs);

            c = row.createCell(24);
            c.setCellValue("I get along better with adults than with people my own age");
            c.setCellStyle(cs);

            c = row.createCell(25);
            c.setCellValue("I have many fears, I am easily scared");
            c.setCellStyle(cs);

            c = row.createCell(26);
            c.setCellValue("I finish the work I'm doing. My attention is good");
            c.setCellStyle(cs);


            sheet.setColumnWidth(0, (15 * 800));
            sheet.setColumnWidth(1, (15 * 800));
            sheet.setColumnWidth(2, (15 * 800));
            sheet.setColumnWidth(3, (15 * 800));
            sheet.setColumnWidth(4, (15 * 800));
            sheet.setColumnWidth(5, (15 * 800));
            sheet.setColumnWidth(6, (15 * 800));
            sheet.setColumnWidth(7, (15 * 800));
            sheet.setColumnWidth(8, (15 * 800));
            sheet.setColumnWidth(9, (15 * 800));
            sheet.setColumnWidth(10, (15 * 800));
            sheet.setColumnWidth(11, (15 * 800));
            sheet.setColumnWidth(12, (15 * 800));
            sheet.setColumnWidth(13, (15 * 800));
            sheet.setColumnWidth(14, (15 * 800));
            sheet.setColumnWidth(15, (15 * 800));
            sheet.setColumnWidth(16, (15 * 800));
            sheet.setColumnWidth(17, (15 * 800));
            sheet.setColumnWidth(18, (15 * 800));
            sheet.setColumnWidth(19, (15 * 800));
            sheet.setColumnWidth(20, (15 * 800));
            sheet.setColumnWidth(21, (15 * 800));
            sheet.setColumnWidth(22, (15 * 800));
            sheet.setColumnWidth(23, (15 * 800));
            sheet.setColumnWidth(24, (15 * 800));
            sheet.setColumnWidth(25, (15 * 800));
            sheet.setColumnWidth(26, (15 * 800));

            // Create a path where we will place our List of objects on external storage
            file = new File(this.getExternalFilesDir(null), studentCode + "STRENGTH" + ".xls");
            FileOutputStream os = null;

            try {
                os = new FileOutputStream(file);
                wb.write(os);
                Log.w("FileUtils", "Writing file" + file);
//                success = true;
            } catch (IOException e) {
                Log.w("FileUtils", "Error writing " + file, e);
            } catch (Exception e) {
                Log.w("FileUtils", "Failed to save file", e);
            } finally {
                try {
                    if (null != os)
                        os.close();
                } catch (Exception ex) {
                }
            }

        }

        final ArrayList<Integer> valueArray = new ArrayList<Integer>();

        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.rdNottrue){
                    rdButtonValue = 0;
                    //Toast.makeText(getApplicationContext(), "Not True : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }

                else if(checkedId == R.id.rdSomewhattrue){
                    rdButtonValue = 1;
                    //Toast.makeText(getApplicationContext(), "Somewhat True : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }

                else if(checkedId == R.id.rdCertainlyTrue){
                    rdButtonValue = 2;
                    //Toast.makeText(getApplicationContext(), "Certainly True : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rdGroup.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Please select at least one option ", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(i<24)
                    {
                        valueArray.add(rdButtonValue);
                        if(i == 23)
                            btnNext.setText("Done");

                        i++;
                        txtSituation.setText(arraySituations[i]);
                        rdGroup.clearCheck();

                        //writeSheet(MoodStatusActivity.this, rdButtonValue.toString());

                    }
                    else {

                        if(isInternetConnected()) {
                            txtSituation.setText(arraySituations[i]);
                            valueArray.add(rdButtonValue);
                            writeSheet(StrengthActivity.this, valueArray, studentCode);
                            StrengthActivity.this.finish();
                        }
                            else
                                Toast.makeText(getApplicationContext(),"Please check internet connection!",Toast.LENGTH_LONG).show();


                    }

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StrengthActivity.this);
        alertDialogBuilder.setMessage("Are you sure you want to go back without finishing?");
        alertDialogBuilder.setIcon(R.drawable.warning_icon);
        alertDialogBuilder.setTitle("Confirmation!");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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

    public static void writeSheet(Context context, ArrayList<Integer> al, String fileName) {
        HSSFWorkbook workbook;
        Boolean rowCreated = false;

        try {

            File newFile = new File(context.getExternalFilesDir(null), fileName + "STRENGTH" + ".xls");

            FileInputStream file = new FileInputStream(newFile);
            workbook = new HSSFWorkbook(file);

            Sheet sheeet = workbook.getSheet("STRENGTH");

            Row row = workbook.getSheetAt(0).createRow(sheeet.getPhysicalNumberOfRows() + 2);

            Cell cell;

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM YY");
            String currentDateandTime = sdf.format(new Date());

            if(k == 2) {
                cell = row.createCell(0);
                cell.setCellValue(currentDateandTime);

                cell = row.createCell(1);
                cell.setCellValue(fileName);
            }

            for(int i = 0; i < al.size(); i++) {
                cell = row.createCell(k);
                cell.setCellValue(al.get(i));
                k++;
            }

            file.close();

            FileOutputStream outFile = new FileOutputStream(newFile);
            workbook.write(outFile);
            outFile.close();
            k = 2;

            UploadFileTask fileUpload = new UploadFileTask(DropboxClient.getClient(LoginActivity.ACCESS_TOKEN), newFile, context);
            fileUpload.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


}//CLASS
