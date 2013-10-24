package com.example.modernhome;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final int _REQUEST_RESULT_CODE = 4711;
	private ListView _resultsListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button speakButton = (Button) findViewById(R.id.speakButton);
        _resultsListView = (ListView) findViewById(R.id.results);
		
		  PackageManager pm = getPackageManager();
	      List<ResolveInfo> activities = pm.queryIntentActivities(
	                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
	        if (activities.size() == 0)
	        {
	            speakButton.setEnabled(false);	            
	        }
		
	}
	
	 public void speakButtonClicked(View v)
	    {
	        startVoiceRecognitionActivity();
	    }
	 
	 private void startVoiceRecognitionActivity()
	    {
	        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
	                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	        startActivityForResult(intent, _REQUEST_RESULT_CODE);
	    }
	 
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == _REQUEST_RESULT_CODE && resultCode == RESULT_OK)
        {
            
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches);
            _resultsListView.setAdapter(arrayAdapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
