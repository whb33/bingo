package com.bingo.web.springbootdemo.utils.context;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

import java.security.Principal;

public class SecurityContext {
	public static final String THREAD_CONTEXT_CURRENT_USER = ThreadContext.class.getName() + "_userinfo";
	public static final String THREAD_CONTEXT_PRINCIPAL = ThreadContext.class.getName() + "_princial";

	public static boolean isAuthenticated() {
		Subject subject = getSubject();
		if (subject == null) {
			return false;
		}
		return subject.isAuthenticated();
	}

	public static boolean isRemembered() {
		Subject subject = getSubject();
		if (subject == null) {
			return false;
		}
		return subject.isRemembered();
	}

	public static boolean isPermitted(String permission) {
		Subject subject = getSubject();
		if (subject == null) {
			return false;
		}
		return subject.isPermitted(permission);
	}

	public static Subject getSubject() {
		try {
			return SecurityUtils.getSubject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Session getSession() {
		Subject subject = getSubject();
		if (subject == null) {
			return null;
		}
		return subject.getSession();
	}

	public static Principal getPrincipal() {
		Object o = ThreadContext.get(THREAD_CONTEXT_PRINCIPAL);
		if (o == null) {
			Subject subject = getSubject();
			if (subject == null) {
				return null;
			}

			o = subject.getPrincipal();
			ThreadContext.put(THREAD_CONTEXT_PRINCIPAL, o);
		}

		if (o != null) {
			return (Principal) o;
		}

		return null;
	}
}
