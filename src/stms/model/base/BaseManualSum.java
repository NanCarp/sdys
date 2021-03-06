package stms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseManualSum<M extends BaseManualSum<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setManualId(java.lang.String manualId) {
		set("manual_id", manualId);
		return (M)this;
	}

	public java.lang.String getManualId() {
		return get("manual_id");
	}

	public M setOffState(java.lang.Integer offState) {
		set("off_state", offState);
		return (M)this;
	}

	public java.lang.Integer getOffState() {
		return get("off_state");
	}

	public M setModuleSet(java.lang.String moduleSet) {
		set("module_set", moduleSet);
		return (M)this;
	}

	public java.lang.String getModuleSet() {
		return get("module_set");
	}

	public M setPreProductMoney(java.math.BigDecimal preProductMoney) {
		set("pre_product_money", preProductMoney);
		return (M)this;
	}

	public java.math.BigDecimal getPreProductMoney() {
		return get("pre_product_money");
	}

	public M setPreImportMoney(java.math.BigDecimal preImportMoney) {
		set("pre_import_money", preImportMoney);
		return (M)this;
	}

	public java.math.BigDecimal getPreImportMoney() {
		return get("pre_import_money");
	}

	public M setPreMoneyDis(java.lang.Float preMoneyDis) {
		set("pre_money_dis", preMoneyDis);
		return (M)this;
	}

	public java.lang.Float getPreMoneyDis() {
		return get("pre_money_dis");
	}

	public M setActExportMoney(java.math.BigDecimal actExportMoney) {
		set("act_export_money", actExportMoney);
		return (M)this;
	}

	public java.math.BigDecimal getActExportMoney() {
		return get("act_export_money");
	}

	public M setActImportMoney(java.math.BigDecimal actImportMoney) {
		set("act_import_money", actImportMoney);
		return (M)this;
	}

	public java.math.BigDecimal getActImportMoney() {
		return get("act_import_money");
	}

	public M setActMoneyDis(java.lang.Float actMoneyDis) {
		set("act_money_dis", actMoneyDis);
		return (M)this;
	}

	public java.lang.Float getActMoneyDis() {
		return get("act_money_dis");
	}

	public M setExistDate(java.util.Date existDate) {
		set("exist_date", existDate);
		return (M)this;
	}

	public java.util.Date getExistDate() {
		return get("exist_date");
	}

	public M setValidDate(java.lang.Integer validDate) {
		set("valid_date", validDate);
		return (M)this;
	}

	public java.lang.Integer getValidDate() {
		return get("valid_date");
	}

	public M setExtensionDate1(java.util.Date extensionDate1) {
		set("extension_date1", extensionDate1);
		return (M)this;
	}

	public java.util.Date getExtensionDate1() {
		return get("extension_date1");
	}

	public M setExtensionDate2(java.util.Date extensionDate2) {
		set("extension_date2", extensionDate2);
		return (M)this;
	}

	public java.util.Date getExtensionDate2() {
		return get("extension_date2");
	}

	public M setReportVerificateDate(java.util.Date reportVerificateDate) {
		set("report_verificate_date", reportVerificateDate);
		return (M)this;
	}

	public java.util.Date getReportVerificateDate() {
		return get("report_verificate_date");
	}

	public M setCaseOverDate(java.util.Date caseOverDate) {
		set("case_over_date", caseOverDate);
		return (M)this;
	}

	public java.util.Date getCaseOverDate() {
		return get("case_over_date");
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
