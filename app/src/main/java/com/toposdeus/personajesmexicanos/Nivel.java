package com.toposdeus.personajesmexicanos;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class Nivel extends Activity implements OnClickListener {
    public static String[] titulos = {"nivel 1", "nivel 2", "nivel 3", "nivel 4", "historicos", "tv", "musica", "humor", "luchadores", "tv 2", "", "", ""};
    Button botonatras;
    TextView txtcoin, txtxp, titulo;
    int nivel, definirpregunta;
    boolean verificarcontestada;
    LinearLayout layout;
    int[] imagenes = {R.array.imagenesnivel1, R.array.imagenesnivel2, R.array.imagenesnivel3, R.array.imagenesnivel4,
            R.array.imagenesnivel5, R.array.imagenesnivel6, R.array.imagenesnivel7, R.array.imagenesnivel8, R.array.imagenesnivel9, R.array.imagenesnivel10};
    private AdView mBottomBanner;

    public static Drawable resizeImage(Context ctx, int resId, int w, int h) {

        // cargamos la imagen de origen
        Bitmap BitmapOrg = BitmapFactory.decodeResource(ctx.getResources(), resId);
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        // calculamos el escalado de la imagen destino
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // para poder manipular la imagen
        // debemos crear una matriz

        Matrix matrix = new Matrix();
        // resize the Bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // volvemos a crear la imagen con los nuevos valores
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0,
                width, height, matrix, true);

        // si queremos poder mostrar nuestra imagen tenemos que crear un
        // objeto drawable y así asignarlo a un botón, imageview...
        return new BitmapDrawable(resizedBitmap);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel);
        txtcoin = (TextView) findViewById(R.id.txtcoin);
        txtxp = (TextView) findViewById(R.id.txtXP);
        botonatras = (Button) findViewById(R.id.botonatras);
        botonatras.setOnClickListener(this);
        txtcoin.setText("" + Metodos.Cargarint(this, getString(R.string.coin)));
        txtcoin.setTypeface(Metodos.fuente(this));
        txtxp.setText("" + Metodos.Cargarint(this, getString(R.string.xp)));
        txtxp.setTypeface(Metodos.fuente(this));
        nivel = Metodos.Cargarint(this, getString(R.string.nivel));
        //arreglobotones = getResources().obtainTypedArray(imagenes[nivel]);
        layout = (LinearLayout) findViewById(R.id.layoutnivel);
        titulo = (TextView) findViewById(R.id.titulonivel);
        titulo.setText(titulos[nivel]);
        titulo.setTypeface(Metodos.fuente(this));
        //ads
        mBottomBanner = (AdView) findViewById(R.id.av_bottom_banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mBottomBanner.loadAd(adRequest);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int ancho = metrics.widthPixels / (100) * 20;
        int anchobtn = metrics.widthPixels / (100) * 28;
        for (int i = 0; i <= (14 / 3); i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            LinearLayout.LayoutParams lll = (LinearLayout.LayoutParams) row.getLayoutParams();
            lll.gravity = Gravity.CENTER;
            lll.setMargins(0, 50, 0, 0);
            row.setLayoutParams(lll);
            for (int j = 0; j < 3; j++) {
                if ((j + (i * 3)) <= 14) {
                    final Button btnTag = new Button(this);
                    btnTag.setLayoutParams(new LayoutParams(anchobtn, anchobtn));
                    LinearLayout.LayoutParams lllp = (LinearLayout.LayoutParams) btnTag.getLayoutParams();
                    lllp.gravity = Gravity.CENTER;
                    lllp.setMargins((metrics.widthPixels / (100) * 3), 0, (metrics.widthPixels / (100) * 3), 0);
                    btnTag.setLayoutParams(lllp);
                    btnTag.setId(j + (i * 3));
                    StateListDrawable states = new StateListDrawable();
                    int o = j + (i * 3);
                    states.addState(new int[]{android.R.attr.state_pressed}, drawbg(resizeImage(this, getResources().obtainTypedArray(
                            imagenes[nivel]).getResourceId(j + (i * 3), 1),ancho, ancho), j + (i * 3), 60));
                    states.addState(new int[]{android.R.attr.state_enabled}, drawbg(resizeImage(this, getResources().obtainTypedArray(
                            imagenes[nivel]).getResourceId(j + (i * 3), 1), ancho, ancho), j + (i * 3), 0));
                    btnTag.setBackground(states);
                    final int finalJ = j;
                    final int finalI = i;
                    btnTag.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            definirpregunta = finalJ + (finalI * 3);
                            //Animation mover = AnimationUtils.loadAnimation(Nivel.this, R.anim.mover);
                            //btnTag.startAnimation(mover);
                            Metodos.preferenciasonido(Nivel.this, R.raw.click);
                            Metodos.preferenciavibrar(Nivel.this, 50);
                            Metodos.Guardarint(Nivel.this, definirpregunta, getString(R.string.quiz));
                            startActivity(new Intent(getBaseContext(), Quiz.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            finish();
                        }
                    });

                    row.addView(btnTag);
                    row.setGravity(Gravity.CENTER);
                }
            }
            layout.addView(row);
        }
        layout.setGravity(Gravity.CENTER);

    }

    public Drawable drawbg(Drawable draw, int i, int alfa) {

        verificarcontestada = Metodos.Cargarboolean(this, "" + nivel + i + R.string.quizresuelto);
        Drawable marco = ResourcesCompat.getDrawable(getResources(), R.mipmap.fondoboton, getApplicationContext().getTheme());
        Drawable[] drawarray = new Drawable[2];
        drawarray[0] = draw;
        if (verificarcontestada == false) {
            marco.setAlpha(140 + alfa);
        } else {
            marco.setAlpha(alfa);
        }
        drawarray[1] = marco;
        LayerDrawable layerdrawable = new LayerDrawable(drawarray);
        return layerdrawable;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Metodos.preferenciasonido(this, R.raw.click);
            Metodos.preferenciavibrar(this, 50);
            Animation mover2 = AnimationUtils
                    .loadAnimation(this, R.anim.mover);
            botonatras.startAnimation(mover2);
            startActivity(new Intent(getBaseContext(), MenuNiveles.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        Animation mover = AnimationUtils.loadAnimation(this, R.anim.mover);
        Metodos.preferenciasonido(this, R.raw.click);
        Metodos.preferenciavibrar(this, 50);
        Intent i = new Intent(this, Quiz.class);
        startActivity(i);
        switch (v.getId()) {

            case R.id.botonatras:
                Animation mover2 = AnimationUtils
                        .loadAnimation(this, R.anim.mover);
                botonatras.startAnimation(mover2);
                startActivity(new Intent(getBaseContext(), MenuNiveles.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                break;
        }

        Metodos.Guardarint(this, definirpregunta, getString(R.string.quiz));
    }

    public void onDestroy() {

        super.onDestroy();
        for (int i = 0; i < 14; i++) {
            layout.destroyDrawingCache();
        }
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
