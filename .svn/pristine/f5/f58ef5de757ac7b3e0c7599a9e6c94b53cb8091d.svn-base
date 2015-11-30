package com.zonekey.study.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;

@ContextConfiguration(locations={"/applicationContext.xml"})
public class ResourceServiceTest extends SpringTxTestCase{
	@Resource
	ResourceService reService;
	

	public void floderList(){
		//reService.setTargetCache("ces", "测试", 3, TimeUnit.MINUTES);
		//String str = reService.getTargetCache("ces");
		//System.out.println(str);
		List<Integer> shareListDel = new ArrayList<Integer>();
		shareListDel.add(1);
		shareListDel.add(2);
		shareListDel.add(3);
		
	}
	@Test	
	public void updateHitCount(){
		int result = reService.updateHitCount("201504290061-303");
		System.out.println(result);
	}

}
