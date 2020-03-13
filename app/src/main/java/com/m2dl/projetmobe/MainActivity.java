package com.m2dl.projetmobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
    private Sensor light;
    private SensorEventListener lightEventListener;
    private Sensor shake;
    private SensorEventListener shakeEventListener;
    private static final float GYROSCOPE_SENSITIVITY = 1.5f;

    // Shake sensors variables
    private double x;
    private double y;
    private double z;
    private double last_x;
    private double last_y;
    private double last_z;
    private double lastUpdate;
    private static final int SHAKE_THRESHOLD = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = findViewById(R.id.mainBoardView);
        manageSensor();
        boardView.setGameOverListener(new BoardView.GameOverListener() {
            @Override
            public void onGameOver() {
                Intent intent = new Intent(MainActivity.this, GameOverActivity.class);
                intent.putExtra("score",Long.toString(boardView.score));
                startActivity(intent);
            }
        });
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
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightEventListener = new SensorEventListener2() {
            @Override
            public void onFlushCompleted(Sensor sensor) {

            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                //Log.i("Sensor", "Sensor light changed");
                if(event.values[0] < 300 && boardView.speed > 200) {
                    Log.i("Sensor","Increase speed");
                    boardView.speed -= 10;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        shake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeEventListener = new SensorEventListener2() {
            @Override
            public void onFlushCompleted(Sensor sensor) {

            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                long curTime = System.currentTimeMillis();
                // only allow one update every 100ms.
                if ((curTime - lastUpdate) > 100) {
                    double diffTime = (curTime - lastUpdate);
                    lastUpdate = curTime;

                    x = event.values[SensorManager.DATA_X];
                    y = event.values[SensorManager.DATA_Y];
                    z = event.values[SensorManager.DATA_Z];

                    double speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                    if (speed > SHAKE_THRESHOLD) {
                        Log.d("sensor", "shake detected with speed: " + speed);
                        boardView.snake.invulnerable = true;
                    }
                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(shakeEventListener, shake, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(gyroscopeEventListner,gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(lightEventListener, light, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
