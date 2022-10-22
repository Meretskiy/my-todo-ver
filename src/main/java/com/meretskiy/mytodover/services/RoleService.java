package com.meretskiy.mytodover.services;

import com.meretskiy.mytodover.model.Role;
import com.meretskiy.mytodover.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleForNewUser() {
        return roleRepository.getOne(1L);
    }
}
