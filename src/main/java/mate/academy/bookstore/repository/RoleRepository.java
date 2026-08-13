package mate.academy.bookstore.repository;

import java.util.Optional;
import mate.academy.bookstore.model.Role;
import mate.academy.bookstore.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
