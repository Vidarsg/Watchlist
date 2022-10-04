module watchlist.core {
    requires com.fasterxml.jackson.databind;

    exports watchlist.core;
    opens watchlist.core to com.fasterxml.jackson.databind;
}