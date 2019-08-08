package www.yangdainsheng.lib_point;

import android.view.View;

public class WrapperOnClickListener implements View.OnClickListener {

    private View.OnClickListener mSource;

    @Override
    public void onClick(View v) {
        try {
            if (mSource != null){
                mSource.onClick(v);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        SensorDataAutoTrackHelper.trackViewOnClick(v);
    }

    public WrapperOnClickListener(View.OnClickListener source){
        mSource = source;
    }
}
