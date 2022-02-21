package stu.napls.nabootauth.config;

public interface GlobalKey {

    String ISSUER = "NABootAuth";

    // NIST SP 800-117
    String JWT_SIGNING_KEY = "nabootauth-@JWT!Secret#key.eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9eyJzdWIiOiJNaWNoYWVsIiwiaXNzIjoiTkFCb290QXV0aCIsImV4cCI6MTY0NTQ0MTM4NX0";
}
