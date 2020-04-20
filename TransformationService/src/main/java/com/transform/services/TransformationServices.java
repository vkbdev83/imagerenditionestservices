package com.transform.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transform.utils.AppProperties;


@Service
public class TransformationServices {
	
	@Autowired
	public AppProperties appProperties;
	
	public Path transformJpegFile(Path sourceFileLocation) throws IOException, InterruptedException {
		
		UUID uuid = UUID.randomUUID();
		
		Path targetFilePath = Paths.get(appProperties.getWorkdir() + uuid.toString() + ".jpg");
	
		//Command could be Magick/COnvert
		ProcessBuilder builder = new ProcessBuilder("magick" , sourceFileLocation.toString() , 
				targetFilePath.toString());
		
		Process convertProcess = builder.start();
		
		//Thread Wait for completion of Image conversion Procees
		convertProcess.waitFor();
		
		return targetFilePath;
		
		
		
	}

}
