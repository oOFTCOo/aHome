package com.example.modernhome;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

public class AsyncConfigReader extends AsyncTask<String, Void, DeviceParser> {
	private DeviceParser _deviceParser;

	private void readConfig(String path) {
		String configPath = path;
		URL configUrl;
		try {
			configUrl = new URL(configPath);
			URLConnection connection = configUrl.openConnection();
			_deviceParser = new DeviceParser(connection.getInputStream());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected DeviceParser doInBackground(String... params) {
		if (params.length == 1) {
			String path = params[0];
			readConfig(path);
			return _deviceParser;
		}
		return null;
	}

}
