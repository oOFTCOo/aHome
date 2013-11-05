package com.example.modernhome;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final int _REQUEST_RESULT_CODE = 4711;
	private ListView _resultsListView;
	private SpeechRecognizer sr;
	private ContinuousRecognitionListener crl;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_resultsListView = (ListView) findViewById(R.id.results);

	}

	public void speakButtonClicked(View v) {

		startVoiceRecognitionActivity();
	}

	private void startVoiceRecognitionActivity() {
		if (SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {
			sr =  SpeechRecognizer
					.createSpeechRecognizer(getApplicationContext());
			crl = new ContinuousRecognitionListener();
			sr.setRecognitionListener(crl);
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
					"com.example.modernhome");

			// intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
			/* startActivityForResult(intent, _REQUEST_RESULT_CODE); */
			sr.startListening(intent);
			Log.i("111", "111");
		}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == _REQUEST_RESULT_CODE && resultCode == RESULT_OK) {

			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, matches);
			_resultsListView.setAdapter(arrayAdapter);

			matchStrings(matches);

		}
		super.onActivityResult(requestCode, resultCode, data);

	}


}
