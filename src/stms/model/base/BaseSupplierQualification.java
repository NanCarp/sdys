package stms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSupplierQualification<M extends BaseSupplierQualification<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setRegistrationCode(java.lang.String registrationCode) {
		set("registration_code", registrationCode);
		return (M)this;
	}

	public java.lang.String getRegistrationCode() {
		return get("registration_code");
	}

	public M setSupplierId(java.lang.Integer supplierId) {
		set("supplier_id", supplierId);
		return (M)this;
	}

	public java.lang.Integer getSupplierId() {
		return get("supplier_id");
	}

	public M setShortName(java.lang.String shortName) {
		set("short_name", shortName);
		return (M)this;
	}

	public java.lang.String getShortName() {
		return get("short_name");
	}

	public M setState(java.lang.Integer state) {
		set("state", state);
		return (M)this;
	}

	public java.lang.Integer getState() {
		return get("state");
	}

	public M setReviewFile(java.lang.String reviewFile) {
		set("review_file", reviewFile);
		return (M)this;
	}

	public java.lang.String getReviewFile() {
		return get("review_file");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

	public M setEntryStaff(java.lang.String entryStaff) {
		set("entry_staff", entryStaff);
		return (M)this;
	}

	public java.lang.String getEntryStaff() {
		return get("entry_staff");
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
