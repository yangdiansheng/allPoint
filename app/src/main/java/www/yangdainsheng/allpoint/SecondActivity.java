package www.yangdainsheng.allpoint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    Button mButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("我是第二个页面");
        mButton1 = findViewById(R.id.bt_1);
        mButton1.setOnClickListener(v -> {
            Log.i("yyy","你点击我了111");
        });
    }
}