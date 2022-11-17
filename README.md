[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2263/gr2263/)

# Group gr2263 repository

## Kodeprosjekt

I mappestrukturen vår finner du selve kodeprosjektet i **watchlist**-mappen. Under denne mappen finner du java-filene som utgjør den funksjonelle appen vår samt tilsvarende testfiler som tester appens funksjonalitet. I tillegg har vi opprettet ressurser som fxml-filer, style-filer og lagringsfiler under **resources\watchlist** i hver av modulene.

[Link til README-doc i kodingsprosjektet](watchlist/README.md)

## Dokumentasjon

Vi har opprettet en mappe på rotnivå som inneholder dokumentasjon for hver øving (release).
I dokumentasjonen har vi forklart hvordan vi har gått fram for å løse øvingsoppgavene, og hvorfor vi har tatt de valgene vi har tatt med hensyn til arbeidskravene for hver enkelt øving.

[Release 1](docs/release1/release1.md)

[Release 2](docs/release2/release2.md)

[Release 3](docs/release3/release3.md)

## Bygging og kjøring

Roten for prosjektet ligger i mappen **watchlist**.

For å bygge prosjektet, naviger til **watchlist**-mappen og kjør kommandoen `mvn install`. Denne kommandoen kjører i tillegg alle tester samt verktøy for kodekvalitet og testdekningsgrad (CheckStyle, Spotbugs og JaCoCo). Om ønskelig kan testene og nevnte verktøy kjøres alene med kommandoen `mvn verify`.

Prosjektet må kjøres fra **fxui**-mappen. Naviger til mappen med `cd fxui` og kjør deretter kommandoen `mvn javafx:run`.

REST-serveren settes opp fra mappen **restserver**. Naviger fra **watchlist**-mappen med `cd springboot/restserver` og kjør kommandoen `mvn spring-boot:run`.