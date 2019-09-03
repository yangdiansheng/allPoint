package www.yangdainsheng.lib_other_page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class OtherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_main);
        Button button = findViewById(R.id.bt_1);
        Button button2 = findViewById(R.id.bt_2);
        Button button3 = findViewById(R.id.bt_3);
        button.setOnClickListener(v -> {
                    print("点击mButton1");
                    Log.i("yyy","--------------------");
                }
        );

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("yyy", "点击mButton2");
            }
        });
        button3.setOnClickListener(v -> {
            Log.i("yyy", "点击mButton3");
        });
    }

    private void print(String s) {
        Log.i("yyy", s);
    }
}
