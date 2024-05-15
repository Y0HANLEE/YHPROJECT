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
		Calendar cal = Calendar.getInstance(); //날짜객체
		cal.add(Calendar.DATE, -1); // 하루전
		String str = sdf.format(cal.getTime()); // 하루전의 시간대를 str로 초기화
		return str.replace("-", File.separator); // "-" >> "/" 교체하여 리턴
	}
	
	//DB목록에 없는 파일 삭제
	private void deleteFilesNotInDB(List<Path> fileListPaths) {		
        File targetDir = Paths.get(UPLOAD_PATH, getFolderYesterDay()).toFile(); //어제까지의 파일들(경로) 
        File[] removeFiles = targetDir.listFiles(file -> !fileListPaths.contains(file.toPath())); //리스트화한 DB의 파일들과 어제까지의 파일들을 비교하여 다른 파일들을 removeFiles로 모음
        // removeFiles삭제
        if (removeFiles != null) {
            for (File file : removeFiles) {
                log.warn("Delete file: " + file.getAbsolutePath());
                if (file.delete()) { log.warn("File deleted successfully"); } else { log.warn("Failed to delete file"); }
            }
        }
    }
	
	@Scheduled(cron="0 0 2 * * *")// method실행주기(cron = "s(0~59) m(0~59) h(0~23) d(1~31) M(1~12) w(1~7(요일)) y(option)") >> 매일 새벽 2시 마다 실행.
	public void checkIntroFiles() throws Exception{
		log.warn("Intro File Check Task Run....................");		
		//DB파일목록
		List<IntroAttachVO> list = imapper.getOldFiles();		
		//DB파일 리스트화
		List<Path> fileListPaths = list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), attach.getUuid() + "_" + attach.getFileName())).collect(Collectors.toList());
		//이미지파일의 경우 썸네일도 함꼐 처리
		list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), "s_"+attach.getUuid()+"_"+attach.getFileName())).forEach(p -> fileListPaths.add(p));
		//함수호출
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
	
	@Scheduled(cron="0 0 2 * * *")// method실행주기(cron = "s(0~59) m(0~59) h(0~23) d(1~31) M(1~12) w(1~7(요일)) y(option)") >> 매일 새벽 2시 마다 실행.
	public void checkAlbumFiles() throws Exception{
		log.warn("Album File Check Task Run....................");
		List<AlbumAttachVO> alist = amapper.getOldFiles();
		List<Path> fileListPaths = alist.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), attach.getUuid() + "_" + attach.getFileName())).collect(Collectors.toList());
		alist.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), "s_"+attach.getUuid()+"_"+attach.getFileName())).forEach(p -> fileListPaths.add(p));
		deleteFilesNotInDB(fileListPaths);			
	}	
	
	@Scheduled(cron="0 0 2 * * *")// method실행주기(cron = "s(0~59) m(0~59) h(0~23) d(1~31) M(1~12) w(1~7(요일)) y(option)") >> 매일 새벽 2시 마다 실행.
	public void checkProfileFiles() throws Exception{
		log.warn("Profile File Check Task Run....................");
		List<ProfileImageVO> list = pmapper.getOldFiles();
		List<Path> fileListPaths = list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), attach.getUuid() + "_" + attach.getFileName())).collect(Collectors.toList());
		list.stream().map(attach -> Paths.get(UPLOAD_PATH, attach.getUploadPath(), "s_"+attach.getUuid()+"_"+attach.getFileName())).forEach(p -> fileListPaths.add(p));
		deleteFilesNotInDB(fileListPaths);			
	}
}
