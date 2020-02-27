package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView winn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        winn = findViewById(R.id.textView3);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String result = getIntent().getExtras().getString("winner");
            winn.setText(result);
        }
    }
}
