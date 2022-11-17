module watchlist.core {
    requires transitive com.fasterxml.jackson.databind;

    exports watchlist.core;
    exports watchlist.json;

    requires java.net.http;

    opens watchlist.core to com.fasterxml.jackson.databind;
}