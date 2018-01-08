package com.toposdeus.personajesmexicanos;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class Estadisticas extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final int RC_LEADERBOARD_UI = 5001;
    private static final int RC_UNUSED = 5001;
    TextView txtmarcador, niveles, pistas, pistasreve, pistasresolv, pistaspreg, pintento, tit;
    Button atras, tabla;
    int nivelescompletados, marcador, perfectos, lineas, pista, preguntar, resolv;
    GoogleApiClient googleapiClient = null;
    private AdView mBottomBanner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);
        tabla = (Button) findViewById(R.id.tablapos);
        tabla.setOnClickListener(this);
        txtmarcador = (TextView) findViewById(R.id.emarcador);
        txtmarcador.setTypeface(Metodos.fuente(this));
        niveles = (TextView) findViewById(R.id.enivelescompletos);
        niveles.setTypeface(Metodos.fuente(this));
        tit = (TextView) findViewById(R.id.titest);
        tit.setTypeface(Metodos.fuente(this));
        pistas = (TextView) findViewById(R.id.epistas);
        pistas.setTypeface(Metodos.fuente(this));
        pistasreve = (TextView) findViewById(R.id.epistasrevelar);
        pistasreve.setTypeface(Metodos.fuente(this));
        pistaspreg = (TextView) findViewById(R.id.epistaspreguntar);
        pistaspreg.setTypeface(Metodos.fuente(this));
        pintento = (TextView) findViewById(R.id.eprimerintento);
        pintento.setTypeface(Metodos.fuente(this));
        pistasresolv = (TextView) findViewById(R.id.epistasresolv);
        pistasresolv.setTypeface(Metodos.fuente(this));
        atras = (Button) findViewById(R.id.botonatras);
        atras.setOnClickListener(this);
        nivelescompletados = 0;

        mBottomBanner = (AdView) findViewById(R.id.av_bottom_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mBottomBanner.loadAd(adRequest);

        googleapiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .enableAutoManage(this, this).build();


        preguntar = Metodos.Cargarint(this, "" + getString(R.string.preguntar));
        marcador = Metodos.Cargarint(this, getString(R.string.marcador));
        resolv = Metodos.Cargarint(this, getString(R.string.pistaresolv));
        perfectos = 0;
        for (int i = 0; i <= 8; i++) {
            int niveles = 0;
            for (int q = 0; q <= 14; q++) {
                if (Metodos.Cargarboolean(this, "" + i + q + R.string.revelar)) {
                    lineas++;
                }
                if (Metodos.Cargarboolean(this, "" + i + q + R.string.pista)) {
                    pista++;
                }
                if (Metodos.Cargarboolean(this, "" + i + q + R.string.quizresuelto)) {
                    niveles++;
                    if (Metodos.Cargarint(this, "" + i + q + getString(R.string.intento)) == 0) {
                        perfectos++;
                    }
                }
                if (niveles == 14) {
                    nivelescompletados++;
                }
            }
        }
      /*  contestada = Metodos.Cargarboolean(this, "" + nivel + quiz + R.string.quizresuelto);
        pistarevelar = Metodos.Cargarboolean(this, "" + nivel + "" + quiz + getString(R.string.revelar));
        SharedPreferences sharedPref;
        sharedPref = this.getSharedPreferences("" + nivel + quiz + getString(R.string.puntos), Context.MODE_PRIVATE);
        puntos = sharedPref.getInt("" + nivel + quiz + getString(R.string.puntos), puntosiniciales);
        coin = Metodos.Cargarint(this, "" + getString(R.string.coin));
        xp = Metodos.Cargarint(this, "" + getString(R.string.xp));
        pistatexto = Metodos.Cargarboolean(this, "" + nivel + "" + quiz + getString(R.string.pista));
        intentos1 = Metodos.Cargarint(this, "" + nivel + quiz + getString(R.string.intento));
        intentos2 = Metodos.Cargarint(this, "" + nivel + quiz + getString(R.string.intento2));*/
        txtmarcador.setText("" + marcador);
        pintento.setText("" + perfectos);
        pistasresolv.setText("" + resolv);
        niveles.setText("" + nivelescompletados);
        pistas.setText("" + pista);
        pistaspreg.setText("" + preguntar);
        pistasreve.setText("" + lineas);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.botonatras:
                Animation mover2 = AnimationUtils
                        .loadAnimation(this, R.anim.mover1);
                atras.startAnimation(mover2);
                Intent iatras = new Intent(this, PantallaPrincipal.class);
                startActivity(iatras);
                finish();
                break;
            case R.id.tablapos:
                if (googleapiClient.isConnected()) {
                    if (googleapiClient != null) {
                        showLeaderboard();

                    } else {
                        Metodos.creartoast(this, getLayoutInflater().inflate(
                                R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), " no estas conectado ");
                    }
                }
                break;


        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Metodos.preferenciasonido(this, R.raw.click);
            Metodos.preferenciavibrar(this, 50);
            Intent i = new Intent(this, PantallaPrincipal.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void showLeaderboard() {
       int xp = Metodos.Cargarint(this, "" + getString(R.string.xp));
        long punt = xp;
        Games.Leaderboards.submitScore(googleapiClient,getString(R.string.leaderboard_conocedores_de_la_fama),punt);
        //startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
          //      googleapiClient, getString(R.string.leaderboard_conocedores_de_la_fama)), RC_LEADERBOARD_UI);
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                googleapiClient, getString(R.string.leaderboard_conocedores_de_la_fama)),
                2);
    }


    public void onDestroy() {

        super.onDestroy();

        if (mBottomBanner != null) {
            mBottomBanner.destroy();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBottomBanner != null) {
            mBottomBanner.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBottomBanner != null) {
            mBottomBanner.pause();
        }
    }
}
