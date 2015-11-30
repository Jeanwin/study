package com.zonekey.study.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.dao.ProcessMapper;
import com.zonekey.study.entity.ProcessInfo;
import com.zonekey.study.vo.ProcessView;

@Service
@Transactional(readOnly = true)
public class ProcessService {
	@Resource
	private ProcessMapper processMapper;

	/**
	 * 查找打点信息列表
	 * 
	 * @param resourceid
	 * @return
	 */
	public List<Map<String,Object>> findProcessList(int resourceid) {
		if (Integer.valueOf(resourceid).toString() != null && !"".equals(Integer.valueOf(resourceid).toString())) {
			return processMapper.processList(resourceid);
		} else {
			return null;
		}
	}

	/**
	 * 添加打点信息
	 * 
	 * @param process
	 * @return
	 */
	@Transactional(readOnly = false)
	public int addProcess(ProcessInfo process) {
		int result = 0;
		System.out.println(processMapper.check(process));
		if (processMapper.check(process) > 0) {
			result = processMapper.update(process);
		} else {
			result = processMapper.insert(process);
		}
		return result;
	}
    /**
     * 删除打点信息
     * @param processList
     * @return
     */
	@Transactional(readOnly = false)
	public int delProcess(List<Integer> processList) {
		if (processList != null && processList.size() > 0) {
			return processMapper.delete(processList);
		} else {
			return 0;
		}
	}
}
