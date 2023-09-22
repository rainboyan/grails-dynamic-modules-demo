# Grails Dynamic Modules Demo

[Grails Dynamic Modules Plugin](https://github.com/rainboyan/grails-plugin-dynamic-modules)(GDMP) offer new ways of creating modular and maintainable Grails applications.

This project [Grails Dynamic Modules Demo](https://github.com/rainboyan/grails-dynamic-modules-demo) show you how to Develop dynamic modules in your Grails plugin, 
I create three plugin module types: `LanguageModuleDescriptor`, `MenuModuleDescriptor`, `MenuItemModuleDescriptor`, and use these modules to rewrite the Grails main page.

## Grails Version

- Grails **4.1.2**

## Usage

[Grails Dynamic Modules Plugin](https://github.com/rainboyan/grails-plugin-dynamic-modules) has been released to [Maven Central](https://central.sonatype.com/artifact/org.rainboyan.plugins/grails-plugin-dynamic-modules), please check and update the latest version.

Since the plugin is for building multiple modules, I highly recommend that you read this guide [Grails Multi-Project Build](https://guides.grails.org/grails-multi-project-build/guide/index.html) first. 

In this project, I create a `Menu` plugin in the `plugins/menu`.

```bash
.
├── gradle
│   └── wrapper
├── grails-app
│   ├── assets
│   ├── conf
│   ├── controllers
│   ├── domain
│   ├── i18n
│   ├── init
│   ├── services
│   ├── taglib
│   ├── utils
│   └── views
├── plugins
│   └── menu
├── src
│   ├── integration-test
│   ├── main
│   └── test
├── build.gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── grails-wrapper.jar
├── grailsw
├── grailsw.bat
└── settings.gradle
```

Add dependency to the `build.gradle`,

```gradle

repositories {
    mavenCentral()
}

dependencies {

    // in Grails 4
    compile "org.rainboyan.plugins:grails-plugin-dynamic-modules:0.1.0"

    // in Grails 5/6
    implementation "org.rainboyan.plugins:grails-plugin-dynamic-modules:0.1.0"
}

```

In the Grails plugin project: `plugins/menu`,

create a Module descriptor: `MenuModuleDescriptor`,

```groovy
@ModuleType('menu')
class MenuModuleDescriptor extends AbstractModuleDescriptor {

    String i18n
    String title
    String link
    String location
    int order

    MenuModuleDescriptor() {
    }

    @Override
    void init(GrailsPlugin plugin, Map args) throws PluginException {
        super.init(plugin, args)
        this.i18n = args.i18n
        this.title = args.title
        this.link = args.link
        this.location = args.location
    }
}
```

and then update the `MenuGrailsPlugin` to extend `grails.plugins.DynamicPlugin`,

```groovy
class MenuGrailsPlugin extends DynamicPlugin {

    // 1. add your new module types
    def providedModules = [
            MenuModuleDescriptor
    ]

    // 2. define 'menu' modules in doWithDynamicModules
    void doWithDynamicModules() {
        menu(key: 'about', i18n: 'menu.about', title: 'About US', link: '/about', location: 'topnav')
        menu(key: 'product', i18n: 'menu.product', title: 'Products', link: '/product', location: 'topnav', enabled: "${Environment.isDevelopmentMode()}") {
            description = "This menu enabled: ${Environment.isDevelopmentMode()}"
            order = 2
        }
        menu(key: 'contact', i18n: 'menu.contact', title: 'Contact', link: '/contact', location: 'topnav', enabled: false)
        menu(key: 'help', i18n: 'menu.help', title: 'Help', link: '/help', location: 'footer')
    }
}
```

now, you can get all the module descriptors in your Grails application throug the extended API of `GrailsPluginManager`,

```groovy

// Get all the ModuleDescriptors
Collection<ModuleDescriptor<?>> allDescriptors = pluginManager.getModuleDescriptors()

// Get all the enabled MenuModuleDescriptor
List<MenuModuleDescriptor> menuDescriptors = pluginManager.getEnabledModuleDescriptorsByClass(MenuModuleDescriptor)

```

Now you can run it,

![Grails Dynamic Modules Demo](screenshot.png)

## License

This plugin is available as open source under the terms of the [APACHE LICENSE, VERSION 2.0](http://apache.org/Licenses/LICENSE-2.0)

## Links

- [Grails Website](https://grails.org)
- [Grails Plugins](https://docs.grails.org/4.0.0/guide/plugins.html)
- [Grails Github](https://github.com/grails)
- [Grails Dynamic Modules Plugin](https://github.com/rainboyan/grails-plugin-dynamic-modules)
- [Grails Dynamic Modules Demo](https://github.com/rainboyan/grails-dynamic-modules-demo)
- [Project Jigsaw](https://openjdk.org/projects/jigsaw/)
- [OSGi Specifications](https://docs.osgi.org/specification/)
- [Spring Dynamic Modules Reference Guide](https://docs.spring.io/spring-osgi/docs/current/reference/html/)
