package br.com.evaluation.car.rent.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class OperationUtil {
	
	
	/**
	 * Method responsible to check if value passed is null or empty
	 * @param value
	 * @return
	 */
	public static boolean checkValue(Object value) {
		
		if(value == null || value.toString().isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static File convertMultiPartToFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}
}
