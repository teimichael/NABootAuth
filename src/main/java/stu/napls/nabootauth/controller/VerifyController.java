package stu.napls.nabootauth.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import stu.napls.nabootauth.config.GlobalKey;
import stu.napls.nabootauth.core.dictionary.TokenConst;
import stu.napls.nabootauth.core.exception.Assert;
import stu.napls.nabootauth.core.response.Response;
import stu.napls.nabootauth.model.Identity;
import stu.napls.nabootauth.model.vo.AuthVerify;
import stu.napls.nabootauth.service.IdentityService;
import stu.napls.nabootauth.service.TokenService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Tei Michael
 * @date 2/21/2022
 */
@RestController
public class VerifyController {

    @Resource
    private IdentityService identityService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/verify")
    public Response<String> verifyToken(@RequestBody AuthVerify authVerify) {
        String token = authVerify.getToken().replaceFirst("Bearer ", "");

        // Basic validation
        Assert.notNull(token, "No token information.");
        Assert.isTrue(!"".equals(token), "Token is empty.");

        // Parse JWT
        Algorithm algorithm = Algorithm.HMAC512(GlobalKey.JWT_SIGNING_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(GlobalKey.ISSUER)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);

        // Expiration
        Assert.isTrue(jwt.getExpiresAt().after(new Date()), "Token has expired.");

        Identity identity = identityService.findByUuid(jwt.getSubject());
        // Corresponding Identity does not exist
        Assert.notNull(identity, "Token is invalid");

        Assert.isTrue(identity.getToken().getContent().equals(authVerify.getToken()) && identity.getToken().getStatus() == TokenConst.NORMAL, "Token is abnormal.");

        return Response.success("Verified.", identity.getUuid());
    }
}
