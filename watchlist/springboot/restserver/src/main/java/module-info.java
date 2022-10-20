module watchlist.springboot.restserver {
  requires transitive com.fasterxml.jackson.databind;

  requires springboot.web;
  requires springboot.beans;
  requires springboot.boot;
  requires springboot.context;

  requires watchlist.core;

  opens todolist.springboot.restserver to spring.beans, spring.context, spring.web;
}