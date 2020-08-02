package jqhkMVC.service;

import com.alibaba.fastjson.JSONObject;
import jqhkMVC.tools.JWTUtils;
import jqhkMVC.models.ModelFactory;
import jqhkMVC.models.User;
import jqhkMVC.models.UserRole;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

// impot ../Utils

class Digest {
    public static String hexFromBytes(byte[] array) {
        String hex = new BigInteger(1, array).toString(16);
        int zeroLength = array.length * 2 - hex.length();
        for (int i = 0; i < zeroLength; i++) {
            hex = "0" + hex;
        }
        return hex;
    }

    public static String md5 (String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(origin.getBytes());
            byte[] result = md.digest();
            String hex = hexFromBytes(result);
            return hex;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("error: %s", e));
        }
    }
}

public class UserService {

    static String salt = "hahaha";
    public static String jwtKey = "jwtKey";

    public static User add(HashMap<String, String> form) {
        String username = form.get("username");
        String password = form.get("password");
        String nickname = form.get("nickname");
        User m = new User();
        m.password = saltedPassword(password);
        m.username = username;
        m.nickname = nickname;
        m.role = UserRole.normal;

        ArrayList<User> all = load();
        if (all.isEmpty()){
            m.id = 1;
        } else {
            User last = all.get(all.size() - 1);
            m.id = last.id + 1;
        }
        all.add(m);
        save(all);

        return m;
    }

    public static void update(Integer id, String password) {
        ArrayList<User> all = load();
        for (User u : all) {
            if (u.id.equals(id)) {
                u.password = saltedPassword(password);
            }
        }
        save(all);
    }

    public static void save(ArrayList<User> list) {
        String className = User.class.getSimpleName();

        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.id.toString());
            lines.add(model.username);
            lines.add(model.password);
            lines.add(model.nickname);
            lines.add(model.role.toString());
            return lines;
        });
    }

    public static ArrayList<User> load() {
        String className = User.class.getSimpleName();

        ArrayList<User> rs = ModelFactory.load(className, 5, modelData -> {
            Integer id = Integer.parseInt(modelData.get(0));
            String username = modelData.get(1);
            String password = modelData.get(2);
            String nickname = modelData.get(3);
            UserRole role = UserRole.valueOf(modelData.get(4));

            User m = new User();
            m.id = id;
            m.username = username;
            m.password = password;
            m.nickname = nickname;
            m.role = role;

            return m;
        });
        return rs;
    }

    public static Boolean validLogin(String username, String password) {
        // Utils.log("validLogin in");
        ArrayList<User> userList = load();
        // Utils.log("userList: %s", userList);
        String saltedPasswotd = saltedPassword(password);
        // Utils.log("login valid username %s saltedPassword %s", username, saltedPasswotd);
        for (User u: userList) {
            if (u.username.equals(username) && u.password.equals(saltedPasswotd)) {
                // Utils.log("login 验证成功");
                return true;
            }
        }
        // Utils.log("validLogin end");
        return false;
    }

    public static User findByUsername(String username) {
        ArrayList<User> userList = load();
        for (User u: userList) {
            if (u.username.equals(username)) {
                return u;
            }
        }
        return null;
    }

    public static User findById(Integer id) {
        ArrayList<User> userList = load();
        for (User u: userList) {
            if (u.id.equals(id)) {
                return u;
            }
        }
        return null;
    }

    public static User guest() {
        User u = new User();
        u.id = -1;
        u.username = "游客";
        u.role = UserRole.guest;
        return u;
    }

    public static String adminUsersHtml() {
        ArrayList<User> all = load();
        StringBuilder result = new StringBuilder();
        for (User m: all) {
            String s = String.format(
                    "<h3>username: %s, password: %s </h3>",
                    m.username,
                    m.password
            );
            result.append(s);
        }
        return result.toString();
    }

    public static String saltedPassword(String password) {
        String result = Digest.md5(password + salt);
        return result;
    }

    public static String userJwt(User user) {
        JSONObject header = new JSONObject(true);
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        JSONObject payload = new JSONObject(true);
        payload.put("userId", user.id);
        Long iat = System.currentTimeMillis() / 1000L;
        Long exp = iat + (60 * 60 * 24);
        payload.put("iat", iat);
        payload.put("exp", exp);
        String token = JWTUtils.jwt(jwtKey, header, payload);
        return token;
    }

    public static User validJwt(String jwt) {
        JSONObject payload = JWTUtils.jwtDecode(UserService.jwtKey, jwt);
        int userId = Integer.parseInt(payload.get("userId").toString());
        Long exp = Long.parseLong(payload.getString("exp").toString());
        Long now = System.currentTimeMillis() / 1000L;
        if (exp < now) {
            return UserService.guest();
        } else {
            User u = UserService.findById(userId);
            if (u == null) {
                return UserService.guest();
            } else {
                return u;
            }
        }
    }

    public static void main(String[] args) {
    }

}