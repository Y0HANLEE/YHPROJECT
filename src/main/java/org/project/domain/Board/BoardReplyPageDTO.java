package org.project.domain.Board;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class BoardReplyPageDTO {	
	private int replyCnt;
	private List<BoardReplyVO> list;	
}
