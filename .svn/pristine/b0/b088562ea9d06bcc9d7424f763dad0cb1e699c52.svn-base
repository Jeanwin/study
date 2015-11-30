package com.zonekey.study.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.common.BaseRedis;
import com.zonekey.study.dao.CommentMapper;

@Service
@Transactional(readOnly = true)
public class CommentService extends BaseRedis {
	@Resource
	private CommentMapper commentDao;


}
