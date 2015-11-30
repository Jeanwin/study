package com.zonekey.study.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.dao.ReviewMapper;

@Service
@Transactional(readOnly = true)
public class ReviewService {
	@Resource
	private ReviewMapper reviewMapper;
	@Transactional(readOnly = true)
	public List<Map<String,Object>> getReviewOptions(){
	    return reviewMapper.getReviewOptions();	
	}
}
