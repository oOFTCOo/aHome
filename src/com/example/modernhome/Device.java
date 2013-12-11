package com.example.modernhome;

import java.util.ArrayList;

public class Device {
	String name;
	String id;
	String group;
	ArrayList<String> states;
	
	public Device(String id, String name, String group, ArrayList<String> state)
	{
		this.id = id;
		this.group = group;
		this.name = name;
		this.states = state;
		
	}

}
