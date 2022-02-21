package stu.napls.nabootauth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stu.napls.nabootauth.config.GlobalKey;
import stu.napls.nabootauth.core.dictionary.ErrorCode;
import stu.napls.nabootauth.core.dictionary.IdentityConst;
import stu.napls.nabootauth.core.dictionary.TokenConst;
import stu.napls.nabootauth.core.exception.Assert;
import stu.napls.nabootauth.core.response.Response;
import stu.napls.nabootauth.model.Identity;
import stu.napls.nabootauth.model.Token;
import stu.napls.nabootauth.model.vo.AuthLogin;
import stu.napls.nabootauth.model.vo.AuthLogout;
import stu.napls.nabootauth.model.vo.AuthPreregister;
import stu.napls.nabootauth.model.vo.AuthRegister;
import stu.napls.nabootauth.service.IdentityService;
import stu.napls.nabootauth.service.TokenService;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tei Michael
 * @date 2/21/2022
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private IdentityService identityService;

    @Resource
    private TokenService tokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/login")
    public Response<String> login(@RequestBody AuthLogin authLogin) {
        Identity identity = identityService.findByUsernameAndSource(authLogin.getUsername(), authLogin.getSource());
        Assert.notNull(identity, ErrorCode.USERNAME_NOT_EXIST, "Username does not exist.");
        Assert.isTrue(identity.getStatus() == IdentityConst.NORMAL, ErrorCode.ABNORMAL, "Account is not normal.");
        Assert.isTrue(bCryptPasswordEncoder.matches(authLogin.getPassword(), identity.getPassword()), ErrorCode.PASSWORD_WRONG, "Wrong password.");

        // Generate and set token
        Token token = identity.getToken();

        Algorithm algorithm = Algorithm.HMAC512(GlobalKey.JWT_SIGNING_KEY);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.setTime(new Date());
        // Token expiry date
        calendar.add(Calendar.HOUR, 2);
//        calendar.add(Calendar.MINUTE, 1);
        Date expiryDate = calendar.getTime();
        token.setIssuingDate(now);
        token.setExpiryDate(expiryDate);
        token.setContent("Bearer " + JWT.create()
                .withIssuer(GlobalKey.ISSUER)
                .withSubject(identity.getUuid())
                .withExpiresAt(expiryDate)
                .sign(algorithm));
        token.setStatus(TokenConst.NORMAL);
        tokenService.update(token);
        return Response.success("Login successfully.", token.getContent());
    }

    @PostMapping("/preregister")
    public Response<String> preRegister(@RequestBody AuthPreregister authPreregister) {
        Identity existIdentity = identityService.findByUsername(authPreregister.getUsername());

        // Whether is preregistered.
        Assert.isTrue(existIdentity == null || existIdentity.getStatus() != IdentityConst.PREREGISTER, ErrorCode.USERNAME_EXIST, "Username exists.");

        Identity identity = new Identity();
        identity.setUuid(UUID.randomUUID().toString());
        identity.setUsername(authPreregister.getUsername());
        identity.setPassword(bCryptPasswordEncoder.encode(authPreregister.getPassword()));
        identity.setSource(authPreregister.getSource());
        identity.setStatus(IdentityConst.PREREGISTER);

        Token token = new Token();
        token.setStatus(TokenConst.INVALID);
        tokenService.create(token);

        identity.setToken(token);
        identityService.update(identity);
        return Response.success("Preregister successfully.", identity.getUuid());
    }

    @PostMapping("/register")
    public Response<String> register(@RequestBody AuthRegister authRegister) {
        Identity identity = identityService.findByUuid(authRegister.getUuid());
        Assert.notNull(identity, "Register failed because UUID is missing.");
        identity.setStatus(IdentityConst.NORMAL);
        identityService.update(identity);
        return Response.success("Register successfully.", identity.getUuid());
    }

    /**
     * This request must be authenticated before calling.
     *
     * @param authLogout logout object
     * @return message
     */
    @PostMapping("/logout")
    public Response<String> logout(@RequestBody AuthLogout authLogout) {
        Identity identity = identityService.findByUuid(authLogout.getUuid());
        Assert.notNull(identity, ErrorCode.USERNAME_NOT_EXIST, "Username does not exist.");
        Token token = identity.getToken();
        token.setStatus(TokenConst.INVALID);
        tokenService.update(token);
        return Response.success("Logout successfully.");
    }
}
