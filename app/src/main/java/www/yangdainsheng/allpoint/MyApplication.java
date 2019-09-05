package www.yangdainsheng.allpoint;

import android.app.Application;
import android.content.Context;

import www.yangdainsheng.lib_point.SensorDataAPI;

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        SensorDataAPI.init(this);
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
