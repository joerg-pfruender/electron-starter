%%
%% Vorlagen:
%% http://be-jo.net/2012/07/formellen-brief-mit-latex-und-g-brief2-setzen/
%% http://be-jo.net/wp-content/uploads/2012/07/g-brief2.pdf
%%
%
% Dies Vorlage wird WIE BESEHEN OHNE GEWAEHR ODER VORBEHALTE
% - ganz gleich, ob ausdruecklich oder stillschweigend - bereitgestellt,
% insbesondere Gewaehrleistungen oder Vorbehalten des EIGENTUMS,
% NICHTVERLETZUNG VON RECHTEN DRITTER, HANDELSUEBLICHKEIT
% oder EIGNUNG FUER EINEN BESTIMMTEN ZWECK.
%
% KEINE HAFTUNG FUER jegliche SCHAEDEN,
% einschliesslich direkter, indirekter,
% konkreter, beilaeufig entstandener Schaeden oder Folgeschaeden
% jeglicher Art, die infolge der Nutzung oder der Unfaehigkeit
% zur Nutzung der Vorlage entstehen
% (insbesondere Schaeden durch Verlust des Firmenwerts,
% Arbeitsunterbrechung, Computerausfall oder Betriebsstoerung
% oder alle sonstigen wirtschaftlichen Schaeden oder Verluste).
%
\documentclass[12pt,ngerman]{g-vereinsbrief}
\usepackage[utf8]{inputenc}
\usepackage[scaled]{helvet}
\usepackage[T1]{fontenc}
\usepackage[pdftex]{graphicx}
\usepackage[export]{adjustbox}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% Einstellungen fuer den Brief

%\unserzeichen
%\trennlinien
%\lochermarke
%\faltmarken
%\fenstermarken

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% Angaben zum Absender


\Name{ ${absenderKopf} \hfill\raggedright{\includegraphics[width=6.0cm,trim={0 0 4cm 0},clip]{logo.eps}}}
%\includegraphics[width=1.0cm]{logo.eps} ${absender}}}
%\includegraphics[width=6.0\textwidth,right]{logo.eps}
%\raggedright{\includegraphics[width=6.0cm]{logo.eps}}

%\Name			{${absenderName}}
\NameZeileA{${vorstandA}}
\NameZeileB{${vorstandB}}
\NameZeileC{${vorstandC}}
\NameZeileD{${vorstandD}}
\NameZeileE{${vorstandE}}
\NameZeileF{${vorstandF}}
\NameZeileG{${vorstandG}}

\AdressZeileA		{${adresseA}}
\AdressZeileB		{${adresseB}}
\AdressZeileC		{${adresseC}}
\AdressZeileD		{${adresseD}}
\AdressZeileE		{${adresseE}}
\AdressZeileF		{${adresseF}}

\TelefonZeileA		{${telefonA}} % 0\,65\,43) 21\,03\,21
\TelefonZeileB		{${telefonB}}
\TelefonZeileC		{${telefonC}}
\TelefonZeileD		{${telefonD}}
\TelefonZeileE		{${telefonE}}
\TelefonZeileF		{${telefonF}}

\InternetZeileA		{${internetA}}
\InternetZeileB		{${internetB}}
\InternetZeileC		{${internetC}}
\InternetZeileD		{${internetD}}
\InternetZeileE		{${internetE}}
\InternetZeileF		{${internetF}}

\BankZeileA		{${kontoA}}
\BankZeileB		{${kontoB}}
\BankZeileC		{${kontoC}}
\BankZeileD		{${kontoD}}
\BankZeileE		{${kontoE}}
\BankZeileF		{${kontoF}}

\RetourAdresse{${absenderRetour}}

\Unterschrift		{${unterschrift}}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% Angaben zum Empfaenger

\Postvermerk		{${postvermerk}}
\Adresse		{${adresseLang}}
\Betreff		{${betreff}}
\Datum			{\today}
\Anrede			{${anrede}}
\Gruss			{${gruss}}{1cm}

%\Anlagen		{\vspace{1.5cm}
%			\underline{Anlagen}\\ \\%
%			Anlage 1\\
%			Anlage 2\\
%			}
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%% Der Brief

\renewcommand\familydefault{\sfdefault}
\begin{document}

\begin{g-brief}

${briefLang}

\end{g-brief}
\end{document}
\grid{\includegraphics[width=6.0cm]{brief_unten.png}}

