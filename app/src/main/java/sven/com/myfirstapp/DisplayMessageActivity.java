package sven.com.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Optional;

public class DisplayMessageActivity extends AppCompatActivity {

    public final static String KEY_EXTRA_MESSAGE = "extra_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        String icsSessionId = this.getIntent().getStringExtra(DisplayMessageActivity.KEY_EXTRA_MESSAGE);
        TextView textView = this.findViewById(R.id.extraMessageText);
        textView.setText(icsSessionId);

    }


}
