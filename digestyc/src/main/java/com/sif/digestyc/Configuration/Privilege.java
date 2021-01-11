package com.sif.digestyc.Configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class Privilege extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
	private static final Logger LOG = LoggerFactory.getLogger(Privilege.class);
	
	public Privilege(Authentication authentication) {
		super(authentication);
	}

	public boolean hasPrivilege(Object targetObject, Object permission) {
		if (authentication == null || !(targetObject instanceof String) || !(permission instanceof String)) {
			return false;
		}
		String target = targetObject.toString().toUpperCase();
		return verification(authentication, target.toUpperCase(), permission.toString().toUpperCase());
	}

	private boolean verification(Authentication auth, String targetType, String permission) {
		for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
			if (grantedAuth.getAuthority().startsWith(targetType)) {
				if (grantedAuth.getAuthority().contains(permission)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasUrl(String url) {
		for (GrantedAuthority grantedAuth : authentication.getAuthorities()) {
			if (grantedAuth.getAuthority().contains(url)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setFilterObject(Object filterObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getFilterObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReturnObject(Object returnObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getReturnObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getThis() {
		// TODO Auto-generated method stub
		return null;
	}

}
