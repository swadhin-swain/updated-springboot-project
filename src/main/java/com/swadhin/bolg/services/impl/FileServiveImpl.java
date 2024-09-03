package com.swadhin.bolg.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.swadhin.bolg.services.FileService;

@Service
public class FileServiveImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		
		//file name
		String name = file.getOriginalFilename();
		
		// random name generate file
		String randomId = UUID.randomUUID().toString();
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		
		
		//full path
		String filePath = path+File.separator+fileName1;
		
		
		//create file if not create
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir(); // create a file if not exists
		}
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName1;
	}

	@Override
	public InputStream getResourse(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path+File.separator+fileName;
		
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
