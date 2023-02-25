package org.rainboyan.demo.module

import grails.plugins.*
import grails.util.Environment
import grails.util.GrailsUtil

class ModuleGrailsPlugin extends DynamicPlugin {
    def version = "1.0.0-SNAPSHOT"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = GrailsUtil.getGrailsVersion()

    def providedModules = [
            LanguageModuleDescriptor,
            MenuModuleDescriptor,
            MenuItemModuleDescriptor
    ]

    Closure doWithSpring() { {->
        // Grails bugs here, because doWithSpring's delegate not set
//            webMenuManager(DefaultWebMenuManager)
        }
    }

    void doWithDynamicMethods() {
        String.metaClass.getMenus = { ->
            def webMenuManager = applicationContext.webMenuManager
            webMenuManager.getMenus(delegate)
        }
        String.metaClass.getMenuItems = { ->
            def webMenuManager = applicationContext.webMenuManager
            webMenuManager.getItems(delegate)
        }
    }

    void doWithDynamicModules() {
        // Supported Languages
        language(key: 'en_US', title: 'English')
        language(key: 'zh_CN', title: 'Chinese (Simplified Chinese)')
        language(key: 'zh_TW', title: 'Chinese (Traditional Chinese)', enabled: false)
        // Web site Menu
        menu(key: 'about', i18n: 'menu.about', title: 'About US', link: '/about', location: 'topnav') {
            order = 1
            description = 'This is a description'
        }
        menu(key: 'product', i18n: 'menu.product', title: 'Products', link: '/product', location: 'topnav', enabled: "${Environment.isDevelopmentMode()}") {
            params = [id: '100', name: 'product', env: Environment.isDevelopmentMode()]
            description = "This menu enabled: ${Environment.isDevelopmentMode()}"
            order = 2
        }
        menu(key: 'contact', i18n: 'menu.contact', title: 'Contact', link: '/contact', location: 'topnav')
        menu(key: 'help', i18n: 'menu.help', title: 'Help', link: '/help', location: 'footer')
        menu(key: 'application_status', i18n: 'menu.application_status', title: 'Application Status', link: '#', location: 'topnav')
        menuItem(key: 'env', title: "Environment: ${Environment.current.name}", link: '#', menu: 'application_status')
        menuItem(key: 'profile', title: "App profile: ${grailsApplication.config.getProperty('grails.profile')}", link: '#', menu: 'application_status')
        menuItem(key: 'appversion', title: "App version: ${grailsApplication.config.getProperty('info.app.version')}", link: '#', menu: 'application_status')
        menuItem(key: 'grailsversion', title: "Grails version: ${grailsApplication.config.getProperty('info.app.grailsVersion')}", link: '#', menu: 'application_status')
        menuItem(key: 'groovyversion', title: "Groovy version: ${GroovySystem.getVersion()}", link: '#', menu: 'application_status')
        menuItem(key: 'javaversion', title: "JVM version: ${System.getProperty('java.version')}", link: '#', menu: 'application_status')
        menuItem(key: 'reloadingenabled', title: "Reloading active: ${Environment.reloadingAgentEnabled}", link: '#', menu: 'application_status')
        menu(key: 'artefacts', i18n: 'menu.artefacts', title: 'Artefacts', link: '#', location: 'topnav')
        menuItem(key: 'controllers', title: "Controllers: ${grailsApplication.controllerClasses.size()}", link: '#', menu: 'artefacts')
        menuItem(key: 'domains', title: "Domains: ${grailsApplication.domainClasses.size()}", link: '#', menu: 'artefacts')
        menuItem(key: 'services', title: "Services: ${grailsApplication.serviceClasses.size()}", link: '#', menu: 'artefacts')
        menuItem(key: 'taglibraries', title: "Tag Libraries: ${grailsApplication.tagLibClasses.size()}", link: '#', menu: 'artefacts')
        menu(key: 'plugins', i18n: 'menu.plugins', title: 'Installed Plugins', link: '#', location: 'topnav')
        applicationContext.getBean('pluginManager').allPlugins.eachWithIndex { plugin, index ->
            menuItem(key: "${plugin.name}", title: "${plugin.name} - ${plugin.version}", link: '#', menu: 'plugins') {
                order = index + 1
            }
        }
    }
}
