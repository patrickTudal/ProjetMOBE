package com.m2dl.projetmobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.m2dl.projetmobe.Board.BoardView;
import com.m2dl.projetmobe.Enum.DirectionEnum;

public class MainActivity extends AppCompatActivity {

    private BoardView boardView;
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private SensorEventListener gyroscopeEventListner;
    private static final float GYROSCOPE_SENSITIVITY = 2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = findViewById(R.id.mainBoardView);
        manageSensor();
    }

    private void manageSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroscopeEventListner = new SensorEventListener2() {
            @Override
            public void onFlushCompleted(Sensor sensor) {
                Log.i("Sensor", "Gyroscope sensor onFlushCompleted");
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                //Log.i("Sensor", "Gyroscope sensor onSensorChanged");
                //Log.i("Sensor change", event.values[0] + " " + event.values[1] + " " + event.values[2]);
                if(event.values[0] < -GYROSCOPE_SENSITIVITY) {
                    Log.i("Change direction", "LEFT");
                    if(boardView.directionEnum != DirectionEnum.RIGHT){
                        boardView.directionEnum = DirectionEnum.LEFT;
                    }
                } else if(event.values[0] > GYROSCOPE_SENSITIVITY) {
                    Log.i("Change direction", "RIGHT");
                    if(boardView.directionEnum != DirectionEnum.LEFT){
                        boardView.directionEnum = DirectionEnum.RIGHT;
                    }
                } else if(event.values[1] < -GYROSCOPE_SENSITIVITY) {
                    Log.i("Change direction", "UP");
                    if(boardView.directionEnum != DirectionEnum.DOWN){
                        boardView.directionEnum = DirectionEnum.UP;
                    }
                } else if(event.values[1] > GYROSCOPE_SENSITIVITY) {
                    Log.i("Change direction", "DOWN");
                    if(boardView.directionEnum != DirectionEnum.UP){
                        boardView.directionEnum = DirectionEnum.DOWN;
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.i("Sensor", "Gyroscope sensor onAccuracyChanged");
            }
        };
        sensorManager.registerListener(gyroscopeEventListner,gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
