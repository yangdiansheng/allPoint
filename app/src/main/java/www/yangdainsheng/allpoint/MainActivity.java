package www.yangdainsheng.allpoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import www.yangdainsheng.lib_other_page.OtherMainActivity;

public class MainActivity extends AppCompatActivity {

    Button mButton1;
    Button mButton2;
    Button mButton3;
    CheckBox mCheckBox1;
    CheckBox mCheckBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = findViewById(R.id.bt_1);
        mButton2 = findViewById(R.id.bt_2);
        mButton3 = findViewById(R.id.bt_3);
        mCheckBox1 = findViewById(R.id.cb);
        mCheckBox2 = findViewById(R.id.cb_lambda);
        mButton1.setOnClickListener(v -> {
            startActivity(new Intent(this, OtherMainActivity.class));
            Log.i("yyy", "点击mButton1");
        });
        mButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                Log.i("yyy", "点击mButton2");
            }
        });
        mButton3.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RecycleViewActivity.class));
            Log.i("yyy", "点击mButton3");
        });
        findViewById(R.id.tv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("yyy", "点击TextView1");
            }
        });
        setonClick(100,10,mButton1);
        setonClick(100,10,mButton1,2);

        mCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("yyy", "点击mCheckBox1  " + isChecked);
            }
        });
        mCheckBox2.setOnCheckedChangeListener((buttonView, isChecked) -> Log.i("yyy", "点击mCheckBox2  " + isChecked));
    }

    private void setonClick(int position,float n,View view) {
        findViewById(R.id.tv_2).setOnClickListener(v -> {
            Log.i("yyy", "position " + position);
            Log.i("yyy", "点击TextView2");
            int i = 1;
            i ++;
            Log.i("yyy", "float " + n);
            view.setTag("1");
        });
    }

    private void setonClick(int position,float n,View view,double b) {
        findViewById(R.id.tv_2).setOnClickListener(v -> {
            Log.i("yyy", "position " + position);
            Log.i("yyy", "点击TextView2");
            int i = 1;
            i ++;
            Log.i("yyy", "float " + n);
            view.setTag("1");
            pro();
        });
    }

    private void pro(){
        Log.i("yyy", "pro ");
    }
}