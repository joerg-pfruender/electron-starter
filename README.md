# Vereinsbrief #

## Zur Benutzung

Electron Applikation zur Erstellung eines Geschäftsbriefs für einen Verein

Basiert auf [JAVA TM](https://www.java.com/de/), [LaTeX](https://www.latex-project.org/) und [Electron](https://github.com/atom/electron)


Vor der Installation von "Vereinsbrief" ist die Installation von Java und LaTex nötig:

* [JAVA TM SE](https://java.com/de/download/)
* [LaTeX](https://www.latex-project.org/get/)


* [Anleitung für Windows](http://praxistipps.chip.de/latex-unter-windows-installieren-so-gehts_30111)
* Ubuntu-Linux: `sudo apt-get install texlive-latex-base texlive-latex-extra texlive-latex-recommended xzdec`

Bei Bedarf müssen evtl. noch TeX-Pakete nachinstalliert werden:


`tlmgr init-usertree`
 
`tlmgr update --all`

`tlmgr install adjustbox`


## Zur Entwicklung

Basiert auf Electron.

Es müssen vorhanden sein:
* [Node.js](https://nodejs.org/en/download/current/)
* [npm](https://www.npmjs.com/get-npm)
* [JAVA TM SE JDK 8]()https://java.com/de/download/)
* [Maven](https://maven.apache.org/)


Wichtige Befehle:
* `make`
* `mvn package` Java bauen
* `npm install` Electron-App bauen
* `npm start`   Electron-App starten
* `npm install electron-packager -g` Electron app zusammenpacken

Vielen Dank an:

* an [Olle Törnström](https://github.com/olle) für die Vorlage von https://github.com/olle/electron-starter
* an Michael Lenzen für das LaTeX-Template
* an [Benni und Jonas](http://be-jo.net/) für die gute g-brief-Vorlage
* an die Macher von electron, LaTeX, Spring-Boot, Freemarker, Apache-Maven und alle anderen, auf denen das Projekt basiert.
* an Gott, der uns alle geschaffen hat.
  