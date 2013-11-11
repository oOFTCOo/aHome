package com.example.modernhome;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.WindowManager;

public class Controller implements Observer {

	private SpeechRecognizer _sr;
	private ObservableRecognitionListener _speechListener;
	private Intent _speechRecognitionIntent;
	private MainActivity _mainView;

	public Controller(MainActivity View) {
		_mainView = View;
		_speechListener = new ObservableRecognitionListener();
		_speechListener.addObserver(this);
		_mainView.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private void matchStrings(ArrayList<String> matches) {
		AsyncHttpCommunication communication = new AsyncHttpCommunication();
		if (matches.contains("Licht aus")) {
			communication.execute("Lampe", "aus");
		} else if (matches.contains("Licht an")) {
			communication.execute("Lampe", "an");
		} else if (matches.contains("Kaffee an")) {
			communication.execute("Kaffee", "an");
		} else if (matches.contains("Kaffee aus")) {
			communication.execute("Kaffee", "aus");
		} else if (matches.contains("schalosien hoch")) {
			communication.execute("Schalosien", "hoch");
		} else if (matches.contains("schalosien runter")) {
			communication.execute("Schalosien", "runter");
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
			}
			else if(data instanceof String)
			{
				String errorMessage = (String) data;
				Log.d("ERROR", errorMessage);
			}
		}
		if (_speechListener.hasSpeechEnded())
			_sr.startListening(_speechRecognitionIntent);

	}

}
