package com.project.qq10.auth.userdetails;

import com.project.qq10.auth.utils.CustomAuthorityUtils;
import com.project.qq10.domain.entity.User;
import com.project.qq10.domain.exception.BusinessException;
import com.project.qq10.domain.exception.ExceptionCode;
import com.project.qq10.domain.repository.UserRepository;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class MemberDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CustomAuthorityUtils authorityUtils;

    public MemberDetailsService(UserRepository userRepository, CustomAuthorityUtils authorityUtils) {
        this.userRepository = userRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(username));
        User findUser = optionalUser.orElseThrow(() ->
                new BusinessException(ExceptionCode.USER_NOT_FOUND));

        return new MemberDetails(findUser);
    }

    @Data
    private final class MemberDetails extends User implements UserDetails {

        MemberDetails(User user) {
            setUserId(user.getUserId());
            setUserName(user.getUserName());
            setEmail(user.getEmail());
            setPassword(user.getPassword());
            setRoles(user.getRoles());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
