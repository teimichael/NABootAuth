package stu.napls.nabootauth.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auth_token")
@Data
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "issuingDate")
    private Date issuingDate;

    @Column(name = "expiryDate")
    private Date expiryDate;

    @Column(name = "status")
    private int status;

}
