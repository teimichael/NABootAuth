package stu.napls.nabootauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.napls.nabootauth.model.Identity;
import stu.napls.nabootauth.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByContent(String content);
}
