package com.zonekey.study.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.ProcessInfo;
import com.zonekey.study.vo.ProcessView;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ProcessMapperTest extends SpringTxTestCase {
	@Resource
	private ProcessMapper pMapper;
	@Test
	public void testCRUD(){
		List<ProcessInfo> ps = new ArrayList<ProcessInfo>();
		List<ProcessView> pv = new ArrayList<ProcessView>();
		ProcessView pv1 = new ProcessView();
		ProcessView pv2 = new ProcessView();
		ProcessInfo p1 = new ProcessInfo();
		ProcessInfo p2 = new ProcessInfo();
		ps.add(p1);
		ps.add(p2);
		pv.add(pv1);
		pv.add(pv2);
		p1.setContent("ceshi1");
		p1.setResourceid(1);
		p1.setUserid("admin");
		p2.setResourceid(1);
		p2.setUserid("admin");
		p2.setContent("ceshi2");
		pMapper.check(p1);
		pMapper.insert(p1);
		pMapper.insertAll(ps);
		pMapper.update(p2);
		pMapper.processList(1);
		pMapper.delete(ps);
    		
	}

}
