class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                
            }
        }
        "/"(controller:"phoneBillDetails",action:"home")
        "500"(controller: "error")
	}
}
