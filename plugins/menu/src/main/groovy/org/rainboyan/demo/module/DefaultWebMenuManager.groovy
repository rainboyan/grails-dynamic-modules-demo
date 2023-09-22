package org.rainboyan.demo.module

import grails.plugins.GrailsPluginManager
import grails.plugins.PluginManagerAware

class DefaultWebMenuManager implements WebMenuManager, PluginManagerAware {

    private GrailsPluginManager pluginManager

    private Map<String, List<MenuModuleDescriptor>> menus = new HashMap<>()
    private Map<String, List<MenuItemModuleDescriptor>> items = new HashMap<>()

    DefaultWebMenuManager() {
    }

    DefaultWebMenuManager(GrailsPluginManager pluginManager) {
        this.pluginManager = pluginManager
    }

    @Override
    boolean hasMenuForLocation(String location) {
        return !getMenus(location).isEmpty()
    }

    @Override
    List<MenuModuleDescriptor> getMenus(String location) {
        if (location == null) {
            return Collections.emptyList()
        }

        List<MenuModuleDescriptor> result = menus.get(location)

        if (result == null) {
            result = new ArrayList<>()
            List<MenuModuleDescriptor> descriptors = pluginManager.getEnabledModuleDescriptorsByClass(MenuModuleDescriptor)
            for (MenuModuleDescriptor descriptor : descriptors) {
                if (location.equalsIgnoreCase(descriptor.getLocation())) {
                    result.add(descriptor)
                }
            }

            menus.put(location, result)
        }

        return result
    }

    @Override
    List<MenuItemModuleDescriptor> getItems(String menu) {
        if (menu == null) {
            return Collections.emptyList()
        }

        List<MenuItemModuleDescriptor> result = items.get(menu)

        if (result == null) {
            result = new ArrayList<MenuItemModuleDescriptor>()
            List<MenuItemModuleDescriptor> descriptors = pluginManager.getEnabledModuleDescriptorsByClass(MenuItemModuleDescriptor)
            for (MenuItemModuleDescriptor descriptor : descriptors) {
                if (menu.equalsIgnoreCase(descriptor.getMenu()))
                    result.add(descriptor)
            }

            items.put(menu, result)
        }

        return result
    }

    @Override
    void setPluginManager(GrailsPluginManager pluginManager) {
        this.pluginManager = pluginManager
    }

}
