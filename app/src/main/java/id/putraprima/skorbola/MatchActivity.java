package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MatchActivity extends AppCompatActivity {

    public static final String WINNER = "winner";
    private TextView home, away, scr_home, scr_away;
    private ImageView home_logo, away_logo;
    private Integer sHome, sAway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan satu angka dari angka 0, setiap kali di tekan
        //3.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang ke ResultActivity, jika seri di kirim text "Draw"

        home        = findViewById(R.id.txt_home);
        away        = findViewById(R.id.txt_away);
        home_logo   = findViewById(R.id.home_logo);
        away_logo   = findViewById(R.id.away_logo);
        scr_home    = findViewById(R.id.score_home);
        scr_away    = findViewById(R.id.score_away);
        scr_home.setText("0");
        scr_away.setText("0");
        sHome = 0;
        sAway = 0;

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            String homeText     = bundle.getString("HOME_KEY");
            String awayText     = bundle.getString("AWAY_KEY");
            String scr_homeText = bundle.getString("SCR_HOME_KEY");
            String scr_awayText = bundle.getString("SCR_AWAY_KEY");

            Bundle extras1  = getIntent().getExtras();
            Bitmap bmp1     = (Bitmap) extras1.getParcelable("IMAGE1_KEY");

            Bundle extras2 = getIntent().getExtras();
            Bitmap bmp2    = (Bitmap) extras2.getParcelable("IMAGE2_KEY");

            home_logo.setImageBitmap(bmp1);
            away_logo.setImageBitmap(bmp2);
            home.setText(homeText);
            away.setText(awayText);
            scr_home.setText(scr_homeText);
            scr_away.setText(scr_awayText);
        }
    }

    public void plusHome(View view){
        sHome++;
        scr_home.setText(String.valueOf(sHome));
    }

    public void plusAway(View view){
        sAway++;
        scr_away.setText(String.valueOf(sAway));
    }

    public void cek(View view) {
        String homeText     = home.getText().toString();
        String awayText     = away.getText().toString();
        String scr_homeText = scr_home.getText().toString();
        String scr_awayText = scr_away.getText().toString();

        String winner;
        if (sHome > sAway){
            winner = home.getText().toString();
        } else if (sAway > sHome){
            winner = away.getText().toString();
        } else {
            winner = "DRAW";
        }

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(WINNER, winner);
        startActivity(intent);
    }
}
