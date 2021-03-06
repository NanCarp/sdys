package stms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseManualProduct<M extends BaseManualProduct<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setProductRecordNum(java.lang.String productRecordNum) {
		set("product_record_num", productRecordNum);
		return (M)this;
	}

	public java.lang.String getProductRecordNum() {
		return get("product_record_num");
	}

	public M setProductCodeAndExtra(java.lang.String productCodeAndExtra) {
		set("product_code_and_extra", productCodeAndExtra);
		return (M)this;
	}

	public java.lang.String getProductCodeAndExtra() {
		return get("product_code_and_extra");
	}

	public M setProductName(java.lang.String productName) {
		set("product_name", productName);
		return (M)this;
	}

	public java.lang.String getProductName() {
		return get("product_name");
	}

	public M setProductSpecification(java.lang.String productSpecification) {
		set("product_specification", productSpecification);
		return (M)this;
	}

	public java.lang.String getProductSpecification() {
		return get("product_specification");
	}

	public M setProductUnit(java.lang.String productUnit) {
		set("product_unit", productUnit);
		return (M)this;
	}

	public java.lang.String getProductUnit() {
		return get("product_unit");
	}

	public M setProductFixedUnit(java.lang.String productFixedUnit) {
		set("product_fixed_unit", productFixedUnit);
		return (M)this;
	}

	public java.lang.String getProductFixedUnit() {
		return get("product_fixed_unit");
	}

	public M setProductReportNum(java.math.BigDecimal productReportNum) {
		set("product_report_num", productReportNum);
		return (M)this;
	}

	public java.math.BigDecimal getProductReportNum() {
		return get("product_report_num");
	}

	public M setProductReportUnitPrice(java.math.BigDecimal productReportUnitPrice) {
		set("product_report_unit_price", productReportUnitPrice);
		return (M)this;
	}

	public java.math.BigDecimal getProductReportUnitPrice() {
		return get("product_report_unit_price");
	}

	public M setProductReportTotalPrice(java.math.BigDecimal productReportTotalPrice) {
		set("product_report_total_price", productReportTotalPrice);
		return (M)this;
	}

	public java.math.BigDecimal getProductReportTotalPrice() {
		return get("product_report_total_price");
	}

	public M setCurrencySystem(java.lang.String currencySystem) {
		set("currency_system", currencySystem);
		return (M)this;
	}

	public java.lang.String getCurrencySystem() {
		return get("currency_system");
	}

	public M setProductProMarker(java.lang.String productProMarker) {
		set("product_pro_marker", productProMarker);
		return (M)this;
	}

	public java.lang.String getProductProMarker() {
		return get("product_pro_marker");
	}

	public M setFixUnitRatio(java.math.BigDecimal fixUnitRatio) {
		set("fix_unit_ratio", fixUnitRatio);
		return (M)this;
	}

	public java.math.BigDecimal getFixUnitRatio() {
		return get("fix_unit_ratio");
	}

	public M setProductLevyMode(java.lang.String productLevyMode) {
		set("product_levy_mode", productLevyMode);
		return (M)this;
	}

	public java.lang.String getProductLevyMode() {
		return get("product_levy_mode");
	}

	public M setProductHandleFlag(java.lang.String productHandleFlag) {
		set("product_handle_flag", productHandleFlag);
		return (M)this;
	}

	public java.lang.String getProductHandleFlag() {
		return get("product_handle_flag");
	}

	public M setProductReportState(java.lang.String productReportState) {
		set("product_report_state", productReportState);
		return (M)this;
	}

	public java.lang.String getProductReportState() {
		return get("product_report_state");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

	public M setVersion(java.lang.String version) {
		set("version", version);
		return (M)this;
	}

	public java.lang.String getVersion() {
		return get("version");
	}

	public M setManualNo(java.lang.String manualNo) {
		set("manual_no", manualNo);
		return (M)this;
	}

	public java.lang.String getManualNo() {
		return get("manual_no");
	}

	public M setCustomsDepartment(java.lang.String customsDepartment) {
		set("customs_department", customsDepartment);
		return (M)this;
	}

	public java.lang.String getCustomsDepartment() {
		return get("customs_department");
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
