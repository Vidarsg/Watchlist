[ Tilbake ](../../README.md)

# Dokumentasjon Release 3

## [Arkitektur](arkitektur.md)

TBD

## Deployment

Vi har implementert **JLink** for å pakke applikasjonen sammen. JLink genererer filer og mapper som gjør det enklere å installere applikasjonen på andre enheter enn utviklerens enhet. Disse filene og mappene er noe uoversiktlig og tungvint for brukeren å måtte installere alle andre pakker og plugins. Dette løser vi med **JPackage**.

    mvn javafx:jlink -f ./watchlist/fxui/

JPackage komprimerer filene fra JLink til en enkelt installeringsfil basert på operativsystemet kommandoen blir kjørt. Filen er av typen `.dmg` for MacOS, `.exe` for Windows, og `.deb` for Linux.

    mvn jpackage:jpackage -f ./watchlist/fxui/

Det skal nevnes at vi ikke fikk bildene til å laste inn i applikasjonen. Da forelesningen kom relativt tett på innleveringsfristen, rakk vi ikke å fikse opp i dette problemet. Vi brukte noe tid til å diskutere hvordan dette potensielt kunne blitt fikset.

Én tanke er at vi kunne lagret bildene lokalt i appen, men vi anså dette som mindre gunstig med tanke på programmets størrelse. En annen idé var å bruke caching til å hente inn bilder i bolker for å sikre at de filmene som vises for brukeren er lastet inn, for så å laste inn bildene fortløpende i bakgrunnen. Da denne delen av innleveringen virket mindre viktig valgte vi heller å bruke tiden på andre mer sentrale utviklingsoppgaver.

## Arbeidsvaner

Under arbeidet med innlevering 3 har vi lagt enda større vekt på god arbeidsmetodikk og tatt med oss tilbakemeldinger på innlevering 2. Blant annet fikk vi innspill om at vi burde inkludere issue-nummer først i commit-meldingen. Dette har vi gjennomført der det er naturlig å knytte til en spesifikk issue. Vi har også valgt å fortsette med å legge til issue-nummer på enden av commit-meldingen for å helgardere oss, samt sørge for at det ikke er noen tvil hvilken issue commiten tilhører.

Alle medlemmene i gruppa har fått tid til å venne seg til det standardiserte commit-oppsettet vårt. Noen commits blir noe store da man kan jobbe på flere deler av programmet samtidig, som til slutt ender opp med å kunne blitt delt i mindre commits for en mer oversiktlig commit-historikk.

Med flittig bruk av VSCodes LiveShare-funksjon har vi også fått samhandlet på flere issues i sanntid uten å måtte vente på andres commits, eller å henge over hverandres PC. Dette vises også med en økning i antall commits merket `co-authored-by`.

### Issues

I Gitlab har vi valgt å skille mellom forskjellige kategorier av issues ved hjelp av 'Labels'. Labelene vi bruker er 'Brukerhistorie', 'Utviklingshistorie', 'Bug', 'Arkitektur', 'Testing', 'Estetisk'. I tillegg har vi labelene 'In Progress' og 'Review' som blir brukt midlertidig for å skille issues visuelt under Issues>Boards.

'Brukerhistorie'-labelen brukes på issues som samsvarer direkte med brukerhistoriene for prosjektet. Disse navngis på formen "N - Beskrivelse", hvor N er nummereringen av brukerhistorien.

'Utviklingshistorie'-labelen brukes på issues som er knyttet til konktrete utviklingsoppgaver. Disse er som oftest en underoppgave av en brukerhistorie. I Gitlab kan vi linke disse issuesene til brukerhistorie-issues, som gjør det lett å se hvilke utviklingshistorier som er tilknyttet hvilke brukerhistorier.

### Git

Når det kommer til arbeidsflyt i Git har vi valgt å lage en ny branch for hver brukerhistorie, samt diverse andre oppgaver som må gjøres. Målet er å merge disse greinene inn i master så fort brukerhistorien/oppgaven er ferdig, slik at master ikke havner for langt bak, som kan føre til tungvinte merge-konflikter.

Under arbeidet med denne innleveringen var vi nødt til å refaktorere koden fra et monolittisk prosjekt til et prosjekt med moduler. Ettersom vi på dette punktet hadde mange forskjellige greiner, kunne vi ikke modularisere alle disse på en grein, siden dette ville blitt veldig mye unødig arbeid. Derfor innførte vi en egen grein hvor vi modulariserte koden, som vi deretter merget de andre greinene inn i. Det ble en del jobb med denne mergingen (spesielt siden git ikke er veldig god på filer som bytter plass), men siden greinene var laget for forskjellige utviklingsoppgaver, var det minimalt med tilfeller hvor forskjellige greiner hadde endret på samme fil. Mergingen ble dermed forholdsvis pen, som viste at arbeidsflyten vi hadde hatt til nå i git har fungert bra.

### Testing og kodekvalitet

Vi har forsøkt å sørge for at vi alltid har tester som er oppdatert og kjører riktig. Etter modulariseringen av koden opplevde vi en del utfordringer med JSON-testene, som gjorde at arbeidet med testene ble litt forsinket. Men vi har alltid lagt stor vekt på å teste manuelt gjennom brukergrensenittet, hvor vi har oppdaget en del større feil og sjekket at appen fungerer som den skal. Det ble også store utfordringer knyttet til TestFX i begynnelsen, men det meste kom på plass før release.

Hva gjelder kodekvalitet har vi valgt å bruke Jacoco for testdekningsgrad, checkstyle for å sjekke formatteringen av koden og spotbugs for å finne bugs i den. Checkstyle har blitt satt opp med Google sin style guide for Java, som er plassert i mappen config/checkstyle. Spotbugs er satt opp med exclude.xml som er å finne i config/spotbugs. Denne er lånt fra todolist-prosjektet. Alle de tre nevnte verktøyene kjører som en del av `mvn install`, men om man kun ønsker å kjøre verktøyene kan man bruke `mvn verify`.

## Sekvensdiagram

![Sekvensdiagram](images/sekvensdiagram.png)
