package com.meretskiy.mytodover.services.impl;

import com.meretskiy.mytodover.model.Role;
import com.meretskiy.mytodover.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultRoleServiceTest {

    public static final long ID = 1L;
    @InjectMocks
    private DefaultRoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void getRoleForNewUserTest() {
        Role mockRole = Mockito.mock(Role.class);
        Mockito.when(roleRepository.getOne(ID)).thenReturn(mockRole);

        Role actualRole = roleService.getRoleForNewUser();

        Assertions.assertNotNull(actualRole);
        Assertions.assertEquals(mockRole, actualRole);
    }
}