package org.rainboyan.demo.module;

import java.util.List;

public interface WebMenuManager {

    boolean hasMenuForLocation(String location);

    List<MenuModuleDescriptor> getMenus(String location);

    List<MenuItemModuleDescriptor> getItems(String menu);

}
