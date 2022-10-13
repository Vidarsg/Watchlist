[ Tilbake ](../../README.md)

# Dokumentasjon Release 2

## [ Arkitektur ](arkitektur.md)

## Arbeidsmetodikk
Under arbeidet med innlevering 2 har vi lagt større vekt på god arbeidsmetodikk i forhold til det vi gjorde med innlevering 1. Med den 1. innleveringen forsøkte vi å legge grunnlaget for en god arbeidsflyt med issues og bruk av git, men ettersom omfanget av innleveringen var begrenset var det først under denne innleveringen at vi faktisk fikk satt arbeidsflyten vår på prøve.


### Issues

I Gitlab har vi valgt å skille mellom forskjellige kategorier av issues ved hjelp av 'Labels'. Labelene vi bruker er 'Brukerhistorie', 'Utviklingshistorie', 'Bug', 'Arkitektur', 'Testing', 'Estetisk'. I tillegg har vi labelene 'In Progress' og 'Review' som blir brukt midlertidig for å skille issues visuelt under Issues>Boards.

'Brukerhistorie'-labelen brukes på issues som samsvarer direkte med brukerhistoriene for prosjektet. Disse navngis på formen "N - Beskrivelse", hvor N er nummereringen av brukerhistorien.

'Utviklingshistorie'-labelen brukes på issues som er knyttet til konktrete utviklingsoppgaver. Disse er som oftest en underoppgave av en brukerhistorie. I Gitlab kan vi linke disse issuesene til brukerhistorie-issues, som gjør det lett å se hvilke utviklingshistorier som er tilknyttet hvilke brukerhistorier. 

### Git 

Når det kommer til arbeidsflyt i Git har vi valgt å lage en ny branch hvor hver brukerhistorie, samt diverse andre oppgaver som må gjøres. Målet er å merge disse greinene inn i master så fort brukerhistorien/oppgaven er ferdig, slik at master ikke havner for langt bak, som kan føre til tungvinte merge-konflikter.

Under arbeidet med denne innleveringen var vi nødt til å refaktorere koden fra et monolittisk prosjekt til et prosjekt med moduler. Ettersom vi på dette punktet hadde mange forskjellige greiner, kunne vi ikke modularisere alle disse på en grein, siden dette ville blitt veldig mye unødig arbeid. Derfor innførte vi en egen grein hvor vi modulariserte koden, som vi deretter merget de andre greinene inn i. Det ble en del jobb med denne mergingen (spesielt siden git ikke er veldig god på filer som bytter plass), men siden greinene var laget for forskjellige utviklingsoppgaver, var det minimalt med tilfeller hvor forskjellige greiner hadde endret på samme fil. Mergingen ble dermed forholdsvis pen, som viste at arbeidsflyten vi hadde hatt til nå i git har fungert bra.  

### Testing og kodekvalitet

Vi har forsøkt å sørge for at vi alltid har tester som er oppdatert og kjører riktig. Etter modulariseringen av koden opplevde vi en del utfordringer med JSON-testene, som gjorde at arbeidet med testene ble litt forsinket. Men vi har alltid lagt stor vekt på å teste manuelt gjennom brukergrensenittet, hvor vi har oppdaget en del større feil og sjekket at appen fungerer som den skal. Det ble også store utfordringer knyttet til TestFX i begynnelsen, men det meste kom på plass før release.

Hva gjelder kodekvalitet har vi valgt å bruke Jacoco for testdekningsgrad, checkstyle for å sjekke formatteringen av koden og spotbugs for å finne bugs i den. Checkstyle har blitt satt opp med Google sin style guide for Java, som er plassert i mappen config/checkstyle. Spotbugs er satt opp med exclude.xml som er å finne i config/spotbugs. Denne er lånt fra todolist-prosjektet. Alle de tre nevnte verktøyene kjører som en del av `mvn install`, men om man kun ønsker å kjøre verktøyene kan man bruke `mvn verify`.