module watchlist.springboot.restserver {
  requires transitive com.fasterxml.jackson.databind;

  requires spring.web;
  requires spring.beans;
  requires spring.boot;
  requires spring.context;
  requires spring.boot.autoconfigure;

  requires watchlist.core;

  opens watchlist.springboot.restserver to spring.beans, spring.context, spring.web;
}