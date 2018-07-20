package au.com.geekfreak.questionnaire;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadFileTask extends AsyncTask {

    private DbxClientV2 dbxClient;
    private File file;
    private Context context;
    boolean download = false;


    DownloadFileTask(DbxClientV2 dbxClient, File file, Context context) {
        this.dbxClient = dbxClient;
        this.file = file;
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
// Download to Phone
            FileOutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(file);
                FileMetadata info = null;
                info = dbxClient.files().download("/Questionnaire Data/" + file.getName()).download(outputStream);

                if(info.getRev()!=null)
                    Log.i("Success", "The file's rev is: " + info.getRev());

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (DbxException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Download Status", "Success");
            download = true;
        }
        else
            download = false;


        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if(download)
            Toast.makeText(context, "Downloaded successfully", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "File not downloaded successfully. Please go back and try again!", Toast.LENGTH_LONG).show();
    }
}
