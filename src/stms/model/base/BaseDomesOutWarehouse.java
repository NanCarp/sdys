package stms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDomesOutWarehouse<M extends BaseDomesOutWarehouse<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setDeliveryNo(java.lang.String deliveryNo) {
		set("delivery_no", deliveryNo);
		return (M)this;
	}

	public java.lang.String getDeliveryNo() {
		return get("delivery_no");
	}

	public M setOutDate(java.util.Date outDate) {
		set("out_date", outDate);
		return (M)this;
	}

	public java.util.Date getOutDate() {
		return get("out_date");
	}

	public M setStorageLocation(java.lang.String storageLocation) {
		set("storage_location", storageLocation);
		return (M)this;
	}

	public java.lang.String getStorageLocation() {
		return get("storage_location");
	}

	public M setCompanyName(java.lang.String companyName) {
		set("company_name", companyName);
		return (M)this;
	}

	public java.lang.String getCompanyName() {
		return get("company_name");
	}

	public M setMaterialNo(java.lang.String materialNo) {
		set("material_no", materialNo);
		return (M)this;
	}

	public java.lang.String getMaterialNo() {
		return get("material_no");
	}

	public M setMaterial(java.lang.String material) {
		set("material", material);
		return (M)this;
	}

	public java.lang.String getMaterial() {
		return get("material");
	}

	public M setBatchNo(java.lang.String batchNo) {
		set("batch_no", batchNo);
		return (M)this;
	}

	public java.lang.String getBatchNo() {
		return get("batch_no");
	}

	public M setTrayNo(java.lang.String trayNo) {
		set("tray_no", trayNo);
		return (M)this;
	}

	public java.lang.String getTrayNo() {
		return get("tray_no");
	}

	public M setOutQuantity(java.lang.Integer outQuantity) {
		set("out_quantity", outQuantity);
		return (M)this;
	}

	public java.lang.Integer getOutQuantity() {
		return get("out_quantity");
	}

	public M setOutTrayQuantity(java.lang.Integer outTrayQuantity) {
		set("out_tray_quantity", outTrayQuantity);
		return (M)this;
	}

	public java.lang.Integer getOutTrayQuantity() {
		return get("out_tray_quantity");
	}

	public M setModulePower(java.lang.Integer modulePower) {
		set("module_power", modulePower);
		return (M)this;
	}

	public java.lang.Integer getModulePower() {
		return get("module_power");
	}

}
