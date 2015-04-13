package com.ass.shiro.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Encodes;

import com.ass.common.generated.model.TUser;
import com.ass.common.service.AccountService;
import com.ass.common.service.AccountServiceImpl;
import com.ass.common.utils.StringUtil;
import com.ass.shiro.dto.CurUser;

/**
 * 
 * @author wangt 2014年11月17日 下午6:06:41 
 */
@Component
public class MyShiroDbRealm extends AuthorizingRealm {
	
	@Resource
	protected AccountService accountService;
	
	/**
	 * 认证回调函数,登录时调用. 获取认证信息added by wangt
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		TUser tUser = accountService.findUserByLoginName(token.getUsername());
		if (tUser != null) {
			byte[] salt = Encodes.decodeHex(tUser.getSalt());
			
			//设置同级，下级查询权限
			boolean siblingQuery = accountService.haveSiblingQuery(tUser.getId());
			boolean lowerQuery = accountService.haveLowerQuery(tUser.getId());
			String queryLevel = "none";
			if(siblingQuery == true && lowerQuery == true){
				queryLevel = "all";
			}
			if(siblingQuery == true && lowerQuery == false){
				queryLevel = "sibling";
			}
			if(siblingQuery == false && lowerQuery == true){
				queryLevel = "lower";
			}
			
			SimpleAuthenticationInfo s = new SimpleAuthenticationInfo(new CurUser(tUser,queryLevel),tUser.getPassword(), ByteSource.Util.bytes(salt), getName());
			return s;
		} else {
			return null;
		}
	}
	
	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用. 获取授权信息 added by wangt
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		CurUser curUser = (CurUser) principals.getPrimaryPrincipal();
		
		//此处获取用户权限的List
		List<String> lst = accountService.getPermissionsByUserid(curUser.getId());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 设置用户的权限
		info.addStringPermissions(lst);
		
		//获得用户所有的角色
		Set<String> roles = new HashSet<String>();
		for(String str : lst){
			if(StringUtil.isNotBlank(str)){
				roles.add(StringUtil.split(str,":")[0]);
			}
		}
		//设置角色       实际使用尽量用权限。
		info.addRoles(roles);
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AccountServiceImpl.HASH_ALGORITHM);
		matcher.setHashIterations(AccountServiceImpl.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	

}
