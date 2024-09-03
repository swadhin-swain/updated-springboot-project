package com.swadhin.bolg.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	
	private int pageNumber;
	
	private int pageSize;
	
	private Long totalElemnts;
	
	private int totalPages;
	
	private boolean lastPage;
	
}
