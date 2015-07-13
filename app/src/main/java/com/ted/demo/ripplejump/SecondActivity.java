package com.ted.demo.ripplejump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.ted.demo.ripplejump.view.RippleBackgroundView;


public class SecondActivity extends ActionBarActivity implements RippleBackgroundView.OnStateChangeListener{
    public static final String ARG_RIPPLE_START_LOCATION = "arg_ripple_start_location";

    private RippleBackgroundView rippleBackgroundView;
    private View realView;

    public static void startSecondFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, SecondActivity.class);
        intent.putExtra(ARG_RIPPLE_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    public void onBack(View view){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        rippleBackgroundView = (RippleBackgroundView) findViewById(R.id.ripple_background);
        realView = findViewById(R.id.ripple_content);
        setupRippleBackground(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void setupRippleBackground(Bundle savedInstanceState) {
        rippleBackgroundView.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_RIPPLE_START_LOCATION);
            rippleBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rippleBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                    rippleBackgroundView.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            rippleBackgroundView.setToFinishedFrame();
        }
    }

    @Override
    public void onStateChange(int state) {
        if (RippleBackgroundView.STATE_FINISHED == state) {
            realView.setVisibility(View.VISIBLE);
        } else {
            realView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
