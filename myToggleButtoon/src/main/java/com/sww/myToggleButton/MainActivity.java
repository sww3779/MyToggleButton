package com.sww.myToggleButton;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.sww.myToggleButton.view.ToggleButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.togglebutton);
    /* //设置开关的背景图片
        toggleButton.setSwitchBackground(R.drawable.switch_background);
        //设置滑动块的背景图片
        toggleButton.setSlideBackground(R.drawable.slide_button_background);

        toggleButton.setCurrentState(true);*/
        toggleButton.setOnToggleButtonChangeListener(new ToggleButton.OnToggleChangedListener() {
            @Override
            public void onToggleStateChanged(boolean currentState) {
                if(currentState){
                    Toast.makeText(MainActivity.this,"开关打开",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"开关关闭",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
