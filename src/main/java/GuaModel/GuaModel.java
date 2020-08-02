package GuaModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jqhkMVC.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public abstract class GuaModel {

	public GuaModel(JSONObject json_obj) {
		this._obj_from_json(json_obj);
	}

	public abstract HashMap<String, String> key_mapper();

	private void _obj_from_json(JSONObject json_obj) {
		HashMap<String, String> mapper = this.key_mapper();
		for (String key : mapper.keySet()) {
			String value = mapper.get(key);
			String[] key_of_json_obj = value.split("\\.");
			Object value_from_json_obj = json_obj;
			for (String p : key_of_json_obj) {
				value_from_json_obj = ((JSONObject) value_from_json_obj).get(p);
				// Utils.log("value %s", String.valueOf(value_from_json_obj));
			}
			this.setAttribute(key, value_from_json_obj);
		}
	}

	private void setAttribute(String name, Object value) {
		try {
			Field f = this.getClass().getDeclaredField(name);
			try {
				// Object v = f.get(this);
				if (f.getName().equals(name)) {
					f.setAccessible(true);
					// String type = f.getType().toString();
					f.set(this, value);
				}
				Utils.log("new %s = %s", f.getName(), f.get(this));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
}
