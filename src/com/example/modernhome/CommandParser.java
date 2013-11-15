package com.example.modernhome;

import java.util.ArrayList;

/**
 * Resolves recognized command words.
 * @author pm
 */
public class CommandParser {
	private static boolean TO_LOWER_CASE = false;
	private DeviceParser parser;
	private ArrayList<String> groups;

	public CommandParser(DeviceParser config) {
		parser = config;
		groups = parser.getGroupNames();
	}

	/**
	 * Determines whether a tuple of a certain location, device, and status combination exists. 
	 * @param location	Literal location.
	 * @param device	Literal device.
	 * @param status	Literal status.
	 * @return	Whether or not the combination of the specified arguments exists.
	 */
	public boolean existsLocationDeviceStatus(String location, String device, String status) {
		if(TO_LOWER_CASE) {
			location = location.toLowerCase();
			device = device.toLowerCase();
			status = status.toLowerCase();
		}
		
		if(! groups.contains(location))
			return false;

		// Traverse devices of specified location, i. e. group.
		// We assume that the group attribute equals the location.
		ArrayList<String> devices = parser.getDevicesOfGroup(location);
		boolean found = false;
		String deviceId = null;
		for(String item: devices) {
			try {
				String name = parser.getDeviceNameById(item);
				if(name.equals(device)) {
					found = true;
					deviceId = item;
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				return false;
			}
		}
		if(! found)
			return false;

		// Check states of device
		ArrayList<String> states = parser.getStatesOfDevice(deviceId);
		for(String item: states) {
			try {
				String name = parser.getStateName(item);
				if(name.equals(status)) {
					return true;
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
				return false;
			}
		}

		// Combination not found.
		return false;
	}
}
