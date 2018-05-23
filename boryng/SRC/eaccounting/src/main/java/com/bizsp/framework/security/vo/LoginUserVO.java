package com.bizsp.framework.security.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class LoginUserVO implements UserDetails {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	// ~ Instance fields
	// ================================================================================================
	private String password;
	private final String username; // 아이디
	private String name; // 이름
	private String companyCode; // 회사코드
	private String companyName; // 회사이름
	private String deptCode; // 부서코드
	private String deptName; // 부서이름
	private String titleCode; // 직급코드
	private String titleName; // 직급이름
	private String dutyCode; // 직책코드
	private String dutyName; // 직책이름
	private String titleAliasCode; // 호칭코드
	private String titleAliasName; // 호칭이름
	private String virtualUserYn; // 가상유저이름
	private String salesYn; // 영업부서 확인
	private String auths;
	private String mobileNo;
	private String telNo;

	private final Set<GrantedAuthority> authorities;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Calls the more complex constructor with all boolean arguments set to
	 * {@code true}.
	 */
	public LoginUserVO(String username, String password, String name, String compamyCode, String companyName, String deptCode, 
			String deptName, String titleCode, String titleName, String dutyCode,
			String dutyName, String titleAliasCode, String titleAliasName, String virtualUserYn, String salesYn, String auths, 
			Collection<? extends GrantedAuthority> authorities, String mobileNo, String telNo) {
		this(username, password, name, compamyCode, companyName, deptCode, deptName, 
				titleCode, titleName, dutyCode, dutyName, titleAliasCode, titleAliasName, virtualUserYn, salesYn, auths, 
				true, true, true, true,	authorities, mobileNo, telNo);
	}

	/**
	 * Construct the <code>LoginUserVO</code> with the details required by
	 * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}
	 * .
	 *
	 * @param username
	 *            the username presented to the
	 *            <code>DaoAuthenticationProvider</code>
	 * @param password
	 *            the password that should be presented to the
	 *            <code>DaoAuthenticationProvider</code>
	 * @param enabled
	 *            set to <code>true</code> if the user is enabled
	 * @param accountNonExpired
	 *            set to <code>true</code> if the account has not expired
	 * @param credentialsNonExpired
	 *            set to <code>true</code> if the credentials have not expired
	 * @param accountNonLocked
	 *            set to <code>true</code> if the account is not locked
	 * @param authorities
	 *            the authorities that should be granted to the caller if they
	 *            presented the correct username and password and the user is
	 *            enabled. Not null.
	 * @param titleAliasName
	 *
	 * @throws IllegalArgumentException
	 *             if a <code>null</code> value was passed either as a parameter
	 *             or as an element in the <code>GrantedAuthority</code>
	 *             collection
	 */
	public LoginUserVO(String username, String password, String name, String compamyCode, String companyName, String deptCode, String deptName, String titleCode, String titleName, String dutyCode,
			String dutyName, String titleAliasCode, String titleAliasName, String virtualUserYn, String salesYn, String auths, 
			boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, String mobileNo, String telNo) {

		if (((username == null) || "".equals(username)) || (password == null)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.username = username;
		this.password = password;
		this.name = name;
		this.companyCode = compamyCode;
		this.companyName = companyName;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.titleCode = titleCode;
		this.titleName = titleName;
		this.dutyCode = dutyCode;
		this.dutyName = dutyName;
		this.titleAliasCode = titleAliasCode;
		this.titleAliasName = titleAliasName;
		this.virtualUserYn = virtualUserYn;
		this.salesYn = salesYn;
		this.auths = auths;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
		this.mobileNo = mobileNo;
		this.telNo = telNo;
	}
	
	
	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getUserId() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getTitleCode() {
		return titleCode;
	}

	public String getTitleName() {
		return titleName;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public String getDutyName() {
		return dutyName;
	}

	public String getTitleAliasCode() {
		return titleAliasCode;
	}

	public String getVirtualUserYn() {
		return virtualUserYn;
	}

	public String getTitleAliasName() {
		return titleAliasName;
	}

	public String getSalesYn() {
		return salesYn;
	}

	public String getAuths() {
		return auths;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void eraseCredentials() {
		password = null;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			// Neither should ever be null as each entry is checked before
			// adding it to the set.
			// If the authority is null, it is a custom authority and should
			// precede others.
			if (g2.getAuthority() == null) {
				return -1;
			}

			if (g1.getAuthority() == null) {
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

}
