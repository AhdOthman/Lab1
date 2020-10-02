
package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    /**
     *
     */
    private SensorManager sensorManager = null;
    /**
     *
     */
    private TextView textView1 = null;
    /**
     *
     */
    private ImageView imageViewLeft;
    /**
     *
     */
    private ImageView imageViewTop = null;
    /**
     *
     */
    private final ThreadLocal<List> listsensors = new ThreadLocal<List>();

    /**
     *
     */
    private  final float numberThre = 3;
    /**
     *
     */
    private  final float numberThree = -3;
    /**
     *
     */
    private final SensorEventListener sel = new SensorEventListener() {
        public void onAccuracyChanged(final Sensor sensor, final int accuracy) {

        }

        public void onSensorChanged(final SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            textView1.setText("x: " + x + "\ny: " + y + "\nz: " + z);

            if (event.values[0] > numberThre) {
                imageViewLeft.setVisibility(View.VISIBLE);
                imageViewLeft.setImageResource(R.drawable
                        .ic_arrow_back_black_24dp);
            } else if (event.values[0] < numberThree) {
                imageViewLeft.setVisibility(View.VISIBLE);
                imageViewLeft.setImageResource(R.drawable
                        .ic_arrow_forward_black_24dp);
            } else {
                imageViewLeft.setVisibility(View.INVISIBLE);
            }

            if (event.values[1] > numberThre) {
                imageViewTop.setVisibility(View.VISIBLE);
                imageViewTop.setImageResource(R.drawable
                        .ic_arrow_downward_black_24dp);
            } else if (event.values[1] < numberThree) {
                imageViewTop.setVisibility(View.VISIBLE);
                imageViewTop.setImageResource(R.drawable
                        .ic_arrow_upward_black_24dp);
            } else {
                imageViewTop.setVisibility(View.INVISIBLE);
            }


        }
    };

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        textView1 = findViewById(R.id.textView1);
        imageViewLeft = findViewById(R.id.imageView1);
        imageViewTop = findViewById(R.id.imageView3);

        listsensors.set(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER));
        if (listsensors.get().size() > 0) {
            sensorManager.registerListener(sel, (Sensor) listsensors
                    .get()
                    .get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public final  void onDestroy() {
        if (listsensors.get().size() > 0) {
            sensorManager.unregisterListener(sel);
        }
        super.onDestroy();
    }
}
