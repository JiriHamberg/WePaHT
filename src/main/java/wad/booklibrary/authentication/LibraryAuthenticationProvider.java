/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.booklibrary.authentication;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 *
 * @author jiri
 */
@Service
public class LibraryAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserRepository userRepository;
    
    @PostConstruct
    private void init(){
        User admin = new User();
        admin.setName("admin");
        admin.setPassword("123");
        userRepository.save(admin);
    }
    
    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        String username = a.getName();
        String password = a.getCredentials().toString();
        if(userRepository.countByNameAndPassword(username, password) == 1){
            grantedAuths.add(new SimpleGrantedAuthority("user"));
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        }
        //return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        throw new AuthenticationException("Incorrect username or password") {};
    }

    @Override
    public boolean supports(Class authType) {
       return authType.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
