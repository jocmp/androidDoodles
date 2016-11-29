package co.josiahcampbell.prefixedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private PrefixEditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameText = (PrefixEditText) findViewById(R.id.editText);
        usernameText.setPrefix("@");
    }
}
