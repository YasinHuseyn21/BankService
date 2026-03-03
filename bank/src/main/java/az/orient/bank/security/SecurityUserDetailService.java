package az.orient.bank.security;
import az.orient.bank.entity.Auth;
import az.orient.bank.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     var auth = authRepository.findByUsername(username)
             .orElseThrow(()-> new UsernameNotFoundException(username));
        return new SecurityUser(auth);
    }
}
