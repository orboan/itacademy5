# Repositori amb exercicis resolts d'IT Academy
## BE Java - Mòdul 8 (Patrons de disseny)
### Nivell 3 (Videos)

A tenir en compte:

Els requeriments de l'enunciat de l'exerici es compleixen i, addicionalment tenim:

- Inicialment estem identificats com a l'usuari amb nom d'usuari 'jamic'. Tot el programa presuposa que els vídeos són d'aquest usuari. En una versió posterior del programa s'inclourà una funcionalitat per tal de fer login o crear un compte d'usuari, de manera que totes les funcionalitats actuals del programa es podran aplicar a tots els usuaris (no només a l'usuari _jamic_).
- Hi ha 2 tipus de repositoris online per als vídeos: el privat i el públic. El repositori privat pertany a l'usuari actual i només és accessible per ell mateix. El repositori públic és accessible a tots els usuaris.
- Quan es crea un vídeo cal informar del seu títol, la seva URL (identificador únic del vídeo) i de la seva durada en segons. Se li podran afegir tantes etiquetes com es vulgui.
- Un cop un vídeo està creat, es podrà pujar (opcionalment) tant al repositori privat com al públic. Si inicialment el vídeo es puja al repositori privat, si es vol pujar al públic, no es tornarà a pujar, però es farà 'públic'. Si el vídeo no es puja, no es podrà reproduir.
- La durada del vídeo informada en la seva creació determinarà la durada de la pujada del vídeo (una tercera part) i la durada del temps de verificació (la meitat del temps de la durada del vídeo).
- Un cop pujat un vídeo, autmàticament es mostrarà el reproductor de vídeo, amb tres estats possibles: Stopped, Playing i Paused. S'observarà l'estat així com el temps transcorregut, fins arribar al final establert per la durada del vídeo. 
- Un cop pujat un vídeo, es podrà escollir de la llista del repositori privat o públic per a tornar ser reproduit.

A nivell de disseny:

- En ser un exercici per a treballar patrons de disseny, he volgut fer un disseny que n'inclogui alguns. En concret, hi trobem: DI, State, Singleton, DAO, Facade, i MVC (aquest és estrictament un patró arquitectònic). També es fa ús de listeners.
- L'arquitectura s'ha estructurat en capes: persistència, business i presentació. 
- De moment (en aquesta versió) no es retornen còpies defensives dels objectes del data source, però caldria fer-ho des dels daos.
- En arrencar l'aplicació des del main, s'injecta un DataSource volàtil, però és perfectament intercanviable amb un altre tips de DataSource (com un mysql) que s'hagi implementat.
- He usat el meu propi Set, anomenat MySet, per tal d'afegir un mètode get(Object o) que el Set no incorpora. Aquest mètode ens permetrà implementar d'una manera més senzilla la gestió d'elements (usuaris, video i tags) repetits al DataSource.
- L'aplicació combina Tui (Terminal User Interface) i Gui. Amb Tui hi ha implementats els menus i els formularis de creació de videos i tags, i amb Gui el feedback de pujada i verificació de vídeo i el reproductor de vídeos.
- L'estat de pujada (uploading, verifying, uploaded) està implementat amb un enum, mentre que l'estat de reproducció (stopped, playing, paused) està implementat amb el patró State (usant un enum a dins).
