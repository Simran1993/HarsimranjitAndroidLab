package algonquin.cst2335.sing1993;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( "MainActivity", "onPause()- The application no longer responds to user input" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( "MainActivity", "onResume() - The application is now responding to user input" );
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( "MainActivity", "onStart() - The application is now visible on screen." );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w( "MainActivity", "onStop() - The application is no longer visible." );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( "MainActivity", "onDestroy() - Any memory used by the application is freed" );
    }
}