package com.vision.alarmmonitor.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

public class ImageDownloader
{
	
	public static final String WS_BASE_URL=PropertyUtil.getInstance().getConfigProperty("alarmmonitor.webservice.baseurl");
	public static final String BUILDING_FOLDER_BASEPATH=PropertyUtil.getInstance().getConfigProperty("alarmmonitor.building_loc");
    public static void main(String[] arguments) throws IOException
    {
    	//downloadFile("http://localhost:8080/FPWebService/fpservices/Building.json/images/download/IMG_20120422_141835.jpg");
    }

    private static final int BUFFER_SIZE = 4096;
    private static final Map<String, BufferedImage> buildingImageMap=new HashMap<String, BufferedImage>();
    
    public static BufferedImage retreiveImageFile(String imageFile,String buildingId) throws IOException{
    	
    	String fileName=buildingId+"_"+imageFile;
    	
    	if(buildingImageMap.containsKey(fileName)){
    		return buildingImageMap.get(fileName);
    	}
    	
    	File file=new File(BUILDING_FOLDER_BASEPATH+fileName);
    	if(!file.exists()){
    		downloadFile(imageFile,buildingId);
    	}
    	 BufferedImage image = null;
    	//byte[] fileBytes=null;
    	 System.out.println("File Path==========>"+file+":"+file.exists());
    	if(file.exists()){
    		
    		    //read image
    		    try{
    		     
    		      //image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    		      image = ImageIO.read(file);
    		      if(image!=null && image.getWidth()>0 ){
    		    	  buildingImageMap.put(fileName,image);
    		      }
    		      System.out.println("Reading complete.");
    		    }catch(IOException e){
    		      System.out.println("Error: "+e);
    		    }
    	}
    	
    	return image;
    	
    	
    }

	/**
	 * Downloads a file from a URL
	 * @param fileURL HTTP URL of the file to be downloaded
	 * @param saveDir path of the directory to save the file
	 * @throws IOException
	 */
	public static void downloadFile(String imageFile,String buildingId)
			throws IOException {
		String fileURL=WS_BASE_URL+"Building.json/images/download/"+imageFile;
		URL url = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10,
							disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
						fileURL.length());
			}

			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();

	    	String outputFileName=buildingId+"_"+imageFile;
	    	File file=new File(BUILDING_FOLDER_BASEPATH+outputFileName);
	    	if (!file.exists()) {
				file.createNewFile();
			}
	    	System.out.println("Going to create file===="+file);
			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(file,false);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();
			
			

			//System.out.println("File downloaded=========>"+outputStream.toByteArray().length);
			//return outputStream.toByteArray();
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
		//return null;
	}
}
