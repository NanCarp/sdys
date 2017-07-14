package stms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDictionary<M extends BaseDictionary<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setKeyword(java.lang.String keyword) {
		set("keyword", keyword);
		return (M)this;
	}

	public java.lang.String getKeyword() {
		return get("keyword");
	}

	public M setKey(java.lang.String key) {
		set("key", key);
		return (M)this;
	}

	public java.lang.String getKey() {
		return get("key");
	}

	public M setValue(java.lang.Integer value) {
		set("value", value);
		return (M)this;
	}

	public java.lang.Integer getValue() {
		return get("value");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

}
