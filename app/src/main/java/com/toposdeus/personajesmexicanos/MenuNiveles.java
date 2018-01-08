package com.toposdeus.personajesmexicanos;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MenuNiveles extends Activity implements OnClickListener {

    int nivel, marcador;
    Button boton1, boton2, boton3, boton4, boton5, boton6, boton7, boton8, boton9, boton10, botonatras;
    TextView txtcoin, txtxp;
    private ProgressDialog progressDialog;
    private AdView mBottomBanner;

    public static void instrucciones(Context context, LayoutInflater inflater) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View v = inflater.inflate(R.layout.dialogocomojugar, null);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        TextView txttit = (TextView) v.findViewById(R.id.titdhow);
        TextView txt1 = (TextView) v.findViewById(R.id.txt1dhow);
        txt1.setText("Obten una breve descripcion del personaje" + Html.fromHtml("<br />") + Html.fromHtml("<br />") + "costo " + Quiz.costopista + " monedas");
        TextView txt2 = (TextView) v.findViewById(R.id.txt2dhow);
        txt2.setText("obten algunas letras del nombre del personaje" + Html.fromHtml("<br />") + Html.fromHtml("<br />") + "costo " + Quiz.costorevelar + " monedas");
        TextView txt3 = (TextView) v.findViewById(R.id.txt3dhow);
        txt3.setText("resuelve el acertijo y descubre al personaje " + Html.fromHtml("<br />") + Html.fromHtml("<br />") + "costo " + Quiz.costocontestar + " monedas");
        TextView txt4 = (TextView) v.findViewById(R.id.txt4dhow);
        txt4.setText("preguntale a tus amigos" + Html.fromHtml("<br />") + Html.fromHtml("<br />") + "costo " + Quiz.costopreguntar + " monedas");
        txttit.setTypeface(Metodos.fuente(context));
        txt1.setTypeface(Metodos.fuente(context));
        txt2.setTypeface(Metodos.fuente(context));
        txt3.setTypeface(Metodos.fuente(context));
        txt4.setTypeface(Metodos.fuente(context));
        dialog.setCancelable(true);
        Button botonentendido = (Button) v.findViewById(R.id.botonok);
        botonentendido.setTypeface(Metodos.fuente(context));
        botonentendido.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                }
        );
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuniveles);
        txtcoin = (TextView) findViewById(R.id.txtcoin);
        txtxp = (TextView) findViewById(R.id.txtXP);
        txtcoin.setText("" + Metodos.Cargarint(this, getString(R.string.coin)));
        txtcoin.setTypeface(Metodos.fuente(this));
        txtxp.setText("" + Metodos.Cargarint(this, getString(R.string.xp)));
        txtxp.setTypeface(Metodos.fuente(this));
        boton1 = (Button) findViewById(R.id.level1);
        boton1.setOnClickListener(this);
        boton1.setTypeface(Metodos.fuente(this));
        boton2 = (Button) findViewById(R.id.level2);
        boton2.setOnClickListener(this);
        boton2.setTypeface(Metodos.fuente(this));
        boton3 = (Button) findViewById(R.id.level3);
        boton3.setOnClickListener(this);
        boton3.setTypeface(Metodos.fuente(this));
        boton4 = (Button) findViewById(R.id.level4);
        boton4.setOnClickListener(this);
        boton4.setTypeface(Metodos.fuente(this));
        boton5 = (Button) findViewById(R.id.level5);
        boton5.setOnClickListener(this);
        boton5.setTypeface(Metodos.fuente(this));
        boton6 = (Button) findViewById(R.id.level6);
        boton6.setOnClickListener(this);
        boton6.setTypeface(Metodos.fuente(this));
        boton7 = (Button) findViewById(R.id.level7);
        boton7.setOnClickListener(this);
        boton7.setTypeface(Metodos.fuente(this));
        boton8 = (Button) findViewById(R.id.level8);
        boton8.setOnClickListener(this);
        boton8.setTypeface(Metodos.fuente(this));
        boton9 = (Button) findViewById(R.id.level9);
        boton9.setOnClickListener(this);
        boton9.setTypeface(Metodos.fuente(this));
        boton10 = (Button) findViewById(R.id.level10);
        boton10.setOnClickListener(this);
        boton10.setTypeface(Metodos.fuente(this));
        botonatras = (Button) findViewById(R.id.botonatras);
        botonatras.setOnClickListener(this);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        LinearLayout.LayoutParams lll = new LinearLayout.LayoutParams(((metrics.widthPixels / 100) * 90),
                ((metrics.widthPixels / 10) * 4));
        lll.gravity = Gravity.CENTER;
        lll.setMargins(0, 50, 0, 0);
        boton1.setLayoutParams(lll);
        boton1.setLayoutParams(lll);
        boton2.setLayoutParams(lll);
        boton3.setLayoutParams(lll);
        boton4.setLayoutParams(lll);
        boton5.setLayoutParams(lll);
        boton6.setLayoutParams(lll);
        boton7.setLayoutParams(lll);
        boton8.setLayoutParams(lll);
        boton9.setLayoutParams(lll);
        boton10.setLayoutParams(lll);
        ganadosmetodo();
        Animation mover2 = AnimationUtils.loadAnimation(this, R.anim.mover2);
        boton1.startAnimation(mover2);
        boton2.startAnimation(mover2);
        boton3.startAnimation(mover2);
        boton4.startAnimation(mover2);
        boton5.startAnimation(mover2);
        boton6.startAnimation(mover2);
        boton7.startAnimation(mover2);
        boton8.startAnimation(mover2);
        boton9.startAnimation(mover2);
        boton10.startAnimation(mover2);

        mBottomBanner = (AdView) findViewById(R.id.av_bottom_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mBottomBanner.loadAd(adRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Metodos.preferenciasonido(this, R.raw.click);
            Metodos.preferenciavibrar(this, 50);
            Animation mover2 = AnimationUtils
                    .loadAnimation(this, R.anim.mover1);
            botonatras.startAnimation(mover2);
            startActivity(new Intent(getBaseContext(), PantallaPrincipal.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        boolean bandera = true;
        Animation mover = AnimationUtils.loadAnimation(this, R.anim.mover);
        Metodos.preferenciasonido(this, R.raw.click);
        Metodos.preferenciavibrar(this, 50);
        switch (v.getId()) {

            case R.id.level1: {
                boton1.startAnimation(mover);
                nivel = 0;
            }
            break;
            case R.id.level2: {
                boton2.startAnimation(mover);
                nivel = 1;
            }
            break;
            case R.id.level3:
                boton3.startAnimation(mover);
                nivel = 2;
                break;

            case R.id.level4:
                boton4.startAnimation(mover);
                nivel = 3;

                break;
            case R.id.level5: {
                boton5.startAnimation(mover);
                nivel = 4;
            }
            break;
            case R.id.level6: {
                boton6.startAnimation(mover);
                nivel = 5;
            }
            break;
            case R.id.level7: {
                boton7.startAnimation(mover);
                nivel = 6;
            }
            break;
            case R.id.level8:
                boton8.startAnimation(mover);
                nivel = 7;
                break;
            case R.id.level9:
                boton8.startAnimation(mover);
                nivel = 8;
                break;
            case R.id.level10:
                boton8.startAnimation(mover);
                nivel = 9;
                break;
            case R.id.botonatras:
                bandera = false;
                Animation mover2 = AnimationUtils
                        .loadAnimation(this, R.anim.mover1);
                botonatras.startAnimation(mover2);
                startActivity(new Intent(getBaseContext(), PantallaPrincipal.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                break;
            default:
                break;
        }
        Metodos.Guardarint(this, nivel, getString(R.string.nivel));

        if (bandera == true) {
            startActivity(new Intent(getBaseContext(), Nivel.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Cargando");
            progressDialog.setCancelable(false);
            progressDialog.show();
            finish();

        }
    }

    private void ganadosmetodo() {
        marcador = Metodos.Cargarint(this, getString(R.string.marcador));
        mrcadoresdeniveles(boton1, 1);
        if (marcador < 2) {
            instrucciones(this, getLayoutInflater());
        }

        if (marcador < 10) {
            boton2.setEnabled(false);
            int punt = 10 - marcador;
            boton2.setTextColor(Color.parseColor("#a2a2a2"));
            boton2.setText(punt + " aciertos para desbloquear");
            boton2.setTextSize(20);
        } else {

            mrcadoresdeniveles(boton2, 2);
        }
        if (marcador < 20) {
            boton3.setEnabled(false);
            int punt = 20 - marcador;
            boton3.setTextSize(20);
            boton3.setTextColor(Color.parseColor("#a2a2a2"));
            boton3.setText(punt + " aciertos para desbloquear");
        } else {
            mrcadoresdeniveles(boton3, 3);
        }
        if (marcador < 34) {
            boton4.setEnabled(false);
            int punt = 34 - marcador;
            boton4.setTextSize(20);
            boton4.setTextColor(Color.parseColor("#a2a2a2"));
            boton4.setText(punt + " aciertos para desbloquear");
        } else {
            mrcadoresdeniveles(boton4, 4);
        }
        if (marcador < 48) {
            boton5.setEnabled(false);
            int punt = 48 - marcador;
            boton5.setTextSize(20);
            boton5.setTextColor(Color.parseColor("#a2a2a2"));
            boton5.setText(punt + " aciertos para desbloquear");
        } else {
            mrcadoresdeniveles(boton5, 5);
        }
        if (marcador < 62) {
            boton6.setEnabled(false);
            int punt = 62 - marcador;
            boton6.setTextSize(20);
            boton6.setTextColor(Color.parseColor("#a2a2a2"));
            boton6.setText(punt + " aciertos para desbloquear");
        } else {
            mrcadoresdeniveles(boton6, 6);
        }
        if (marcador < 76) {
            boton7.setEnabled(false);
            int punt = 76 - marcador;
            boton7.setTextSize(20);
            boton7.setTextColor(Color.parseColor("#a2a2a2"));
            boton7.setText(punt + " aciertos para desbloquear");
        } else {
            mrcadoresdeniveles(boton7, 7);
        }
        if (marcador < 90) {
            boton8.setEnabled(false);
            int punt = 90 - marcador;
            boton8.setTextSize(18);
            boton8.setTextColor(Color.parseColor("#a2a2a2"));
            boton8.setText(punt + " aciertos para desbloquear");
        } else {
            mrcadoresdeniveles(boton8, 8);
        }
        if (marcador < 105) {
            boton9.setEnabled(false);
            int punt = 105 - marcador;
            boton9.setTextSize(18);
            boton9.setTextColor(Color.parseColor("#a2a2a2"));
            boton9.setText(punt + " aciertos para desbloquear");
        } else {
            mrcadoresdeniveles(boton9, 9);
        }
        if (marcador < 120) {
            boton10.setEnabled(false);
            int punt = 120 - marcador;
            boton10.setTextSize(18);
            boton10.setTextColor(Color.parseColor("#a2a2a2"));
            boton10.setText(punt + " aciertos para desbloquear");
        } else {
            mrcadoresdeniveles(boton10, 10);
        }
    }

    private void mrcadoresdeniveles(Button boton, int nivel) {
        int contestadas = 0;

        for (int q = 0; q <= 14; q++) {
            if (Metodos.Cargarboolean(this, "" + (nivel - 1) + q + R.string.quizresuelto) == true) {
                contestadas++;
            }
        }
        boton.setText(Nivel.titulos[nivel-1] + Html.fromHtml("<br />") + "" + Html.fromHtml("<br />") + contestadas + "/" + "15");


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

    public void onDestroy() {

        super.onDestroy();

        if (mBottomBanner != null) {
            mBottomBanner.destroy();
        }

    }
}
