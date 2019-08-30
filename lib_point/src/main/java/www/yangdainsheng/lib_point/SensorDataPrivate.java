package www.yangdainsheng.lib_point;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SensorDataPrivate {

    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        String androidId = "";
        try {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {

        }
        return androidId;
    }

    public static void registerActivityLifecycleCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                trackAppViewScreen(activity);
//                View view = activity.findViewById(android.R.id.content);
//                delegateViewOnClickListener(activity, view);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static void trackAppViewScreen(Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("$activity", activity.getClass().getCanonicalName());
            SensorDataAPI.getInstance().track("$AppViewScreen", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //普通点击事件
    public static void trackViewOnClick(View view) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("$element_type", view.getClass().getCanonicalName());
            jsonObject.put("$element_id", SensorDataPrivate.getViewId(view));
            jsonObject.put("$element_content", SensorDataPrivate.getElementContent(view));
            Activity activity = SensorDataPrivate.getActivityFromView(view);
            if (activity != null) {
                jsonObject.put("$activity", activity.getClass().getCanonicalName());
            }
            SensorDataAPI.getInstance().track("$AppClick", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //checkbox切换事件
    public static void trackViewOnCheckChange(CompoundButton buttonView, boolean isChecked) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("$element_type", buttonView.getClass().getCanonicalName());
            jsonObject.put("$element_id", SensorDataPrivate.getViewId(buttonView));
            jsonObject.put("$element_content", SensorDataPrivate.getElementContent(buttonView));
            jsonObject.put("$element_checked", isChecked);
            Activity activity = SensorDataPrivate.getActivityFromView(buttonView);
            if (activity != null) {
                jsonObject.put("$activity", activity.getClass().getCanonicalName());
            }
            SensorDataAPI.getInstance().track("$AppClick", jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getViewId(View view) {
        String idString = "";
        try {
            if (view.getId() != View.NO_ID) {
                idString = view.getContext().getResources().getResourceEntryName(view.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idString;
    }

    private static String getElementContent(View view) {
        if (view == null) {
            return "";
        }
        String text = "";
        if (view instanceof Button) {
            text = ((Button) view).getText().toString();
        }
        return text;
    }

    private static Activity getActivityFromView(View view) {
        Activity activity = null;
        if (view == null) {
            return null;
        }
        try {
            Context context = view.getContext();
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else if (context instanceof ContextWrapper) {
                //需要逐层找到Activity
                while (!(context instanceof Activity) && context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                }
                if (context instanceof Activity) {
                    activity = (Activity) context;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activity;
    }

    public static void delegateViewOnClickListener(Activity activity, View view) {
        if (activity == null || view == null) {
            return;
        }
        //通过反射获取view中onClick事件
        View.OnClickListener listener = getOnClickListener(view);
        if (listener != null && !(listener instanceof WrapperOnClickListener)) {
            view.setOnClickListener(new WrapperOnClickListener(listener));
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            if (childCount > 0) {
                for (int i = 0; i < childCount; i++) {
                    View childView = viewGroup.getChildAt(i);
                    delegateViewOnClickListener(activity, childView);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private static View.OnClickListener getOnClickListener(View view) {
        boolean hasOnClick = view.hasOnClickListeners();
        if (hasOnClick) {
            try {
                Class viewClazz = Class.forName("android.view.View");
                Method listenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");
                if (!listenerInfoMethod.isAccessible()) {
                    listenerInfoMethod.setAccessible(true);
                }
                Object listenerInfoObj = listenerInfoMethod.invoke(view);
                Class linstenerInfoClazz = Class.forName("android.view.View$ListenerInfo");
                Field onClickListenerField = linstenerInfoClazz.getDeclaredField("mOnClickListener");
                if (!onClickListenerField.isAccessible()) {
                    onClickListenerField.setAccessible(true);
                }
                return (View.OnClickListener) onClickListenerField.get(listenerInfoObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
