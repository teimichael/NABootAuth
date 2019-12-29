package stu.napls.nabootauth.service;

import stu.napls.nabootauth.model.Token;

public interface TokenService {
    Token create(Token token);

    Token update(Token token);

    Token findById(long id);

    Token findByContent(String content);
}
