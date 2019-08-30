package www.yangdainsheng.allpoint;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RecycleViewActivity extends AppCompatActivity {

    RecyclerView mListView;
    MAdapter mMAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        mListView = findViewById(R.id.list);
        mMAdapter = new MAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mListView.setLayoutManager(layoutManager);
        mListView.setAdapter(mMAdapter);
    }

    public static class MAdapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<String> mList = new ArrayList<>() ;

        public MAdapter(){
            for (int i= 0; i < 100; i++){
                mList.add("------   " + i);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false));
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.bind(getItem(i),i);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public String getItem(int position){
            return mList.get(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        Button mButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.title);
            mButton = itemView.findViewById(R.id.bt);
        }

        public void bind(String s,int position){
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("yyy","item btn onclick" + position);
                }
            });
            mTextView.setOnClickListener(v -> {
                Log.i("yyy","item textview onclick" + position);
            });
            mTextView.setText(s);
        }
    }


}