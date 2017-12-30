package com.zy.bos.service.system;

import com.zy.bos.domain.system.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> findAll();

    void save(Menu model);
}
