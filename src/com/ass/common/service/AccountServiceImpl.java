package com.ass.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TUserMapper;
import com.ass.common.generated.model.TUser;
import com.ass.common.generated.model.TUserExample;
import com.ass.common.utils.StringUtil;
import com.ass.log.service.UserLogService;



//Spring Service Bean的标识.
@Service
public class AccountServiceImpl extends BaseServiceImpl implements AccountService{

//	@Resource
//	private AuthorityService authorityService;
	@Resource
	private TUserMapper tUserMapper;
	@Resource
	private CommonService commonService;
	@Resource
	private UserLogService userLogService;

	/** 加密策略 */
	public static final String HASH_ALGORITHM = "SHA-1";
	/** 迭代次数 */
	public static final int HASH_INTERATIONS = 1024;
	/** 盐长 */
	private static final int SALT_SIZE = 8;


	/**
	 * 保存新建用户以及其角色
	 * @param tUser
	 * @param roles
	 * @author wangt 2014年11月27日 下午5:32:19 
	 */
	public void saveUser(TUser tUser, String roles) {
		// 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
		// 新增用户默认密码为员工号!!!
		tUser.setPassword(tUser.getLoginName());
		if (StringUtil.isNotEmpty(tUser.getPassword())) {
			encryptPassword(tUser);
		}
		//设置t_organization_code
		String sql2 = "select code from t_organization where id = "+tUser.gettOrganizationId();
		Map<String, Object> mp = commonService.selectOneBySql(sql2);
		tUser.settOrganizationCode(StringUtil.getString(mp.get("code")));
		
		tUserMapper.insertSelective(tUser);
		//设置角色
		if(StringUtil.isNotBlank(roles)){
			String[] roles2 = StringUtil.split(roles, ",");
			for(String s : roles2){
				String sql = "insert into t_user_role set t_user_id = "+tUser.getId()+", t_role_id = "+ s;
				commonService.insertBySql(sql);
			}
			
		}
		//记录日志，暂不使用 TODO
		//userLogService.addUser(tUser);
	}
	
	/**
	 * 保存编辑后的用户信息以及角色
	 * @param tUser
	 * @param roles
	 * @author wangt 2014年11月27日 下午5:32:44 
	 */
	public void editUser(TUser tUser, String roles){
		//编辑用户,不修改密码
		if (StringUtil.isNotEmpty(null)) {
			encryptPassword(tUser);
		}else{
			//设置成null ， 下面的updateByPrimaryKeySelective方法才会不去修改它
			tUser.setPassword(null);
			tUser.setSalt(null);
		}
		//记录日志 暂不使用 TODO
		//userLogService.editUser(tUser);
		//设置t_organization_code
		String sql2 = "select code from t_organization where id = "+tUser.gettOrganizationId();
		Map<String, Object> mp = commonService.selectOneBySql(sql2);
		tUser.settOrganizationCode(StringUtil.getString(mp.get("code")));
		
		
		tUserMapper.updateByPrimaryKeySelective(tUser);
		String sqld = "delete from t_user_role where t_user_id = "+tUser.getId();
		commonService.deleteBySql(sqld);
		
		//设置角色
		
		if(StringUtil.isNotBlank(roles)){
			String[] roles2 = StringUtil.split(roles, ",");
			for(String s : roles2){
				String sql = "insert into t_user_role set t_user_id = "+tUser.getId()+", t_role_id = "+ s;
				commonService.insertBySql(sql);
			}
			
		}
	}
	
	private HashMap<Long, List<String>> permissionMaps = new HashMap<Long, List<String>>();

	/**
	 * 获得用户的所有权限 的permission
	 * 传null为当前登录人
	 * @param id
	 * @return List<String>
	 */
	public List<String> getPermissionsByUserid(Long id) {
		if(id == null){
			id = this.getUser().getId();
		}
		
		//防止每次访问这个方法都要查询数据库
		if (!permissionMaps.containsKey(id) || permissionMaps.get(id) == null ) {
			this.reloadPermissionMaps(id);
		}
		return this.permissionMaps.get(id);
	}
	
	private void reloadPermissionMaps(Long id){
		//数据库中存 admin:add,
		String sql = "select distinct p.permission permission_ from t_user u "
				+ " left join t_user_role ur on ur.t_user_id=u.id "
				+ " left join t_role_permission rp on rp.t_role_id=ur.t_role_id "
				+ " left join t_permission p on p.id = rp.t_permission_id "
				+ " where p.pid!=0 and u.id = "+id;
		List<Map<String, Object>> lst = commonService.selectBySql(sql);
		List<String> ls = new ArrayList<String>();
		for(int i=0; i<lst.size(); i++){
			ls.add(StringUtil.getString(lst.get(i).get("permission_")));
		}
		this.permissionMaps.put(id, ls);
	}
	
	
	
	/**
	 * 获得用户的所有权限 的id
	 * 传null为当前登录人
	 * @param id
	 * @return
	 * @author wangt 2014年11月27日 下午9:44:03 
	 */
	public List<String> getPermissionIdsByUserid(Long id) {
		if(id == null){
			id = this.getUser().getId();
		}
		String sql = "select distinct p.id id_ from t_user u "
				+ " left join t_user_role ur on ur.t_user_id=u.id "
				+ " left join t_role_permission rp on rp.t_role_id=ur.t_role_id "
				+ " left join t_permission p on p.id = rp.t_permission_id "
				+ " where p.pid!=0 and u.id = "+id;
		List<Map<String, Object>> lst = commonService.selectBySql(sql);
		List<String> ls = new ArrayList<String>();
		for(int i=0; i<lst.size(); i++){
			ls.add(StringUtil.getString(lst.get(i).get("id_")));
		}
		return ls;
	}
	
	
	/**
	 * 判断是否是管理员
	 * 传null为当前登录人
	 * @param id
	 * @return
	 * @author wangt 2014年11月27日 下午9:50:15 
	 */
	public boolean isAdmin(Long id){
		if(id == null){
			id = this.getUser().getId();
		}
		List<String> allPermissionId = this.getPermissionIdsByUserid(id);
		if(allPermissionId.contains(this.getProp("admin_permission"))){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否有同级查询权限
	 * 传null为当前登录人
	 * @param id
	 * @return
	 * @author wangt 2014年11月27日 下午9:50:15 
	 */
	public boolean haveSiblingQuery(Long id){
		if(id == null){
			id = this.getUser().getId();
		}
		List<String> allPermissionId = this.getPermissionIdsByUserid(id);
		if(allPermissionId.contains(this.getProp("query_the_sibling"))){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否有查询下级的权限
	 * 传null为当前登录人
	 * @param id
	 * @return
	 * @author wangt 2014年11月27日 下午9:53:12 
	 */
	public boolean haveLowerQuery(Long id){
		if(id == null){
			id = this.getUser().getId();
		}
		List<String> allPermissionId = this.getPermissionIdsByUserid(id);
		if(allPermissionId.contains(this.getProp("query_the_lower"))){
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * 通过登录名获得用户
	 * @param LoginName
	 * @return
	 * @author wangt 2014年11月27日 下午5:33:06 
	 */
	public TUser findUserByLoginName(String LoginName) {
		//适用于所用的单表操作
		//比如在业务层里面是
		TUserExample example = new TUserExample();
		//未删除的用户
		example.createCriteria().andLoginNameEqualTo(LoginName).andIsdeleteEqualTo(0);
		List<TUser> userList = tUserMapper.selectByExample(example);
		if(userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
		
	}
	
	

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void encryptPassword(TUser TUser) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		TUser.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(TUser.getPassword().getBytes(),	salt, HASH_INTERATIONS);
		TUser.setPassword(Encodes.encodeHex(hashPassword));
	}

}
