package com.example.modernhome;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private Controller _controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
