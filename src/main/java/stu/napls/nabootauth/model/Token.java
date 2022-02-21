package stu.napls.nabootauth.model;

import lombok.*;
import org.hibernate.Hibernate;
import stu.napls.nabootauth.core.dictionary.StatusCode;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "auth_token")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", unique = true)
    private String content;

    @Column(name = "issuing_date")
    private Date issuingDate;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "status", columnDefinition = "integer default " + StatusCode.NORMAL)
    private int status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Token token = (Token) o;
        return id != null && Objects.equals(id, token.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
