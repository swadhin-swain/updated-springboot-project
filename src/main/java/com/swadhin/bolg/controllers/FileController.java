package com.swadhin.bolg.controllers;


import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StreamUtils;

import com.swadhin.bolg.payloads.FileResponse;
import com.swadhin.bolg.services.impl.FileServiveImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileServiveImpl fileServiveImpl;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(
			@RequestParam("image") MultipartFile image
			) {
		String fileName = null;
		
		try {
			fileName = this.fileServiveImpl.uploadImage(path, image);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null,"Image is not uploaded due to internal server error"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(new FileResponse(fileName,"Image is successfully uploaded"),HttpStatus.OK);
	}
	
	//method to serve file
	
	@GetMapping("/images/{imageName}")
	public void downloadImage(
			@PathVariable String imageName,
			HttpServletResponse response
			) throws IOException {
		
		InputStream resource = this.fileServiveImpl.getResourse(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
