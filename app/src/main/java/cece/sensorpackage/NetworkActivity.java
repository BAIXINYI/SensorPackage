package cece.sensorpackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request;
import okhttp3.Callback;

public class NetworkActivity extends Activity implements Callback {

    private OkHttpClient okHttpClient = new OkHttpClient();
    private String TAG = "This is a tag";
    private String responseMessage;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        display("to start");

        mHandler = new Handler(Looper.getMainLooper());
    }

    public void testNetwork(View view) throws IOException {
        Request request = new Request.Builder().url("http:/192.168.0.5:3000").get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String mMessage = "failed: " + e.getMessage();
                Log.e(TAG, mMessage); // no need inside run()
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        display(mMessage); // must be inside run()
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String mMessage = "response code: " + response.code() + "\nresponse body: " + response.body().string();
                Log.i(TAG, mMessage); // no need inside run()
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        display(mMessage); // must be inside run()
                    }
                });
            }

        });
    }

    public void uploadFile(View view) {
        String pathString = this.getExternalFilesDir(null).toString() + "/SENSOR_DATA/gyroscopeData.csv";
        File file = new File(pathString);
        try {
            upload("http:/192.168.0.5:3000/uploadfile", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pathString = this.getExternalFilesDir(null).toString() + "/SENSOR_DATA/accelerometerData.csv";
        file = new File(pathString);
        try {
            upload("http:/192.168.0.5:3000/uploadfile", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pathString = this.getExternalFilesDir(null).toString() + "/SENSOR_DATA/gravityData.csv";
        file = new File(pathString);
        try {
            upload("http:/192.168.0.5:3000/uploadfile", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pathString = this.getExternalFilesDir(null).toString() + "/SENSOR_DATA/magnetometerData.csv";
        file = new File(pathString);
        try {
            upload("http:/192.168.0.5:3000/uploadfile", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload(String url, File file) throws IOException {
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("text/csv"), file))
                .addFormDataPart("UserId", "1")
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        okHttpClient.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        responseMessage = "failed: " + e.getMessage();
        Log.i(TAG, responseMessage);
        NetworkActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                display(responseMessage);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        responseMessage = "response code: " + response.code() + "\nresponse body: " + response.body().string();
        Log.i(TAG, responseMessage);
        NetworkActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                display(responseMessage);
            }
        });
    }

    private void display(String message) {
        TextView responseTextView = findViewById(R.id.responseTextView);
        responseTextView.setText(message);
    }

    public void go2FirstActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}