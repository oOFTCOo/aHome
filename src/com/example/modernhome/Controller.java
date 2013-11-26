package com.example.modernhome;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.WindowManager;

public class Controller implements Observer {

	private SpeechRecognizer _sr;
	private ObservableRecognitionListener _speechListener;
	private Intent _speechRecognitionIntent;
	private DeviceParser _deviceParser;
	private CommandParser _commandParser;
	public MainActivity _mainView;
	public AudioManager _audioManager;
	public boolean _buzzWordRecognized;
    private String objektErkannt;
    private String objektStatusErkannt;
	public SoundPool _soundPool;
	public int _sound;

	public Controller(MainActivity View) {
		_mainView = View;
		init();
	}

	private void say(String text) {
		Intent tts = new Intent(_mainView, TTS.class);
		tts.putExtra(Intent.EXTRA_TEXT, text);
		_mainView.startActivity(tts);
	}

	private void init() {
		_soundPool = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
		_sound = _soundPool.load(_mainView, R.raw.ding, 1);
		_buzzWordRecognized = false;
		_speechListener = new ObservableRecognitionListener();
		_speechListener.addObserver(this);
		try {
			_deviceParser = new AsyncConfigReader().execute(
					"http://ahome.social-butler.de/config.xml").get();
			_commandParser = new CommandParser(_deviceParser);
			//boolean test = _commandParser.existsLocationDeviceStatus("main", "Lampe", "an");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_audioManager = (AudioManager) _mainView
				.getSystemService(Context.AUDIO_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			// turn off beep sound
			_audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
		}
		_mainView.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	}

    public void sendHttpRequest() {

        AsyncHttpCommunication communication = new AsyncHttpCommunication();
        communication.execute(objektErkannt, objektStatusErkannt);
    }

	private void matchStrings(ArrayList<String> matches) {


		if ((matches.contains("okay Zuhause")||matches.contains("okay Zuhause")||matches.contains("okay zuhause an")||matches.contains("okay zu hause")||matches.contains("okay zu Hause"))&&_buzzWordRecognized==false) {
			_mainView.buzzWordRecognized();

		} else if (_buzzWordRecognized) {
			if (matches.contains("Licht aus")) {
                objektErkannt = "Lampe_Badezimmer";
                objektStatusErkannt = "aus";
                _mainView.executeText.setText("Schalte Licht im Badezimmer aus");
                _mainView.commandRecognized();
                say("Schalte Licht im Badezimmer aus");
			} else if (matches.contains("Licht an")) {
                objektErkannt = "Lampe_Badezimmer";
                objektStatusErkannt = "an";
                _mainView.executeText.setText("Schalte Licht im Badezimmer an");
                _mainView.commandRecognized();
                say("Schalte Licht im Badezimmer an");
			} else if (matches.contains("Kaffee an")) {
                objektErkannt = "Kaffee_Kueche";
                objektStatusErkannt = "an";
                _mainView.executeText.setText("Schalte Kaffemaschine in Küche an");
                _mainView.commandRecognized();
                say("Schalte Kaffemaschine in Küche an");
			} else if (matches.contains("Kaffee aus")) {
                objektErkannt = "Kaffee_Kueche";
                objektStatusErkannt = "aus";
                _mainView.executeText.setText("Schalte Kaffemaschine in Küche aus");
                _mainView.commandRecognized();
                say("Schalte Kaffemaschine in Küche aus");
			} else if (matches.contains("schalosien hoch")) {
                objektErkannt = "Schalosien_Schlafzimmer";
                objektStatusErkannt = "hoch";
                _mainView.executeText.setText("Fahre Schalosien im Schlafzimmer hoch");
                _mainView.commandRecognized();
                say("Fahre Schalosien im Schlafzimmer hoch");
			} else if (matches.contains("schalosien runter")) {
                objektErkannt = "Schalosien_Schlafzimmer";
                objektStatusErkannt = "runter";
                _mainView.executeText.setText("Fahre Schalosien im Schlafzimmer runter");
                _mainView.commandRecognized();
                say("Fahre Schalosien im Schlafzimmer runter");
			}

		}
	}

	public void onPause() {
		_sr.destroy();
	}

	public void onResume() {
		if (SpeechRecognizer.isRecognitionAvailable(_mainView
				.getApplicationContext())) {
			_sr = SpeechRecognizer.createSpeechRecognizer(_mainView
					.getApplicationContext());
			_sr.setRecognitionListener(_speechListener);
			_speechRecognitionIntent = new Intent(
					RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			_speechRecognitionIntent.putExtra(
					RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			_speechRecognitionIntent
					.putExtra(
                            RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,
                            10);
			_speechRecognitionIntent.putExtra(
					RecognizerIntent.EXTRA_CALLING_PACKAGE,
					"com.example.modernhome");
		}
		_sr.startListening(_speechRecognitionIntent);
	}

	@Override
	public void update(Observable observable, Object data) {
		if (data != null) {
			if (data instanceof ArrayList<?>) {
				@SuppressWarnings("unchecked")
				ArrayList<String> temp = (ArrayList<String>) data;
				matchStrings(temp);
			} else if (data instanceof String) {
				String errorMessage = (String) data;
				Log.d("ERROR", errorMessage);
				_buzzWordRecognized = false;
			}
		}
		if (_speechListener.hasSpeechEnded())
			_sr.startListening(_speechRecognitionIntent);

	}

}
