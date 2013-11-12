package com.example.modernhome;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.content.pm.ActivityInfo;


public class MainActivity extends Activity {
	
	private Controller _controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


        // Set window fullscreen and remove title bar, and force landscape orientation
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

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
	
	public void buzzWordRcognized()
	{
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
