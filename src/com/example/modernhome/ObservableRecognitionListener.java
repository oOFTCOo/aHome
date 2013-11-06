package com.example.modernhome;

import java.util.ArrayList;
import java.util.Observable;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

public class ObservableRecognitionListener extends Observable implements RecognitionListener {

	@Override
	public void onBeginningOfSpeech() {
		// TODO Auto-generated method stub
		Log.d("my", "onBeginningOfSpeech");

	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		// TODO Auto-generated method stub
		Log.d("my", "onBufferReceived");

	}

	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
		Log.d("my", "onEndOfSpeech");
	}

	@Override
	public void onError(int error) {
		switch (error) {
		case 1:
			Log.d("ERROR", "Network operation timed out.");
			break;
		case 2:
			Log.d("ERROR", "Other network related errors.");
			break;
		case 3:
			Log.d("ERROR", "Audio recording error.");
			break;
		case 4:
			Log.d("ERROR", "Server sends error status.");
			break;
		case 5:
			Log.d("ERROR", "Other client side errors.");
			break;
		case 6:
			Log.d("ERROR", "No speech input");
			break;
		case 7:
			Log.d("ERROR", "No recognition result matched. ");
			break;
		case 8:
			Log.d("ERROR", "RecognitionService busy.");
			break;
		case 9:
			Log.d("ERROR", "Insufficient permissions");
			break;

		default:
			Log.d("ERROR", "Unknown Error ");
			break;
		}

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
		// TODO Auto-generated method stub
		Log.d("my", "onReadyForSpeech");
	}

	@Override
	public void onResults(Bundle results) {
		// TODO Auto-generated method stub
		ArrayList<String> temp = results
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		Log.d("my", "onResults");
		this.notifyObservers(temp);
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		// TODO Auto-generated method stub
		Log.d("my", "onRmsChanged");
	}

}
