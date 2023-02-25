package org.rainboyan.demo

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "/about"(view:"/about")
        "/product"(view:"/product")
        "/contact"(view:"/contact")
        "/help"(view:"/help")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
