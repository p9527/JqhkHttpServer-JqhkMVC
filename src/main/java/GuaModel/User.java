package GuaModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jqhkMVC.Utils;

import java.util.HashMap;

public class User extends GuaModel {
	public Integer id;
	public String name;
	public String real_name;
	public String test;

	public User(JSONObject json_obj) {
		super(json_obj);
	}

	@Override
	public HashMap<String, String> key_mapper() {
		HashMap<String, String> mapper = new HashMap<>();
		mapper.put("id", "id");
		mapper.put("name", "name");
		mapper.put("real_name", "profile.real_name");
		mapper.put("test", "profile.test.test1");
		return mapper;
	}

	public static void main(String[] args) {
		String jsonStr = Utils.load("db/user.json");
		JSONObject json = JSON.parseObject(jsonStr);
		User s = new User(json);
		Utils.log("s %s", s.id);
	}
}
