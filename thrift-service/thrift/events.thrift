namespace java model

struct EventResource {
    1: i16 version;
    2: i64 time;
    3: string message;
}

service EventService {
    void handlEvent(1:EventResource event)
}