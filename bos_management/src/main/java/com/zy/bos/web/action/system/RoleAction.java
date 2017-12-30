package com.zy.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.domain.system.Role;
import com.zy.bos.service.system.Roleservice;
import com.zy.bos.utils.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

    private String[] permissionIds;
    private String menuIds;

    public void setPermissionIds(String[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    @Autowired
    private Roleservice roleservice;

    @Action(value = "role_list", results = {
            @Result(type = "json")
    })
    public String list() {
        List<Role> roles = roleservice.findAll();
        ActionContext.getContext().getValueStack().push(roles);
        return SUCCESS;
    }

    @Action(value = "role_save", results = {
            @Result(type = "redirect", location = "pages/system/role.html")
    })
    public String save() {
        roleservice.save(model, permissionIds, menuIds);
        return SUCCESS;
    }


}
