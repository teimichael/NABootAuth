package stu.napls.nabootauth.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stu.napls.nabootauth.config.GlobalKey;
import stu.napls.nabootauth.core.dictionary.ErrorCode;
import stu.napls.nabootauth.core.dictionary.StatusCode;
import stu.napls.nabootauth.core.exception.Assert;
import stu.napls.nabootauth.core.response.Response;
import stu.napls.nabootauth.model.Identity;
import stu.napls.nabootauth.model.Token;
import stu.napls.nabootauth.model.vo.AuthRegister;
import stu.napls.nabootauth.service.IdentityService;
import stu.napls.nabootauth.service.TokenService;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @Author Tei Michael
 * @Date 12/28/2019
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private IdentityService identityService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/login")
    public Response login(String username, String password) {
        Identity identity = identityService.findByUsername(username);
        Assert.notNull(identity, ErrorCode.USERNAME_NOT_EXIST, "Username does not exist.");
        Assert.isTrue(identity.getPassword().equals(password), ErrorCode.PASSWORD_WRONG, "Wrong password.");

        Token token = identity.getToken();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.setTime(new Date());
        // Token expiry date
        calendar.add(Calendar.HOUR, 2);
//        calendar.add(Calendar.MINUTE, 1);
        Date expiryDate = calendar.getTime();
        token.setIssuingDate(now);
        token.setExpiryDate(expiryDate);
        token.setContent("Bearer " + Jwts.builder()
                .setSubject(identity.getUuid())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, GlobalKey.JWT_SIGNING_KEY)
                .compact());
        token.setStatus(StatusCode.Token.NORMAL.getValue());
        tokenService.update(token);
        return Response.success("Login successfully.", token.getContent());
    }

    @PostMapping("/preregister")
    public Response preRegister(String username, String password) {
        Assert.isNull(identityService.findByUsername(username), ErrorCode.USERNAME_EXIST, "Username exists.");
        Identity identity = new Identity();
        identity.setUuid(UUID.randomUUID().toString());
        identity.setUsername(username);
        identity.setPassword(password);
        Date date = new Date();
        identity.setCreateDate(date);
        identity.setUpdateDate(date);
        identity.setStatus(StatusCode.Identity.PREREGISTER.getValue());

        Token token = new Token();
        token.setStatus(StatusCode.Token.INVALID.getValue());
        tokenService.create(token);

        identity.setToken(token);
        identityService.update(identity);
        return Response.success("Preregister successfully.", identity.getUuid());
    }

    @PostMapping("/register")
    public Response register(@RequestBody AuthRegister authRegister) {
        Identity identity = identityService.findByUuid(authRegister.getUuid());
        Assert.notNull(identity, "Register failed because UUID is missing.");
        identity.setStatus(StatusCode.Identity.NORMAL.getValue());
        identityService.update(identity);
        return Response.success("Register successfully.", identity.getUuid());
    }

    @PostMapping("/logout")
    public Response logout(String uuid) {

        Identity identity = identityService.findByUuid(uuid);
        Assert.notNull(identity, ErrorCode.USERNAME_NOT_EXIST, "Username does not exist.");
        Token token = identity.getToken();
        token.setStatus(StatusCode.Token.INVALID.getValue());
        tokenService.update(token);
        return Response.success("Logout successfully.");
    }
}
