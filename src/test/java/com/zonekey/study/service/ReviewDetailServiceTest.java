package com.zonekey.study.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;

@ContextConfiguration(locations={"/applicationContext.xml"})
public class ReviewDetailServiceTest extends SpringTxTestCase{
	@Resource
    ReviewDetailService rdService;	
	@Test
	public void testAll(){
		List<Integer> worksIds = new ArrayList<Integer>();
		worksIds.add(1);
		worksIds.add(2);
		worksIds.add(3);
		worksIds.add(4);
		worksIds.add(5);
		List<String> specialist = new ArrayList<String>();
		specialist.add("admin");
		specialist.add("123456");
		rdService.assingnSpecialist(worksIds, specialist, 2);
		rdService.findDetailByWorkId(1);
		//System.out.println(JsonUtil.toJson(rdService.findDetailByWorkId(1)));
	}
}
