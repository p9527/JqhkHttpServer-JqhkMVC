package jqhkMVC.aspect;

import jqhkMVC.Request;
import jqhkMVC.RouteAnnotationFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AspectAnnotationFactory {

	public static byte[] aop(Request request) {
		try {
			Class aspect = PremissionAspect.class;
			for (Method method : aspect.getDeclaredMethods()) {
				if (method.isAnnotationPresent(Around.class)) {
					try {
						Around anno = method.getAnnotation(Around.class);
						boolean pathExit = Arrays.asList(anno.routes()).contains(request.path);
						// Utils.log("aop path %s %s", request.path, pathExit);
						if (pathExit) {
							return (byte[]) method.invoke(aspect, request);
						}
					} catch (Throwable ex) {
						ex.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return RouteAnnotationFactory.responseForPath(request);
	}
}
