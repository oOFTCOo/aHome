package com.example.modernhome;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

public class TTS extends Activity implements TextToSpeech.OnInitListener {

	private static final int CHECK_TTS_AVAILABILITY = 1234;
	private TextToSpeech _tts;
	private Intent _result;
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub

	}

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
//				_tts = new TextToSpeech(this, this);
//				_result.putExtra("tts", new TextToSpeechParcel(_tts));
//				setResult(Activity.RESULT_OK, _result);
//				finish();
			} else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

}
