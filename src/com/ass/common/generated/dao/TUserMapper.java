package com.ass.common.generated.dao;

import com.ass.common.generated.model.TUser;
import com.ass.common.generated.model.TUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUserMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	int countByExample(TUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	int deleteByExample(TUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	int insert(TUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	int insertSelective(TUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	List<TUser> selectByExample(TUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	TUser selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TUser record,
			@Param("example") TUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	int updateByExample(@Param("record") TUser record,
			@Param("example") TUserExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	int updateByPrimaryKeySelective(TUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_user
	 * @mbggenerated  Mon Dec 08 16:05:39 CST 2014
	 */
	int updateByPrimaryKey(TUser record);
}