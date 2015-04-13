package com.ass.common.generated.model;

public class TRolePermission {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.id
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.t_role_id
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    private Long tRoleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.t_permission_id
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    private Long tPermissionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.make_time
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    private Integer makeTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.operator
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    private String operator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.modify_time
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    private Integer modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.modify_operator
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    private String modifyOperator;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.id
     *
     * @return the value of t_role_permission.id
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.id
     *
     * @param id the value for t_role_permission.id
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.t_role_id
     *
     * @return the value of t_role_permission.t_role_id
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public Long gettRoleId() {
        return tRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.t_role_id
     *
     * @param tRoleId the value for t_role_permission.t_role_id
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public void settRoleId(Long tRoleId) {
        this.tRoleId = tRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.t_permission_id
     *
     * @return the value of t_role_permission.t_permission_id
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public Long gettPermissionId() {
        return tPermissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.t_permission_id
     *
     * @param tPermissionId the value for t_role_permission.t_permission_id
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public void settPermissionId(Long tPermissionId) {
        this.tPermissionId = tPermissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.make_time
     *
     * @return the value of t_role_permission.make_time
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public Integer getMakeTime() {
        return makeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.make_time
     *
     * @param makeTime the value for t_role_permission.make_time
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public void setMakeTime(Integer makeTime) {
        this.makeTime = makeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.operator
     *
     * @return the value of t_role_permission.operator
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public String getOperator() {
        return operator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.operator
     *
     * @param operator the value for t_role_permission.operator
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.modify_time
     *
     * @return the value of t_role_permission.modify_time
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public Integer getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.modify_time
     *
     * @param modifyTime the value for t_role_permission.modify_time
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.modify_operator
     *
     * @return the value of t_role_permission.modify_operator
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public String getModifyOperator() {
        return modifyOperator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.modify_operator
     *
     * @param modifyOperator the value for t_role_permission.modify_operator
     *
     * @mbggenerated Tue Nov 18 08:58:40 CST 2014
     */
    public void setModifyOperator(String modifyOperator) {
        this.modifyOperator = modifyOperator == null ? null : modifyOperator.trim();
    }
}