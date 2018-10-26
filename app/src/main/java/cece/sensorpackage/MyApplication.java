package cece.sensorpackage;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import sensorReaders.AccelerometerSensorReader;
import sensorReaders.DirectoryAndFile;
import sensorReaders.GravitySensorReader;
import sensorReaders.GyroscopeSensorReader;
import sensorReaders.LightSensorReader;
import sensorReaders.LinearAcceleratorSensorReader;
import sensorReaders.MagnetometerSensorReader;
import sensorReaders.OrientationSensorReader;
import sensorReaders.RotationalVectorSensorReader;
import sensorReaders.SensorPrinter;
import sensorReaders.ValueStore;

public class MyApplication extends Application {
    public AccelerometerSensorReader getmAccelerometerSensorReader() {
        return mAccelerometerSensorReader;
    }

    public GyroscopeSensorReader getmGyroscopeSensorReader() {
        return mGyroscopeSensorReader;
    }

    public GravitySensorReader getmGravitySensorReader() {
        return mGravitySensorReader;
    }

    public RotationalVectorSensorReader getmRotationalVectorSensorReader() {
        return mRotationalVectorSensorReader;
    }

    public LinearAcceleratorSensorReader getmLinearAcceleratorSensorReader() {
        return mLinearAcceleratorSensorReader;
    }

    public MagnetometerSensorReader getmMagnetometerSensorReader() {
        return mMagnetometerSensorReader;
    }

    public OrientationSensorReader getmOrientationSensorReader() {
        return mOrientationSensorReader;
    }

    public LightSensorReader getmLightSensorReader() {
        return mLightSensorReader;
    }

    public ValueStore getmValueStore() {
        return mValueStore;
    }

    private AccelerometerSensorReader mAccelerometerSensorReader;
    private GyroscopeSensorReader mGyroscopeSensorReader;
    private GravitySensorReader mGravitySensorReader;
    private RotationalVectorSensorReader mRotationalVectorSensorReader;
    private LinearAcceleratorSensorReader mLinearAcceleratorSensorReader;
    private MagnetometerSensorReader mMagnetometerSensorReader;
    private OrientationSensorReader mOrientationSensorReader;
    private LightSensorReader mLightSensorReader;
    private SensorManager sensorManager;
    ValueStore mValueStore;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        int sampleRate = 10000;
        int writeRate = 100000;

        mValueStore = new ValueStore();
        DirectoryAndFile dataFiles = new DirectoryAndFile(this);

        SensorPrinter mSensorPrinter = new SensorPrinter(sensorManager, dataFiles.getSensorTypeFile());
        mSensorPrinter.print();

        mAccelerometerSensorReader = new AccelerometerSensorReader(mValueStore,
                sensorManager,
                sampleRate,
                writeRate,
                dataFiles.getAccelerometerFile());
        mGyroscopeSensorReader = new GyroscopeSensorReader(mValueStore,
                sensorManager,
                sampleRate,
                writeRate,
                dataFiles.getGyroscopeFile());
        mGravitySensorReader = new GravitySensorReader(mValueStore,
                sensorManager,
                sampleRate,
                writeRate,
                dataFiles.getGravityFile());
        mRotationalVectorSensorReader = new RotationalVectorSensorReader(mValueStore,
                sensorManager,
                sampleRate,
                writeRate,
                dataFiles.getRotationalVectorFile());
        mLinearAcceleratorSensorReader = new LinearAcceleratorSensorReader(mValueStore,
                sensorManager,
                sampleRate,
                writeRate,
                dataFiles.getLinearAcceleratorFile());
        mMagnetometerSensorReader = new MagnetometerSensorReader(mValueStore,
                sensorManager,
                sampleRate,
                writeRate,
                dataFiles.getMagnetometerFile());
        mOrientationSensorReader = new OrientationSensorReader(mValueStore,
                sensorManager,
                sampleRate,
                writeRate,
                dataFiles.getOrientationFile());
        mLightSensorReader = new LightSensorReader(mValueStore,
                sensorManager,
                sampleRate,
                writeRate,
                dataFiles.getLightFile());
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}