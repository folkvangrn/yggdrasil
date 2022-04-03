package ovh.nixenos.tab.server.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserPrincipal implements UserDetails {
    private User user;

    public CustomUserPrincipal(User user) {
        this.user = user;
    }
    
  /**
   * getter for password hash (private)
   * */
  public String getPassword() { return this.user.getPassword(); }

  /**
   * getter for username
   * */
  public String getUsername() { return this.user.getUsername(); }


  public boolean isEnabled() {
    return this.user.isActive();
  }

  public boolean isAccountNonExpired() {
    return this.user.isActive();
  }

  public boolean isAccountNonLocked() {
    return this.user.isActive();
  }

  public boolean isCredentialsNonExpired() {
    return this.user.isActive();
  }

  public List<GrantedAuthority> getAuthorities() {
     String role = this.user.getRole();
     GrantedAuthority authority = new SimpleGrantedAuthority(role);
     List <GrantedAuthority> temp = new ArrayList<>();
     temp.add(authority);
     return temp; 
  }

}