package com.toposdeus.personajesmexicanos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Metodos extends Activity {

    static MediaPlayer mediaPlayer;
    static int colorfondo = Color.parseColor("#127b7f");

    public static Typeface fuente(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/ARCADE_N.TTF");
        return font;
    }

    public static boolean Cargarboolean(Context context, String nombre) {
        SharedPreferences sharedPref;
        boolean prefsonido;
        sharedPref = context.getSharedPreferences(nombre, Context.MODE_PRIVATE);
        prefsonido = sharedPref.getBoolean(nombre, false);
        return prefsonido;

    }

    public static void Guardarboolean(Context contexto, boolean prefsonido, String nombre) {
        SharedPreferences sharedPref;
        sharedPref = contexto.getSharedPreferences(
                nombre, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(nombre, prefsonido);
        editor.commit();
    }

    public static int Cargarint(Context context, String nombre) {
        SharedPreferences sharedPref;
        int puntuacion;
        sharedPref = context.getSharedPreferences(nombre, Context.MODE_PRIVATE);
        puntuacion = sharedPref.getInt(nombre, 0);
        return puntuacion;

    }

    public static void Guardarint(Context contexto, int puntuacion, String nombre) {
        SharedPreferences sharedPref;
        sharedPref = contexto.getSharedPreferences(
                nombre, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(nombre, puntuacion);
        editor.commit();
    }

    public static void preferenciasonido(Context contexto, int sound) {
        SoundManager soundm;
        soundm = new SoundManager(contexto);
        int sonidotemp = soundm.load(sound);
        if (Metodos.Cargarboolean(contexto, contexto.getString(R.string.prefsonidostring)) == true) {
            soundm.play(sonidotemp);
            mediaPlayer = MediaPlayer.create(contexto, sound);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    // Do something. For example: playButton.setEnabled(true);
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                }
            });
        }

    }

    public static void preferenciavibrar(Context contexto, int tim) {
        if (Metodos.Cargarboolean(contexto, contexto.getString(R.string.prefvibrarstring)) == true) {
            Vibrator vib = (Vibrator) contexto.getSystemService(VIBRATOR_SERVICE);
            vib.vibrate(tim);
        }
    }


    public static void creartoast(Context context, View view, String texto) {
        Toast toast = new Toast(context);
        //usamos cualquier layout como Toast
        //View toast_layout = getLayoutInflater().inflate( R.layout.toast, (ViewGroup) findViewById( R.id.lytLayout ) );
        toast.setView(view);
        //se podría definir el texto en el layout si es invariable pero lo hacemos programáticamente como ejemplo
        //tenemos acceso a cualquier widget del layout del Toast
        LinearLayout tlayout = (LinearLayout) view.findViewById(R.id.lytLayout);
        //  tlayout.setBackgroundColor( Metodos.colorfondo );
        TextView textView = (TextView) view.findViewById(R.id.toastmsj);
        textView.setText(texto);
        textView.setTypeface(Metodos.fuente(context));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


}
