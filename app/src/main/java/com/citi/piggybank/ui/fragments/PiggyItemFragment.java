package com.citi.piggybank.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.citi.piggybank.R;
import com.citi.piggybank.loaders.PiggyBankLoader;
import com.citi.piggybank.provider.PiggyContract;
import com.citi.piggybank.ui.activities.BaseActivity;
import com.citi.piggybank.utils.CursorUtils;

/**
 * PiggyItemFragment
 * Version info
 * 26.03.2015
 * Created by Dzmitry_Slutski.
 */
public class PiggyItemFragment extends BaseFragment implements SensorEventListener {

    public static final String ARG_ITEM_ID = "ARG_ITEM_ID";
    public static final String ARG_ITEM_COUNT = "ARG_ITEM_COUNT";
    public static final String ARG_ITEM_INDEX = "ARG_ITEM_INDEX";
    public static final String ARG_ITEM_NAME = "ARG_ITEM_NAME";
    public static final String ARG_ITEM_GOAL = "ARG_ITEM_GOAL";
    public static final String ARG_ITEM_PERCENT = "ARG_ITEM_PERCENT";
    public static final String ARG_ITEM_ON_WITHDRAW = "ARG_ITEM_ON_WITHDRAW";

    private int loadCount = 0;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    private int mCount;
    private int mIndex;
    //    private int mPercent;
//    private String mGoal;
//    private String mItemName;
    private int mID;
    private TextView mItemName;
    private TextView mCounter;
    private TextView mItemGoal;
    private TextView mPercent;
    private View mCongratulations;
    private View mRightArrow;
    private View mLeftArrow;
    private View mActionButton;
    private View mProgress;
    private View mData;
    private int mAmount;
    private int mGoal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        mID = arguments.getInt(ARG_ITEM_ID);

        mCount = arguments.getInt(ARG_ITEM_COUNT);
        mIndex = arguments.getInt(ARG_ITEM_INDEX);
//        mPercent = arguments.getInt(ARG_ITEM_PERCENT);
//        mItemName = arguments.getString(ARG_ITEM_NAME);
//        mGoal = arguments.getString(ARG_ITEM_GOAL);


        senSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    public void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    public void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected boolean showActionBar() {
        return true;
    }

    @Override
    protected String getActionBarTitle() {
        return "PIGGY BANK";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piggy_item, container, false);
        mCounter = (TextView) view.findViewById(R.id.item_counter);


        mItemName = (TextView) view.findViewById(R.id.item_name);
        mItemGoal = (TextView) view.findViewById(R.id.item_goal);
        mPercent = (TextView) view.findViewById(R.id.percent);
        mCongratulations = view.findViewById(R.id.congratulation);
        mRightArrow = view.findViewById(R.id.arrow_right);
        mLeftArrow = view.findViewById(R.id.arrow_left);
        mActionButton = view.findViewById(R.id.fill_up);
        mProgress = view.findViewById(android.R.id.progress);
        mData = view.findViewById(R.id.data);

        initLoader();

        return view;
    }

    private View.OnClickListener mWithDrawListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener mFillUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            BaseActivity activity = (BaseActivity) getActivity();

            activity.replaceFragment(R.id.container, FillUpFragment.newInstance(mID, mAmount, mGoal), "", true);
        }
    };


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.fill_up);
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                        }

                    });
                    mp.start();
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new PiggyBankLoader(getActivity(), mID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (!CursorUtils.isEmpty(cursor)) {
            cursor.moveToFirst();

            mCounter.setText(Integer.toString(mIndex + 1) + "/" + mCount);

            mItemName.setText(CursorUtils.getString(cursor, PiggyContract.COLUMN_NAME));
            mGoal = CursorUtils.getInt(cursor, PiggyContract.COLUMN_GOAL);
            mAmount = CursorUtils.getInt(cursor, PiggyContract.COLUMN_AMOUNT);
            mItemGoal.setText("GOAL: $ " + mGoal);
            int percent = mAmount * 100 / mGoal;
            mPercent.setText("" + percent + "%");
            mCongratulations.setVisibility(percent == 100 ? View.VISIBLE : View.INVISIBLE);
            mRightArrow.setVisibility(mCount == mIndex + 1 ? View.INVISIBLE : View.VISIBLE);
            mLeftArrow.setVisibility(mIndex == 0 ? View.INVISIBLE : View.VISIBLE);

            if (percent == 100) {
                mActionButton.setBackgroundResource(R.drawable.btn_withdraw);
                mActionButton.setOnClickListener(mWithDrawListener);
            } else {
                mActionButton.setBackgroundResource(R.drawable.btn_fill_up);
                mActionButton.setOnClickListener(mFillUpListener);
            }
            mData.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);

            if (loadCount == 0) {
                loadCount = mAmount;
            } else if (loadCount != mAmount) {
                MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.fill_up);
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }

                });
                mp.start();
            }
        }
    }
}
