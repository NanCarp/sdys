package stms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCompany<M extends BaseCompany<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setCompanyName(java.lang.String companyName) {
		set("company_name", companyName);
		return (M)this;
	}

	public java.lang.String getCompanyName() {
		return get("company_name");
	}

	public M setState(java.lang.Boolean state) {
		set("state", state);
		return (M)this;
	}

	public java.lang.Boolean getState() {
		return get("state");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}

	public java.lang.String getRemark() {
		return get("remark");
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
