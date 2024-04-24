package org.project.domain.Album;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class AlbumReplyPageDTO {	
	private int replyCnt;
	private List<AlbumReplyVO> list;	
}
