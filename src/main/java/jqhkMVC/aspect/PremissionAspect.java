package jqhkMVC.aspect;

import jqhkMVC.Request;
import jqhkMVC.controller.*;
import jqhkMVC.RouteAnnotationFactory;
import jqhkMVC.models.User;
import jqhkMVC.models.UserRole;

public class PremissionAspect {

	@Around(routes = {"/topic", "/topic/create", "/topic/add", "/comment/add"})
	public static byte[] loginRequired(Request request) {
		User u = Route.currentUser(request);
		if (u.role.equals(UserRole.guest)) {
			return UserRoute.login(request);
		}
		return RouteAnnotationFactory.responseForPath(request);
	}

}
