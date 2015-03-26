package com.citi.piggybank;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.citi.piggybank.provider.DBHelper;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * CoreApplication
 * Version information
 * Date: 24.03.2015
 * Time: 13:34
 * Created by Dzmitry Slutskiy.
 */
public class CoreApplication extends Application {

    private static final String TAG = CoreApplication.class.getSimpleName();

    private static final Class SERVICES[] = new Class[]{
            //ALLOW ONLY EMPTY CONSTRUCTOR OR CONSTRUCTOR TAKEN ONLY Context as parameter
            DBHelper.class
    };
    private Map<String, Object> mServiceMap;// = new ConcurrentHashMap<>();
    private Map<String, Class> mServiceClassMap;// = new ConcurrentHashMap<>();


    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Arial Rounded MT Bold Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        Log.d(TAG, "CoreApp.onCreate()");
        //init default preference (if needed)
        initServiceMap();
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Context context, String key) {
        if (context == null || key == null) {
            throw new IllegalArgumentException("Context and key must not be null");
        }
        T systemService = (T) context.getSystemService(key);
        if (systemService == null) {
            context = context.getApplicationContext();
            systemService = (T) context.getSystemService(key);
        }
        if (systemService == null) {
            throw new IllegalStateException(key + " not available");
        }
        return systemService;
    }


    private void initServiceMap() {
        synchronized (CoreApplication.class) {
            if (mServiceMap == null) {
                Log.d(TAG, "mServiceMap init");
                mServiceMap = new ConcurrentHashMap<>();
            }
            if (mServiceClassMap == null) {
                Log.d(TAG, "mServiceClassMap init");
                mServiceClassMap = new ConcurrentHashMap<>();

                //register base class in class map for later use
                //don't instantiate this class here because CoreApplication instance create everything
                //when system initialize synchronize application (sync manager - default every minutes)
                for (Class cls : SERVICES) {
                    mServiceClassMap.put(cls.getSimpleName(), cls);
                }
            }
        }
    }

    @Override
    public Object getSystemService(String name) {
        if (mServiceClassMap == null || mServiceMap == null) {
            initServiceMap();
        }
        Class clazz = mServiceClassMap.get(name);
        if (clazz != null) {
            Object service = mServiceMap.get(name);
            if (service == null) {
                synchronized (CoreApplication.class) {
                    try {

                        //try to instantiate -NEED for existing empty constructor!
                        Log.d(TAG, "Init service: " + name);
                        service = createInstance(clazz);

                        mServiceMap.put(name, service);
                    } catch (Exception e) {
                        //rethrow exception
                        throw new RuntimeException(e);
                    }
                }
            }
            return service;
        } else {
            return super.getSystemService(name);
        }
    }

    private Object createInstance(Class clazz) throws Exception {
        Object result;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException insE) {
            //may be this service need Context as constructor parameters - give him Context
            Constructor constructor;
            try {
                //noinspection unchecked
                constructor = clazz.getConstructor(Context.class);
            } catch (NoSuchMethodException exception) {
                constructor = null;
            }
            if (constructor == null) {
                throw new IllegalArgumentException("Class " + clazz +
                        " can't instantiate! Need constructor without params or only Context as params");
            }
            result = constructor.newInstance(this);
        }
        return result;
    }


}
