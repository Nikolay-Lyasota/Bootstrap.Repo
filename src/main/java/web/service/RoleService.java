package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.RoleDaoImpl;
import web.model.Role;


import java.util.*;

@Service
public class RoleService {

    @Autowired
    private RoleDaoImpl roleDao;

    private Map<String, Role> map = new WeakHashMap<>();

    public Role getRoleByName(String role) {
        Role thisRole = map.get(role);
        if(Objects.nonNull(thisRole)) {
            return thisRole;
        }
        Role roleByName = roleDao.getSingleRoleByName(role);
        map.put(role,roleByName);
        return roleByName;
    }

    public List<Role> getAllRoles() {
       return roleDao.getAllRoles();
    }
}

