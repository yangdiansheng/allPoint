package www.yangdainsheng.lib_point;

import android.app.Application;
import android.util.Log;

import org.json.JSONObject;

import java.util.Map;

public class SensorDataAPI {

    public static String TAG = SensorDataAPI.class.getSimpleName();

    private static SensorDataAPI INSTANCE;
    private String mDeviceId;
    private Map<String,Object> mDeviceInfo;

    public static SensorDataAPI init(Application application){
        synchronized (SensorDataAPI.class){
            if (INSTANCE == null){
                INSTANCE = new SensorDataAPI(application);
            }
            return INSTANCE;
        }
    }

    public static SensorDataAPI getInstance(){
        return INSTANCE;
    }

    private SensorDataAPI(Application application){
        mDeviceId = SensorDataPrivate.getAndroidID(application.getApplicationContext());
        SensorDataPrivate.registerActivityLifecycleCallbacks(application);
    }

    public void track(String eventName, JSONObject properties){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("event",eventName);
            jsonObject.put("device_id",mDeviceId);
//            JSONObject sendProperties = new JSONObject(mDeviceInfo);
            jsonObject.put("properties",properties);
            jsonObject.put("time",System.currentTimeMillis());
            Log.i(TAG,jsonObject.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
