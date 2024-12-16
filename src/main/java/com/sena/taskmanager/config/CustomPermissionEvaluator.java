package com.sena.taskmanager.config;

import com.sena.taskmanager.entity.CRUDAction;
import com.sena.taskmanager.entity.EntityType;
import com.sena.taskmanager.entity.Role;
import com.sena.taskmanager.exceptions.BadRequestException;
import com.sena.taskmanager.repository.RoleRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    RoleRepository roleRepository;

    public CustomPermissionEvaluator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object action) {
        if ((auth == null) || (targetDomainObject == null) || !(action instanceof String)){
            return false;
        }

        switch ((String) targetDomainObject) {
            case "permission":
                String[] actionArray = action.toString().toUpperCase().split("_");
                CRUDAction crudAction = CRUDAction.valueOf(actionArray[0]);
                EntityType entityType = EntityType.valueOf(actionArray[1]);
                return hasPrivilege(auth, entityType, crudAction);
            case "role":
                return canCreateUserByRole(auth, action.toString().toUpperCase());
            default:
                return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String entityTypeStr, Object privilege) {
        if ((auth == null) || (entityTypeStr == null) || (!(privilege instanceof String))){
            return false;
        }

        EntityType entityType = EntityType.valueOf(entityTypeStr.toUpperCase());
        CRUDAction action = CRUDAction.valueOf(((String) privilege).toUpperCase());
        return hasPrivilege(auth, entityType, action);
    }

    public boolean canCreateUserByRole(Authentication auth, String role) {
        Optional<Role> userRole = roleRepository.findByName(auth.getAuthorities().stream().findFirst().get().toString());
        Optional<Role> requestedRole = roleRepository.findByName(role);
        if (userRole.isPresent() && requestedRole.isPresent() && userRole.get().getId() >= requestedRole.get().getId()) {
            return true;
        }
        throw new BadRequestException("No tiene permiso para crear un usuario con el rol especificado");
    }

    private boolean hasPrivilege(Authentication auth, EntityType entityType, CRUDAction action) {
        List<Role> userRoles = roleRepository.findByNameIn(auth.getAuthorities().stream()
                .map(Object::toString)
                .toList());
        boolean hasPermission = userRoles.stream()
                .map(Role::getPrivileges)
                .anyMatch(privileges -> privileges.stream()
                        .anyMatch(privilege -> privilege.getEntityType() == entityType && privilege.getAction() == action));

        if (hasPermission) {
            return true;
        }
        throw new BadRequestException("No tiene permiso para realizar esta acción");
    }
}
