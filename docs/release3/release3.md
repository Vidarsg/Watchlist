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
