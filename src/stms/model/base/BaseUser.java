package stms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setAccount(java.lang.String account) {
		set("account", account);
		return (M)this;
	}

	public java.lang.String getAccount() {
		return get("account");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}

	public java.lang.String getPassword() {
		return get("password");
	}

	public M setCompanyId(java.lang.Integer companyId) {
		set("company_id", companyId);
		return (M)this;
	}

	public java.lang.Integer getCompanyId() {
		return get("company_id");
	}

	public M setDepartmentId(java.lang.Integer departmentId) {
		set("department_id", departmentId);
		return (M)this;
	}

	public java.lang.Integer getDepartmentId() {
		return get("department_id");
	}

	public M setRoleId(java.lang.Integer roleId) {
		set("role_id", roleId);
		return (M)this;
	}

	public java.lang.Integer getRoleId() {
		return get("role_id");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setReviewTime(java.util.Date reviewTime) {
		set("review_time", reviewTime);
		return (M)this;
	}

	public java.util.Date getReviewTime() {
		return get("review_time");
	}

}
