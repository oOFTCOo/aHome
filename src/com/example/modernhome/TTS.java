package com.example.modernhome;

import java.util.Locale;

import android.speech.tts.TextToSpeech;
import android.util.Log;

public class TTS implements TextToSpeech.OnInitListener {


	private TextToSpeech _tts;	
	
	public TTS(MainActivity mainView)
	{
		_tts = new TextToSpeech(mainView, this);
	}

	
	public void speak(String text)
	{
		_tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		
	}


	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {

			int result = _tts.setLanguage(Locale.GERMAN);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} 
//				else {
//				Bundle extras = getIntent().getExtras();
//				if (extras != null) {
//					String text = extras.getString(Intent.EXTRA_TEXT);
//					_tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//				}
//			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}
	

}
