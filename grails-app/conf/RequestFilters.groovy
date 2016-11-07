class RequestFilters {
	def filters = {
	   requestHeadersFilter(controller: '*', action: '*') {
		   after = {
			   response.setHeader("Pragma", "no-cache")
			   response.setDateHeader("Expires", 1L)
			   response.setHeader("Cache-Control", "no-cache")
			   response.addHeader("Cache-Control", "no-store")
		   }
	   }
	}
 }