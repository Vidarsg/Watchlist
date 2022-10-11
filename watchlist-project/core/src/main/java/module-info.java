module watchlist.core {
    requires transitive com.fasterxml.jackson.databind;

    exports watchlist.core;
    exports watchlist.json;
    
    opens watchlist.core to com.fasterxml.jackson.databind;
}