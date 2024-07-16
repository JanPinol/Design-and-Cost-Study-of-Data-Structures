			    	--- ProjectE PAED 2n Semestre ---
           /\                                                 /\
 _         )( ______________________   ______________________ )(         _
(_)///////(**)______________________> <______________________(**)\\\\\\\(_)
           )(                                                 )(
           \/    --- ELS CAVALLERS DE LA TAULA DE HASH ---    \/




1.- Per fer aquest projecte s'ha utilitzat el llenguatge de progamació Java
i no s'ha utilitzat cap altre llibreria externa.

2.- L'IDE utilitzat per programar ha estat l'IntelIJ en la versió 2023.1.1 i
versions 2022. Amb el SDK <openjdk-18 java version 18.0.2>.

3.- CONSIDERACIONS DE EXECUCIÓ
	3.0 - GENERAL
		3.0.1 - Abans d'executar el programa s'ha d'anar a la carpeta
		"business" i buscar la classe "GlobalsBusiness" i canviar de manera
		manual el path dels fitxers datasets que es llegiran a la variable
		estàtica corresponent, si no es fa bé el programa llençarà una exeption
		i s'aturarà la execució.

		3.0.2 - Per la consola es mostraran totes les opcions en català
		i per tant hi ha paraules que contenen accents i depenent quina consola
		s'utilitzi pot ser que no detecti aquell caràctes (si s'utilitza la de
		l'IDE	IntelIJ no hi ha cap problema)

		3.0.3 - En tots els menus hi ha una opció de tornar endarrera en el cas
 		que vulgui
		
		
	3.1 - Sobre orenetes i cocos (Grafs)

		3.1.1 - Si s'executa l'opcio 'B' - DETECCIÓ DE TRAJECTES HABITUALS 
		no es canvia el graf intern, si no que et retorna un nou graf amb els
		mateixos nodes que l'original pero passat per l'algoritme de MST.
		
		3.1.2 - Si s'executa l'opció 'C' - MISSATGERIA PREMIUM
		l'algoritme determina el camí més eficient per enviar un missatge des d'un
		punt a un altre, sempre que el punt d'origen i el destí no siguin climes
		polars i tropicals o viceversa i existeixin possibles camins on una oreneta
		no passi per un clima que no pot (les orenetes africanes només poden passar
		per climes continentals o tropicals i les europees per polars i continentals).
		En cas que es pugui crear el camí per on el tipus d'oreneta pot passar, es
		mostrarà el temps emprat per enviar-la, la distància a recórrer i els
		llocs d'interés per on passa.
		Si no es pot traçar una ruta, llavors s'indicarà que no es pot enviar
		cap oreneta cap aquell lloc.

	3.2 - Caça de bruixes (Arbres binaris de cerca)
		
		Cal esmentar que el desenvolupament i testeig d'aquesta part del projecte s'ha fet
		en major part emprant els datasets de menys repeticions, per tant, es pot donar el cas que 
		si s'utilitzen altres datasets (els de més repeticions) alguna funcionalitat no acabi
		de funcionar com s'espera. 

		3.2.1 - S'ha creat dos arbres, un AVL i un altre BST, es dona la possiblitat
		d'escollir el que es vulgui a l'escollir la funcionalitat CAÇA DE BRUIXES

		3.2.2 - En totes les opcions si introduim una dada que no correspon en el
 		format que el programa s'espera, se't llençarà un missatge d'error i a
		continuació un missatge de "Esculli una opció: " després d'aquest missatge
		s'ha de introduir el valor correcte si no així fins que s'introdueixi o pari
		la execució de manera forçada.

		3.2.3 - Si es tria la opció 'F' - TORNAR ENRERE l'arbre binari de cerca es
		borra de la memoria i, per tant, tot el que no estigui a persistència, es a dir
		al dataset no quedarà guardat i al crear-se de nou no apareixerà.

	3.3 - Tanques de bardissa (Arbres R)
		
		3.3.1 - Els arbres R estan ben creats però es pot donar el cas que a l'hora
		d'afegir una nova és possible que no compleixi amb el "minimum bounding box"

		3.3.2 - La visualizació es fa de manera que cada tabulat representa 
		l'alçada a la que es troba la Fulla. En color vermell es mostraran les
		coordenades de les "locations", en blau es mostrarà la guia del que apareixerà
		després en color verd i finalment en color verd es mostrarà la informació de
		la bardissa o bardisses d'aquella 'location'
		
		3.3.3 - Si es tria la opció 'E' - OPTIMITZACIÓ ESTÈTICA el color proporcionat
		al final es mostra en nombres exadecimals.

		3.3.4 - En totes les opcions si introduim una dada que no correspon en el
 		format que el programa s'espera, se't llençarà un missatge d'error i a
		continuació un missatge de "Esculli una opció: " després d'aquest missatge
		s'ha de introduir el valor correcte si no així fins que s'introdueixi o pari
		la execució de manera forçada.

		3.3.5 - Si es tria la opció 'F' - TORNAR ENRERE l'arbre R es
		borra de la memoria i, per tant, tot el que no estigui a persistència, es a dir
		al dataset no quedarà guardat i al crear-se de nou no apareixerà.

	3.4 - D'heretges i blasfems (Taules)

		3.4.1 - En totes les opcions si introduim una dada que no correspon en el
 		format que el programa s'espera, se't llençarà un missatge d'error i a
		continuació un missatge de "Esculli una opció: " després d'aquest missatge
		s'ha de introduir el valor correcte si no així fins que s'introdueixi o pari
		la execució de manera forçada.
		
		3.4.2 - Si es tria la opció de l'historiograma al tancar la GUI no es para
		l'execució del programa.

		3.4.3 - Si es tria la opció 'G' - TORNAR ENRERE les Taules es
		borren de la memoria i, per tant, tot el que no estigui a persistència, es a dir
		al dataset no quedarà guardat i al crear-se de nou no apareixerà.


-----------------------
--> GRUP G3_S2_PAED <--
-----------------------

----------- AUTORS ------------
  Oriol Rebordosa Cots
  Leonardo Ruben Edenak Chouev
  Jan Piñol Castuera
  Joan Tarragó Pina
-------------------------------	