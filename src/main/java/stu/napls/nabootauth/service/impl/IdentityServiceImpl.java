package stu.napls.nabootauth.service.impl;

import org.springframework.stereotype.Service;
import stu.napls.nabootauth.model.Identity;
import stu.napls.nabootauth.repository.IdentityRepository;
import stu.napls.nabootauth.service.IdentityService;

import javax.annotation.Resource;

/**
 * @Author Tei Michael
 * @Date 12/29/2019
 */
@Service("identityService")
public class IdentityServiceImpl implements IdentityService {

    @Resource
    private IdentityRepository identityRepository;

    @Override
    public Identity create(Identity identity) {
        return identityRepository.save(identity);
    }

    @Override
    public Identity findByUsername(String username) {
        return identityRepository.findByUsername(username);
    }

    @Override
    public Identity findByUuid(String uuid) {
        return identityRepository.findByUuid(uuid);
    }
}
