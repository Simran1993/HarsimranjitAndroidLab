package algonquin.cst2335.sing1993;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_EMAIL = "LoginName";
    private static final String PREFS_NAME = "MyData";

    private EditText emailEditText;
    private SharedPreferences prefs;

    private String savedEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w(TAG, "In onCreate() - Loading Widgets");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.editTextEmail);
        Button loginButton = findViewById(R.id.buttonLogin);

        // Retrieve the SharedPreferences object
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Retrieve the saved email address, use empty string if not found
        String emailAddress = prefs.getString(KEY_EMAIL, "");

        // Pre-fill the EditText field with the saved email address
        emailEditText.setText(emailAddress);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the email address to SharedPreferences
                String email = emailEditText.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(KEY_EMAIL, email);
                editor.apply();

                // Create an Intent to start SecondActivity
                Intent nextPageIntent = new Intent(MainActivity.this, SecondActivity.class);

                // Attach the email address to the Intent
                nextPageIntent.putExtra("EmailAddress", email);

                // Start the activity using the intent
                startActivity(nextPageIntent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "In onStart() - Activity starting");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "In onResume() - Activity resuming");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "In onPause() - Activity pausing");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "In onStop() - Activity stopping");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "In onDestroy() - Activity being destroyed");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the email address
        savedEmail = emailEditText.getText().toString();
        outState.putString(KEY_EMAIL, savedEmail);
    }
}