package www.yangdainsheng.allpoint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = findViewById(R.id.bt_1);
        mButton1.setOnClickListener(v -> {
            startActivity(new Intent(this,SecondActivity.class));
            Log.i("yyy","你点击我了");
        });
    }
}