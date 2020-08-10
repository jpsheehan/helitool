package nz.sheehan.securethehelirigs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.DialogFragment;

public class TelnetDialogFragment extends DialogFragment {

    Integer heliRigId;
    String host;
    ITelnetTaskCompletion parent;

    public TelnetDialogFragment(ITelnetTaskCompletion parent, Integer id)
    {
        super();

        if ((id < 1) || (id > 4)) {
            throw new IllegalArgumentException("Id must be between 1 and 4 (inclusive).");
        }

        heliRigId = id;
        host = "132.181.52." + heliRigId.toString();
        this.parent = parent;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialogPrompt)
                .setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TelnetTask task = new TelnetTask();
                        task.delegate = parent;
                        task.execute(host);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

}
