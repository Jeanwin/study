package com.zonekey.study.dao;




import java.util.List;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.Server;

@MyBatisRepository
public interface ServerMapper extends BaseMapper<Server, String> {
	 //查询所有服务器配置
     public List<Server> findAll(); 
     //根据类型查询服务器配置
     public String getServerByType(String type);
     /*
      * 修改服务ip时验证ip是否重复
      * 查询ip不为当前修改记录的所有数据
      */
     public List<Server> findRest(String id);
     /**
      * 获取服务器的地址
      * @param id
      * @return
      */
     public List<String> getServers(String id);
}
