package com.example.modernhome;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

public class TTS extends Activity implements TextToSpeech.OnInitListener {

	private static final int CHECK_TTS_AVAILABILITY = 1234;
	private Intent _result;
	private TextToSpeech _tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, CHECK_TTS_AVAILABILITY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHECK_TTS_AVAILABILITY) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				setResult(Activity.RESULT_OK, _result);
				_tts = new TextToSpeech(this, this);
				super.finish();
			} else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	@Override
	public void onDestroy() {
		if (_tts != null) {
			_tts.stop();
			_tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {

			int result = _tts.setLanguage(Locale.US);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {
				Bundle extras = getIntent().getExtras();
				if (extras != null) {
					String text = extras.getString(Intent.EXTRA_TEXT);
					_tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
				}
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

}
