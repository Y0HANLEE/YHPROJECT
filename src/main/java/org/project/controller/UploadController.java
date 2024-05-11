
package org.project.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.project.domain.FileDTO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	//private static final String UPLOAD_FOLDER = "/opt/tomcat/upload"; //aws
	//private static final String UPLOAD_PATH = "/opt/tomcat/upload/"; //aws
	private static final String UPLOAD_FOLDER = "C:\\upload"; 
	private static final String UPLOAD_PATH = "C:\\upload\\";
	
	/* ���� ���� ��Ģ */
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-",File.separator);
	}	
	
	/* �̹��� ���� üũ */
	private boolean checkImg(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath()); // ���� ��ο��� Ȯ���ڸ� Ȯ�� �� MIMEŸ������ ��ȯ
			return contentType.startsWith("image"); // image�� �����ϴ��� Ȯ��
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return false; // �⺻�����δ� �̹��� ������ �ƴ� ������ �Ǵ�
	}

	/* ���� ���� ���ε� */	
	@ResponseBody
	@PostMapping("/uploadSingle")
	public ResponseEntity<FileDTO> singleFileUpload(@RequestParam("singleFile") MultipartFile singleFile) {
	    // ���� ������ ������ ��ü ����
	    FileDTO file = new FileDTO();
	    
	    try {
	        // ���ε��� ���� ����
	        String uploadFolder = UPLOAD_FOLDER;
	        File uploadPath = new File(uploadFolder, getFolder());
	        String uploadFolderPath = getFolder();

	        // ���� �̸��� ������ ���ٸ� �� ������ ����
	        if (!uploadPath.exists()) {
	            uploadPath.mkdirs();
	        }

	        // UUID ����
	        String uuid = UUID.randomUUID().toString();

	        // ���ε��� ���ϸ� ����
	        String originalFilename = singleFile.getOriginalFilename();
	        String uploadFileName = uuid + "_" + originalFilename;

	        // IE ������ ���� ��� ó��
	        uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

	        // ���� ����
	        File saveFile = new File(uploadPath, uploadFileName);
	        singleFile.transferTo(saveFile);

	        // ���� ���� ����
	        file.setFileName(originalFilename);
	        file.setUuid(uuid);
	        file.setUploadPath(uploadFolderPath);

	        // �̹������� Ȯ�� �� ����� ����_�������̹����̹Ƿ� �� �۰�
	        if (checkImg(saveFile)) {
	            file.setFileType(true);
	            File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
	            FileOutputStream thumbnail = new FileOutputStream(thumbnailFile);
	            Thumbnailator.createThumbnail(singleFile.getInputStream(), thumbnail, 30, 30);
	            thumbnail.close();
	        }

	        return new ResponseEntity<>(file, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	/* ajax��� �������� ���� + ������ �����(�α���) */	
	@ResponseBody
	@PostMapping(value="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
		List<FileDTO> list = new ArrayList<>();
		String uploadFolder = UPLOAD_FOLDER; // ���ε� �� ���� ���
		File uploadPath = new File(uploadFolder, getFolder()); // �� ������ ���� ��¥ �̸����� �Ѵ�.
		String uploadFolderPath = getFolder(); // ���ε����� �̸�				

		// ���� �̸��� ������ ���ٸ� �� ������ �����Ѵ�.
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}		
		
		for(MultipartFile file : uploadFile) {
			FileDTO attach = new FileDTO();
			log.info("File Name:"+file.getOriginalFilename());
			log.info("File Size:"+file.getSize());
			
			String uploadFileName = file.getOriginalFilename();
			
			//IE������ ���� ���
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			attach.setFileName(uploadFileName);
			
			UUID uuid = UUID.randomUUID(); // UUID(����ID_����)
			
			//�ߺ������� ���� UUID����
			uploadFileName = uuid.toString()+"_"+uploadFileName;			
						
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				file.transferTo(saveFile);
				
				attach.setUuid(uuid.toString());
				attach.setUploadPath(uploadFolderPath);
				
				//fileType(img) check
				if(checkImg(saveFile)) {
					attach.setFileType(true);					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_"+uploadFileName)); //������� "s_uuid_���ϸ�"���� ����(outputstream-���)					
					Thumbnailator.createThumbnail(file.getInputStream(), thumbnail, 100, 100);//��������ϸ�, 100*100������� ����� ����(inputstream-�Է�)					
					thumbnail.close();
				}				
				list.add(attach);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
		
	/* ����� �����ϱ� */
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		File file = new File(UPLOAD_PATH+fileName);		
		
		ResponseEntity<byte[]> result = null; // byte[]�� ���� ������ �ѱ�� ����.
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	/* �ٿ�ε� */
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){		
		Resource resource = new FileSystemResource(UPLOAD_PATH+fileName); //�ٿ�ε��� ����
		//Resource resource = new FileSystemResource("/opt/tomcat/upload/"+fileName); //aws
		
		if (resource.exists() == false) {// �ٿ�ε��� ������ ���ٸ� 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404����
		}
		
		String resourceName = resource.getFilename(); // �ٿ�ε��� ������ �̸�
		
		//UUID���� : ��ü�̸� �� "_" �������ں��� ���� >> (s_)uuid_filename > filename(��¥�̸�)
		String resourceOriginName = resourceName.substring(resourceName.indexOf("_")+1);  
		
		HttpHeaders headers = new HttpHeaders(); // ���
		
		try {
			String downloadName = null; //�ٿ�ε��̸� ����(���������� ����)
			if(userAgent.contains("Trident")){ //IE
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginName,"UTF-8").
				replaceAll("\\+", " ");
			} else if(userAgent.contains("Edge")) { //Edge
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginName,"UTF-8");
				log.info("Edge name: " + downloadName);
			}else {
				log.info("Chrome browser"); //Chrome
				downloadName = new String(resourceOriginName.getBytes("UTF-8"), "ISO-8859-1");
			}
			headers.add("Content-Disposition", "attachment; filename="+downloadName); //����� ��������
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	/* ���ϻ��� */	
	@ResponseBody
	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName, String type){
		//�⺻������ ������� ��� : �Խ����� ÷�����ϸ�Ͽ� �ִ� ������. (register, modify���� ���)
		log.info("[UploadController]Delete file-------------"+fileName);
		File file;
		
		try {
			file = new File(UPLOAD_PATH+URLDecoder.decode(fileName, "UTF-8"));
			//file = new File("/opt/tomcat/upload/"+URLDecoder.decode(fileName, "UTF-8")); //aws
			
			file.delete();// �Ϲ������� ��� ��ο� �ش��ϴ� ���� �ٷ� ����
			if(type.equals("image")) { // �̹��� Ÿ���� �����̶��,
				String largeFileName = file.getAbsolutePath().replace("s_", ""); // �����ο��� s_�� ��������.
				file = new File(largeFileName); // largeFileName�� �� File��ü�� ����
				file.delete(); // ���ϻ��� : �������� + largeFile(s_�� ���� �����) ��� ����
				log.info("[UploadController]---------------------------���"+file);
				log.info("[UploadController]---------------------------��������");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}	
}
