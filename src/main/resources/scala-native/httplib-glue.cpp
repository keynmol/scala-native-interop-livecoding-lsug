#include "httplib-glue.h"
#include "httplib.hpp"
#include <string>

void prepare(httplib::Response &res, Resp *resp) {
  res.status = resp->code;
  switch (resp->bodyType) {
  case JSON:
    res.set_content(static_cast<char *>(resp->body), "application/json");
    break;
  case TEXT:
    res.set_content(static_cast<char *>(resp->body), "text/plain");
    break;
  default:
    break;
  }
}

void start_server(Handlers *handlers, const char *socket, int port) {
  httplib::Server server;

  server.Get("/list", [&](const httplib::Request &req, httplib::Response &res) {
    auto resp = handlers->list();

    prepare(res, resp);
  });

  server.Post(
      "/create", [&](const httplib::Request &req, httplib::Response &res) {
        auto width = req.get_param_value("width");
        auto height = req.get_param_value("height");

        std::cout << "Creating order with width " << width << " and height "
                  << height << std::endl;

        auto resp = handlers->create(std::stoi(width), std::stoi(height));

        prepare(res, resp);
      });

  std::cout << "Starting server on http://" << socket << ":" << port << std::endl;

  server.listen(socket, port);
}
