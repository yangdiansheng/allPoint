package www.yangdainsheng.allpoint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView mListView;
    MAdapter mMAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mListView = findViewById(R.id.list);
        mMAdapter = new MAdapter(this);

        mListView.setAdapter(mMAdapter);
    }

    public static class MAdapter extends BaseAdapter {

        private ArrayList<String> mList = new ArrayList<>() ;
        Context mContext;

        public MAdapter(Context context){
            for (int i= 0; i < 100; i++){
                mList.add("------   " + i);
            }
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder") View view = View.inflate(mContext,R.layout.item_list,null);
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null){
                viewHolder = new ViewHolder();
                viewHolder.mTextView = view.findViewById(R.id.title);
                viewHolder.mButton = view.findViewById(R.id.bt);
                view.setTag(viewHolder);
            }

            viewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("yyy","item btn onclick" + position);
                }
            });
            viewHolder.mTextView.setOnClickListener(v -> {
                Log.i("yyy","item textview onclick" + position);
            });
            viewHolder.mTextView.setText(getItem(position));
            return view;
        }
    }

    public static class ViewHolder{
        TextView mTextView;
        Button mButton;
    }


}