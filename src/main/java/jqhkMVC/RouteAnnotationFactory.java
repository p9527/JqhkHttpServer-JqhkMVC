package jqhkMVC;

import jqhkMVC.controller.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

public class RouteAnnotationFactory {
	static ArrayList<Class> allRoutes = null;

	static {
		try {
			allRoutes = new ArrayList<Class>(Arrays.asList(getClasses("jqhkMVC.Route")));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public static byte[] responseForPath(Request request) {
		try {
			Method method = matchPath(request);
			if (method != null) {
				return (byte[]) method.invoke(null, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Route.route404(request);
	}

	private static Method matchPath(Request request) {
		try {
			for (Class route : allRoutes) {
				for (Method method : route.getDeclaredMethods()) {
					if (method.isAnnotationPresent(RequestMapping.class)) {
						try {
							RequestMapping anno = method.getAnnotation(RequestMapping.class);
							if (anno.path().equals(request.path)) {
								// Utils.log("method name %s", method.ge);
								return method;
							}
						} catch (Throwable ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		Class[] allClasses = getClasses("jqhkMVC.Route");
		Utils.log("%s", Arrays.asList(allClasses));
	}

	private static Class[] getClasses(String packageName)
			throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
}
