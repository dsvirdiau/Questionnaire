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

public class PerceivedActivity extends AppCompatActivity {


    TextView txtSituation, txtSituation2;
    RadioGroup rdGroup, rdGroup2;
    RadioButton reallytrueRDButton, sortoftrueRDButton, reallytrueRDButton2, sortoftrueRDButton2;
    Integer rdButtonValue = null;
    Button btnNext;
    String studentCode;
    String[] arraySituations = new String[11];
    String[] arraySituations2 = new String[11];
    int i = 0;
    static int j = 2, k = 2;

    private Sheet sheet = null;
    private Workbook wb;
    private Cell c = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perceived);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            studentCode = extras.getString("StudentCode");
        }

        File file = new File(this.getExternalFilesDir(null), studentCode + "PERCEIVED" + ".xls");

        DownloadFileTask fileDownload = new DownloadFileTask(DropboxClient.getClient(LoginActivity.ACCESS_TOKEN), file, this);
        fileDownload.execute();

        txtSituation = (TextView) findViewById(R.id.txtSituation3);
        txtSituation2 = (TextView) findViewById(R.id.txtSituation4);
        rdGroup = (RadioGroup) findViewById(R.id.rdGroup3);
        rdGroup2 = (RadioGroup) findViewById(R.id.rdGroup4);

        reallytrueRDButton = (RadioButton) findViewById(R.id.rdReallytrueforme);
        sortoftrueRDButton = (RadioButton) findViewById(R.id.rdSortoftrueforme);

        sortoftrueRDButton2 = (RadioButton) findViewById(R.id.rdSortoftrueforme2);
        reallytrueRDButton2 = (RadioButton) findViewById(R.id.rdReallytrueforme2);


        btnNext = (Button) findViewById(R.id.btnNext3);

        arraySituations = getResources().getStringArray(R.array.perceived1);
        txtSituation.setText(arraySituations[i]);

        arraySituations2 = getResources().getStringArray(R.array.perceived2);
        txtSituation2.setText(arraySituations2[i]);


        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("FILE", "Storage not available or read only");
        }

        wb = new HSSFWorkbook();

        sheet = wb.getSheet("PERCEIVED");

        if(sheet == null)
        {
            //Cell style for header row
            CellStyle cs = wb.createCellStyle();
            cs.setFillForegroundColor(HSSFColor.LIME.index);
            cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            sheet = wb.createSheet("PERCEIVED");

            // Generate column headings
            Row row = sheet.createRow(0);

            c = row.createCell(0);
            c.setCellValue("Date");
            c.setCellStyle(cs);

            c = row.createCell(1);
            c.setCellValue("Student Code");
            c.setCellStyle(cs);

            c = row.createCell(2);
            c.setCellValue("Question 1");
            c.setCellStyle(cs);

            c = row.createCell(3);
            c.setCellValue("Question 2");
            c.setCellStyle(cs);

            c = row.createCell(4);
            c.setCellValue("Question 3");
            c.setCellStyle(cs);

            c = row.createCell(5);
            c.setCellValue("Question 4");
            c.setCellStyle(cs);

            c = row.createCell(6);
            c.setCellValue("Question 5");
            c.setCellStyle(cs);

            c = row.createCell(7);
            c.setCellValue("Question 6");
            c.setCellStyle(cs);

            c = row.createCell(8);
            c.setCellValue("Question 7");
            c.setCellStyle(cs);

            c = row.createCell(9);
            c.setCellValue("Question 8");
            c.setCellStyle(cs);

            c = row.createCell(10);
            c.setCellValue("Question 9");
            c.setCellStyle(cs);

            c = row.createCell(11);
            c.setCellValue("Question 10");
            c.setCellStyle(cs);

            c = row.createCell(12);
            c.setCellValue("Question 11");
            c.setCellStyle(cs);

            c = row.createCell(13);
            c.setCellValue("Question 12");
            c.setCellStyle(cs);

            sheet.setColumnWidth(0, (15 * 500));
            sheet.setColumnWidth(1, (15 * 500));
            sheet.setColumnWidth(2, (15 * 500));
            sheet.setColumnWidth(3, (15 * 500));
            sheet.setColumnWidth(4, (15 * 500));
            sheet.setColumnWidth(5, (15 * 500));
            sheet.setColumnWidth(6, (15 * 500));
            sheet.setColumnWidth(7, (15 * 500));
            sheet.setColumnWidth(8, (15 * 500));
            sheet.setColumnWidth(9, (15 * 500));
            sheet.setColumnWidth(10, (15 * 500));
            sheet.setColumnWidth(11, (15 * 500));
            sheet.setColumnWidth(12, (15 * 500));
            sheet.setColumnWidth(13, (15 * 500));

            // Create a path where we will place our List of objects on external storage
            file = new File(this.getExternalFilesDir(null), studentCode + "PERCEIVED" + ".xls");
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

                if(rdGroup2.getCheckedRadioButtonId() != -1)
                    rdGroup2.clearCheck();

                if(checkedId == R.id.rdReallytrueforme){
                    rdButtonValue = 1;
//                    Toast.makeText(getApplicationContext(), "Really True : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }

                else if(checkedId == R.id.rdSortoftrueforme){
                    rdButtonValue = 2;
//                    Toast.makeText(getApplicationContext(), "Sort of True : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }



            }
        });

        rdGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(rdGroup.getCheckedRadioButtonId() != -1)
                    rdGroup.clearCheck();

                if(checkedId == R.id.rdSortoftrueforme2){
                    rdButtonValue = 3;
//                    Toast.makeText(getApplicationContext(), "Sort of True 2 : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }

                else if(checkedId == R.id.rdReallytrueforme2){
                    rdButtonValue = 4;
//                    Toast.makeText(getApplicationContext(), "Really True 2 : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }



            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rdGroup.getCheckedRadioButtonId() == -1 &&  rdGroup2.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(), "Please select at least one option ", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(i<11)
                    {
                        valueArray.add(rdButtonValue);
                        if(i == 10)
                            btnNext.setText("Done");

                        i++;

                        txtSituation.setText(arraySituations[i]);
                        txtSituation2.setText(arraySituations2[i]);

                        rdGroup.clearCheck();
                        rdGroup2.clearCheck();
                    }
                    else {

                        if(isInternetConnected())
                        {
                            txtSituation.setText(arraySituations[i]);
                            txtSituation2.setText(arraySituations2[i]);
                            valueArray.add(rdButtonValue);
                            writeSheet(PerceivedActivity.this, valueArray, studentCode);
                            PerceivedActivity.this.finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Please check internet connection!",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


    }//onCreate


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PerceivedActivity.this);
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

            File newFile = new File(context.getExternalFilesDir(null), fileName + "PERCEIVED" + ".xls");

            FileInputStream file = new FileInputStream(newFile);
            workbook = new HSSFWorkbook(file);

            Sheet sheeet = workbook.getSheet("PERCEIVED");

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

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}//Class
