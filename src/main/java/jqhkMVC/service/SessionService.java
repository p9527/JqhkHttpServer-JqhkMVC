package jqhkMVC.service;

import jqhkMVC.models.ModelFactory;
import jqhkMVC.models.Session;

import java.util.ArrayList;

// impot ../Utils


public class SessionService {
    public static Session add(String sessionId, Integer userId) {
        Session m = new Session();
        m.sessionId = sessionId;
        m.userId = userId;

        ArrayList<Session> all = load();
        all.add(m);
        save(all);

        return m;
    }

    public static void save(ArrayList<Session> list) {
        String className = Session.class.getSimpleName();

        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.sessionId);
            lines.add(model.userId.toString());
            return lines;
        });
    }

    public static ArrayList<Session> load() {
        String className = Session.class.getSimpleName();

        ArrayList<Session> rs = ModelFactory.load(className, 2, modelData -> {
            String sessionId = modelData.get(0);
            Integer userId = Integer.parseInt(modelData.get(1));

            Session m = new Session();
            m.sessionId = sessionId;
            m.userId = userId;

            return m;
        });
        return rs;
    }


    public static Session findById(String sessionId) {
        ArrayList<Session> ms = load();

        for (int i = 0; i < ms.size(); i++) {
            Session e = ms.get(i);
            if (e.sessionId.equals(sessionId)) {
                return e;
            }
        }
        return null;
    }
}