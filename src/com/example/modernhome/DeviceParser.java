package com.example.modernhome;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DeviceParser {
	private Document dom = null;
	private XPath pathfinder;
	private ArrayList<String> groups;
	private HashMap<String,String> alterTriggers;
	private HashMap<String,String> queryTriggers;
	private ArrayList<Device> listOfDevices;

	public DeviceParser(InputStream is) {
		super();
		groups = new ArrayList<String>();
		alterTriggers = new HashMap<String,String>();
		queryTriggers = new HashMap<String,String>();
		listOfDevices = new ArrayList<Device>();
		init(is);
	}

	/**
	 * Initialize the DOM, using an opened input stream.
	 * @param is	Opened input stream.
	 */
	public void init(InputStream is) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setCoalescing(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(is);
			pathfinder =  XPathFactory.newInstance().newXPath();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return a list of nodes that match a particular XPath expression.
	 * @param	XPath expression
	 * @return	NodeList of matching nodes.
	 */
	private NodeList queryNodes(String expression) {
		NodeList nodes = null;
		if(dom == null) {
			return null;
		}
		try {
			nodes = (NodeList) pathfinder.compile(expression).
				evaluate(dom, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return nodes;
	}

	/**
	 * Return a singular node that matches a particular XPath expression.
	 * @param	XPath expression
	 * @return	Matching node.
	 */
	private Node queryNode(String expression) {
		if(dom == null) {
			return null;
		}
		Node node = null;
		try {
			node = (Node) pathfinder.compile(expression).
				evaluate(dom, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return node;
	}

	/**
	 * Return a list of predefined group names.
	 * @return	ArrayList of group names.
	 */
	public ArrayList<String> getGroupNames() {
		if(groups.size() != 0) {
			return groups;
		}
		NodeList nodes = queryNodes("//device");
		if(nodes == null) {
			return groups;
		}

		for(int i = 0; i < nodes.getLength(); i++) {
			NamedNodeMap attr = nodes.item(i).getAttributes();
			String name = attr.getNamedItem("group").getNodeValue();
			if(name != null && (! groups.contains(name))) {
				groups.add(name);
			}
		}

		return groups;
	}
	
	public ArrayList<Device> getDevices() {
		NodeList nodes = queryNodes("//device");
		if(nodes == null) {
			return null;
		}

		for(int i = 0; i < nodes.getLength(); i++) {
			NamedNodeMap attr = nodes.item(i).getAttributes();
			String groupName = attr.getNamedItem("group").getNodeValue();
			String deviceId = attr.getNamedItem("id").getNodeValue();
			String deviceName = attr.getNamedItem("name").getNodeValue();
			ArrayList<String> deviceStateIds = this.getStatesOfDevice(deviceId);
				
			
			listOfDevices.add(new Device(deviceId, deviceName, groupName, deviceStateIds));
		}

		return listOfDevices;
	}
	
	/**
	 * Return a list of devices IDs that reside within a particular group.
	 * @param	Name of the enclosing group.
	 * @return	ArrayList of device IDs.
	 */
	public ArrayList<String> getDevicesOfGroup(String group) {
		ArrayList<String> list = new ArrayList<String>();
		NodeList nodes = queryNodes("//device[@group='" + group + "']");
		if(nodes == null) {
			return list;
		}

		for(int i = 0; i < nodes.getLength(); i++) {
			NamedNodeMap attr = nodes.item(i).getAttributes();
			String id = attr.getNamedItem("id").getNodeValue();
			if(id != null && (! list.contains(id))) {
				list.add(id);
			}
		}

		return list;
	}

	/**
	 * Return the name of a device.
	 * @param	ID of the device.
	 * @return	Name of the device, or NULL if the device does not exist.
	 */
	public String getDeviceNameById(String deviceId) {
		Node node = queryNode("//device[@id='" + deviceId + "']/@name");
		return (node == null) ? null : node.getNodeValue();
	}

	/**
	 * Return a list of available states for a particular device.
	 * @param	ID of device.
	 * @return	ArrayList of state IDs.
	 */
	public ArrayList<String> getStatesOfDevice(String deviceId) {
		ArrayList<String> list = new ArrayList<String>();
		NodeList nodes = queryNodes("//device[@id='" + deviceId + "']/state/@id");
		if(nodes == null) {
			return list;
		}

		for(int i = 0; i < nodes.getLength(); i++) {
			list.add(nodes.item(i).getNodeValue());
		}
		return list;
	}

	/**
	 * Return the name of a particular state.
	 * @param	ID of the state.
	 * @return	Name of the state.
	 */
	public String getStateName(String stateId) {
		Node node = queryNode("//state[@id='" + stateId + "']/@name");
		return (node == null) ? null : node.getNodeValue();
	}

	/**
	 * Return the action of a state.
	 * @param	ID of the state.
	 * @return	Action of the state, or NULL if the state does not exist.
	 */
	public String getStateAction(String stateId) {
		Node node = queryNode("//state[@id='" + stateId + "']/action");
		return (node == null) ? null : node.getFirstChild().getNodeValue();
	}
	
	/**
	 * Return the inquiry question of a state.
	 * @param	ID of the state.
	 * @return	Inquiry of the state, if any.
	 */
	public String getStateInquiry(String stateId) {
		Node node = queryNode("//state[@id='" + stateId + "']/inquiry");
		return (node == null) ? null : node.getFirstChild().getNodeValue();
	}

	/**
	 * Return the action required to query the state of a device.
	 * @param deviceId	ID of the device to be queried.
	 * @return	Action of the query, or NULL if the device does not exist.
	 */
	public String getQueryAction(String deviceId) {
		Node node = queryNode("//device[@id='" + deviceId + "']/query/action");
		return (node == null) ? null : node.getFirstChild().getNodeValue();
	}

	/**
	 * Return a character string that contains the verbal status of a device and one of its associated states.
	 * @param deviceId	ID of the device.
	 * @param stateId	ID of the state.
	 * @return	A character string of the constituted state, or NULL.
	 */
	public String getStateReply(String deviceId, String stateId) {
		Node node = queryNode("//device[@id='" + deviceId + "']/query/reply");
		if(node == null) {
			return null;
		}
		String reply = node.getFirstChild().getNodeValue();
		if(reply == null) {
			return null;
		}
		String state = getStateName(stateId);
		if(state == null) {
			return null;
		}
		return reply.replaceAll("#", state);
	}

	/**
	 * Return all commands that alter a particular state.
	 * @param	ID of the triggered state.
	 * @return	ArrayList of commands.
	 */
	public ArrayList<String> getAlterStateTriggers(String stateId) {
		ArrayList<String> list = new ArrayList<String>();
		NodeList nodes = queryNodes("//state[@id='" + stateId + "']/triggers/trigger");
		if(nodes == null) {
			return list;
		}

		for(int i = 0; i < nodes.getLength(); i++) {
			list.add(nodes.item(i).getFirstChild().getNodeValue());
		}
		return list;
	}

	/**
	 * Return all commands that query the state of a device.
	 * @param	ID of the device.
	 * @return	ArrayList of commands to trigger the state.
	 */
	public ArrayList<String> getQueryStateTriggers(String deviceId) {
		ArrayList<String> list = new ArrayList<String>();
		NodeList nodes = queryNodes("//device[@id='" + deviceId + "']/query/triggers/trigger");
		if(nodes == null) {
			return list;
		}

		for(int i = 0; i < nodes.getLength(); i++) {
			list.add(nodes.item(i).getFirstChild().getNodeValue());
		}
		return list;
	}

	/**
	 * Return a hash of all commands that trigger state changes.
	 * @return	HashMap of all triggers.
	 */
	public HashMap<String,String> getAlterTriggers() {
		if(alterTriggers.size() > 0) {
			return alterTriggers;
		}
		NodeList nodes = queryNodes("//state/triggers/trigger");
		if(nodes == null) {
			return alterTriggers;
		}

		try {
			for(int i = 0; i < nodes.getLength(); i++) {
				String key = nodes.item(i).getFirstChild().getNodeValue();
				Node parent = nodes.item(i).getParentNode().getParentNode();
				NamedNodeMap attr = parent.getAttributes();
				String id = attr.getNamedItem("id").getNodeValue();
				if(id != null) {
					alterTriggers.put(key, id);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return alterTriggers;
	}

	/**
	 * Return the state ID of a particular trigger, should it exist.
	 * @param	Command trigger.
	 * @return	ID of the triggered state, or NULL if the command does not trigger a state change.
	 */
	public String getAlterStateTrigger(String trigger) {
		if(alterTriggers.size() == 0) {
			getAlterTriggers();
		}
		return alterTriggers.get(trigger);
	}

	/**
	 * Return a hash of all commands that trigger device queries.
	 * @return	HashMap of all triggers.
	 */
	public HashMap<String,String> getQueryTriggers() {
		if(queryTriggers.size() > 0) {
			return queryTriggers;
		}
		NodeList nodes = queryNodes("//query/triggers/trigger");
		if(nodes == null) {
			return queryTriggers;
		}

		try {
			for(int i = 0; i < nodes.getLength(); i++) {
				String key = nodes.item(i).getFirstChild().getNodeValue();
				Node parent = nodes.item(i).getParentNode().getParentNode().getParentNode();
				NamedNodeMap attr = parent.getAttributes();
				String id = attr.getNamedItem("id").getNodeValue();
				if(id != null) {
					queryTriggers.put(key, id);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return queryTriggers;
	}

	/**
	 * Return the device ID of a particular trigger, should it exist.
	 * @param	Command trigger.
	 * @return	ID of the triggered state, or NULL if the command does not trigger a state query.
	 */
	public String getQueryStateTrigger(String trigger) {
		if(queryTriggers.size() == 0) {
			getQueryTriggers();
		}
		return queryTriggers.get(trigger);
	}

	// End of DeviceParser
}
