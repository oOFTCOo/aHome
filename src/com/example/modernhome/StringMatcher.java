package com.example.modernhome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.util.Log;

public class StringMatcher {
	private DeviceParser _deviceParser;
	private CommandParser _commandParser;
	private ArrayList<String> _liStates;
	private ArrayList<String> _liDevices;
	private ArrayList<String> _liGroup;
	private ArrayList<Device> _devices;
	private HashMap<String, String> _deviceIdtoState;

	public StringMatcher() {
		_liStates = new ArrayList<String>();
		_liDevices = new ArrayList<String>();
		_liGroup = new ArrayList<String>();
		_deviceIdtoState = new HashMap<String, String>();
		init();
	}

	public String[] getCommand(ArrayList<String> matches) {

		ArrayToCommand atc = new ArrayToCommand();
		String[] saResult = atc.GetCommands(matches, _liStates, _liGroup,
				_liDevices);
		String deviceId = _commandParser.existsLocationDeviceStatus(
				saResult[1], saResult[0], saResult[2]);
		String stateId;
		if (_deviceIdtoState.containsKey(deviceId + saResult[2])) {
			stateId = _deviceIdtoState.get(deviceId + saResult[2]);
			return new String[]{_deviceParser.getStateAction(stateId)};
		}

		return null;

	}

	private void init() {
		try {
			AsyncConfigReader acr = new AsyncConfigReader();
			acr.execute("http://ahome.social-butler.de/config.xml");//.get();
			_deviceParser = acr.get();
			_devices = _deviceParser.getDevices();
			for (Device device : _devices) {
				if (!_liGroup.contains(device.group))
					_liGroup.add(device.group);
				if (!_liGroup.contains(device.name))
					_liDevices.add(device.name);
				for (String state : device.states) {
					String stateName = _deviceParser.getStateName(state);
					if (!_liStates.contains(stateName))
						_liStates.add(stateName);
					if (!_deviceIdtoState.containsKey(device.id + stateName))
						_deviceIdtoState.put(device.id + stateName, state);
				}

			}
			_commandParser = new CommandParser(_deviceParser);
		} catch (InterruptedException e) {
			Log.d("ERROR", e.getMessage());
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			Log.d("ERROR", e.getMessage());
		}

	}

}
