package nz.sheehan.securethehelirigs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.longdo.mjpegviewer.MjpegView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RigControllerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RigControllerFragment extends Fragment implements ITelnetTaskCompletion {

    private MjpegView mViewer;
    private String mUrl;
    private View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "rig_id";

    // TODO: Rename and change types of parameters
    private Integer mRigId;

    public RigControllerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param rigId The id of the heli rig (1 - 4).
     * @return A new instance of fragment RigControllerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RigControllerFragment newInstance(Integer rigId) {
        RigControllerFragment fragment = new RigControllerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, rigId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRigId = getArguments().getInt(ARG_PARAM1);
            mUrl = String.format("http://132.181.52.%d:7070/camera1.mjpg", mRigId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rig_controller, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;

        // create things here
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        final RigControllerFragment self = this;

        mViewer = view.findViewById(R.id.heliFeed);
        mViewer.setMode(MjpegView.MODE_FIT_WIDTH);
        mViewer.setAdjustHeight(true);
        mViewer.setUrl(mUrl);
        mViewer.startStream();

        Button resetButton = (Button) view.findViewById(R.id.buttonReset);
        resetButton.setText(String.format("Reset Rig #%d", mRigId));
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TelnetDialogFragment(self, mRigId).show(fragmentManager, "dialog");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewer.stopStream();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewer.startStream();
    }

    public void taskFinish(Boolean success) {
        int stringId = R.string.telnetFailure;

        if (success) {
            stringId = R.string.telnetSuccess;
        }

        Snackbar snackbar = Snackbar.make(mView, stringId, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}