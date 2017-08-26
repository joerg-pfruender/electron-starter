# Vereinsbrief #

## Zur Installation

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

## Zur Benutzung

Wenn mal was nicht so aussieht, wie Du dachtest, schau mal bei LaTeX nach, wie man's formatieren soll:
[https://wch.github.io/latexsheet/latexsheet.pdf](https://wch.github.io/latexsheet/latexsheet.pdf)

Wichtige Sonderzeichen, die man escapen muss:

* \ -> \\
* $ -> \$
* % -> \%
* & -> \&
* & -> \&

Ich habe überlegt, diese immer zu ersetzen, aber dann bekommen die LaTeX-Profis wieder Probleme. Daher nicht.


## Einfache Anpassungen

Dieses Programm soll "hackbar" sein.

### Die Datei `vereinsbrief.tex.ftl` kann an eigene Bedürfnisse angepasst werden.

Platzhalter haben die Form `${mein_platzhalter}`.
Wenn ein größeres Textfeld im Formular erscheinen soll, dann `${mein_platzhalterLang}`.

### Die Datei `logo.eps` kann durch ein eigenes Logo für den Briefkopf rechts oben ersetzt werden.

Wenn die Größe des Logos beibehalten wird, dann müssen auch keine Anpassungen in `vereinsbrief.tex.ftl` gemacht werden.   


## Zur Weiterentwicklung

Basiert auf [Electron](https://github.com/atom/electron).

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

* an [Olle Törnström](https://github.com/olle) für die Vorlage von [https://github.com/olle/electron-starter](https://github.com/olle/electron-starter)
* an Michael Lenzen für das LaTeX-Template
* an [Benni und Jonas](http://be-jo.net/) für die gute g-brief-Vorlage
* an die Macher von electron, LaTeX, Spring-Boot, Freemarker, Apache-Maven und alle anderen, auf denen das Projekt basiert.
* an Gott, der uns alle geschaffen hat.
  