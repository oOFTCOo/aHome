package com.example.modernhome;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpCommunication extends AsyncTask<String, Void, HttpResponse> {

	private HttpClient _client;
	private HttpPost _httpPost;
	private HttpResponse _response;
	private String _adress = "http://ahome.social-butler.de/commands.php";
    

	public AsyncHttpCommunication() {
		_client = new DefaultHttpClient();

	}

	private void HttpPost(String Key, String Value) {
		
			_httpPost = new HttpPost(_adress);
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair(Key, Value));
			try {
				_httpPost.setEntity(new UrlEncodedFormEntity(pairs));
				_response = _client.execute(_httpPost);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
 catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}


	}

	@Override
	protected HttpResponse doInBackground(String... params) {
		HttpPost(params[0], params[1]);
        Log.d("aHome",_response.toString());
        return _response;
	}

}
