class UrlMappings {
  static mappings = {
    "/$controller/$action?/$id?" {
      constraints {
        // apply constraints here
      }
    }

    "/" {
      controller = "main"
      action = "index"
    }

    "500"(view: '/error')
  }
}
