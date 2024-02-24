package algonquin.cst2335.sing1993;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    private EditText phoneNumberEditText;
    private Button callButton;
    private Button takePhotoButton;
    private ImageView imageViewCamera;

    private TextView emailTextView;

    private ActivityResultLauncher<Intent> phoneCallLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        phoneNumberEditText = findViewById(R.id.editTextPhone);
        callButton = findViewById(R.id.buttonCall);
        takePhotoButton = findViewById(R.id.button2);
        imageViewCamera = findViewById(R.id.imageView);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedPhoneNumber = prefs.getString("PhoneNumber", "");
        phoneNumberEditText.setText(savedPhoneNumber);

        Intent intent = getIntent();
        String emailAddress = intent.getStringExtra("EmailAddress");
        emailTextView = findViewById(R.id.textViewGreetings);
        emailTextView.setText("Welcome back " + emailAddress);

        String filename = "Picture.png";
        File file = new File(getFilesDir(), filename);

        if (file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageViewCamera.setImageBitmap(theImage);
        }

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getExtras() != null) {
                                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                                if (thumbnail != null) {
                                    imageViewCamera.setImageBitmap(thumbnail);
                                    saveBitmapToFile(thumbnail);
                                }
                            }
                        }
                    }
                });

        callButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString().trim();
                initiatePhoneCall(phoneNumber);
            }
        });

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCameraIntent();
            }
        });

        phoneCallLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Handle the result if needed
                    }
                }
        );

        loadSavedBitmap();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber", phoneNumberEditText.getText().toString());
        editor.apply();
    }

    private void initiatePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        phoneCallLauncher.launch(callIntent);
    }

    private void launchCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    private void saveBitmapToFile(Bitmap bitmap) {
        FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            Log.d("SaveBitmapToFile", "Bitmap saved successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSavedBitmap() {
        String filename = "Picture.png";
        File file = new File(getFilesDir(), filename);

        if (file.exists()) {
            Log.d("LoadSavedBitmap", "File exists. Loading bitmap...");
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageViewCamera.setImageBitmap(theImage);
        } else {
            Log.d("LoadSavedBitmap", "File does not exist.");
            // File doesn't exist, handle accordingly
        }
    }
}