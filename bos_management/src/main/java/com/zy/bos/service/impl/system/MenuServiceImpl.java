package com.zy.bos.service.impl.system;

import com.zy.bos.dao.base.system.MenuRepository;
import com.zy.bos.domain.system.Menu;
import com.zy.bos.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {


    @Override
    public void save(Menu model) {
        if(model.getParentMenu() != null &&
                model.getParentMenu().getId() == 0) {
            model.setParentMenu(null);
        }
        menuRepository.save(model);
    }

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}
