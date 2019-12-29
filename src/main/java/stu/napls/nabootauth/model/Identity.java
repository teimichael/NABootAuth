package stu.napls.nabootauth.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "auth_identity")
@Data
public class Identity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "updateDate")
    private Date updateDate;

    @Column(name = "status")
    private int status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token", referencedColumnName = "id")
    private Token token;

}
