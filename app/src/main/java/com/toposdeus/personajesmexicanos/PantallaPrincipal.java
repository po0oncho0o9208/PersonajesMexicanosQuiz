package com.toposdeus.personajesmexicanos;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.firebase.analytics.FirebaseAnalytics;


public class PantallaPrincipal extends FragmentActivity implements OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, RewardedVideoAdListener {
    GoogleApiClient googleapiClient = null;
    Button botonplay, botonopcion, botonmemo, botonmonedas, btnest;
    private RewardedVideoAd mRewardedVideoAd;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantallaprincipal);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        botonplay = (Button) findViewById(R.id.botonplay);
        botonmonedas = (Button) findViewById(R.id.botonmonedas);
        btnest = (Button) findViewById(R.id.botonest);
        btnest.setOnClickListener(this);
        botonmonedas.setOnClickListener(this);
        botonplay.setOnClickListener(this);
        botonopcion = (Button) findViewById(R.id.botonopciones);
        botonopcion.setOnClickListener(this);
        botonmemo = (Button) findViewById(R.id.botonmemo);
        botonmemo.setOnClickListener(this);
        Animation mover2 = AnimationUtils.loadAnimation(this, R.anim.mover2);
        botonopcion.startAnimation(mover2);
        botonplay.startAnimation(mover2);
        botonmemo.startAnimation(mover2);
        botonmonedas.startAnimation(mover2);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd("ca-app-pub-1984616735532779/5554750331",
                new AdRequest.Builder().build());


        googleapiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .enableAutoManage(this, this).build();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View vi = inflater.inflate(R.layout.dialogoconfirm, null);
            builder.setView(vi);
            final AlertDialog dialog = builder.create();
            //decidir despues si sera cancelable o no
            dialog.setCancelable(false);
            LinearLayout linear = (LinearLayout) vi.findViewById(R.id.confirmfondo);
            TextView txtconfirm = (TextView) vi.findViewById(R.id.txtconfirm);
            txtconfirm.setText("deseas salir de la aplicacion");
            txtconfirm.setTypeface(Metodos.fuente(this));
            Button botonsi = (Button) vi.findViewById(R.id.botonsi);
            botonsi.setTypeface(Metodos.fuente(this));
            botonsi.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            finish();
                            onDestroy();
                            System.exit(0);
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
            //Metodos.dialogo( this, getLayoutInflater(), "¿seguro deseas salir de la aplicacion?", 0 );
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        Animation mover = AnimationUtils.loadAnimation(this, R.anim.mover1);
        Metodos.preferenciasonido(this, R.raw.click);
        Metodos.preferenciavibrar(this, 50);
        switch (v.getId()) {
            case R.id.botonopciones:
                botonopcion.startAnimation(mover);
                new CountDownTimer(350, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        Intent mainIntent1 = new Intent().setClass(PantallaPrincipal.this,
                                Opciones.class);
                        startActivity(mainIntent1);
                        finish();
                    }
                }.start();
                break;
            case R.id.botonplay:
                botonplay.startAnimation(mover);
                new CountDownTimer(350, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        Intent mainIntent = new Intent().setClass(PantallaPrincipal.this,
                                MenuNiveles.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }.start();
                break;
            case R.id.botonmemo:
                if (googleapiClient.isConnected()) {
                    startActivityForResult(Games.Achievements.getAchievementsIntent(googleapiClient), 0);
                } else {
                    Metodos.creartoast(this, getLayoutInflater().inflate(
                            R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), "no estas conectado");
                }
                break;
            case R.id.botonest:



                btnest.startAnimation(mover);
                new CountDownTimer(350, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        Intent mainIntent1 = new Intent().setClass(PantallaPrincipal.this,
                                Estadisticas.class);
                        startActivity(mainIntent1);
                        finish();
                    }
                }.start();
                break;
            case R.id.botonmonedas:

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View vi = inflater.inflate(R.layout.dialogoconfirm, null);
                builder.setView(vi);
                final AlertDialog dialog = builder.create();
                //decidir despues si sera cancelable o no
                dialog.setCancelable(true);
                LinearLayout linear = (LinearLayout) vi.findViewById(R.id.confirmfondo);
                TextView txtconfirm = (TextView) vi.findViewById(R.id.txtconfirm);
                txtconfirm.setText("¿quieres reproducir un video por 50 monedas?");
                txtconfirm.setTypeface(Metodos.fuente(this));
                Button botonsi = (Button) vi.findViewById(R.id.botonsi);
                botonsi.setTypeface(Metodos.fuente(this));
                botonsi.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mRewardedVideoAd.isLoaded()) {
                                    mRewardedVideoAd.show();
                                } else {
                                    Metodos.creartoast(PantallaPrincipal.this, getLayoutInflater().inflate(
                                            R.layout.toast, (ViewGroup) findViewById(R.id.lytLayout)), " intentalo mas tarde ");
                                }
                                dialog.cancel();
                            }

                        });

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

            default:
                break;
        }

    }

    @Override
    public void onDestroy() {
        if (googleapiClient.isConnected())
            googleapiClient.disconnect();
        mRewardedVideoAd.destroy(this);

        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (googleapiClient != null) {
            googleapiClient.connect();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Toast.makeText(this, "" + result.toString(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem reward) {
        Toast.makeText(this, "Has ganado  " + reward.getAmount() + "  " + reward.getType()
                , Toast.LENGTH_SHORT).show();
        int coin = Metodos.Cargarint(this, "" + getString(R.string.coin));
        Metodos.Guardarint(this, (coin + 50), "" + getString(R.string.coin));
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }


}
