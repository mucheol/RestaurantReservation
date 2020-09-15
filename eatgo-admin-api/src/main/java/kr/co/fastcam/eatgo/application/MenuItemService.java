package kr.co.fastcam.eatgo.application;

import kr.co.fastcam.eatgo.domain.MenuItem;
import kr.co.fastcam.eatgo.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MenuItemService {

    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getMenuItems(Long restaurantId) {
        return menuItemRepository.findAllByRestaurantId(restaurantId);
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        for(MenuItem menuItem : menuItems){
            update(restaurantId, menuItem);
        }
    }

    private void update(Long restaurantId, MenuItem menuItem) {
        if(menuItem.isDestroy()){   //삭제해야할 경우
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }

        //삭제안해도 되는 경우
        menuItem.setRestaurantId(restaurantId);
        menuItemRepository.save(menuItem);
    }
}
