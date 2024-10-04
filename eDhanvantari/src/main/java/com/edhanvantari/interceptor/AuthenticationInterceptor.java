package com.edhanvantari.interceptor;

import java.util.Map;

import com.edhanvantari.form.LoginForm;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticationInterceptor extends ActionSupport implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {

		Map<String, Object> sessionAttribute = actionInvocation.getInvocationContext().getSession();

		LoginForm loginForm = (LoginForm) sessionAttribute.get("USER");

		if (loginForm == null) {

			addActionError("User is not authorised for the action.");
			return Action.LOGIN;
		} else {

			Action action = (Action) actionInvocation.getAction();

			if (action instanceof UserAware) {
				((UserAware) action).setUser(loginForm);
			}
			return actionInvocation.invoke();
		}
	}

}
