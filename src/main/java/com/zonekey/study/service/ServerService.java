package com.zonekey.study.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zonekey.study.dao.ServerMapper;
import com.zonekey.study.entity.Server;

@Service
@Transactional(readOnly = true)
public class ServerService {
	@Autowired
	ServerMapper serverMapper;

	// 查找所有服务器配置
	@Transactional(readOnly = false)
	public List<Server> findAll() {
		return serverMapper.findAll();
	}

	// 根据服务器类型查找服务器
	@Transactional(readOnly = false)
	public String getByType(String type) {
		return serverMapper.getServerByType(type);
	}

	// 查找网络服务器
	@Transactional(readOnly = false)
	public String getWebServer(String type) {
		return serverMapper.getServerByType(type);
	}

	// 查找服务地址
	@Transactional(readOnly = true)
	public String getServer(String type,String suffix) {
		List<String> ips = serverMapper.getServers(type);
		return ips.get(0)+suffix;
	}
}
