package stu.napls.nabootauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import stu.napls.nabootauth.config.GlobalKey;

import java.util.Calendar;
import java.util.Date;

public class AuthJWTTest {

    @Test
    public void testCreateJWT() {
        try {
            Algorithm algorithm = Algorithm.HMAC512(GlobalKey.JWT_SIGNING_KEY);

            // Token expiry date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MINUTE, 1);
            Date expiryDate = calendar.getTime();

            String token = JWT.create()
                    .withIssuer(GlobalKey.ISSUER)
                    .withSubject("Michael")
                    .withExpiresAt(expiryDate)
                    .sign(algorithm);
            System.out.println(token);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }

    }

    @Test
    public void testVerifyJWT() {
        String token = "token";
        try {
            Algorithm algorithm = Algorithm.HMAC512(GlobalKey.JWT_SIGNING_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(GlobalKey.ISSUER)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getSubject());
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
        }
    }

}
