package com.toposdeus.personajesmexicanos;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.DisplayMetrics;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


public class Quiz extends FragmentActivity implements OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    final static int costocontestar = 500, costorevelar = 300, costopista = 300, costopreguntar = 10, puntosiniciales = 50;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    TextView txtcoin, recompensa, txtXP;
    Button botonpista, botonresolver, botonatras, botonrevelar, botonpreguntar,
            btn0, btn1, btn2, bnt3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13,
            btn14, btn15, btn16, btn17, btn18, btn19, btn20, btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28;
    ImageView imagen;
    int quiz, nivel, numletras, preguntar, numbtn, pistaresolv;
    String pistarayita = "", Nombre = "", Pista = "";

    int puntos, coin, xp, marcador, posicionbtn, cont = 0, intentos1, intentos2, marcadorperf;
    boolean pistarevelar, pistatexto, contestada;
    int[] imagenes = {R.array.imagenesnivel1, R.array.imagenesnivel2, R.array.imagenesnivel3, R.array.imagenesnivel4,
            R.array.imagenesnivel5, R.array.imagenesnivel6, R.array.imagenesnivel7, R.array.imagenesnivel8, R.array.imagenesnivel9, R.array.imagenesnivel10};
    int[] pistasnombres = {R.array.pistanombresnivel1, R.array.pistanombresnivel2, R.array.pistanombresnivel3, R.array.pistanombresnivel4,
            R.array.pistanombresnivel5, R.array.pistanombresnivel6, R.array.pistanombresnivel7, R.array.pistanombresnivel8,
            R.array.pistanombresnivel9, R.array.pistanombresnivel1,};
    int[] pistas = {R.array.pistasnivel1, R.array.pistasnivel2, R.array.pistasnivel3, R.array.pistasnivel4,
            R.array.pistasnivel5, R.array.pistasnivel6, R.array.pistasnivel7, R.array.pistasnivel8,
            R.array.pistasnivel9, R.array.pistasnivel1,};
    int[] nombres = {R.array.nombresnivel1, R.array.nombresnivel2, R.array.nombresnivel3, R.array.nombresnivel4,
            R.array.nombresnivel5, R.array.nombresnivel6, R.array.nombresnivel7, R.array.nombresnivel8,
            R.array.nombresnivel9, R.array.nombresnivel1,};
    Button[] botonesup = {btn0, btn1, btn2, bnt3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12,
            btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20, btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28},
            botonesdown = {btn0, btn1, btn2, bnt3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11,
                    btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20, btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28};
    int[] listaid;
    String[] letras;
    String[] comparador;
    String resp = "";
    InterstitialAd mInterstitialAd;
    GoogleApiClient googleapiClient = null;
    LinearLayout layoutbtn1, layoutbtn1down, layoutbtn2down;
    private AdView mAdView;
    private ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
        cargardatospregunta();

        googleapiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .enableAutoManage(this, this).build();

        mAdView = (AdView) findViewById(R.id.av_bottom_banner);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        AdView adViewban = new AdView(this);
        adViewban.setAdSize(AdSize.BANNER);
        adViewban.setAdUnitId(getString(R.string.adbannerid));

        adViewban = (AdView) findViewById(R.id.av_bottom_banner);
        AdRequest adRequestban = new AdRequest.Builder().build();
        mAdView.loadAd(adRequestban);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.adinterid));

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
            }
        });

        txtcoin = (TextView) findViewById(R.id.txtcoin);
        txtcoin.setTypeface(Metodos.fuente(this));
        txtcoin.setText("" + coin);
        txtXP = (TextView) findViewById(R.id.txtXP);
        txtXP.setTypeface(Metodos.fuente(this));
        txtXP.setText("" + xp);
        recompensa = (TextView) findViewById(R.id.recompensa);
        recompensa.setTypeface(Metodos.fuente(this));
        recompensa.setText("" + (puntosiniciales - 5 * intentos1));
        botonpista = (Button) findViewById(R.id.botonpista);
        botonatras = (Button) findViewById(R.id.botonatras);
        botonrevelar = (Button) findViewById(R.id.botonrevelar);
        botonresolver = (Button) findViewById(R.id.botonresolver);
        botonpreguntar = (Button) findViewById(R.id.botonpreguntar);
        imagen = (ImageView) findViewById(R.id.imagenvista);
        botonpista.setOnClickListener(this);
        botonatras.setOnClickListener(this);
        botonrevelar.setOnClickListener(this);
        botonresolver.setOnClickListener(this);
        botonpreguntar.setOnClickListener(this);

        Resources res = this.getResources();
        Nombre = res.getStringArray(nombres[nivel])[quiz];
        Pista = res.getStringArray(pistas[nivel])[quiz];
        pistarayita = res.getStringArray(pistasnombres[nivel])[quiz];
        imagen.setImageResource(getResources().obtainTypedArray(
                imagenes[nivel]).getResourceId(quiz, 1));

        String nombretemp = Nombre.replace(" ", "");
        numletras = Nombre.length();
        if (numletras < 14) {
            numbtn = nombretemp.length() + (14 - nombretemp.length());
            letras = new String[numbtn];
            String[] letrasrandom = {"a", "u", "c", "d", "e", "f", "g", "h", "i", "o", "x", "p", "f", "g", "h", "i", "o", "x", "p"};
            Collections.shuffle(Arrays.asList(letrasrandom));
            for (int i = nombretemp.length(); i < numbtn; i++) {
                letras[i] = letrasrandom[i];
            }
        } else {
            numbtn = nombretemp.length();
            letras = new String[numbtn];
        }
        for (int i = 0; i < nombretemp.length(); i++) {
            letras[i] = String.valueOf(nombretemp.charAt(i));

        }
        listaid = new int[numletras];
        comparador = new String[numletras];
        Collections.shuffle(Arrays.asList(letras));
        layoutbtn1 = (LinearLayout) findViewById(R.id.layoutbotones1);
        layoutbtn1down = (LinearLayout) findViewById(R.id.layoutbotones1abajo);
        layoutbtn2down = (LinearLayout) findViewById(R.id.layoutbotones2abajo);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        for (int i = 0; i <= (numletras / 8); i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 8; j++) {
                if ((j + (i * 8)) < numletras) {
                    final Button btnTag = new Button(this);
                    btnTag.setLayoutParams(new LinearLayout.LayoutParams((metrics.widthPixels / (9)),
                            (metrics.widthPixels / (9))));
                    //btnTag.setText( " " + Nombre.charAt( (j + (i * 7))));
                    btnTag.setId(j + (i * 8));
                    final int finalI = j + (i * 8);
                    btnTag.setBackgroundResource(R.drawable.boton2);
                    btnTag.setTextColor(Color.WHITE);
                    botonesup[j + (i * 8)] = btnTag;
                    btnTag.setTypeface(Metodos.fuente(this));
                    if (String.valueOf(Nombre.charAt(j + (i * 8))).equals(" ")) {
                        comparador[j + (i * 8)] = " ";
                        cont = +1;
                        btnTag.setText(" ");
                        btnTag.setAlpha(0);
                    }
                    if (contestada) {
                        btnTag.setText(String.valueOf(Nombre.charAt(j + (i * 8))));
                    } else {

                        btnTag.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Metodos.preferenciasonido(Quiz.this, R.raw.click);
                                Metodos.preferenciavibrar(Quiz.this, 25);
                                quitarboton(btnTag, finalI);
                            }
                        });
                        btnTag.setClickable(false);
                    }
                    row.addView(btnTag);
                }
            }
            layoutbtn1.addView(row);
        }

        if (!contestada) {
            for (int i = 0; i <= (numbtn / 8); i++) {
                LinearLayout row = new LinearLayout(this);
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < 8; j++) {
                    if ((j + (i * 8)) < numbtn) {
                        final Button btnTag = new Button(this);
                        if (!String.valueOf(letras[(j + (i * 8))]).equals(" ")) {
                            btnTag.setLayoutParams(new LinearLayout.LayoutParams((metrics.widthPixels / (9)),
                                    (metrics.widthPixels / (9))));
                            btnTag.setText(" " + letras[(j + (i * 8))]);
                            btnTag.setClickable(false);
                            btnTag.setBackgroundResource(R.drawable.boton2);
                            btnTag.setId(j + (i * 8));
                            btnTag.setTextColor(Color.WHITE);
                            btnTag.setTypeface(Metodos.fuente(this));
                            final int finalI = j + (i * 8);
                            botonesdown[j + (i * 8)] = btnTag;
                            btnTag.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Metodos.preferenciasonido(Quiz.this, R.raw.click);
                                    Metodos.preferenciavibrar(Quiz.this, 25);
                                    agregarboton(btnTag, finalI);
                                }
                            });
                        }
                        row.addView(btnTag);
                    }
                }
                layoutbtn1down.addView(row);

            }
        }
        if (pistatexto == true) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, getResources().getDrawable(R.mipmap.pistabuyback));
            stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, getResources().getDrawable(R.mipmap.pistabuy));
            botonpista.setBackground(stateListDrawable);

        }
        if (pistarevelar == true) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, getResources().getDrawable(R.mipmap.pista2buyback));
            stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, getResources().getDrawable(R.mipmap.pista2buy));
            botonrevelar.setBackground(stateListDrawable);

        }
        LinearLayout.LayoutParams layoutParams = null;
        if (contestada == true) {
            botonpreguntar.setEnabled(false);
            recompensa.setText("");
            botonrevelar.setEnabled(false);
            botonpista.setEnabled(false);
            botonresolver.setEnabled(false);
            layoutParams = new LinearLayout.LayoutParams((metrics.widthPixels / 3) * 2, (metrics.widthPixels / 3) * 2);

        } else {
            layoutParams = new LinearLayout.LayoutParams((metrics.widthPixels / 2), (metrics.widthPixels / 2));
        }
        imagen.setLayoutParams(layoutParams);
        Animation entrada = AnimationUtils.loadAnimation(this,
                R.anim.entradaquiz);
        imagen.startAnimation(entrada);
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        layoutbtn1down.addView(adView);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Metodos.preferenciasonido(this, R.raw.click);
            Metodos.preferenciavibrar(this, 50);
            Animation mover2 = AnimationUtils
                    .loadAnimation(this, R.anim.mover1);
            botonatras.startAnimation(mover2);
            regresarnivelanterior();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        Metodos.preferenciasonido(this, R.raw.click);
        Metodos.preferenciavibrar(this, 50);
        switch (v.getId()) {

            case R.id.botonpista:
                if (pistatexto == true) {
                    dialogopista(Pista, true);
                } else {
                    dialogoconfirm("Deseas obtener informacion de la imagen por " + costopista + " monedas", 2);
                }
                break;
            case R.id.botonresolver:
                dialogoconfirm("Deseas resolver la imagen por " + costocontestar + " monedas", 0);
                break;
            case R.id.botonrevelar:
                if (pistarevelar == true) {
                    dialogopista(pistarayita, false);
                } else
                    dialogoconfirm("Deseas revelar algunas letras por " + costorevelar + " monedas", 1);

                break;
            case R.id.botonatras:
                Animation mover2 = AnimationUtils
                        .loadAnimation(this, R.anim.mover1);
                botonatras.startAnimation(mover2);
                guardardatospregunta();
                regresarnivelanterior();
                break;
            case R.id.botonpreguntar:
                dialogoconfirm("pregunta a tus amigos por " + costopreguntar + " monedas", 3);
                break;
        }
    }


    private void regresarnivelanterior() {
        guardardatospregunta();
        for (int i = 0; i < numletras; i++) {
            botonesdown[i] = null;
            botonesup[i] = null;
        }
        imagen = null;
        startActivity(new Intent(getBaseContext(), Nivel.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");
        progressDialog.setCancelable(false);
        progressDialog.show();
        finish();
    }

    private void avanzarnivel() {
        boolean verif;
        for (int i = (quiz + 1); i <= 15; i++) {
            if (i == (15)) {
                regresarnivelanterior();
            } else {
                verif = Metodos.Cargarboolean(this, "" + nivel + i + R.string.quizresuelto);
                if (!verif) {
                    Metodos.Guardarint(Quiz.this, i, getString(R.string.quiz));
                    startActivity(new Intent(getBaseContext(), Quiz.class));
                    finish();
                    break;
                }
            }
        }
    }

    public void cargardatospregunta() {
        coin = Metodos.Cargarint(this, "" + getString(R.string.coin));
        xp = Metodos.Cargarint(this, "" + getString(R.string.xp));
        quiz = Metodos.Cargarint(this, getString(R.string.quiz));
        nivel = Metodos.Cargarint(this, getString(R.string.nivel));
        marcador = Metodos.Cargarint(this, getString(R.string.marcador));
        pistaresolv = Metodos.Cargarint(this, getString(R.string.pistaresolv));
        marcadorperf = Metodos.Cargarint(this, "" + getString(R.string.marcadorperf));
        contestada = Metodos.Cargarboolean(this, "" + nivel + quiz + R.string.quizresuelto);
        pistarevelar = Metodos.Cargarboolean(this, "" + nivel + quiz + getString(R.string.revelar));
        SharedPreferences sharedPref;
        sharedPref = this.getSharedPreferences("" + nivel + quiz + getString(R.string.puntos), Context.MODE_PRIVATE);
        puntos = sharedPref.getInt("" + nivel + quiz + getString(R.string.puntos), puntosiniciales);
        preguntar = Metodos.Cargarint(this, "" + getString(R.string.preguntar));
        pistatexto = Metodos.Cargarboolean(this, "" + nivel + quiz + getString(R.string.pista));
        intentos1 = Metodos.Cargarint(this, "" + nivel + quiz + getString(R.string.intento));
        intentos2 = Metodos.Cargarint(this, "" + nivel + quiz + getString(R.string.intento2));

    }

    public void guardardatospregunta() {
        Metodos.Guardarboolean(this, pistarevelar, "" + nivel + quiz + getString(R.string.revelar));
        Metodos.Guardarint(this, coin, "" + getString(R.string.coin));
        Metodos.Guardarint(this, puntos, "" + nivel + quiz + getString(R.string.puntos));
        Metodos.Guardarboolean(this, pistatexto, "" + nivel + quiz + getString(R.string.pista));
        Metodos.Guardarboolean(this, contestada, "" + nivel + quiz + R.string.quizresuelto);
        Metodos.Guardarint(this, xp, getString(R.string.xp));
        Metodos.Guardarint(this, pistaresolv, getString(R.string.pistaresolv));
        Metodos.Guardarint(this, preguntar, getString(R.string.preguntar));
        Metodos.Guardarint(this, marcadorperf, getString(R.string.marcadorperf));
        Metodos.Guardarint(this, marcador, getString(R.string.marcador));
        Metodos.Guardarint(this, intentos1, "" + nivel + quiz + getString(R.string.intento));
        Metodos.Guardarint(this, intentos2, "" + nivel + quiz + getString(R.string.intento2));
        //Metodos.Guardarint(this, recompensa, "" + nivel + quiz + getString(R.string.puntosiniciales));
    }

    private void dialogoconfirm(String tit, final int caso) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogoconfirm, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        //decidir despues si sera cancelable o no
        dialog.setCancelable(true);
        LinearLayout linear = (LinearLayout) vi.findViewById(R.id.confirmfondo);
        TextView txtconfirm = (TextView) vi.findViewById(R.id.txtconfirm);
        txtconfirm.setText(tit);
        txtconfirm.setTypeface(Metodos.fuente(this));
        Button botonsi = (Button) vi.findViewById(R.id.botonsi);
        botonsi.setTypeface(Metodos.fuente(this));
        botonsi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (caso) {
                            case 0:
                                //resolver
                                if (coin >= costocontestar) {
                                    pistaresolv++;
                                    layoutbtn1.removeAllViews();
                                    contestada = true;
                                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                                    coin = coin - costocontestar;
                                    botonresolver.setEnabled(false);
                                    txtcoin.setText("" + coin);
                                    for (int i = 0; i <= (numletras / 8); i++) {
                                        LinearLayout row = new LinearLayout(Quiz.this);
                                        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT));

                                        for (int j = 0; j < 8; j++) {
                                            if ((j + (i * 8)) < numletras) {
                                                final Button btnTag = new Button(Quiz.this);
                                                btnTag.setLayoutParams(new LinearLayout.LayoutParams((metrics.widthPixels / (9)),
                                                        (metrics.widthPixels / (9))));
                                                //btnTag.setText( " " + Nombre.charAt( (j + (i * 7))));
                                                btnTag.setClickable(false);
                                                btnTag.setId(j + (i * 8));
                                                botonesup[j + (i * 8)] = btnTag;
                                                btnTag.setBackgroundResource(R.drawable.boton2);
                                                btnTag.setTextColor(Color.WHITE);
                                                btnTag.setTypeface(Metodos.fuente(Quiz.this));
                                                if (String.valueOf(Nombre.charAt(j + (i * 8))).equals(" ")) {
                                                    comparador[j + (i * 8)] = " ";
                                                    cont = cont + 1;
                                                    btnTag.setText(" ");
                                                    btnTag.setAlpha(0);
                                                }
                                                btnTag.setText(String.valueOf(Nombre.charAt(j + (i * 8))));
                                                row.addView(btnTag);
                                            }
                                        }
                                        layoutbtn1.addView(row);
                                        layoutbtn1down.removeAllViews();
                                    }
                                    //quitar

                                    coin = coin + (puntosiniciales - (intentos1 * 5));
                                    xp = xp + (puntosiniciales - (intentos1 * 5));
                                    marcador++;
                                    if (marcador == 10 || marcador == 20 || marcador == 34
                                            || marcador == 48 || marcador == 62 || marcador == 76 || marcador == 90) {
                                        Metodos.creartoast(Quiz.this, getLayoutInflater().inflate(
                                                R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), " nivel desbloqueado ");
                                    }
                                    contestada = true;
                                    if (mInterstitialAd.isLoaded()) {
                                        mInterstitialAd.show();
                                    }
                                    botonpreguntar.setEnabled(false);
                                    botonrevelar.setEnabled(false);
                                    botonpista.setEnabled(false);
                                    botonresolver.setEnabled(false);
                                    recompensa.setText("");
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((metrics.widthPixels / 3) * 2, (metrics.widthPixels / 3) * 2);
                                    imagen.setLayoutParams(layoutParams);
                                    guardardatospregunta();
                                } else
                                    Metodos.creartoast(Quiz.this, getLayoutInflater().inflate(
                                            R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), "no tienes suficientes puntos");
                                break;
                            case 1:
                                //revelar
                                if (coin >= costorevelar) {
                                    coin = coin - costorevelar;
                                    txtcoin.setText("" + coin);
                                    dialogopista(pistarayita, false);
                                    pistarevelar = true;
                                    StateListDrawable stateListDrawable = new StateListDrawable();
                                    stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, getResources().getDrawable(R.mipmap.pista2buyback));
                                    stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, getResources().getDrawable(R.mipmap.pista2buy));
                                    botonrevelar.setBackground(stateListDrawable);
                                    guardardatospregunta();
                                } else
                                    Metodos.creartoast(Quiz.this, getLayoutInflater().inflate(
                                            R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), "no tienes suficientes puntos");
                                break;
                            case 2:
                                // pista
                                if (coin >= costopista) {
                                    coin = coin - costopista;
                                    txtcoin.setText("" + coin);
                                    pistatexto = true;
                                    dialogopista(Pista, true);
                                    StateListDrawable stateListDrawable = new StateListDrawable();
                                    stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, getResources().getDrawable(R.mipmap.pistabuyback));
                                    stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, getResources().getDrawable(R.mipmap.pistabuy));
                                    botonpista.setBackgroundDrawable(stateListDrawable);
                                    botonpista.setBackground(stateListDrawable);
                                    guardardatospregunta();
                                } else
                                    Metodos.creartoast(Quiz.this, getLayoutInflater().inflate(
                                            R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), "no tienes suficientes puntos");
                                break;
                            case 3:
                                //preguntar
                                if (coin >= costopreguntar) {
                                    if (googleapiClient != null) {
                                        if (googleapiClient.isConnected()) {
                                            Games.Achievements.increment(googleapiClient, getString(R.string.achievement_pregunton), 1);
                                        }
                                    }
                                    if (ContextCompat.checkSelfPermission(Quiz.this,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(Quiz.this,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                        } else {

                                            ActivityCompat.requestPermissions(Quiz.this,
                                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                                        }
                                    }
                                    coin = coin - costopreguntar;
                                    Intent intento = new Intent(Intent.ACTION_SEND);
                                    intento.setType("*/*");
                                    Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
                                    String fileName = nivel + "" + quiz + "FMQ.jpg";
                                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                                    File ExternalStorageDirectory = Environment.getExternalStorageDirectory();
                                    File file = new File(ExternalStorageDirectory + File.separator + fileName);
                                    FileOutputStream fileOutputStream = null;
                                    try {
                                        file.createNewFile();
                                        fileOutputStream = new FileOutputStream(file);
                                        fileOutputStream.write(bytes.toByteArray());
                                    } catch (IOException e) {

                                    } finally {
                                        if (fileOutputStream != null) {
                                            Uri bmpUri = Uri.parse(file.getPath());
                                            intento.putExtra(Intent.EXTRA_TEXT, "¿Me ayudas a adivinar quien es este personaje?" + Html.fromHtml("<br />") +
                                                    "Descubre mas en Personajes Mexicanos Quiz " + Html.fromHtml("<br />") +
                                                    "https://play.google.com/store/apps/details?id=com.toposdeus.personajesmexicanos");
                                            intento.putExtra(
                                                    Intent.EXTRA_STREAM,
                                                    bmpUri);
                                            startActivity(Intent.createChooser(intento,
                                                    "Preguntale a tus amigos "));
                                        }
                                    }
                                    preguntar++;
                                    guardardatospregunta();
                                } else
                                    Metodos.creartoast(Quiz.this, getLayoutInflater().inflate(
                                            R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), "no tienes suficientes puntos");
                                break;

                        }
                        txtcoin.setText("" + coin);
                        txtXP.setText("" + xp);
                        dialog.cancel();
                    }
                }
        );
        Button botonno = (Button) vi.findViewById(R.id.botonno);
        botonno.setTypeface(Metodos.fuente(this));
        botonno.setOnClickListener(
                new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                }
        );
        dialog.show();
        //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
    }

    private void dialogopista(String pista, boolean pistainfo) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogopista, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        //decidir despues si sera cancelable o no
        dialog.setCancelable(true);
        TextView tit = (TextView) vi.findViewById(R.id.dptitulo);
        TextView txtpista = (TextView) vi.findViewById(R.id.dppista);
        txtpista.setText(pista);
        if (pistainfo == false) {
            txtpista.setTypeface(Metodos.fuente(this));
        }
        tit.setTypeface(Metodos.fuente(this));
        Button botonok = (Button) vi.findViewById(R.id.dpbotonok);
        botonok.setTypeface(Metodos.fuente(this));
        botonok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                }
        );
        dialog.show();
        //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
    }

    public void agregarboton(Button boton, int id) {
        if (cont < numletras) {
            posicionbtn = 0;
            // bloquear boton de origen  agrgar letra al boton destino agregar la letra al array comparador
            for (int i = 0; i <= numletras; i++) {
                if (botonesup[i].getText().equals("")) {
                    posicionbtn = posicionbtn + i;
                    break;
                }
            }
            botonesup[posicionbtn].setClickable(true);
            botonesup[posicionbtn].setText(letras[id]);
            comparador[posicionbtn] = letras[id];
            cont = cont + 1;
            //validacion de la respuesta
            if (cont == numletras) {
                for (int i = 0; i < numletras; i++) {
                    resp += comparador[i];
                }
                if (resp.equals(Nombre)) {
                    if (Metodos.Cargarboolean(this, getString(R.string.prefsonidostring)) == true) {
                        Metodos.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.winneer);
                        Metodos.mediaPlayer.start();
                    }
                    coin = coin + (puntosiniciales - (intentos1 * 5));
                    marcador++;
                    xp = xp + (puntosiniciales - (intentos1 * 5));
                    if (marcador == 10 || marcador == 20 || marcador == 34
                            || marcador == 48 || marcador == 62 || marcador == 76 || marcador == 90 || marcador == 105) {
                        Metodos.creartoast(this, getLayoutInflater().inflate(
                                R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), " nivel desbloqueado ");
                    }
                    contestada = true;
                    guardardatospregunta();
                    if (quiz == 2 || quiz == 5 || quiz == 8 || quiz == 11 || quiz == 14) {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                    }
                    for (int i = 0; i < numletras; i++) {
                        botonesup[i].setClickable(false);
                    }
                    botonpreguntar.setEnabled(false);
                    botonrevelar.setEnabled(false);
                    botonpista.setEnabled(false);
                    botonresolver.setEnabled(false);
                    recompensa.setText("");
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((metrics.widthPixels / 3) * 2, (metrics.widthPixels / 3) * 2);
                    imagen.setLayoutParams(layoutParams);
                    layoutbtn1down.removeAllViews();
                    Metodos.preferenciasonido(this, R.raw.winneer);
                    dialogonext("Felicidades has acertado ganaste " + (puntosiniciales - (intentos1 * 5)) + " puntos");
                } else {
                    vibrarbotones();
                    Metodos.preferenciasonido(this, R.raw.wrong);
                    Metodos.preferenciavibrar(this, 50);
                    resp = "";
                    intentos2++;
                    if (intentos1 < 5) {
                        intentos1++;
                        Metodos.creartoast(this, getLayoutInflater().inflate(
                                R.layout.toast5, (ViewGroup) findViewById(R.id.lytLayout)), " -5 ");

                    }
                    txtcoin.setText("" + coin);
                    txtXP.setText("" + xp);
                    recompensa.setText("" + (puntosiniciales - 5 * intentos1));
                }
                verificarlogros();
                txtcoin.setText("" + coin);
                txtXP.setText("" + xp);
                guardardatospregunta();
            }
            // asignar id de posicion al array de posiciones de origen
            listaid[posicionbtn] = id;
            boton.setClickable(false);
            boton.setText("");
        }
    }

    private void verificarlogros() {

        if (googleapiClient != null) {
            if (googleapiClient.isConnected()) {
                long punt = xp;
                Games.Leaderboards.submitScore(googleapiClient,getString(R.string.leaderboard_conocedores_de_la_fama),punt);

                if (intentos1 == 0) {
                    Games.Achievements.increment(googleapiClient, getString(R.string.achievement_perfeccionista_i), 1);
                    Games.Achievements.increment(googleapiClient, getString(R.string.achievement_perfeccionista_ii), 1);
                    Games.Achievements.increment(googleapiClient, getString(R.string.achievement_perfeccionista_iii), 1);
                }
                if (coin >= 2000) {
                    Games.Achievements.unlock(googleapiClient, getString(R.string.achievement_avaro_i));
                }
                int nivel1 = 0;
                int nivel4 = 0;
                int nivel7 = 0;

                for (int i = 0; i <= 14; i++) {
                    if (Metodos.Cargarboolean(this, "" + 0 + i + R.string.quizresuelto)) {
                        nivel1++;
                        if (nivel1 == 15) {
                            Games.Achievements.unlock(googleapiClient, getString(R.string.achievement_principiante_i));
                        }
                        if (Metodos.Cargarboolean(this, "" + 4 + i + R.string.quizresuelto)) {
                            nivel4++;
                            if (nivel4 == 15) {
                                Games.Achievements.unlock(googleapiClient, getString(R.string.achievement_intermedio_i));
                            }
                        }
                        if (Metodos.Cargarboolean(this, "" + 7 + i + R.string.quizresuelto)) {
                            nivel7++;
                            if (nivel7 == 15) {
                                Games.Achievements.unlock(googleapiClient, getString(R.string.achievement_experto_i));
                            }
                        }
                    }
                    if (intentos2 >= 10) {
                        Games.Achievements.increment(googleapiClient,
                                getString(R.string.achievement_estas_en_apuros), 1);
                    }
                }
            }
        }
    }


    public void quitarboton(Button boton, int id) {
        boton.setClickable(false);
        botonesdown[listaid[id]].setClickable(true);
        botonesdown[listaid[id]].setText(letras[listaid[id]]);
        cont = cont - 1;
        boton.setText("");
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


    public void onDestroy() {
        for (int i = 0; i < numletras; i++) {
            botonesdown[i] = null;
            botonesup[i] = null;
        }
        imagen = null;
        super.onDestroy();

    }

    private void dialogonext(String tit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialogonext, null);
        builder.setView(vi);
        final AlertDialog dialog = builder.create();
        //decidir despues si sera cancelable o no
        dialog.setCancelable(true);
        LinearLayout linear = (LinearLayout) vi.findViewById(R.id.confirmfondo);
        TextView txtconfirm = (TextView) vi.findViewById(R.id.txtconfirm);
        txtconfirm.setText(tit);
        txtconfirm.setTypeface(Metodos.fuente(this));
        Button botonsi = (Button) vi.findViewById(R.id.botonsi);
        botonsi.setTypeface(Metodos.fuente(this));
        botonsi.setText("siguiente");
        botonsi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        avanzarnivel();
                        dialog.cancel();
                    }

                }
        );
        Button botonno = (Button) vi.findViewById(R.id.botonno);
        botonno.setTypeface(Metodos.fuente(this));
        botonno.setText("atras");
        botonno.setOnClickListener(
                new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        regresarnivelanterior();
                        dialog.cancel();
                    }
                }
        );
        dialog.show();
        //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
    }

    private void vibrarbotones() {
        Metodos.preferenciavibrar(Quiz.this, 100);
        for (int i = 0; i < numletras; i++) {
            Animation entrada = AnimationUtils.loadAnimation(Quiz.this,
                    R.anim.vibrarbotones);
            botonesup[i].startAnimation(entrada);
            botonesup[i].setTextColor(Color.RED);
        }
        new CountDownTimer(200, 100) {


            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                for (int i = 0; i < numletras; i++) {
                    Animation entrada = AnimationUtils.loadAnimation(Quiz.this,
                            R.anim.vibrarbotones);
                    botonesup[i].startAnimation(entrada);
                    botonesup[i].setTextColor(Color.WHITE);


                }
            }
        }.start();


    }

}
