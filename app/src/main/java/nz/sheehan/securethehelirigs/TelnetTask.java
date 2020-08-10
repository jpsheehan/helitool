package nz.sheehan.securethehelirigs;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.io.OutputStream;
import java.net.Socket;

public class TelnetTask extends AsyncTask<String, Void, Boolean> {

    public ITelnetTaskCompletion delegate = null;
    private Exception exception;

    final byte[] payload = "reset\r\n".getBytes();

    @Override
    protected Boolean doInBackground(String... strings) {
        String host = strings[0];
        Integer port = 4444;

        try {
            Log.i("JPS", String.format("Sending kill message to heli rig %s", host));
            Socket socket = new Socket(host, port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(payload);
            outputStream.flush();
            outputStream.close();
            socket.close();
            return true;
        } catch (Exception e) {
            exception = e;
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success)
    {
        delegate.taskFinish(success);
    }

}
