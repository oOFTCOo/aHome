package com.example.modernhome;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.content.pm.ActivityInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.os.AsyncTask;

public class MainActivity extends Activity {

	private Controller _controller;
    private TextView anweisungText;
    public TextView okText;
    public TextView executeText;
    public ImageButton microBtn;

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

        addListenerOnButton();

		anweisungText = (TextView) findViewById(R.id.textView);
		okText = (TextView) findViewById(R.id.textView2);
		executeText = (TextView) findViewById(R.id.textView3);
		// Intent ttsIntent = new Intent(this, TTS.class);
		// startActivityForResult(ttsIntent, TTS_RESULT);
		_controller = new Controller(this);
	}

    public void addListenerOnButton() {

        microBtn = (ImageButton) findViewById(R.id.imageButton);

        microBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buzzWordRecognized();
            }
        });



    }
	@Override
	protected void onPause() {
		if (_controller != null)
			_controller.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (_controller != null)
			_controller.onResume();
		super.onResume();
	}
	
	public void buzzWordRecognized()
	{
        anweisungText.setVisibility(View.INVISIBLE);
        okText.setVisibility(View.VISIBLE);
        _controller._buzzWordRecognized = true;
        //_audioManager.playSoundEffect(AudioManager.FX_FOCUS_NAVIGATION_UP, 100);
        _controller._soundPool.play(_controller._sound, 1, 1, 1, 0, 1);
        _controller._sound = _controller._soundPool.load(_controller._mainView, R.raw.ding, 1);

	}

    public void commandRecognized()
    {

        okText.setVisibility(View.INVISIBLE);
        executeText.setVisibility(View.VISIBLE);
        _controller._soundPool.play(_controller._sound, 1, 1, 1, 0, 1);
        _controller._sound = _controller._soundPool.load(_controller._mainView, R.raw.ding, 1);
        new LongOperation().execute("");
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    public class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            anweisungText.setVisibility(View.VISIBLE);
            okText.setVisibility(View.INVISIBLE);
            executeText.setVisibility(View.INVISIBLE);
            _controller._buzzWordRecognized=false;
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
