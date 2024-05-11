package org.project.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.project.domain.FileDTO;
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
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance(); //날짜객체
		cal.add(Calendar.DATE, -1); // 하루전
		String str = sdf.format(cal.getTime()); // 하루전의 시간대를 str로 초기화
		return str.replace("-", File.separator); // "-" >> "/" 교체하여 리턴
	}
	
	@Scheduled(cron="0 56 * * * *")// method실행주기(cron = "s(0~59) m(0~59) h(0~23) d(1~31) M(1~12) w(1~7(요일)) y(option)") >> 매일 새벽 2시 마다 실행.
	public void checkFiles() throws Exception{
		log.warn("File Check Task Run....................");
		log.warn(".......................................");
		
		// DB의 파일목록
		List<FileDTO> allList = new ArrayList<>();
        allList.addAll(bmapper.getOldFiles());
        allList.addAll(amapper.getOldFiles());
        allList.addAll(pmapper.getOldFiles());
        allList.addAll(imapper.getOldFiles());
		
		// DB의 파일들을 리스트화
		List<Path> fileListPaths = allList.stream().map(attach -> Paths.get("C:\\upload", attach.getUploadPath(), attach.getUuid()+"_"+attach.getFileName())).collect(Collectors.toList());
		
		// 이미지파일의 경우 썸네일도 함꼐 처리
		allList.stream().map(attach -> Paths.get("C:\\upload", attach.getUploadPath(), "s_"+attach.getUuid()+"_"+attach.getFileName())).forEach(p -> fileListPaths.add(p));
		
		deleteFilesNotInDB(fileListPaths);			
	}	
	
	private void deleteFilesNotInDB(List<Path> fileListPaths) {
		// 어제까지의 파일들(경로)
        File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
        // 리스트화한 DB의 파일들과 어제까지의 파일들을 비교하여 다른 파일들을 removeFiles로 모음
        File[] removeFiles = targetDir.listFiles(file -> !fileListPaths.contains(file.toPath()));
        // removeFiles삭제
        if (removeFiles != null) {
            for (File file : removeFiles) {
                log.warn("Deleting file: " + file.getAbsolutePath());
                if (file.delete()) {
                    log.warn("File deleted successfully");
                } else {
                    log.warn("Failed to delete file");
                }
            }
        }
    }
}
