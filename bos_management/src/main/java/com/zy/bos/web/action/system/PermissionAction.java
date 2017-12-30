package com.zy.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.domain.system.Permission;
import com.zy.bos.service.system.PermissionService;
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
public class PermissionAction extends BaseAction<Permission> {

    @Autowired
    private PermissionService permissionService;


    @Action(value = "permission_list", results = {
            @Result(type = "json")
    })
    public String list() {

        List<Permission> permissions =  permissionService.findAll();
        ActionContext.getContext().getValueStack().push(permissions);
        return SUCCESS;
    }


    @Action(value = "permission_save", results = {
            @Result(type = "redirect", location = "pages/system/permission.html")
    })
    public String save() {

        permissionService.save(model);
        return SUCCESS;
    }

}
