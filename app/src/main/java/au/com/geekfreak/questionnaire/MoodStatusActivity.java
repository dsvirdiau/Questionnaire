package au.com.geekfreak.questionnaire;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
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


public class MoodStatusActivity extends AppCompatActivity {

    TextView txtSituation, txtSituationMeaning;
    RadioGroup rdGroup;
    RadioButton notatallRDButton, alittleRDButton, moderatelyRDButton, quiteabitRDButton, extremelyRDButton;
    Integer rdButtonValue = null;
    Button btnNext, btnMeaning, btnOkDialog;
    int i = 0;
    static int j = 2, k = 2;
    String[] arraySituations = new String[23];
    String[] arraySituationMeanings = new String[23];
    String studentCode;

    private Sheet sheet = null;
    private Workbook wb;
    private Cell c = null;

    Dialog dialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            studentCode = extras.getString("StudentCode");
        }

        File file = new File(this.getExternalFilesDir(null), studentCode + "POMS" + ".xls");
        //studentCode = intent.getExtras().getString("StudentCode");
        DownloadFileTask fileDownload = new DownloadFileTask(DropboxClient.getClient(LoginActivity.ACCESS_TOKEN), file, this);
        fileDownload.execute();


        txtSituation = (TextView) findViewById(R.id.txtSituation);

        rdGroup = (RadioGroup) findViewById(R.id.rdGroup);
        notatallRDButton = (RadioButton) findViewById(R.id.notatall);
        alittleRDButton = (RadioButton) findViewById(R.id.alittle);
        moderatelyRDButton = (RadioButton) findViewById(R.id.moderately);
        quiteabitRDButton = (RadioButton) findViewById(R.id.quiteabit);
        extremelyRDButton = (RadioButton) findViewById(R.id.extremely);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnMeaning = (Button) findViewById(R.id.btnMeaning);

        arraySituations = getResources().getStringArray(R.array.situations);
        arraySituationMeanings = getResources().getStringArray(R.array.situationMeanings);

        txtSituation.setText(arraySituations[i]);


        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("FILE", "Storage not available or read only");
        }

        wb = new HSSFWorkbook();

        sheet = wb.getSheet("POMS");

        //!file.exists() &&
        if(sheet == null)
        {

            //Cell style for header row
            CellStyle cs = wb.createCellStyle();
            cs.setFillForegroundColor(HSSFColor.LIME.index);
            cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            sheet = wb.createSheet("POMS");

            // Generate column headings
            Row row = sheet.createRow(0);

            c = row.createCell(0);
            c.setCellValue("Date");
            c.setCellStyle(cs);

            c = row.createCell(1);
            c.setCellValue("Student Code");
            c.setCellStyle(cs);

            c = row.createCell(2);
            c.setCellValue("Panicky");
            c.setCellStyle(cs);

            c = row.createCell(3);
            c.setCellValue("Lively");
            c.setCellStyle(cs);

            c = row.createCell(4);
            c.setCellValue("Confused");
            c.setCellStyle(cs);

            c = row.createCell(5);
            c.setCellValue("Worn Out");
            c.setCellStyle(cs);

            c = row.createCell(6);
            c.setCellValue("Depressed");
            c.setCellStyle(cs);

            c = row.createCell(7);
            c.setCellValue("Downhearted");
            c.setCellStyle(cs);

            c = row.createCell(8);
            c.setCellValue("Annoyed");
            c.setCellStyle(cs);

            c = row.createCell(9);
            c.setCellValue("Exhausted");
            c.setCellStyle(cs);

            c = row.createCell(10);
            c.setCellValue("Mixed-Up");
            c.setCellStyle(cs);

            c = row.createCell(11);
            c.setCellValue("Sleepy");
            c.setCellStyle(cs);

            c = row.createCell(12);
            c.setCellValue("Bitter");
            c.setCellStyle(cs);

            c = row.createCell(13);
            c.setCellValue("Unhappy");
            c.setCellStyle(cs);

            c = row.createCell(14);
            c.setCellValue("Anxious");
            c.setCellStyle(cs);

            c = row.createCell(15);
            c.setCellValue("Worried");
            c.setCellStyle(cs);

            c = row.createCell(16);
            c.setCellValue("Energetic");
            c.setCellStyle(cs);

            c = row.createCell(17);
            c.setCellValue("Miserable");
            c.setCellStyle(cs);

            c = row.createCell(18);
            c.setCellValue("Muddled");
            c.setCellStyle(cs);

            c = row.createCell(19);
            c.setCellValue("Nervous");
            c.setCellStyle(cs);

            c = row.createCell(20);
            c.setCellValue("Angry");
            c.setCellStyle(cs);

            c = row.createCell(21);
            c.setCellValue("Active");
            c.setCellStyle(cs);

            c = row.createCell(22);
            c.setCellValue("Tired");
            c.setCellStyle(cs);

            c = row.createCell(23);
            c.setCellValue("Bad Tempered");
            c.setCellStyle(cs);

            c = row.createCell(24);
            c.setCellValue("Alert");
            c.setCellStyle(cs);

            c = row.createCell(25);
            c.setCellValue("Uncertain");
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
            sheet.setColumnWidth(14, (15 * 500));
            sheet.setColumnWidth(15, (15 * 500));
            sheet.setColumnWidth(16, (15 * 500));
            sheet.setColumnWidth(17, (15 * 500));
            sheet.setColumnWidth(18, (15 * 500));
            sheet.setColumnWidth(19, (15 * 500));
            sheet.setColumnWidth(20, (15 * 500));
            sheet.setColumnWidth(21, (15 * 500));
            sheet.setColumnWidth(22, (15 * 500));
            sheet.setColumnWidth(23, (15 * 500));
            sheet.setColumnWidth(24, (15 * 500));
            sheet.setColumnWidth(25, (15 * 500));

            // Create a path where we will place our List of objects on external storage
            file = new File(this.getExternalFilesDir(null), studentCode + "POMS" + ".xls");
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

                if(checkedId == R.id.notatall){
                    rdButtonValue = 0;
                    //Toast.makeText(getApplicationContext(), "Not at All : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }

                else if(checkedId == R.id.alittle){
                    rdButtonValue = 1;
                    //Toast.makeText(getApplicationContext(), "A Little : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }

                else if(checkedId == R.id.moderately){
                    rdButtonValue = 2;
                    //Toast.makeText(getApplicationContext(), "Moderately : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }

                else if(checkedId == R.id.quiteabit){
                    rdButtonValue = 3;
                    //Toast.makeText(getApplicationContext(), "Quite A Bit : " + rdButtonValue, Toast.LENGTH_SHORT).show();
                }

                else if(checkedId == R.id.extremely){
                    rdButtonValue = 4;
                    //Toast.makeText(getApplicationContext(), "Extremely : " + rdButtonValue, Toast.LENGTH_SHORT).show();
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
                    if(i<23)
                    {
                        valueArray.add(rdButtonValue);
                        if(i == 22)
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
                            writeSheet(MoodStatusActivity.this, valueArray, studentCode);
                            MoodStatusActivity.this.finish();

                        }else
                            Toast.makeText(getApplicationContext(),"Please check internet connection!",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        btnMeaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(MoodStatusActivity.this);
                dialog.setContentView(R.layout.popup_dialog);
                dialog.setTitle("Alternative word!");

                txtSituationMeaning = (TextView) dialog.findViewById(R.id.txtMeaning);
                btnOkDialog = (Button) dialog.findViewById(R.id.btnOKDialog);

                txtSituationMeaning.setText(arraySituationMeanings[i]);

                btnOkDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MoodStatusActivity.this);
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

            File newFile = new File(context.getExternalFilesDir(null), fileName + "POMS" + ".xls");

            FileInputStream file = new FileInputStream(newFile);
            workbook = new HSSFWorkbook(file);

            Sheet sheeet = workbook.getSheet("POMS");

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

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}
