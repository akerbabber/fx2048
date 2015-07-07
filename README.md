# fx2048: commento tecnico

Membri del progetto:

* Fadda Nicola
* Murru Maurizio
* Piras Luca
* Setzu Mattia

## Team working

Il gruppo ha deciso di lavorare, durante ogni fase del progetto, con tutti i suoi membri riuniti, in modo da favorire lo scambio e la condivisione di idee ed approcci al problema. Inoltre è stato sfruttato a pieno lo strumento consigliato, github, con cui ogni membro ha potuto lavorare in autonomia al compito specifico assegnatogli, facendo infine confluire nel repository  scelto i vari moduli progettati.

La prima fase è stata dedicata alla lettura ed all'analisi del codice originario. Sono emerse alcune peculiarità nelle tecniche proprie di Java 8 e non affrontate durante il corso, quali *lambda expressions* e gestione multithread delle animazioni, su cui il gruppo si è soffermato con curiosità.

Conclusasi questa breve fase, l'attenzione si è poi spostata sulla ricerca di un algoritmo utile alla risoluzione automatica del gioco. È risultato particolarmente efficace l'utilizzo di tecniche di brainstorming, in cui ogni membro ha apportato un contributo basato sulla propria esperienza da giocatore. Una sessione di testing ha portato il gruppo a decidere di affidarsi ad algoritmi già consolidati  e la cui validità è già comprovata. Numerosi riferimenti presenti nei maggiori motori di ricerca hanno puntato ad una discussione presente su *stackoverflow* ([What is the optimal algorithm for the game, 2048?](http://stackoverflow.com/questions/22342854/what-is-the-optimal-algorithm-for-the-game-2048)), in cui vari programmatori hanno proposto le proprie soluzioni, alcune in linguaggi cui il gruppo non è particolarmente avvezzo, quali python e javascript. Tuttavia una delle risposte, pubblicata dall'utente *ov3y*, era completa di una descrizione per sommi capi dei criteri che stanno alla base dell'algoritmo corrispondente. Di comune decisione, il gruppo ha stabilito di progettare un algoritmo di propria mano, ma che facesse tesoro dei principi cardine elencati dal suddetto programmatore.

La successiva fase di implementazione vera e propria è stata scissa in coppie, senza comunque mai dividere il gruppo; Piras e Murru hanno lavorato alla realizzazione della logica dell'algoritmo e alla stesura dei suoi moduli fondamentali, mentre Fadda e Setzu hanno analizzato la gestione multithread di animazioni ed interfaccia grafica, fornendo un'interfaccia tra automa e gioco. Son state sperimentate con discreto sucesso diverse sessioni di *pair programming*, cui è stata aggiunta una piccola variante: le coppie originarie hanno spesso scambiato i propri membri per favorire in primo luogo la rilevazione di banali errori, spesso di difficile individuazione da parte del programmatore, e in secondo luogo per uniformare lo stile e trovare nuovi spunti da ogni membro.

##Automa: l'algoritmo

I criteri che costituiscono i capisaldi dell'algoritmo sono:

* Monotonia
* Contiguità
* Numero di celle libere
* Merge

Analizzati di seguito più nel dettaglio.

**Monotonia:** definiamo come monotono un insieme di caselle adiacenti e ordinate i cui valori sono crescenti in una medesima direzione.

**Contiguità:** definiamo come contigua una coppia di caselle adiacenti di pari valore e su cui si possa quindi operare un merge alla mossa successiva.

**Numero di celle libere:** definiamo come *libere* le celle prive di una casella occupante. Non è considerata la casella inserita automaticamente e casualmente alla mossa successiva dal programma.

**Merge:** definiamo come *merge in una data direzione* il merge di caselle che risulta dal movimento della griglia di gioco in una data direzione. A maggior valore delle caselle che subiscono il merge, corrisponde un maggiore punteggio di merge.

Tali valori, inseriti in una funzione ponderata, contribuiscono all'assegnazione di un punteggio ad ogni direzione, consentendo all'automa di individuare la giocata più conveniente. Ad ogni passo del gioco, una funzione che agisce da arbitro centralizzato gestisce la simulazione euristica delle quattro mosse possibili. Il modulo di valutazione della mossa prevede dei parametri che descrivono il peso di ogni criterio; inoltre, avendo notato i grandi vantaggi ottenibili mantendo un vertice cui far tendere la monotonia, sono stati introdotti dei pesi diversificati  attribuiti ad ogni direzione.

Una ulteriore ottimizzazione deriva dall'adozione di una struttura di simulazione euristica ad albero. Ognuna delle quattro simulazioni testate dalla funzione di previsione si ramifica a sua volta, ricorsivamente, in altre quattro simulazioni, generando un albero di altezza determinabile staticamente. La valutazione totale deriva dalla somma dei nodi in ampiezza ed in profondità. Questa soluzione garantisce la selezione della mossa in prospettiva mediamente migliore, senza dover necessariamente simulare la posizione ed il valore della cella che verrà inserita casualmente alla mossa successiva.

## Dettagli implementativi

**handler e multithreading.** L'handler che fornisce un'interfaccia tra la classe game2048.Game2048 e il giocatore automatico è stata implementata nel rispetto dello stile di codice adottato dall'autore originario, sfruttando le *lamda expressions*.

Le animazioni eseguite dall'interfaccia grafica, gestite mediante la classe ParallelTransition, hanno richiesto una sincronizzazione con l'input dell'utente, in modo che questi non possa interrompere una mossa in azione. L'utilizzo di un *mutex* e del lock automatico mediante la keyword *synchronized* consentono di ovviare a questo problema, ponendone però uno ulteriore. L'autore non ha infatti previsto una forma di bufferizzazione delle mosse dell'utente. In particolar modo, in caso di input troppo veloce, il gioco potrebbe non eseguire alcune delle mosse poiché in attesa dello sblocco del *mutex*.

Per garantire un'esperienza di gioco più varia, si è deciso di lasciare all'utente,  ad ogni mossa, la libertà di giocare in prima persona o di far eseguire una mossa all'automa.

**Automa.** L'interfaccia fornita, *Griglia*, è stata implementata da una classe *GrigliaObject*, che sulla falsariga della *gameGrid*, dichiarata dell'autore originario nella classe *game2048.GameManager*, ha implementato una Map<Location,Integer>. Tuttavia, la modellazione dell'algoritmo si presta maggiormente all'utilizzo di una struttura dati più semplice quale una matrice di *int*, implementata dalla classe *MatrixGriglia*, le cui istanze possono essere costruite a partire da oggetti di tipo *Griglia*.

La classe *MatrixGriglia* fornisce dunque metodi utili al calcolo dei criteri dell'algoritmo, logicamente appartenenti allo stato della tabella. Il calcolo dei punteggi e la scelta della mossa migliore sono delegati ai metodi di *MyGiocatoreAutomatico*.
