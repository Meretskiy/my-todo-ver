package com.meretskiy.mytodover.services.impl;

import com.meretskiy.mytodover.model.Role;
import com.meretskiy.mytodover.repositories.RoleRepository;
import com.meretskiy.mytodover.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultRoleService implements RoleService {

    public static final long DEFAULT_ROLE_ID = 1L;

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleForNewUser() {
        return roleRepository.getOne(DEFAULT_ROLE_ID);
    }
}
