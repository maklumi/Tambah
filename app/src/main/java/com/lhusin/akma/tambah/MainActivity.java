package com.lhusin.akma.tambah;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textViewSoalan;
    TextView textViewBetul, textViewLabelBetul;
    TextView textViewSalah;
    Button buttonTag1, buttonTag2, buttonTag3, buttonTag4;
    GridLayout gridLayoutPilihanJawapan;
    ArrayList<Integer> pilihanJawapan = new ArrayList<Integer>();
    int tagBetul;
    int skor = 0;
    int bilanganSoalan;
    int bilanganSalah = 0;
    ProgressBar progressBar;
    ImageButton imageButtonBetul, imageButtonSalah;
    FloatingActionButton fab;
    String heart="";
    int lifeheart = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Mula!", Snackbar.LENGTH_SHORT).show();
                        //.setAction("Action", null).show();
                mulakanMain();
            }
        });


        textViewSoalan = (TextView)findViewById(R.id.textViewSoalan);
        buttonTag1 = (Button)findViewById(R.id.buttonTag1);
        buttonTag2 = (Button)findViewById(R.id.buttonTag2);
        buttonTag3 = (Button)findViewById(R.id.buttonTag3);
        buttonTag4 = (Button)findViewById(R.id.buttonTag4);
        gridLayoutPilihanJawapan = (GridLayout)findViewById(R.id.gridLayoutPilihanJawapan);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        textViewLabelBetul = (TextView)findViewById(R.id.textViewLabelBetul);
        textViewBetul = (TextView)findViewById(R.id.textViewBetul);
        textViewSalah = (TextView)findViewById(R.id.textViewSalah);
        heart = new String(new int[] {0x2764}, 0,1);


           }

    public void mulakanMain() {
        fab.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);
        textViewBetul.setText("0");
        stateButton(true);
        textViewSalah.setText(new String(new char[lifeheart]).replace("\0", heart));
        skor = 0;
        bilanganSoalan =1;

        rekaSoalan();

        new CountDownTimer(10200,100){

            public void onTick(long millisUntilFinished) {

                progressBar.incrementProgressBy(1);
            }
            public void onFinish() {
                bilaTamat();
            }
        }.start();
    }

    public void bilaTamat() {
        fab.setVisibility(View.VISIBLE);
        progressBar.setProgress(progressBar.getMax());
        stateButton(false);
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
            textViewBetul.setText(Integer.toString(skor));
        } else {
        if ((bilanganSoalan - skor ) < lifeheart +1 ) {
                textViewSalah.setText(new String(new char[lifeheart-(bilanganSoalan-skor)]).replace("\0", heart));
            } else {
              bilaTamat();
            }
        }
        bilanganSoalan++;
        rekaSoalan();

    }

       @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setEnabled(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bilaTamat();
    }
}
