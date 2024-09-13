<h1 align="center">ProgettoSWE_Casali_Nero</h1>
<div align="center"><i>Un software Java per la gestione di eventi popolari</i></div>

## Funzionalità Principali
### Impiegato Comunale:
<h5>L'impiegato comunale (IC) ricopre la figura di amministratore della piattaforma, pertanto potrà effettuare le classiche operazioni <b>CRUD</b> su:
  <ul>
    <li>Cittadini</li>
    <li>Eventi</li>
    <li>Biglietti</li>
    <li>Padiglioni</li>
    <li>Licenze</li>
  </ul>
</h5>

### Cittadino:
<h5>Il cittadino ricopre un doppio ruolo all'interno della piattaforma:
  <ul>
    <li><b>Passivo:</b> Può prenotare fino a 2 biglietti per un evento</li>
    <li><b>Attivo:</b> Può prenotare* un padiglione per offrire un servizio durante l'evento</li>
  </ul>
</h5>
<h6>* la prenotazione è riservata ai possessori di una licenza attiva</small>

## Design Pattern
<h5>Per consentire una ipotetica manutenibilità nel tempo abbiamo scelto di implementare i seguenti design pattern:
  <ul>
    <li><b>Model View Controller (M.V.C.)</li>
    <li><b>Data Access Object (D.A.O.)</li>
    <li><b>Builder</li>
    <li><b>Singleton</li>
  </ul>
</h5>

## Librerie di terze parti
<h5>Le seguenti librerie sono state implementate nel progetto per garantirne il corretto funzionamento:
  <ul>
    <li><b>Connector/J:</b> Libreria per la connessione a database di tipo MySql</li>
    <li><b>JavaFX:</b> Libreria per il supporto della GUI</li>
    <li><b>PDFjet:</b> Libreria per la creazione di documenti PDF</li>
    <li><b>Barbecue:</b> Libreria per la generazione di codici a barre</li>
    <li><b>Quagga:</b> Libreria per la lettura tramite fotocamera dei codici a barre</li>
  </ul>
</h5>

## Extra
<h5>Per rendere il progetto ipoteticamente funzionante abbiamo realizzato un piccolo applicativo web utilizzabile da tutti gli IC posti ai vari ingressi dell'evento.<br>
  Questo strumento è di supporto al software principale e permette tramite un comune smartphone di verificare il biglietto, altrimenti verificabile tramite un PC.<br>
</h5>
