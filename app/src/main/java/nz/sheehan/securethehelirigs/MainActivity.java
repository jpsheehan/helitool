package nz.sheehan.securethehelirigs;

import android.os.Bundle;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        for (int i = 1; i <= 4; ++i) {
            RigControllerFragment rig = new RigControllerFragment();
            Bundle args = new Bundle();
            args.putInt("rig_id", i);
            rig.setArguments(args);
            transaction.add(R.id.rigContainer, rig);
        }
        transaction.commit();

    }

}