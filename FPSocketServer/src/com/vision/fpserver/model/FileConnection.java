package com.vision.fpserver.model;

import java.io.File;
import java.io.PrintWriter;

public class FileConnection {

	
	private String clientName;
	private File file;
	private PrintWriter fileWriter;
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public PrintWriter getFileWriter() {
		return fileWriter;
	}
	public void setFileWriter(PrintWriter fileWriter) {
		this.fileWriter = fileWriter;
	}
	
	
}
