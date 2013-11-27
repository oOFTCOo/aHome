package com.example.modernhome;

import java.util.ArrayList;
import java.util.Observable;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

public class ObservableRecognitionListener extends Observable implements
		RecognitionListener {
	private boolean speechEnded;
	
	public boolean hasSpeechEnded()
	{
		if(speechEnded)
		{
			speechEnded = false;
			return true;
		}
		return false;
	}

	public ObservableRecognitionListener() {
		speechEnded = false;
	}

	@Override
	public void onBeginningOfSpeech() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
			noSpeechCountdown.cancel();
		Log.d("my", "onBeginningOfSpeech");

	}
	
	private CountDownTimer noSpeechCountdown = new CountDownTimer(5000, 5000) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onFinish() {
			notifyObservers();
			
		}
	};

	@Override
	public void onBufferReceived(byte[] buffer) {
		// TODO Auto-generated method stub
		Log.d("my", "onBufferReceived");

	}

	@Override
	public void onEndOfSpeech() {
		Log.d("my", "onEndOfSpeech");
	}

	@Override
	public void onError(int error) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
			noSpeechCountdown.cancel();
		String errorMessage;
		switch (error) {
		case 1:
			errorMessage = "Network operation timed out.";
			break;
		case 2:
			errorMessage = "Other network related errors.";
			break;
		case 3:
			errorMessage = "Audio recording error.";
			break;
		case 4:
			errorMessage = "Server sends error status.";
			break;
		case 5:
			errorMessage = "Other client side errors.";
			break;
		case 6:
			errorMessage ="No speech input";
			break;
		case 7:
			errorMessage ="No recognition result matched.";
			break;
		case 8:
			errorMessage ="RecognitionService busy.";
			break;
		case 9:
			errorMessage ="Insufficient permissions";
			break;

		default:
			errorMessage ="Unknown Error ";
			break;
		}
		speechEnded = true;
		setChanged();
		this.notifyObservers(errorMessage);
		clearChanged();
		

	}

	@Override
	public void onEvent(int eventType, Bundle params) {
		// TODO Auto-generated method stub
		Log.d("my", "onEvent");
	}

	@Override
	public void onPartialResults(Bundle partialResults) {
		// TODO Auto-generated method stub
		Log.d("my", "onPartialResults");
	}

	@Override
	public void onReadyForSpeech(Bundle params) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
			noSpeechCountdown.start();
		Log.d("my", "onReadyForSpeech");
	}

	@Override
	public void onResults(Bundle results) {
		// TODO Auto-generated method stub
		ArrayList<String> temp = results
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		Log.d("my", "onResults");
		speechEnded = true;
		setChanged();
		this.notifyObservers(temp);
		clearChanged();
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		// TODO Auto-generated method stub
		Log.d("my", "onRmsChanged");
	}

}
