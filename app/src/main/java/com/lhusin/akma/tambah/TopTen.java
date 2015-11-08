package com.lhusin.akma.tambah;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.PublicKey;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link TopTen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopTen extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "mSkor";
    private static final String ARG_PARAM2 = "gamestate";

    private int mSkor;
    private MainActivity.GameState gameState;

    HighscoreManager hm;
    TextView textView;
    EditText editText;
    Button buttonOK, buttonCancel;

    Context mcontex = MainActivity.getInstance().getApplicationContext();

    public static TopTen newInstance(int score, int gameState1) {
        TopTen fragment = new TopTen();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, score);
        args.putInt(ARG_PARAM2, gameState1);
        fragment.setArguments(args);
        return fragment;
    }

    public TopTen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSkor = getArguments().getInt(ARG_PARAM1, 0);
            int temp = getArguments().getInt(ARG_PARAM2, 0);
            if (temp == MainActivity.GameState.HIGHSCORE.ordinal()) {
                gameState = MainActivity.GameState.HIGHSCORE;
                Log.i("Gamestate", "" + temp);
            } else {
                gameState = MainActivity.GameState.READY;
                Log.i("Gamestate", "" + temp);
            }
        }
    }

    public void MasukNama() {
        String namaMasukan = "";
        namaMasukan = editText.getText().toString();
        hm.addScore(namaMasukan, mSkor);
        System.out.print(hm.getHighscoreString());
        textView.setText(hm.getHighscoreString());
       // Log.i("Minimum score", hm.getMinNumberCanEnterTopTen() + "");

    }

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_ten, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //test
        hm = new HighscoreManager(mcontex);
       /* hm.addScore("Bart",240);
        hm.addScore("Marge", 300);
        hm.addScore("Maggie",220);
        hm.addScore("Homer", 100);
        */
       // hm.addScore("Lisa", 0);

        System.out.print(hm.getHighscoreString());

        textView = (TextView)getView().findViewById(R.id.textView);
        textView.setText(hm.getHighscoreString());

        editText = (EditText)getView().findViewById(R.id.editTextMasukNama);
        buttonCancel = (Button)getView().findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);
        buttonOK = (Button)getView().findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(this);

        //
        if (gameState == MainActivity.GameState.HIGHSCORE) {
            editText.setVisibility(View.VISIBLE);
            buttonOK.setVisibility(View.VISIBLE);
            buttonCancel.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.INVISIBLE);
            buttonOK.setVisibility(View.INVISIBLE);
            buttonCancel.setText("Kembali");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        whileTopTenIsShownListener = null;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCancel:
                goBackToCallingActivity();
                break;
            case R.id.buttonOK:
                InputMethodManager inputMethodManager = (InputMethodManager)
                      getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                MasukNama();
                goBackToCallingActivity();
                break;
        }
    }


    public void goBackToCallingActivity() {
        if (whileTopTenIsShownListener != null) {

            whileTopTenIsShownListener.setGameState(MainActivity.GameState.READY);
        }
        getFragmentManager().popBackStack();
    }

    WhileTopTenIsShownListener whileTopTenIsShownListener;

    public interface WhileTopTenIsShownListener {
        public void setGameState(MainActivity.GameState gameState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            whileTopTenIsShownListener = (WhileTopTenIsShownListener)context;
            whileTopTenIsShownListener.setGameState(MainActivity.GameState.READY);
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " tiada TopTenListener");
        }
    }
}
