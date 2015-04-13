package com.ass.common.service;

import java.util.List;
import java.util.Map;

import com.ass.common.generated.model.TProperties;


/**
 * 对htProperties的操作
 * 
 * @author wangt 20140308
 * 
 */
public interface PropertiesService {

	
	/**
	 * 根据codetype获得properties
	 * 
	 * @param codeType
	 *            codetype
	 * @return List<HtProperties>
	 */
	public List<TProperties> getPropertiesByCodeType(String codeType);

	
	/**
	 * 获得combobox下拉填充数据，code-->id   codename-->name
	 * @param codeType
	 * @return
	 * @author wangt 2014年11月26日 上午10:09:40 
	 */
	public List<Map<String,String>> getComboboxCode2NameByType(String codeType);
	
	/**
	 * 获得combobox下拉填充数据，id-->id   codename-->name
	 * @param codeType
	 * @return
	 * @author wangt 2014年11月26日 上午10:11:32 
	 */
	public List<Map<String,String>> getComboboxId2NameByType(String codeType);
	
	/**
	 * 根据codetype和code获得名称
	 * 
	 * @param codeType
	 *            codetype
	 * @param code
	 *            code
	 * @return codename
	 */
	public String getCodeNameByCode(String codeType, String code);

	/**
	 * 根据codetype和name获得code
	 * 
	 * @param codeType
	 *            codetype
	 * @param code
	 *            code
	 * @return codename
	 */
	public String getCodeByName(String codeType, String name);
	
	/**
	 * 获取键值对Map<code,codename>
	 * @author G
	 */
	public Map<String,String>getProMapsByCodeType(String codeType);
}
