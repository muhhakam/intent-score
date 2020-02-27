package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private EditText home, away;
    private ImageView home_logo, away_logo;
    private static final int GALLERY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity

        home = findViewById(R.id.home_team);
        away = findViewById(R.id.away_team);
        home_logo = findViewById(R.id.home_logo);
        away_logo = findViewById(R.id.away_logo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage1 = (Bitmap) data.getExtras().get("data");
                        home_logo.setImageBitmap(selectedImage1);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage1 =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage1 != null) {
                            Cursor cursor = getContentResolver().query(selectedImage1,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                home_logo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;

                case 2:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage2 = (Bitmap) data.getExtras().get("data");
                        away_logo.setImageBitmap(selectedImage2);
                    }
                    break;

                case 3:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage2 =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage2 != null) {
                            Cursor cursor = getContentResolver().query(selectedImage2,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                away_logo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (data != null){
                try{
                    Uri imageUri1 = data.getData();
                    Bitmap bitmap =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri1);
                    home_logo.setImageBitmap(bitmap);
                } catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    private void selectImage1(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture1 = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture1, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto1 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto1, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectImage2(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 2);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 3);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void changeAvatar(View view) {
        selectImage1(MainActivity.this);
    }

    public void changeAvatar2(View view) {
        selectImage2(MainActivity.this);
    }

    public void next(View view) {
        if (TextUtils.isEmpty(home.getText().toString().trim())) {
            Toast.makeText(view.getContext(), "Nama Team Home Tidak oleh Kosong!", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(away.getText().toString().trim())) {
            Toast.makeText(view.getContext(), "Nama Team Away Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
        } else {
            String homeText = home.getText().toString();
            String awayText = away.getText().toString();

            if (!(homeText).equals("") && !(awayText).equals("")) {
                Intent intent = new Intent(this, MatchActivity.class);

                home_logo.buildDrawingCache();
                away_logo.buildDrawingCache();
                Bitmap image1 = home_logo.getDrawingCache();
                Bitmap image2 = away_logo.getDrawingCache();

                Bundle extras1 = new Bundle();
                extras1.putParcelable("IMAGE1_KEY", image1);
                intent.putExtras(extras1);
                intent.putExtra("HOME_KEY", homeText);

                Bundle extras2 = new Bundle();
                extras2.putParcelable("IMAGE2_KEY", image2);
                intent.putExtras(extras2);
                intent.putExtra("AWAY_KEY", awayText);

                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Tolong isi semua data !",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

