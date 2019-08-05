package www.yangdainsheng.allpoint;

import android.app.Application;

import www.yangdainsheng.lib_point.SensorDataAPI;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SensorDataAPI.init(this);
    }
}
