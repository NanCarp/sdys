package stms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSupplier<M extends BaseSupplier<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setYear(java.util.Date year) {
		set("year", year);
		return (M)this;
	}

	public java.util.Date getYear() {
		return get("year");
	}

	public M setSupplierId(java.lang.Integer supplierId) {
		set("supplier_id", supplierId);
		return (M)this;
	}

	public java.lang.Integer getSupplierId() {
		return get("supplier_id");
	}

	public M setContractNo(java.lang.String contractNo) {
		set("contract_no", contractNo);
		return (M)this;
	}

	public java.lang.String getContractNo() {
		return get("contract_no");
	}

	public M setSupplierLevel(java.lang.String supplierLevel) {
		set("supplier_level", supplierLevel);
		return (M)this;
	}

	public java.lang.String getSupplierLevel() {
		return get("supplier_level");
	}

	public M setSupplierField(java.lang.String supplierField) {
		set("supplier_field", supplierField);
		return (M)this;
	}

	public java.lang.String getSupplierField() {
		return get("supplier_field");
	}

	public M setSupplierYears(java.lang.Integer supplierYears) {
		set("supplier_years", supplierYears);
		return (M)this;
	}

	public java.lang.Integer getSupplierYears() {
		return get("supplier_years");
	}

	public M setSupplierBail(java.math.BigDecimal supplierBail) {
		set("supplier_bail", supplierBail);
		return (M)this;
	}

	public java.math.BigDecimal getSupplierBail() {
		return get("supplier_bail");
	}

	public M setPayDate(java.util.Date payDate) {
		set("pay_date", payDate);
		return (M)this;
	}

	public java.util.Date getPayDate() {
		return get("pay_date");
	}

	public M setWithdrawDate(java.util.Date withdrawDate) {
		set("withdraw_date", withdrawDate);
		return (M)this;
	}

	public java.util.Date getWithdrawDate() {
		return get("withdraw_date");
	}

	public M setContact(java.lang.String contact) {
		set("contact", contact);
		return (M)this;
	}

	public java.lang.String getContact() {
		return get("contact");
	}

	public M setPhone(java.lang.String phone) {
		set("phone", phone);
		return (M)this;
	}

	public java.lang.String getPhone() {
		return get("phone");
	}

	public M setEmail(java.lang.String email) {
		set("email", email);
		return (M)this;
	}

	public java.lang.String getEmail() {
		return get("email");
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
