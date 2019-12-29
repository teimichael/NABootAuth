package stu.napls.nabootauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.napls.nabootauth.model.Identity;

public interface IdentityRepository extends JpaRepository<Identity, Long> {
    Identity findByUsername(String username);

    Identity findByUuid(String uuid);
}
