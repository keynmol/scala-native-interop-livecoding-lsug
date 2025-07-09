#include "httplib.hpp"
#include <string>
#include "httplib-glue.h"

void prepare(httplib::Response &res, Resp* resp) {
    res.status = resp->code;
    switch(resp->bodyType) {
        case JSON:
           res.set_content(reinterpret_cast<char*>(resp->body), "application/json");
           break;
        default:
           break;
    }
}

void start_server(Handlers* handlers, const char* socket, int port) {
    httplib::Server server;

    server.Get("/list",
               [&](const httplib::Request &req, httplib::Response &res) {
                 auto resp = handlers->list();

                 prepare(res, resp);
               });

    server.Post("/create", [&](const httplib::Request &req, httplib::Response &res) {
      std::string width = req.get_param_value("width");
      std::string height = req.get_param_value("height");

      std::cout << "Creating image with width " << width << " and height " << height << std::endl;

      auto resp = handlers->create(std::stoi(width), std::stoi(height));

      prepare(res, resp);
    });

    server.listen(socket, port);
}
