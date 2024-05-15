package org.project.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.project.domain.IntroAttachVO;
import org.project.domain.Album.AlbumAttachVO;
import org.project.domain.Board.BoardAttachVO;
import org.project.domain.User.ProfileImageVO;
import org.project.mapper.Album.AlbumAttachMapper;
import org.project.mapper.Board.BoardAttachMapper;
import org.project.mapper.Intro.IntroAttachMapper;
import org.project.mapper.User.ProfileImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper bmapper;
	
	@Setter(onMethod_ = @Autowired)
	private AlbumAttachMapper amapper;
	
	@Setter(onMethod_ = @Autowired)
	private ProfileImageMapper pmapper;
	
	@Setter(onMethod_ = @Autowired)
	private IntroAttachMapper imapper;
	
	private static final String UPLOAD_PATH = "C:\\upload";
	//private static final String UPLOAD_PATH = "/opt/tomcat/upload"; //AWS
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance(); //��¥��ü
		cal.add(Calendar.DATE, -1); // �Ϸ���
		String str = sdf.format(cal.getTime()); // �Ϸ����� �ð��븦 str�� �ʱ�ȭ
		return str.replace("-", File.separator); // "-" >> "/" ��ü�Ͽ� ����
	}
	
	//DB��Ͽ� ���� ���� ����
	private void deleteFilesNotInDB(List<Path> fileListPaths) {		
        File targetDir = Paths.get(UPLOAD_PATH, getFolderYesterDay()).toFile(); //���������� ���ϵ�(���) 
        File[] removeFiles = targetDir.listFiles(file -> !fileListPaths.contains(file.toPath())); //����Ʈȭ�� DB�� ���ϵ�� ���������� ���ϵ��� ���Ͽ� �ٸ� ���ϵ��� removeFiles�� ����
        // removeFiles����
        if (removeFiles != null) {
            for (File file : removeFiles) {
                log.warn("Delete file: " + file.getAbsolutePath());
                if (file.delete()) { log.warn("File deleted successfully"); } else { log.warn("Failed to delete file"); }
            }
        }
    }
	
	@Scheduled(cron="0 0 2 * * *")// method�����ֱ�(cron = "s(0~59) m(0~59) h(0~23) d(1~31) M(1~12) w(1~7(����)) y(option)") >> ���� ���� 2�� ���� ����.
	public void checkIntroFiles() throws Exception{
		log.warn("Intro File Check Task Run....................");		
		//DB���ϸ��
		List<IntroAttachVO> list = imapper.getOldFiles();		
		//DB���� ����Ʈȭ
		List<Path> fileListPaths = list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), attach.getUuid() + "_" + attach.getFileName())).collect(Collectors.toList());
		//�̹��������� ��� ����ϵ� �Բ� ó��
		list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), "s_"+attach.getUuid()+"_"+attach.getFileName())).forEach(p -> fileListPaths.add(p));
		//�Լ�ȣ��
		deleteFilesNotInDB(fileListPaths);			
	}	
	
	@Scheduled(cron="0 0 2 * * *")
	public void checkBoardFiles() throws Exception{
		log.warn("Board File Check Task Run....................");
		List<BoardAttachVO> list = bmapper.getOldFiles();
		List<Path> fileListPaths = list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), attach.getUuid() + "_" + attach.getFileName())).collect(Collectors.toList());
		list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), "s_"+attach.getUuid()+"_"+attach.getFileName())).forEach(p -> fileListPaths.add(p));		
		deleteFilesNotInDB(fileListPaths);			
	}	
	
	@Scheduled(cron="0 0 2 * * *")// method�����ֱ�(cron = "s(0~59) m(0~59) h(0~23) d(1~31) M(1~12) w(1~7(����)) y(option)") >> ���� ���� 2�� ���� ����.
	public void checkAlbumFiles() throws Exception{
		log.warn("Album File Check Task Run....................");
		List<AlbumAttachVO> alist = amapper.getOldFiles();
		List<Path> fileListPaths = alist.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), attach.getUuid() + "_" + attach.getFileName())).collect(Collectors.toList());
		alist.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), "s_"+attach.getUuid()+"_"+attach.getFileName())).forEach(p -> fileListPaths.add(p));
		deleteFilesNotInDB(fileListPaths);			
	}	
	
	@Scheduled(cron="0 0 2 * * *")// method�����ֱ�(cron = "s(0~59) m(0~59) h(0~23) d(1~31) M(1~12) w(1~7(����)) y(option)") >> ���� ���� 2�� ���� ����.
	public void checkProfileFiles() throws Exception{
		log.warn("Profile File Check Task Run....................");
		List<ProfileImageVO> list = pmapper.getOldFiles();
		List<Path> fileListPaths = list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), attach.getUuid() + "_" + attach.getFileName())).collect(Collectors.toList());
		list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), "s_"+attach.getUuid()+"_"+attach.getFileName())).forEach(p -> fileListPaths.add(p));
		deleteFilesNotInDB(fileListPaths);			
	}
}
