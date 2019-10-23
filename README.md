# chatUDP
UDP socket exercise for TPS class (classi 5te)

## lezioni precedenti
con la realizzazione del **[ServerEcho](https://github.com/Prof-Matteo-Palitto-Peano/ServerEcho)** abbiamo introdotto il socket UDP in Java.

Allo studente veniva richiesto come progetto, di trasformare il codice del **ServerEcho** in una semplice chat:

Questa e' una realizzazione di riferimento per gli studenti che hanno bisogno di qualche spunto per completare il progetto.

I concetti che sono stati introdotti per realizzare la chat sono:
1. Il client una volta connesso al server deve rimanere in ascolto perche' il server potrebbe inviare messaggi di qualche altro utente in qualsiasi momento
2. Il server una volta ricevuto un messaggio da un client/utente deve quindi inoltrarlo a tutti gli utenti connessi alla CHAT

## Client
Il client riceve modifiche del codice piu' sostanziali e viene realizzato con la seguente architettura:
1. main THREAD che stabilisce la connessione e gestisce:
2. THREAD per l'invio del messaggio digitato da utente
3. THREAD per la ricezione dei messaggi inviati dal server

Per un totale di 3 thread totali

**NOTA** 
c'e' una versione del client con tutto il codice in un file unico, che vuole mostrare l'uso di thread dichiarati in-line

La versione aggiornata e meglio commentata e' la versione con 3 file (uno per thread)

## Server
Il server rimane praticamente invariato risptetto a quello visto nell'esercizio del **[ServerEcho](https://github.com/Prof-Matteo-Palitto-Peano/ServerEcho)**

Le differenze sono nell'utilizzo di una struttura dati per memorizzare i client connessi al server, usata per poi implementare il broadcast dei messaggi ricevuti a tutti i client connessi

**NOTA**
La struttura dati usata per tale scopo e' la HashMap per la semplicita' e velocita' della ricerca.

## Eventuali modifiche richieste allo studente
1. Modificare Client per richiedre all'utente un "user name" affinche' possa essere inviato al server insieme ai messaggi
2. Modificare il Client per dare una interfaccia grafica all'utente (vedi WhatsApp)
3. Modificare il Server per inviare gli utlimi 10 messaggi della CHAT e quindi la lista degli utenti collegati alla chat quando un nuovo utente si collega per la prima volta
