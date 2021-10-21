package pam.pollub.myfrags;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Fragment1 extends Fragment {

    public interface OnButtonClickListener {
        public void onButtonClickShuffle();
        public void onButtonClickClockwise();
        public void onButtonClickHide();
        public void onButtonClickRestore();
    }
    //2.
    private OnButtonClickListener callback = null;
    //3.
    public void setOnButtonClickListener(OnButtonClickListener callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        Button buttonShuffle = (Button) view.findViewById(R.id.button_shuffle);
        Button buttonClockwise = (Button) view.findViewById(R.id.button_clockwise);
        Button buttonHide = (Button) view.findViewById(R.id.button_hide);
        Button buttonRestore = (Button) view.findViewById(R.id.button_restore);
        buttonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickShuffle();
            }
        });
        buttonClockwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickClockwise();
            }
        });
        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickHide();
            }
        });
        buttonRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) callback.onButtonClickRestore();
            }
        });
        return view;
    }
}