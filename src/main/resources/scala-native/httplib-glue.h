extern "C" {

typedef enum {
    OK = 200,
    SERVER_ERROR = 500,
    BAD_REQUEST = 400
} Code;

typedef enum {
    JSON, TEXT, NONE
} BodyType;

typedef struct {
    Code code;
    BodyType bodyType;
    void* body;
} Resp;

typedef struct {
    Resp* (*list)(void);
    Resp* (*create)(int width, int height);
} Handlers;

void start_server(Handlers* handlers, const char* socket, int port);
}
