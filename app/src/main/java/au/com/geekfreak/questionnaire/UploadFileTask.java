package au.com.geekfreak.questionnaire;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Async task to upload a file to a directory
 */
class UploadFileTask extends AsyncTask{

    private DbxClientV2 dbxClient;
    private File file;
    private Context context;
    boolean upload = false;

    UploadFileTask(DbxClientV2 dbxClient, File file, Context context) {
        this.dbxClient = dbxClient;
        this.file = file;
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {

                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting())
                {
                    // Upload to Dropbox
                    InputStream inputStream = new FileInputStream(file);
                    dbxClient.files().uploadBuilder("/Questionnaire Data/" + file.getName()) //Path in the user's Dropbox to save the file.
                            .withMode(WriteMode.OVERWRITE) //always overwrite existing file
                            .uploadAndFinish(inputStream);
                    Log.d("Upload Status", "Success");
                    upload = true;
                } else {
                    upload = false;
                }

        } catch (DbxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(upload)
            Toast.makeText(context, "Uploaded successfully", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Upload not successful", Toast.LENGTH_LONG).show();
    }
}
