package com.ass.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TPropertiesMapper;
import com.ass.common.generated.model.TProperties;
import com.ass.common.generated.model.TPropertiesExample;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;

/**
 * htProperteis 表查询
 * 
 * @author wangt 20140307
 */
@Service
public class PropertiesServiceImpl extends BaseServiceImpl implements
		PropertiesService {

	/** 用于存储字典数据 **/
	private HashMap<String, List<TProperties>> TPropertiesHashMap = new HashMap<String, List<TProperties>>();

	@Resource
	private TPropertiesMapper tPropertiesMapper;

	/**
	 * 该方法主要用于下拉框的键值对的初始化！用于property表。
	 * 表中有三个主要字段
	 * codetype ：类型
	 * code     ：编码
	 * codename ：名称
	 * 查询的时候我们根据
	 * 传入codetype
	 * 返回的map中，code作为key，codename作为value
	 * 
	 */
	@Override
	public Map<String,String> getProMapsByCodeType(String codeType){
		
		List<TProperties>list =this.getPropertiesByCodeType(codeType);
		//LinkedHashMap, 先进先出,由于保证书顺序
		Map<String,String> map =  new LinkedHashMap<String,String>();
		
		for(TProperties Properties:list){
			map.put(Properties.getCode(),Properties.getCodename());
		}
		
		return map;
	}
	
	
	
	
	/**
	 * 根据codetype 获得 properties
	 * 
	 * @author wangt
	 */
	@Override
	public List<TProperties> getPropertiesByCodeType(String codeType) {
		logger.info("查询TProperties,CodeType:" + codeType);

		if (!TPropertiesHashMap.containsKey(codeType) || TPropertiesHashMap.get(codeType) == null	|| TPropertiesHashMap.get(codeType).size() == 0) {
			logger.info("重新加载[" + codeType + "]的缓存数据");
			this.reloadTPropertiesMap(codeType);
			logger.info("加载[" + codeType + "]数据完成，共生成"	+ TPropertiesHashMap.get(codeType).size() + "条数据");
		}
		List<TProperties> copyList = new ArrayList<TProperties>();
		for (TProperties TProperties : (List<TProperties>) TPropertiesHashMap.get(codeType)) {
			copyList.add(TProperties);
		}
		return copyList;
	}
	/**
	 * 获得combobox下拉填充数据，code-->id   codename-->name
	 * @param codeType
	 * @return
	 * @author wangt 2014年11月26日 上午10:09:40 
	 */
	@Override
	public List<Map<String, String>> getComboboxCode2NameByType(String codeType) {
		List<TProperties> lP = this.getPropertiesByCodeType(codeType);
		List<Map<String, String>>  lst= new ArrayList<Map<String, String>>();
		if(lP.size()>0){
			for(TProperties p : lP){
				Map<String, String> m = new HashMap<String, String>();
				m.put("id", p.getCode());
				m.put("name", p.getCodename());
				lst.add(m);
			}
			return lst;
		}
		return null;
	}


	/**
	 * 获得combobox下拉填充数据，id-->id   codename-->name
	 * @param codeType
	 * @return
	 * @author wangt 2014年11月26日 上午10:11:32 
	 */
	@Override
	public List<Map<String, String>> getComboboxId2NameByType(String codeType) {
		List<TProperties> lP = this.getPropertiesByCodeType(codeType);
		List<Map<String, String>>  lst= new ArrayList<Map<String, String>>();
		if(lP.size()>0){
			for(TProperties p : lP){
				Map<String, String> m = new HashMap<String, String>();
				m.put("id", StringUtil.getString(p.getId()));
				m.put("name", p.getCodename());
				lst.add(m);
			}
			return lst;
		}
		return null;
	}


	@Override
	public String getCodeNameByCode(String codeType, String code) {
		logger.info("查询codename, codetype：" + codeType + ";code:" + code);
		List<TProperties> propertiesLst = this.getPropertiesByCodeType(codeType);
		if (propertiesLst != null && propertiesLst.size() > 0) {
			for (TProperties TProperties : propertiesLst) {
				if (TProperties.getCode().equals(code)) {
					return TProperties.getCodename();
				}
			}
		}
		return "";
	}

	@Override
	public String getCodeByName(String codeType, String name) {
		logger.info("查询code, CodeType:" + codeType + ";codeName:" + name);
		List<TProperties> propertiesLst = this
				.getPropertiesByCodeType(codeType);
		if (propertiesLst != null && propertiesLst.size() > 0) {
			for (TProperties TProperties : propertiesLst) {
				if (TProperties.getCodename().equals(name)) {
					return TProperties.getCode();
				}
			}
		}
		return "";
	}

	/**
	 * 根据 codeType 更新字典信息到 TPropertiesHashMap
	 * 
	 * @author wangt
	 * @param codeType
	 */
	public void reloadTPropertiesMap(String codeType) {
		logger.info("开始生成字典[" + codeType + "]信息Map");
		TPropertiesExample example = new TPropertiesExample();
		example.createCriteria().andCodetypeEqualTo(codeType);
		example.setOrderByClause("id ASC, CODE ASC");
		List<TProperties> TPropertiesLst = this.tPropertiesMapper.selectByExample(example);
		if (TPropertiesLst != null && TPropertiesLst.size() > 0) {
			TPropertiesHashMap.put(codeType, TPropertiesLst);
		} else {
			TPropertiesHashMap.put(codeType, new ArrayList<TProperties>());
		}
		logger.info("结束生成字典[" + codeType + "]信息Map");
	}

	/**
	 * 清空TPropertiesHashMap数据
	 * 
	 * @author wangt
	 */
	public void clearTPropertiesMap() {
		if (TPropertiesHashMap != null) {
			TPropertiesHashMap.clear();
			logger.info("清空TPropertiesHashMap数据完成");
		} else {
			logger.info("清空TPropertiesHashMap数据失败");
		}
	}




	
	
}
