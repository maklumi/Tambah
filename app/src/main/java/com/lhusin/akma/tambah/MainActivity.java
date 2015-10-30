package com.lhusin.akma.tambah;

import android.graphics.drawable.Drawable;
import android.media.Image;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

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
                Snackbar.make(view, "Mula!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mulakanMain(fab);
            }
        });


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

    }

    public void mulakanMain(View v) {
        fab.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);
        textViewSalah.setText("0");
        textViewBetul.setText("0");
        buttonTag1.setEnabled(true);
        buttonTag2.setEnabled(true);
        buttonTag3.setEnabled(true);
        buttonTag4.setEnabled(true);
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
                buttonTag1.setEnabled(false);
                buttonTag2.setEnabled(false);
                buttonTag3.setEnabled(false);
                buttonTag4.setEnabled(false);
            }
        }.start();
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
            imageButtonBetul.animate().rotation(360f);
            imageButtonBetul.getDrawable();

        } else {

          //  Toast.makeText(getApplicationContext(),"tak", Toast.LENGTH_SHORT).show();
            textViewSalah.setText(Integer.toString(bilanganSoalan-skor));
            imageButtonSalah.animate().rotation(360f);
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
}
