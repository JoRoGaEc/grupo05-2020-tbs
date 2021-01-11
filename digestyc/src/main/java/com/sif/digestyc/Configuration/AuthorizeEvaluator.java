package com.sif.digestyc.Configuration;

import java.io.Serializable;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthorizeEvaluator implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if (authentication == null || targetDomainObject == null || !(permission instanceof String)) {
			return false;
		}
		String target = targetDomainObject.getClass().getSimpleName().toUpperCase();
		return hasPrivilege(authentication, target.toUpperCase(), permission.toString().toUpperCase());
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		if (authentication == null || targetType == null || !(permission instanceof String)) {
			return false;
		}
		return hasPrivilege(authentication, targetType.toUpperCase(), permission.toString().toUpperCase());
	}

	
	private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
		for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
			if (grantedAuth.getAuthority().startsWith(targetType)) {
				if (grantedAuth.getAuthority().contains(permission)) {
					return true;
				}
			}
		}
		return false;
	}
	
}