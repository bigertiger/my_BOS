package com.zy.bos.web.action.system;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.domain.system.Menu;
import com.zy.bos.service.system.MenuService;
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
public class MenuAction extends BaseAction<Menu>{

    @Autowired
    private MenuService menuService;

    @Action(value = "menu_list", results = {
            @Result(type = "json")
    })
    public String list() {

        List<Menu> menus = menuService.findAll();
        ActionContext.getContext().getValueStack().push(menus);

        return SUCCESS;
    }

    @Action(value = "menu_save", results = {
            @Result(type = "redirect", location = "pages/system/menu.html")
    })
    public String save() {
        menuService.save(model);


        return SUCCESS;
    }


}
