package www.yangdainsheng.allpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import www.yangdainsheng.lib_other_page.OtherMainActivity;

public class MainActivity extends AppCompatActivity {

    Button mButton1;
    Button mButton2;
    Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = findViewById(R.id.bt_1);
        mButton2 = findViewById(R.id.bt_2);
        mButton3 = findViewById(R.id.bt_3);
        mButton1.setOnClickListener(v -> {
            startActivity(new Intent(this, OtherMainActivity.class));
            int i = 0;
            i++;
            Log.i("yyy","你点击我了" + i);
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("yyy","你点击我了222");
            }
        });
        mButton3.setOnClickListener(v -> {
            Log.i("yyy","你点击我了3333");
        });


    }
}