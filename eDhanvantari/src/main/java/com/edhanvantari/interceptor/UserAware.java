package com.edhanvantari.interceptor;

import com.edhanvantari.form.LoginForm;

public interface UserAware {

	public void setUser(LoginForm form);
}
