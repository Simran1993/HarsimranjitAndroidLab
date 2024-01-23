package algonquin.cst2335.sing1993;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mytext = findViewById(R.id.textview);
        EditText myedit = findViewById(R.id.myedittext);
        Button btn = findViewById(R.id.mybutton);
         btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            String editString=myedit.getText().toString();
                mytext.setText( "Your edit text has: " + editString);
            }
        });
    }

}