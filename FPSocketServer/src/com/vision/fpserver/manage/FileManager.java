package com.vision.fpserver.manage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.vision.fpserver.model.FileConnection;
import com.vision.fpserver.util.PropertyUtil;

public class FileManager {
	
	private Properties currentStatusFileVals=null;
	private OutputStream currentStatusFileOutputStr=null;
	
	private Map<String,FileConnection> fileMessagesMap=new HashMap<String, FileConnection>();
	
	boolean newMessageAdded=false;
	String currentStatusFileLoc=null;
	File currentStatusFile=null;
	InputStream curStatusFileInpStr=null;
	
	File statusHistoryFile=null;
	PrintWriter statusHistoryWriter=null;
	
	public FileManager(){
		try {
			initializeFileManager();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initializeFileManager() throws IOException{
		
		currentStatusFileLoc=PropertyUtil.getInstance()
				.getConfigProperty("fpserver.fpmessages.base_loc")+PropertyUtil.getInstance()
				.getConfigProperty("fpserver.fpmessages.currentstatusfilename");
		String statusHistoryFileLoc=PropertyUtil.getInstance()
		.getConfigProperty("fpserver.fpmessages.base_loc")+PropertyUtil.getInstance()
		.getConfigProperty("fpserver.fpmessages.filename");
		
		System.out.println("Messages Base Loc :"+PropertyUtil.getInstance()
				.getConfigProperty("fpserver.fpmessages.base_loc"));
		
		System.out.println("Current Status Messages File :"+PropertyUtil.getInstance()
				.getConfigProperty("fpserver.fpmessages.currentstatusfilename"));
		
		currentStatusFile=createFile(currentStatusFileLoc);
		curStatusFileInpStr=new FileInputStream(currentStatusFile);
		currentStatusFileVals=new Properties();
		currentStatusFileVals.load(curStatusFileInpStr);
		//currentStatusFileOutputStr=new FileOutputStream(currentStatusFile);
		
		
		
		statusHistoryFile=new File(statusHistoryFileLoc);
		statusHistoryWriter=new PrintWriter(new FileOutputStream( statusHistoryFile,true));
	}
	
	
	private File createFile(String fileName) throws IOException{
		File file=new File(fileName);
		boolean createdFile=file.exists();
		if(!file.exists()){
			createdFile=file.createNewFile();
		}
		System.out.println("Fle Creation status "+file.getAbsolutePath()+":"+createdFile);
		return file;
	}
	
	public void updateCurrentStatusFileMessage(String clientName,String message) throws IOException{
		
		currentStatusFileVals.put(clientName, message);
		newMessageAdded=true;
		//currentStatusFileVals.store(currentStatusFileOutputStr, "Status Updated On"+new Date());
		
		
	}
	
	public void saveCurrentStatusFile() throws IOException{
		//System.out.println("Going to update current status file===>"+newMessageAdded);
		if(newMessageAdded){
			newMessageAdded=false;
			//currentStatusFileVals.store();
			currentStatusFileOutputStr=new FileOutputStream(currentStatusFile);
			currentStatusFileVals.store(currentStatusFileOutputStr, null);
			
			if(curStatusFileInpStr!=null){
				curStatusFileInpStr.close();
			}
			if(currentStatusFileOutputStr!=null){
				currentStatusFileOutputStr.close();
			}
			
			currentStatusFile=createFile(currentStatusFileLoc);
			curStatusFileInpStr=new FileInputStream(currentStatusFile);
			currentStatusFileVals.clear();
			//currentStatusFileVals=new Properties();
			currentStatusFileVals.load(curStatusFileInpStr);
			
		}
		
	}
	
	public void saveFirePanelMessageToFile(String client,String message){
		statusHistoryWriter.println(client+","+message);
		statusHistoryWriter.flush();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		FileManager manager=new FileManager();
		
		for(int i=0;i<10;i++){
		manager.updateCurrentStatusFileMessage("Test1","Val11");
		//manager.updateCurrentStatusFileMessage("Test2","Val2");
		manager.updateCurrentStatusFileMessage("Test3","Val0");
		manager.saveCurrentStatusFile();
		Thread.sleep(3000);
		}
	}
}
