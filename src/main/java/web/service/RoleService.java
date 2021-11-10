package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.RoleDaoData;
import web.model.Role;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

@Service
public class RoleService {

    @Autowired
    private RoleDaoData roleDao;

    private final Map<String, Role> map = new WeakHashMap<>();

    public Role getRoleByName(String role) {
        Role thisRole = map.get(role);
        if (Objects.nonNull(thisRole)) {
            return thisRole;
        }
        Role roleByName = roleDao.findByRole(role);
        map.put(role, roleByName);
        return roleByName;
    }

    public List<Role> getAllRoles() {
        return roleDao.getAllByRole();
    }
}

