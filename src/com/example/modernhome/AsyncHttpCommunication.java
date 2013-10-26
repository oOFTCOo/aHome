package com.example.modernhome;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpCommunication extends AsyncTask<String, Void, Void> {

	private HttpClient _client;
	private HttpPost _httpPost;
	private HttpResponse _response;
	private String _adress = "http://ahome.social-butler.de/command.php";

	public AsyncHttpCommunication() {
		_client = new DefaultHttpClient();

	}

	private void HttpPost(String Key, String Value) {
		
			_httpPost = new HttpPost(_adress + "?" + Key + "=" + Value );
			
			//List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			//pairs.add(new BasicNameValuePair(Key, Value));
			try {
				//_httpPost.setEntity(new UrlEncodedFormEntity(pairs));
				_response = _client.execute(_httpPost);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//_response = _client.execute(_httpPost);
 catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}

	@Override
	protected Void doInBackground(String... params) {
		HttpPost(params[0], params[1]);
		return null;
	}

}
