package com.lhusin.akma.tambah;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    TextView textViewSoalan;
    TextView textViewBetul;
    TextView textViewSalah;
    Button buttonTag1, buttonTag2, buttonTag3, buttonTag4;
    GridLayout gridLayoutPilihanJawapan;
    ArrayList<Integer> pilihanJawapan = new ArrayList<Integer>();
    int tagBetul;
    int skor = 0;
    int bilanganSoalan;
    int bilanganSalah = 0;
    private ProgressBar progressBar;
    ImageButton imageButtonBetul, imageButtonSalah;
    FloatingActionButton fab;
    String heart="";


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        textViewSoalan = (TextView)findViewById(R.id.textViewSoalan);
        buttonTag1 = (Button)findViewById(R.id.buttonTag1);
        buttonTag2 = (Button)findViewById(R.id.buttonTag2);
        buttonTag3 = (Button)findViewById(R.id.buttonTag3);
        buttonTag4 = (Button)findViewById(R.id.buttonTag4);
        gridLayoutPilihanJawapan = (GridLayout)findViewById(R.id.gridLayoutPilihanJawapan);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        imageButtonBetul = (ImageButton)findViewById(R.id.imageButtonBetul);
        imageButtonSalah = (ImageButton)findViewById(R.id.imageButtonSalah);
        textViewBetul = (TextView)findViewById(R.id.textViewBetul);
        textViewSalah = (TextView)findViewById(R.id.textViewSalah);
        heart = new String(new int[] {0x2764}, 0,1);
    }


    public void mulakanMain(View v) {
        fab.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);
        textViewSalah.setText("0");
        textViewBetul.setText("0");
        stateButton(true);
        textViewSalah.setText(new String(new char[5]).replace("\0", heart));
        skor = 0;
        bilanganSoalan =0;

        rekaSoalan();

        //countdown
        new CountDownTimer(10200,100){
            public void onTick(long millisUntilFinished) {

                progressBar.incrementProgressBy(1);
            }

            public void onFinish() {

                fab.setVisibility(View.VISIBLE);
                progressBar.setProgress(progressBar.getMax());
                stateButton(false);
            }
        }.start();
    }

    private void stateButton(boolean enabled) {
        buttonTag1.setEnabled(enabled);
        buttonTag2.setEnabled(enabled);
        buttonTag3.setEnabled(enabled);
        buttonTag4.setEnabled(enabled);
    }

    private void rekaSoalan() {
        int angka = 11;
        int bilTag = 4;

        int p,q,jawapanBetul;
        Random rawak = new Random();
        p = rawak.nextInt(angka);
        q = rawak.nextInt(angka);
        jawapanBetul = p+q;

        textViewSoalan.setText(p + " + " + q);

        tagBetul = rawak.nextInt(bilTag);

        int jawapanSalah;

        pilihanJawapan.clear();

        for (int i = 0; i < bilTag; i++) {
            if (i == tagBetul) { //random placement of true answer

                pilihanJawapan.add(jawapanBetul);
            }

            do {
                jawapanSalah = rawak.nextInt(angka*2-1);
            } while (jawapanBetul == jawapanSalah);

            pilihanJawapan.add(jawapanSalah);

        }

        buttonTag1.setText(Integer.toString(pilihanJawapan.get(0)));
        buttonTag2.setText(Integer.toString(pilihanJawapan.get(1)));
        buttonTag3.setText(Integer.toString(pilihanJawapan.get(2)));
        buttonTag4.setText(Integer.toString(pilihanJawapan.get(3)));
    }

    public void menjawap(View v) {
        if (v.getTag().toString().equals(Integer.toString(tagBetul))) {
            skor++;

            // Toast.makeText(getApplicationContext(), "Betul", Toast.LENGTH_SHORT).show();
            textViewBetul.setText(Integer.toString(skor));


        } else {

            //  Toast.makeText(getApplicationContext(),"tak", Toast.LENGTH_SHORT).show();
            // textViewSalah.setText(Integer.toString(bilanganSoalan - skor));
            if ((bilanganSoalan - skor ) < 6 ) {
                textViewSalah.setText(new String(new char[5-(bilanganSoalan-skor)]).replace("\0", heart));
            } else {
                //habis heart
                stateButton(false);
                Log.i("salah", "" + (bilanganSoalan - skor));
                textViewSalah.setText("Habis!");
            }

        }
        bilanganSoalan++;
        rekaSoalan();

    }

}
