package com.toposdeus.personajesmexicanos;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;


public class Opciones extends FragmentActivity implements OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    CheckBox sonido, vibrar;
    Button botonatras, reestablecer, instrucciones, botoncomparte, botoncalifica;
    TextView textoopciones;
    GoogleApiClient googleapiClient = null;
    int comparte;
    private ProgressDialog progressDialog;
    private AdView mBottomBanner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones);
        textoopciones = (TextView) findViewById(R.id.textopciones);
        textoopciones.setTypeface(Metodos.fuente(this));
        sonido = (CheckBox) findViewById(R.id.checkboxsonido);
        sonido.setOnClickListener(this);
        sonido.setTypeface(Metodos.fuente(this));
        vibrar = (CheckBox) findViewById(R.id.checkboxvibrar);
        vibrar.setOnClickListener(this);
        vibrar.setTypeface(Metodos.fuente(this));
        reestablecer = (Button) findViewById(R.id.botonreestablecer);
        reestablecer.setOnClickListener(this);
        botoncalifica = (Button) findViewById(R.id.botoncalifica);
        botoncalifica.setOnClickListener(this);
        botoncomparte = (Button) findViewById(R.id.botoncomparte);
        botoncomparte.setOnClickListener(this);
        botoncomparte.setTypeface(Metodos.fuente(this));
        instrucciones = (Button) findViewById(R.id.botoncomojugar);
        instrucciones.setOnClickListener(this);
        instrucciones.setTypeface(Metodos.fuente(this));
        reestablecer.setTypeface(Metodos.fuente(this));
        botoncalifica.setTypeface(Metodos.fuente(this));
        botonatras = (Button) findViewById(R.id.botonatras);
        botonatras.setOnClickListener(this);
        mBottomBanner = (AdView) findViewById(R.id.av_bottom_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mBottomBanner.loadAd(adRequest);
        sonido.setChecked(Metodos.Cargarboolean(this, getString(R.string.prefsonidostring)));
        vibrar.setChecked(Metodos.Cargarboolean(this, getString(R.string.prefvibrarstring)));
        comparte = Metodos.Cargarint(this, "" + getString(R.string.comparte));


        googleapiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .enableAutoManage(this, this).build();


        if (sonido.isChecked() == true) {
            sonido.setText("sonido");
        } else {
            sonido.setText("sin sonido");
        }
        if (vibrar.isChecked() == true) {
            vibrar.setText("vibrar");
        } else {
            vibrar.setText("sin vibrar");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.checkboxsonido:
                if (sonido.isChecked() == true) {
                    sonido.setText(" sonido");
                }
                if (sonido.isChecked() == false) {
                    sonido.setText("sin sonido");
                }
                Metodos.Guardarboolean(this, sonido.isChecked(), getString(R.string.prefsonidostring));
                break;
            case R.id.checkboxvibrar:
                if (vibrar.isChecked() == true) {
                    vibrar.setText(" vibrar");
                }
                if (vibrar.isChecked() == false) {
                    vibrar.setText("sin vibrar");
                }
                Metodos.Guardarboolean(this, vibrar.isChecked(), getString(R.string.prefvibrarstring));
                break;
            case R.id.botonatras:
                Animation mover2 = AnimationUtils
                        .loadAnimation(this, R.anim.mover1);
                botonatras.startAnimation(mover2);
                Intent iatras = new Intent(this, PantallaPrincipal.class);
                startActivity(iatras);
                finish();
                break;

            case R.id.botoncomojugar:
                MenuNiveles.instrucciones(this, getLayoutInflater());
                break;
            case R.id.botoncomparte:

                if (googleapiClient != null) {
                    if (googleapiClient.isConnected()) {
                        Games.Achievements.increment(googleapiClient, getString(R.string.achievement_compartenos), 1);
                    }
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Descubre que tanto sabes de los personajes mexicanos mas famosos  " + Html.fromHtml("<br />") + "https://play.google.com/store/apps/details?id=com.toposdeus.personajesmexicanos");
                startActivity(Intent.createChooser(intent, "Comparte la App"));
                comparte++;
                Metodos.Guardarint(this, comparte, "" + getString(R.string.comparte));
                break;
            case R.id.botonreestablecer:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View vi = inflater.inflate(R.layout.dialogoconfirm, null);
                builder.setView(vi);
                final AlertDialog dialog = builder.create();
                //decidir despues si sera cancelable o no
                dialog.setCancelable(true);
                LinearLayout linear = (LinearLayout) vi.findViewById(R.id.confirmfondo);
                TextView txtconfirm = (TextView) vi.findViewById(R.id.txtconfirm);
                txtconfirm.setTypeface(Metodos.fuente(this));
                Button botonsi = (Button) vi.findViewById(R.id.botonsi);
                botonsi.setTypeface(Metodos.fuente(this));
                botonsi.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                progressDialog = new ProgressDialog(Opciones.this);
                                progressDialog.setMessage("Borrando");
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                if (googleapiClient != null) {
                                    if (googleapiClient.isConnected()) {
                                        if (Metodos.Cargarint(Opciones.this, getString(R.string.marcador)) > 50) {
                                            Games.Achievements.unlock(googleapiClient, getString(R.string.achievement_estas_demente));
                                        } else {
                                            Games.Achievements.unlock(googleapiClient, getString(R.string.achievement_volviendo_a_empezar));
                                        }
                                    }
                                }
                                borrar();
                                progressDialog.dismiss();
                            }
                        }
                );
                Button botonno = (Button) vi.findViewById(R.id.botonno);
                botonno.setTypeface(Metodos.fuente(this));
                botonno.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();

                            }
                        }
                );
                dialog.show();
                break;
            case (R.id.botoncalifica):
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.toposdeus.personajesmexicanos");
                Intent intento = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intento);
                break;
        }
        Metodos.preferenciasonido(this, R.raw.click);
        Metodos.preferenciavibrar(this, 50);
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

    private void borrar() {

        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();
        SharedPreferences sharedPref;
        sharedPref = this.getSharedPreferences(getString(R.string.puntuacion)
                , Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
        sharedPref = this.getSharedPreferences(getString(R.string.marcador)
                , Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
        sharedPref = this.getSharedPreferences(getString(R.string.marcadorperf)
                , Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
        sharedPref = this.getSharedPreferences("" + getString(R.string.coin)
                , Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
        sharedPref = this.getSharedPreferences("" + getString(R.string.xp)
                , Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
        sharedPref = this.getSharedPreferences("" + getString(R.string.preguntar)
                , Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();

        for (int i = 0; i <= 8; i++) {
            for (int q = 0; q <= 14; q++) {
                sharedPref = this.getSharedPreferences("" + i + q + R.string.quizresuelto
                        , Context.MODE_PRIVATE);
                sharedPref.edit().clear().commit();
                sharedPref = this.getSharedPreferences("" + i + q + getString(R.string.revelar)
                        , Context.MODE_PRIVATE);
                sharedPref.edit().clear().commit();
                sharedPref = this.getSharedPreferences("" + i + q + getString(R.string.pista)
                        , Context.MODE_PRIVATE);
                sharedPref.edit().clear().commit();
                sharedPref = this.getSharedPreferences("" + i + q + getString(R.string.puntos)
                        , Context.MODE_PRIVATE);
                sharedPref.edit().clear().commit();
                sharedPref = this.getSharedPreferences("" + i + q + getString(R.string.intento)
                        , Context.MODE_PRIVATE);
                sharedPref.edit().clear().commit();
                sharedPref = this.getSharedPreferences("" + i + q + getString(R.string.intento2)
                        , Context.MODE_PRIVATE);
                sharedPref.edit().clear().commit();

            }
        }
        Metodos.creartoast(this, getLayoutInflater().inflate(
                R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), "hecho");

    }


    public void onDestroy() {

        super.onDestroy();
        if (googleapiClient.isConnected())
            googleapiClient.disconnect();
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onStart() {
        super.onStart();
        if (googleapiClient != null) {
            googleapiClient.connect();
        }
    }
}
