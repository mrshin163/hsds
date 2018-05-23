package com.bizsp.framework.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

import com.bizsp.framework.security.vo.LoginUserVO;

public class CstmzngJdbcDaoImpl extends JdbcDaoImpl {
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> users = loadUsersByUsername(username);

        if (users.size() == 0) {
            logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"));
        }

        LoginUserVO user = (LoginUserVO)users.get(0); // contains no GrantedAuthority[]

        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

        if (getEnableAuthorities()) {
            dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
        }

        if (getEnableGroups()) {
            dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
        }

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
        
        addCustomAuthorities(user.getUsername(), dbAuths);

        if (dbAuths.size() == 0) {
            logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");

            throw new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.noAuthority",
                            new Object[] {username}, "User {0} has no GrantedAuthority"));
        }

        return createUserDetails(username, user, dbAuths);
    }

    /**
     * Executes the SQL <tt>usersByUsernameQuery</tt> and returns a list of UserDetails objects.
     * There should normally only be one matching user.
     */
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                String name = rs.getString(3);
                String companyCode = rs.getString(4);
                String companyName = rs.getString(5);
                String deptCode = rs.getString(6);
                String deptName = rs.getString(7);
                String titleCode = rs.getString(8);
                String titleName = rs.getString(9);
                String dutyCode = rs.getString(10);
                String dutyName = rs.getString(11);
                String titleAliasCode = rs.getString(12);
                String titleAliasName = rs.getString(13);
                String virtualUserYn = rs.getString(14);
                String salesYn = rs.getString(15);
                String auths = rs.getString(16);
                String mobileNo = rs.getString(17);
                String telNo = rs.getString(18);
                return new LoginUserVO(username, password, name, companyCode, companyName, deptCode, deptName, 
                		titleCode, titleName, dutyCode, dutyName, titleAliasCode, titleAliasName, virtualUserYn, salesYn, auths, AuthorityUtils.NO_AUTHORITIES, mobileNo, telNo);
            }

        });
    }

    /**
     * Loads authorities by executing the SQL from <tt>authoritiesByUsernameQuery</tt>.
     *
     * @return a list of GrantedAuthority objects for the user
     */
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return getJdbcTemplate().query(getAuthoritiesByUsernameQuery(), new String[] {username}, new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String roleName = getRolePrefix() + rs.getString(2);

                return new SimpleGrantedAuthority(roleName);
            }
        });
    }
    
    /**
     * Can be overridden to customize the creation of the final UserDetailsObject which is
     * returned by the <tt>loadUserByUsername</tt> method.
     *
     * @param username the name originally passed to loadUserByUsername
     * @param userFromUserQuery the object returned from the execution of the
     * @param combinedAuthorities the combined array of authorities from all the authority loading queries.
     * @return the final UserDetails which should be used in the system.
     */
    protected UserDetails createUserDetails(String username,LoginUserVO userFromUserQuery,
    	List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        return new LoginUserVO(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.getName(), userFromUserQuery.getCompanyCode(), userFromUserQuery.getCompanyName(),
        						userFromUserQuery.getDeptCode(), userFromUserQuery.getDeptName(), userFromUserQuery.getTitleCode(), userFromUserQuery.getTitleName(), 
        						userFromUserQuery.getDutyCode(), userFromUserQuery.getDutyName(), userFromUserQuery.getTitleAliasCode(), userFromUserQuery.getTitleAliasName(), 
        						userFromUserQuery.getVirtualUserYn(), userFromUserQuery.getSalesYn(), userFromUserQuery.getAuths(), combinedAuthorities, userFromUserQuery.getMobileNo(), userFromUserQuery.getTelNo());
    }
}
