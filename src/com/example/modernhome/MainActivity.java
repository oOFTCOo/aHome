package com.example.modernhome;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.content.pm.ActivityInfo;
import android.widget.TextView;
import android.view.View;


public class MainActivity extends Activity {
	
	private Controller _controller;
    private TextView anweisungText;
    public TextView okText;
    public TextView executeText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


        // Set window fullscreen and remove title bar, and force landscape orientation
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        anweisungText = (TextView)findViewById(R.id.textView);
        okText = (TextView)findViewById(R.id.textView2);
        executeText = (TextView)findViewById(R.id.textView3);
		_controller = new Controller(this); 
	}
	
	@Override
	protected void onPause() {
		_controller.onPause();
		super.onPause();
	}
	
	@Override
	protected void onResume() 
	{
		_controller.onResume();
		super.onResume();
	}
	
	public void buzzWordRecognized()
	{

        Log.d("Daniel","Buzz");
        anweisungText.setVisibility(View.INVISIBLE);
        okText.setVisibility(View.VISIBLE);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
