package cece.sensorpackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import sensorReaders.AccelerometerSensorReader;
import sensorReaders.LightSensorReader;
import sensorReaders.MagnetometerSensorReader;
import sensorReaders.LinearAcceleratorSensorReader;
import sensorReaders.DirectoryAndFile;
import sensorReaders.DisplaySensorValuesInterface;
import sensorReaders.GravitySensorReader;
import sensorReaders.GyroscopeSensorReader;
import sensorReaders.OrientationSensorReader;
import sensorReaders.RotationalVectorSensorReader;
import sensorReaders.SensorPrinter;
import sensorReaders.ValueStore;

public class MainActivity extends AppCompatActivity {

    private AccelerometerSensorReader mAccelerometerSensorWriter;
    private AccelerometerSensorReader mAccelerometerSensorReader;
    private GyroscopeSensorReader mGyroscopeSensorWriter;
    private GyroscopeSensorReader mGyroscopeSensorReader;
    private GravitySensorReader mGravitySensorWriter;
    private GravitySensorReader mGravitySensorReader;
    private RotationalVectorSensorReader mRotationalVectorSensorWriter;
    private RotationalVectorSensorReader mRotationalVectorSensorReader;
    private LinearAcceleratorSensorReader mLinearAcceleratorSensorWriter;
    private MagnetometerSensorReader mMagnetometerSensorWriter;
    private OrientationSensorReader mOrientationSensorWriter;
    private LightSensorReader mLightSensorWriter;
    ValueStore mValueStore;

//    private int mInterval = 1000; // 5 seconds by default, can be changed later
//    private Handler mHandler;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//      mHandler = new Handler();

        int sampleRate = 10000;
        int displayRate = 500000;

        mAccelerometerSensorWriter = ((MyApplication) this.getApplication()).getmAccelerometerSensorReader();
        mGyroscopeSensorWriter = ((MyApplication) this.getApplication()).getmGyroscopeSensorReader();
        mGravitySensorWriter = ((MyApplication) this.getApplication()).getmGravitySensorReader();
        mRotationalVectorSensorWriter = ((MyApplication) this.getApplication()).getmRotationalVectorSensorReader();
        mLinearAcceleratorSensorWriter = ((MyApplication) this.getApplication()).getmLinearAcceleratorSensorReader();
        mMagnetometerSensorWriter = ((MyApplication) this.getApplication()).getmMagnetometerSensorReader();
        mOrientationSensorWriter = ((MyApplication) this.getApplication()).getmOrientationSensorReader();
        mLightSensorWriter = ((MyApplication) this.getApplication()).getmLightSensorReader();


        mValueStore = new ValueStore();
        DirectoryAndFile dataFiles = new DirectoryAndFile(this);

        SensorPrinter mSensorPrinter = new SensorPrinter(sensorManager, dataFiles.getSensorTypeFile());
        mSensorPrinter.print();

        TextView[] accelerometerTextView = new TextView[3];
        accelerometerTextView[0] = findViewById(R.id.magnetometerXTextView);
        accelerometerTextView[1] = findViewById(R.id.magnetometerYTextView);
        accelerometerTextView[2] = findViewById(R.id.magnetometerZTextView);
        Display3ValuesInTextView accelerometerDisplay = new Display3ValuesInTextView(accelerometerTextView);

        TextView[] gyroscopeTextView = new TextView[3];
        gyroscopeTextView[0] = findViewById(R.id.linearAcceleratorXTextView);
        gyroscopeTextView[1] = findViewById(R.id.linearAcceleratorYTextView);
        gyroscopeTextView[2] = findViewById(R.id.linearAcceleratorZTextView);
        Display3ValuesInTextView gyroscopeDisplay = new Display3ValuesInTextView(gyroscopeTextView);

        TextView[] gravityTextView = new TextView[3];
        gravityTextView[0] = findViewById(R.id.orientationXTextView);
        gravityTextView[1] = findViewById(R.id.orientationYTextView);
        gravityTextView[2] = findViewById(R.id.orientationZTextView);
        Display3ValuesInTextView gravityDisplay = new Display3ValuesInTextView(gravityTextView);

        TextView[] rotationalVectorTextView = new TextView[3];
        rotationalVectorTextView[0] = findViewById(R.id.rotationalVectorXTextView);
        rotationalVectorTextView[1] = findViewById(R.id.rotationalVectorYTextView);
        rotationalVectorTextView[2] = findViewById(R.id.rotationalVectorZTextView);
        Display3ValuesInTextView rotationalVectorDisplay = new Display3ValuesInTextView(rotationalVectorTextView);

        mAccelerometerSensorReader = new AccelerometerSensorReader(true,
                                                                 sensorManager,
                                                                 accelerometerDisplay,
                                                                 sampleRate,
                                                                 displayRate);
        mGyroscopeSensorReader = new GyroscopeSensorReader(true,
                                                         sensorManager,
                                                         gyroscopeDisplay,
                                                         sampleRate,
                                                         displayRate);
        mGravitySensorReader = new GravitySensorReader(true,
                                                        sensorManager,
                                                        gravityDisplay,
                                                        sampleRate,
                                                        displayRate);
        mRotationalVectorSensorReader = new RotationalVectorSensorReader(true,
                                                                      sensorManager,
                                                                      rotationalVectorDisplay,
                                                                      sampleRate,
                                                                      displayRate);

        mAccelerometerSensorReader.open();
        mGyroscopeSensorReader.open();
        mGravitySensorReader.open();
        mRotationalVectorSensorReader.open();

    }

    private class Display3ValuesInTextView implements DisplaySensorValuesInterface{
        private TextView[] mDisplayLocations;

        Display3ValuesInTextView(TextView[] displayLocations) {
            mDisplayLocations = displayLocations;
        }

        @SuppressLint("DefaultLocale")
        public void execute(float[] values) {
            mDisplayLocations[0].setText(String.format("%.5f", values[0]));
            mDisplayLocations[1].setText(String.format("%.5f", values[1]));
            mDisplayLocations[2].setText(String.format("%.5f", values[2]));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startSensors(View view) {
        mAccelerometerSensorWriter.open();
        mGyroscopeSensorWriter.open();
        mGravitySensorWriter.open();
        mRotationalVectorSensorWriter.open();
        mLinearAcceleratorSensorWriter.open();
        mMagnetometerSensorWriter.open();
        mOrientationSensorWriter.open();
        mLightSensorWriter.open();
    //        mLogger.run();
    }

    public void endSensors(View view) {
        mAccelerometerSensorWriter.close();
        mGyroscopeSensorWriter.close();
        mGravitySensorWriter.close();
        mRotationalVectorSensorWriter.close();
        mLinearAcceleratorSensorWriter.close();
        mMagnetometerSensorWriter.close();
        mOrientationSensorWriter.close();
        mLightSensorWriter.close();
    //        mHandler.removeCallbacks(mLogger);
    }

    public void go2SecondActivity(View view) {
        mAccelerometerSensorReader.close();
        mGyroscopeSensorReader.close();
        mGravitySensorReader.close();
        mRotationalVectorSensorReader.close();

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

//        private Runnable mLogger = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    TextView temp = findViewById(R.id.testTextView);
//                    temp.setText(String.valueOf(mValueStore.getAccelerometerValues()[0]));
//                } finally {
//                    mHandler.postDelayed(mLogger, mInterval);
//                }
//            }
//        };
}
