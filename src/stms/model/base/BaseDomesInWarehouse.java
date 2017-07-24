package stms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDomesInWarehouse<M extends BaseDomesInWarehouse<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setInDate(java.util.Date inDate) {
		set("in_date", inDate);
		return (M)this;
	}

	public java.util.Date getInDate() {
		return get("in_date");
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

	public M setInQuantity(java.lang.Integer inQuantity) {
		set("in_quantity", inQuantity);
		return (M)this;
	}

	public java.lang.Integer getInQuantity() {
		return get("in_quantity");
	}

	public M setInTrayQuantity(java.lang.Integer inTrayQuantity) {
		set("in_tray_quantity", inTrayQuantity);
		return (M)this;
	}

	public java.lang.Integer getInTrayQuantity() {
		return get("in_tray_quantity");
	}

	public M setModulePower(java.lang.Integer modulePower) {
		set("module_power", modulePower);
		return (M)this;
	}

	public java.lang.Integer getModulePower() {
		return get("module_power");
	}

}