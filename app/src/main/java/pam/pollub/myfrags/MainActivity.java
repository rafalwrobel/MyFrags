package pam.pollub.myfrags;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends FragmentActivity implements Fragment1.OnButtonClickListener {

    private int[] frames;
    private boolean hiden;

    private int [] sequence;

    private void newFragments() {
        Fragment[] newFragments = new Fragment[]{new Fragment1(), new Fragment2(), new Fragment3(), new Fragment4()};
        //--- Nowy kod
        Fragment[] inSequence = new Fragment[] {newFragments[sequence[0]], newFragments[sequence[1]], newFragments[sequence[2]], newFragments[sequence[3]] };
        newFragments = inSequence;
        //--- Koniec nowego kodu
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < 4; i++) {
            transaction.replace(frames[i], newFragments[i]);
            if (hiden && !(newFragments[i] instanceof Fragment1)) transaction.hide(newFragments[i]);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onButtonClickShuffle() {
        //Toast.makeText(getApplicationContext(), "Shuffle", Toast.LENGTH_SHORT).show();
        //Stary kod. Teraz NIE mieszamy ramek, bo wtedy przestaje działać Clockwise.
        //List<Integer> list = new ArrayList<>(Arrays.asList(frames[0], frames[1], frames[2], frames[3]));
        //Collections.shuffle(list);
        //for (int i = 0; i < 4; i++) frames[i] = list.get(i);
        //--- Nowy kod. Mieszamy sekwencje fragmentów.
        List<Integer> s = new ArrayList<>(Arrays.asList(sequence[0], sequence[1], sequence[2], sequence[3]));
        Collections.shuffle(s);
        for(int i = 0; i < 4; i++) sequence[i] = s.get(i);
        //--- Koniec nowego kodu
        newFragments();
    }
    @Override
    public void onButtonClickClockwise() {

        int t = frames[0];
        frames[0] = frames[1];
        frames[1] = frames[2];
        frames[2] = frames[3];
        frames[3] = t;

        newFragments();
    }
    @Override
    public void onButtonClickHide() {

        if(hiden) return;

        FragmentManager fragmentManager = getSupportFragmentManager();

        for (Fragment f : fragmentManager.getFragments()) {

            if (f instanceof Fragment1 ) continue;

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(f);


            transaction.addToBackStack(null);
            transaction.commit();
        }

        hiden = true;
    }
    @Override
    public void onButtonClickRestore() {

        if (!hiden) return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        for (Fragment f : fragmentManager.getFragments()) {
            if (f instanceof Fragment1) continue;
            transaction.show(f);
        }

        transaction.addToBackStack(null);
        transaction.commit();

        hiden = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            frames = new int[]{R.id.frame1, R.id.frame2, R.id.frame3, R.id.frame4};
            hiden = false;
            sequence = new int[]{0,1,2,3}; // <--- Początkowa sekwencja fragmentów
            Fragment[] fragments = new Fragment[]{new Fragment1(), new Fragment2(), new Fragment3(), new Fragment4()};
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
            );
            for (int i = 0; i < 4; i++) {
                transaction.add(frames[i], fragments[i]);
            }
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            frames = savedInstanceState.getIntArray("FRAMES");
            hiden = savedInstanceState.getBoolean("HIDEN");
            sequence = savedInstanceState.getIntArray("SEQUENCE"); // <--- Nowy kod
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("FRAMES", frames);
        outState.putBoolean("HIDEN", hiden);
        outState.putIntArray("SEQUENCE", sequence); // <--- Nowy kod
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof Fragment1) {
            ((Fragment1) fragment).setOnButtonClickListener(this);
        }
    }

}