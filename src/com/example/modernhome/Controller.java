package com.example.modernhome;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class Controller implements Observer {

	private SpeechRecognizer _sr;
	private ObservableRecognitionListener _speechListener;
	private Intent _speechRecognitionIntent;
	private MainActivity _mainView;
	private AudioManager _audioManager;
	private boolean _buzzWordRecognized;
	private SoundPool _soundPool;
	private int _sound;

	public Controller(MainActivity View) {
		_mainView = View;
		_soundPool = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
		_sound = _soundPool.load(_mainView, R.raw.ding, 1);
		_buzzWordRecognized = false;
		_speechListener = new ObservableRecognitionListener();
		_speechListener.addObserver(this);
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

	private void matchStrings(ArrayList<String> matches) {
		AsyncHttpCommunication communication = new AsyncHttpCommunication();

		if (matches.contains("okay Zuhause")) {
			_mainView.buzzWordRecognized();
			_buzzWordRecognized = true;
			//_audioManager.playSoundEffect(AudioManager.FX_FOCUS_NAVIGATION_UP, 100);		
			_soundPool.play(_sound, 1, 1, 1, 0, 1);
			_sound = _soundPool.load(_mainView, R.raw.ding, 1);
		}
		else if (_buzzWordRecognized) {
			if (matches.contains("Licht aus")) {
				communication.execute("Lampe", "aus");
                _mainView.executeText.setText("Schalte Licht aus");
                _mainView.executeText.setVisibility(View.VISIBLE);
                _mainView.okText.setVisibility(View.INVISIBLE);

			} else if (matches.contains("Licht an")) {
				communication.execute("Lampe", "an");
                _mainView.executeText.setText("Schalte Licht ein");
                _mainView.executeText.setVisibility(View.VISIBLE);
                _mainView.okText.setVisibility(View.INVISIBLE);

			} else if (matches.contains("Kaffee an")) {
				communication.execute("Kaffee", "an");
                _mainView.executeText.setText("Schalte Kaffemaschine an");
                _mainView.executeText.setVisibility(View.VISIBLE);
                _mainView.okText.setVisibility(View.INVISIBLE);

			} else if (matches.contains("Kaffee aus")) {
				communication.execute("Kaffee", "aus");
                _mainView.executeText.setText("Schalte Kaffemaschine aus");
                _mainView.executeText.setVisibility(View.VISIBLE);
                _mainView.okText.setVisibility(View.INVISIBLE);

			} else if (matches.contains("schalosien hoch")) {
				communication.execute("Schalosien", "hoch");
                _mainView.executeText.setText("Fahre Schalosien hoch");
                _mainView.executeText.setVisibility(View.VISIBLE);
                _mainView.okText.setVisibility(View.INVISIBLE);

			} else if (matches.contains("schalosien runter")) {
				communication.execute("Schalosien", "runter");
                _mainView.executeText.setText("Fahre Schalosien runter");
                _mainView.executeText.setVisibility(View.VISIBLE);
                _mainView.okText.setVisibility(View.INVISIBLE);

			}
			_buzzWordRecognized = false;
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
			_speechRecognitionIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 10);
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
