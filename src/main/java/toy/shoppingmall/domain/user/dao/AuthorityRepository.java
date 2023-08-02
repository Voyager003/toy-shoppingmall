package toy.shoppingmall.domain.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.shoppingmall.domain.model.Authority;
import toy.shoppingmall.domain.model.Role;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(Role role);
}
