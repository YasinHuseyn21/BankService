package az.orient.bank.repository;

import az.orient.bank.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth,Long> {
    Auth findAuthByUsernameAndPasswordAndActive(String username,String password,Integer active);

    Auth findAuthByTokenAndActive(String token,int active);

    Auth findAuthByTokenAndUsernameAndActive(String token,String username,int active);

    Optional<Auth> findByUsername(String username);
}
