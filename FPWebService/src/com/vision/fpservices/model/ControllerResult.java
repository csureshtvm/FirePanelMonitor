package com.vision.fpservices.model;

import java.util.List;

public class ControllerResult {

	private String name;
	private List<Controller> controllers;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the controllers
	 */
	public List<Controller> getControllers() {
		return controllers;
	}

	/**
	 * @param controllers
	 *            the controllers to set
	 */
	public void setControllers(List<Controller> controllers) {
		this.controllers = controllers;
	}

}
