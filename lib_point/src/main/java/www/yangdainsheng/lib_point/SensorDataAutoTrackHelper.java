package www.yangdainsheng.lib_point;

import android.view.View;
import android.widget.CompoundButton;

public class SensorDataAutoTrackHelper {

    public static void trackViewOnClick(View view){
        SensorDataPrivate.trackViewOnClick(view);
    }

    public static void trackViewOnCheckChange(CompoundButton buttonView, boolean isChecked){
        SensorDataPrivate.trackViewOnCheckChange(buttonView, isChecked);
    }
}
