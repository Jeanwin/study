package com.zonekey.study.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.dao.ActiveMapper;
import com.zonekey.study.dao.ReviewDetailMapper;
import com.zonekey.study.vo.ActiveView;
import com.zonekey.study.vo.ReviewDetailView;

@Service
@Transactional(readOnly = true)
public class ReviewDetailService {
	@Resource
	private ReviewDetailMapper rdMapper;
	@Resource
	private ActiveMapper acMapper;
	private static final Logger LOG = LoggerFactory.getLogger(ReviewDetailService.class);

	/**
	 * 分配专家,可以选择多个作品进行分配 若选择的作品已经分配专家，会将原来的评审专家清空 重新分配
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public int assingnSpecialist(List<Integer> worksIds, List<String> specialist, int activeid) {
		if (worksIds == null || worksIds.size() == 0 || specialist == null || specialist.size() ==0 || Integer.valueOf(activeid) == null || "".equals(Integer.valueOf(activeid).toString())) {
			return 0;
		} else {
			ActiveView acview = acMapper.findActiveById(activeid);
			List<ReviewDetailView> viewAdd = new ArrayList<ReviewDetailView>();
			// 遍历作品id
			for (Integer workid : worksIds) {
				//根据做品id查询原来该作品的专家分配记录
				List<ReviewDetailView> viewOld = rdMapper.findByWorksId(workid);
				List<String> spe = new ArrayList<String>();
				List<String> speRemove = new ArrayList<String>();
				//从原来添加的专家列表中剔除新添加专家已有的记录，这部分记录必须删除
				if(viewOld != null && viewOld.size() >0){
					for (ReviewDetailView rv : viewOld) {
						spe.add(rv.getUserid());
					}
					for (String a : spe) {
						//如果新添加的专家列表中不包含原有的专家
						if(!specialist.contains(a)){
							//检验原来的专家是否已评审
							String isover = rdMapper.checkIsOver(workid,a);
							if(isover != null && !"".equals(isover)){
								//如果未评
								if("0".equals(isover)){
									speRemove.add(a);
								}
							}
						}
					}
					if(speRemove != null && speRemove.size() > 0){
						//根据作品id和专家登录名移除分配记录
						rdMapper.delByWorkIdAndSpe(workid,speRemove);	
					}
				}
				// 每个专家对应一条记录
				for (String speciaer : specialist) {
					//根据作品id,专家登录名验证是否已评审
					String isover = rdMapper.checkIsOver(workid,speciaer);
					//如果原来已经分配
					if(isover != null && !"".equals(isover)){
						//如果还没有评审
						if("0".equals(isover)){
					    	LOG.info("专家:"+speciaer+"已经被分配评审作品:"+workid);
					    }
						//如果已经评审
						else{
					    	LOG.info("专家:"+speciaer+"已经评审了作品:"+workid);
					    } 
					}
					//原来未分配，插入分配记录
					else{
						ReviewDetailView view = new ReviewDetailView();
						view.setWorksid(workid);
						view.setUserid(speciaer);
						view.setTemplateid(acview.getContemplate());
						view.setIsover("0");
						viewAdd.add(view);
					}
				}
			}
			int addViewCount = 0;
			if( viewAdd.size() > 0 ){
				addViewCount = rdMapper.insertDetails(viewAdd);
			}
		    return addViewCount;
		}
	}

	/**
	 * 根据作品id查询作品评审详情
	 * 
	 * @param workid
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ReviewDetailView> findDetailByWorkId(int workid) {
		if (Integer.valueOf(workid) != null && !"".equals(Integer.valueOf(workid).toString())) {
			return rdMapper.findDetailByWorkId(workid);
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @param workid
	 * @return
	@Transactional(readOnly = true)
	 */
	public ReviewDetailView findReviewByUser(int workid) {
		if (Integer.valueOf(workid) != null && !"".equals(Integer.valueOf(workid).toString())) {
			return rdMapper.findReviewByUser(workid);
		}else{
			return null;
		}
	}
}
