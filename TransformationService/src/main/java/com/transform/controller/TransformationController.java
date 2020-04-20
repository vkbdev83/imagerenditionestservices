package com.transform.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.transform.services.TransformationServices;
import com.transform.utils.AppProperties;

@RestController
@RequestMapping("/transformfile")
public class TransformationController {
	
	@Autowired
	public AppProperties appProperties;

	@Autowired
	public TransformationServices services;

	@GetMapping("/")
	public String home() {
		return "Hello File Controller. Try Post Method for File Submission";
	}

	@PostMapping(value = "/jpeg", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<StreamingResponseBody> transformImage(@RequestParam("file") MultipartFile file) 
			throws IOException, InterruptedException {
		
		

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		Path path = Paths.get(appProperties.getWorkdir(),fileName);
		
		
		try(InputStream inputStream = file.getInputStream()){
			
			
			Files.copy(inputStream,path,StandardCopyOption.REPLACE_EXISTING);
			
		}
		
		Path transformFilePath = services.transformJpegFile(path);
		
	
		//Async Response to support large files
		StreamingResponseBody streamingOutput = outputStream -> {
			
			try(InputStream inputStream = new FileInputStream(transformFilePath.toString());outputStream){
				
				StreamUtils.copy(inputStream, outputStream);
			
				System.out.println("WRITE COMPLETE");
			} 
		
			System.out.println("CLOSED STREAM");
		};
		

		return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+
		transformFilePath.getFileName().toString())
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(streamingOutput);

	}


}
