module watchlist.springboot {
  requires transitive com.fasterxml.jackson.databind;

  requires springboot.web;
  requires springboot.beans;
  requires springboot.boot;
  requires springboot.context;

  requires watchlist.core;

  opens watchlist.core to com.fasterxml.jackson.databind;
}