package com.ass.common.generated.dao;

import com.ass.common.generated.model.TLogDetail;
import com.ass.common.generated.model.TLogDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TLogDetailMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int countByExample(TLogDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int deleteByExample(TLogDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int insert(TLogDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int insertSelective(TLogDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	List<TLogDetail> selectByExampleWithBLOBs(TLogDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	List<TLogDetail> selectByExample(TLogDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	TLogDetail selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TLogDetail record,
			@Param("example") TLogDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int updateByExampleWithBLOBs(@Param("record") TLogDetail record,
			@Param("example") TLogDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int updateByExample(@Param("record") TLogDetail record,
			@Param("example") TLogDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int updateByPrimaryKeySelective(TLogDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int updateByPrimaryKeyWithBLOBs(TLogDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_log_detail
	 * @mbggenerated  Wed Nov 26 15:44:32 CST 2014
	 */
	int updateByPrimaryKey(TLogDetail record);
}