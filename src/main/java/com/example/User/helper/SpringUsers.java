package com.example.User.helper;

import com.example.User.database.UserMasterData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class SpringUsers extends User {

    private String companyId;
    private String email;
    private Map<String, Object> deviceInfo;
    private String firstName;

    private Integer userType;

    public SpringUsers(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
    }

    public SpringUsers(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public SpringUsers(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String companyId,
                       Map<String, Object> deviceInfo, String firstName, String email) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.companyId = companyId;
        this.deviceInfo = deviceInfo;
        this.firstName = firstName;
        this.email = email;
    }

    public SpringUsers(String username, String password, boolean enabled, boolean accountNonExpired
            , boolean credentialsNonExpired, boolean accountNonLocked
            , Collection<? extends GrantedAuthority> authorities, UserMasterData userMasterData) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.companyId = userMasterData.getCompanyId();
        this.email = userMasterData.getEmail();
        this.deviceInfo = userMasterData.getDeviceInfo();
        this.firstName = userMasterData.getFirstName();
        this.userType = userMasterData.getUserType();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}