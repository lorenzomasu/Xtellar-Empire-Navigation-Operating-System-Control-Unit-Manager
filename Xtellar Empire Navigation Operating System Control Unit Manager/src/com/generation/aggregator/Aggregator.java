package com.generation.aggregator;

import java.math.BigInteger;
import java.util.*;

import com.generation.base.Entity;
import com.generation.dao.*;
import com.generation.db.Database;
import com.generation.db.IDatabase;
import com.generation.entities.*;

public class Aggregator implements IAggregator{

	private static Aggregator instance;
	
	private Map<String,Object> dependencies = new HashMap<String,Object>();
	
	/**
	 * costruttore aggregator, crea tutti i dao necessari e li mette in dependencies
	 * @author Ivan Capra
	 */
	private Aggregator() {
		IDatabase db = Database.getInstance();
		db.setPercorso("jdbc:mysql://localhost:3306/impero");
		db.setUser("root");
		db.setPw("root");
		IDAO daoesseri = new DAOEsseri(db);
		dependencies.put("daoesseri", daoesseri);
		
		IDAO daopianeti = new DAOPianeti(db);
		dependencies.put("daopianeti", daopianeti);
		
		IDAO daorazze = new DAORazze(db);
		dependencies.put("daorazze", daorazze);
		
		IDAO daorisorse = new DAORisorse(db);
		dependencies.put("daorisorse", daorisorse);
		
		IDAO daorubrica = new DAORubrica(db);
		dependencies.put("daorubrica", daorubrica);

		IDAO daocontiene = new DAOContiene(db);
		dependencies.put("daocontiene", daocontiene);
		
	}
	
	/**
	 * metodo per prendere l'istanza dell'aggregator
	 */
	public static Aggregator getInstance() {
		if(instance == null)
			instance = new Aggregator();
		return instance;
	}
	
	/**
	 * prende l'istanza dell'aggregator e la torna.
	 * @param chiave instance dell'aggregator
	 * @return
	 */
	public Object get(String chiave) {
		return dependencies.get(chiave);
	}
	
	/*
	 * RICORDARSI DI METTERE CERCA, MODIFICA, CARICA ED ELIMINA PER OGNI SINGOLA ENTITY
	 */
	
	/**
	 * cerca l'essere con id preso in ingresso
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	public Entity cercaEssere(int id) {
		return  ((DAOEsseri) getInstance().get("daoesseri")).load(BigInteger.valueOf(id));
	}
	
	/**
	 * modifica o crea l'essere tramite il dao
	 * @param e
	 * @return
	 * @author Ivan Capra
	 */
	
	public Entity modificaCreaEssere(Entity e) {
		return ((DAOEsseri) getInstance().get("daoesseri")).load(e);
	}
	
	/**
	 * elimina l'essere dato l'id in ingresso
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	
	public boolean eliminaEssere(int id) {
		return ((DAOEsseri) getInstance().get("daoesseri")).delete(BigInteger.valueOf(id));
	}
	
	
	
	/**
	 * cerca il pianeta con id preso in ingresso
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	
	public Entity cercaPianeta(int id) {
		return  ((DAOPianeti) getInstance().get("daopianeti")).load(BigInteger.valueOf(id));
	}
	/**
	 * modifica o crea il pianeta tramite il dao
	 * @param e
	 * @return
	 * @author Ivan Capra
	 */
	public Entity modificaCreaPianeta(Entity e) {
		return ((DAOPianeti) getInstance().get("daopianeti")).load(e);
	}
	/**
	 * elimina il pianeta dato l'id in ingresso
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	
	public boolean eliminaPianeta(int id) {
		return ((DAOPianeti) getInstance().get("daopianeti")).delete(BigInteger.valueOf(id));
	}
	
	
	
	/**
	 * cerca la razza con id preso in ingresso
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	
	public Entity cercaRazza(int id) {
		return  ((DAORazze) getInstance().get("daorazze")).load(BigInteger.valueOf(id));
	}
	/**
	 * modifica o crea la razza tramite il dao
	 * @param e
	 * @return
	 * @author Ivan Capra
	 */
	public Entity modificaCreaRazza(Entity e) {
		return ((DAORazze) getInstance().get("daorazze")).load(e);
	}
	/**
	 * elimina la razza dato l'id in ingresso
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	public boolean eliminaRazza(int id) {
		return ((DAORazze) getInstance().get("daorazze")).delete(BigInteger.valueOf(id));
	}
	
	/**
	 * cerca la risorsa con id preso in ingresso
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	
	public Entity cercaRisorsa(int id) {
		return  ((DAORisorse) getInstance().get("daorisorse")).load(BigInteger.valueOf(id));
	}
	/**
	 * modifica o crea la risorsa tramite il dao
	 * @param e
	 * @return
	 * @author Ivan Capra
	 */
	
	public Entity modificaCreaRisorsa(Entity e) {
		return ((DAORisorse) getInstance().get("daorisorse")).load(e);
	}
	/**
	 * elimina la risorsa dato l'id in ingresso
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	
	public boolean eliminaRisorsa(int id) {
		return ((DAORisorse) getInstance().get("daorisorse")).delete(BigInteger.valueOf(id));
	}
	
	/**
	 * modifica la risorsa appartenente ad un pianeta 
	 * @param idpianeta da modificare
	 * @param idrisorsadamodificare l'id da cambiare
	 * @param idrisorsanuova il nuovo id
	 * @return
	 * @author Ivan Capra
	 */
	public Entity modificaRisorsaAPianeta(int idpianeta, int idrisorsadamodificare, int idrisorsanuova) {
		Map<String, String> e = new HashMap<String,String>();
		e.put("idpianeta", idpianeta+"");
		e.put("idrisorse", idrisorsadamodificare+"");
		Map<String, String> modifica = new HashMap<String,String>();
		modifica.put("idpianeta", idpianeta+"");
		modifica.put("idrisorse", idrisorsanuova+"");
		return ((DAOContiene)getInstance().get("daocontiene")).load(e, modifica);
	}
	/**
	 * aggiunge una risorsa ad un pianeta
	 * @param idpianeta il pianeta a cui aggiungere
	 * @param idrisorsa l'id risorsa da aggiungere
	 * @param quantita la quantita da cambiare
	 * @return
	 * @author Ivan Capra
	 */
	public Entity aggiungiRisorsaAPianeta(int idpianeta, int idrisorsa, int quantita) {
		Map<String, String> e = new HashMap<String,String>();
		e.put("idpianeta", idpianeta+"");
		e.put("idrisorse", idrisorsa+"");
		e.put("quantita", quantita+"");
		return ((DAOContiene)getInstance().get("daocontiene")).load(e, null);
	}
	/**
	 * elimino la risorsa da un pianeta
	 * @param idpianeta pianeta da eliminare
	 * @param idrisorsa risorsa da eliminare
	 * @return
	 * @author Ivan Capra
	 */
	
	public boolean eliminaRisorsaPianeta(int idpianeta, int idrisorsa) {
		return ((DAOContiene)getInstance().get("daocontiene")).deleteRisorsaDaPianeta(BigInteger.valueOf(idpianeta), BigInteger.valueOf(idrisorsa));
	}
	
	
	/**
	 * cerca numero nella rubrica
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	
	public Entity cercaRubrica(int id) {
		return  ((DAORubrica) getInstance().get("daorubrica")).load(BigInteger.valueOf(id));
	}
	/**
	 * modifica o crea numero nella rubrica
	 * @param Entity
	 * @return
	 * @author Ivan Capra
	 */
	public Entity modificaCreaRubrica(Entity e) {
		return ((DAORubrica) getInstance().get("daorubrica")).load(e);
	}
	/**
	 * elimina numero nella rubrica
	 * @param id
	 * @return
	 * @author Ivan Capra
	 */
	public boolean eliminaRubrica(int id) {
		return ((DAORubrica) getInstance().get("daorubrica")).delete(BigInteger.valueOf(id));
	}
	
	
	
	
	/*
	 * CRUD
	 * 
	 */
	/**
	 * ritorno il numero di persone presenti nella citta in ingresso
	 */
	@Override
	public int numeriCitta(String citta) {
		List<Entity> test = ((DAORubrica)getInstance().get("daorubrica")).elencoNumeri(citta);
		if(test == null)
			return 0;
		else
			return test.size();
	}
/**
 * ritorno la lista di razze con la caratteristica richiesta presente nella loro descrizione
 * @author Ivan Capra
 */
	@Override
	public List<Razza> esiste(String caratteristica) {
		List<Entity> test = ((DAORazze)getInstance().get("daorazze")).esiste(caratteristica);
		if(test == null || test.isEmpty())
			return null;
		List<Razza> ris = new ArrayList<Razza>();
		for(Entity e : test)
			ris.add((Razza)e);
		return ris;
	}
/**
 * ritorno la grandezza media di tutti i pianeti dell'impero
 * 0 se non ci sono pianeti
 * @author Ivan Capra
 */
	@Override
	public double grandezzaMediaPianeti() {
		return ((DAOPianeti) getInstance().get("daopianeti")).grandezzaMediaPianeti();
	}
/**
 * ritorno la grandezza totale di tutti i pianeti dell'impero
 * 0 se non ci sono pianeti
 * @author Ivan Capra
 */
	@Override
	public int grandezzaTotalePianeti() {
		return ((DAOPianeti) getInstance().get("daopianeti")).grandezzaTotalePianeti();
	}
/**
 * ritorno vero se esiste gia' un pianeta con quel nome, false se non esiste
 * @author Ivan Capra
 */
	@Override
	public boolean doppione(String nome) {
		return ((DAOPianeti) getInstance().get("daopianeti")).doppione(nome);
	}
/**
 * ritorno il o i pianeti piu grande nell'impero
 * null se non ci sono pianeti
 * @author Ivan Capra
 */
	@Override
	public List<Pianeta> pianetaPiuGrande() {
		List<Entity> pianeti = ((DAOPianeti) getInstance().get("daopianeti")).pianetaPiuGrande();
		if(pianeti == null || pianeti.size() == 0)
			return null;
		List<Pianeta> p = new ArrayList<Pianeta>();
		for(Entity e : pianeti) {
			p.add((Pianeta)e);
		}
		return p;
	}
/**
 * ritorno il o i pianeti piu piccoli nell'impero
 * null se non ci sono pianeti
 * @author Ivan Capra
 */
	@Override
	public List<Pianeta> pianetaPiuPiccolo() {
		List<Entity> pianeti = ((DAOPianeti) getInstance().get("daopianeti")).pianetaPiuPiccolo();
		if(pianeti == null || pianeti.size() == 0)
			return null;
		List<Pianeta> p = new ArrayList<Pianeta>();
		for(Entity e : pianeti) {
			p.add((Pianeta)e);
		}
		return p;
	}
/**
 * ritorno l'eta' media nell'impero
 * @author Ivan Capra
 */
	@Override
	public double etaMedia() {
		return ((DAOEsseri) getInstance().get("daoesseri")).etaMedia();
	}

	/**
	 * ritorno l'eta' massima nell'impero
	 * @author Ivan Capra
	 */
	@Override
	public int etaMassima() {
		return ((DAOEsseri) getInstance().get("daoesseri")).etaMassima();
	}

	/**
	 * ritorno l'eta' minima dell'impero
	 * @author Ivan Capra
	 */
	@Override
	public int etaMinima() {
		return ((DAOEsseri) getInstance().get("daoesseri")).etaMinima();
	}

	/**
	 * ritorno il numero di ripetizioni del cognome, 0 se non trovato
	 * @author Ivan Capra
	 */
	
	@Override
	public int ripetizioneCognome(String cognome) {
		return  ((DAOEsseri) getInstance().get("daoesseri")).ripetizioneCognome(cognome); 
	}
	
	/**
	 * ritorno il valore medio delle risorse dell'impero
	 * @author Ivan Capra 
	 */
	@Override
	public double valoreMedioRisorse() {
		return ((DAORisorse)getInstance().get("daorisorse")).valoreMedioRisorse();
	}

	/**
	 * ritorno il valore della risorsa cercata, -1 se non trovata
	 */
	@Override
	public int valoreRisorsa(String nomeRisorsa) {
		return ((DAORisorse)getInstance().get("daorisorse")).valoreRisorsa(nomeRisorsa);
	}

	/**
	 * ritorno la citta del numero cercato, 
	 * null se non trovato
	 * @author Gary Garcia
	 */
	
	@Override
	public String cittaDelNumero(int numero) {
		return ((DAORubrica)getInstance().get("daorubrica")).cittaDelNumero(numero);
	}

	
	/**
	 * ritorno l'indirizzo del numero cercato,
	 * null se non lo trova
	 * @author Gary Garcia
	 */
	@Override
	public String indirizzoDelNumero(int numero) {
		return ((DAORubrica) getInstance().get("daorubrica")).indirizzoDelNumero(numero);
	}
	
	/**
	 * ritorno i dettagli interi del numero cercato: il pianeta, la citta' e l'indirizzo, la razza e l'essere che ce l'ha
	 * "nessun numero trovato" se non lo trovo
	 * @author Ivan Capra
	 */

	@Override
	public String dettagliNumero(int numero) {
		List<Entity> esseri = ((DAOEsseri) getInstance().get("daoesseri")).list();
		Essere essere = null;
		if(esseri == null || esseri.isEmpty())
			return "Popolazione non trovata.";
		for(Entity e : esseri) {
			if(((Essere)e).getIdnumero() == numero) {
				essere = (Essere)e;
			}
		}
		if(essere == null)
			return "Nessun numero trovato.";
		Pianeta p = (Pianeta) ((DAOPianeti) getInstance().get("daopianeti")).load(BigInteger.valueOf(essere.getIdpianeta()));
		Razza r = (Razza) ((DAORazze)getInstance().get("daorazze")).load(BigInteger.valueOf(essere.getIdrazza()));
		return p.toString() +"\n" + cittaDelNumero(numero) + ", " + indirizzoDelNumero(numero) + "\n" + r.toString() + "\n" + essere.toString();
	}

	/**
	 * Ritorno la quantita' di una risorsa nell'impero
	 * -1 se la risorsa non e' stata trovata
	 *  @author Ivan Capra
	 */
	
	@Override
	public int quantitaRisorsaImpero(String nomeRisorsa) {
		List<Entity> risorse = ((DAORisorse)getInstance().get("daorisorse")).list();
		int id = -1;
		if(risorse == null || risorse.isEmpty())
			return -1;
		for(Entity risorsa : risorse) {
			if(((Risorsa)risorsa).getNome().equalsIgnoreCase(nomeRisorsa)) {
				id = risorsa.getId().intValue();
				break;
			}
		}
		if(id == -1)
			return -1;
		List<Entity> contiene = ((DAOContiene)getInstance().get("daocontiene")).list();
		if(contiene == null || contiene.isEmpty())
			return 0;
		int tot = 0;
		for(Entity e : contiene) {
			if(((Contiene)e).getIdrisorse() == id)
				tot += ((Contiene)e).getQuantita();
			System.out.println("quantita risorsa aggregator: " + e.toString());
		}
		return tot;
	}

	/**
	 * ritorno la popolazione intera dell'impero
	 * @author Ivan Capra
	 */
	@Override
	public int popolazioneTotaleImpero() {
		return ((DAOEsseri)getInstance().get("daoesseri")).list().size();
	}

	/**
	 * ritorno la distanza tra due pianeti, null se uno dei due non esiste
	 * @author Ivan Capra
	 */
	@Override
	public String distanzaPianeti(String nomea, String nomeb) {
		return  ((DAOPianeti)getInstance().get("daopianeti")).distanzaPianeti(nomea, nomeb);
	}

	/**
	 * ritorno la lista di razze che vivono su un pianeta
	 * ritorna nullo se il pianeta non esiste
	 * @author Ivan Capra
	 */
	@Override
	public List<Razza> razzeSuPianeta(String p) {
		List<Entity> pianeti = ((DAOPianeti)getInstance().get("daopianeti")).list();
		Pianeta pia = null;
		if(pianeti == null || pianeti.isEmpty())
			return null;
		for(Entity e : pianeti) {
			if(((Pianeta)e).getNome().equalsIgnoreCase(p)) {
				pia = (Pianeta)e;
				break;
			}
		}
		if(pia == null)
			return null;
		List<Entity> esseri = ((DAOEsseri)getInstance().get("daoesseri")).list();
		List<Entity> razze = ((DAORazze)getInstance().get("daorazze")).list();
		List<Razza> risposta = new ArrayList<Razza>();
		if(razze == null || razze.isEmpty() || esseri == null || esseri.isEmpty())
			return null;
		for(Entity e : esseri) {
			if(((Essere)e).getIdpianeta() == pia.getId().intValue()) {
				for(Entity pi : razze) {
					if(risposta.contains(pi))
						break;
					else if(pi.getId().intValue() == ((Essere)e).getIdrazza()) {
						risposta.add((Razza)pi);
						break;
					}
				}
			}
		}
		return risposta;
	}

	/**
	 * ritorno la lista di pianeti con una certa razza
	 * ritorna null se la razza non esiste, lista size 0 se nessun pianeta ha quella razza
	 * @author Ivan Capra
	 */
	@Override
	public List<Pianeta> pianetiConRazza(String r) {
		List<Entity> razze = ((DAORazze)getInstance().get("daorazze")).list();
		Razza raz = null;
		for(Entity e : razze) {
			if(((Razza)e).getNome().equalsIgnoreCase(r)) {
				raz = (Razza)e;
				break;
			}
		}
		if(raz == null)
			return null;
		List<Entity> esseri = ((DAOEsseri)getInstance().get("daoesseri")).list();
		List<Entity> pianeti = ((DAOPianeti)getInstance().get("daopianeti")).list();
		List<Pianeta> risposta = new ArrayList<Pianeta>();
		
		for(Entity e : esseri) {
			if(((Essere)e).getIdrazza() == raz.getId().intValue()) {
				for(Entity pi : pianeti) {
					if(risposta.contains(pi))
						break;
					else if(pi.getId().intValue() == ((Essere)e).getIdpianeta()) {
						risposta.add((Pianeta)pi);
						break;
					}
				}
			}
		}
		return risposta;
	}

	/**
	 * ritorna il numero di esseri per quella razza, -1 se razza non esiste
	 * @author Ivan Capra
	 */
	@Override
	public int popolazioneRazza(String r) {
		List<Entity> razze = ((DAORazze)getInstance().get("daorazze")).list();
		Razza raz = null;
		if(razze == null)
			return -1;
		for(Entity e : razze) {
			if(((Razza)e).getNome().equalsIgnoreCase(r)) {
				raz = (Razza)e;
				break;
			}
		}
		if(raz == null)
			return -1;
		List<Entity> esseri = ((DAOEsseri)getInstance().get("daoesseri")).list();
		if(esseri == null || esseri.isEmpty())
			return 0;
		int tot=0;
		for(Entity e : esseri) {
			if(((Essere)e).getIdrazza() == raz.getId().intValue())//if
				tot++;
		}
		return tot;
	}
	
	/**
	 * ritorna il numero di persone su un pianeta
	 * -1 se il pianeta non e' stato trovato
	 * @author Ivan Capra
	 */
	@Override
	public int popolazionePianeta(String p) {
		List<Entity> pianeti = ((DAOPianeti)getInstance().get("daopianeti")).list();
		Pianeta pia = null;
		if(pianeti == null || pianeti.isEmpty())
			return -1;
		for(Entity e : pianeti) {
			if(((Pianeta)e).getNome().equalsIgnoreCase(p)) {
				pia = (Pianeta)e;
				break;
			}
		}
		if(pia == null)
			return -1;
		List<Entity> esseri = ((DAOEsseri)getInstance().get("daoesseri")).list();
		if(esseri == null || esseri.isEmpty())
			return -1;
		int tot=0;
		for(Entity e : esseri) {
			if(((Essere)e).getIdpianeta() == pia.getId().intValue())//if
				tot++;
		}
		return tot;
	}
	
	/**
	 * ritorna la lista delle risorse piu' presenti nell'impero, piu di una nel caso siano in parita'
	 * null se non trova risorse
	 * @author Ivan Capra
	 */
	
	public List<Risorsa> risorsaPiuPresente() {
		List<Entity> contiene = ((DAOContiene)getInstance().get("daocontiene")).list();
		Map<Integer, Integer> mappa = new HashMap<Integer, Integer>();
		
		for(Entity e : contiene) {
			if(mappa.get(((Contiene)e).getIdrisorse()) == null)
				mappa.put(((Contiene)e).getIdrisorse(), 0);
			else
				mappa.put(((Contiene)e).getIdrisorse(), mappa.get(((Contiene)e).getIdrisorse()) + ((Contiene)e).getQuantita());
		}
		if(mappa.isEmpty())
			return null;
		List<Risorsa> ris = new ArrayList<Risorsa>();
		int tot=Integer.MIN_VALUE;
		for(Integer i : mappa.keySet()) {
			if(tot<=mappa.get(i)) {
				Risorsa r = new Risorsa();
				r.setId(i);
				if(tot == mappa.get(i)) 
					ris.add(r);
				else {
					ris.clear();
					ris.add(r);
					tot = mappa.get(i);
				}
			}
		}
		for(int i=0;i<ris.size();i++) {
			Risorsa supp = ris.get(i);
			supp = (Risorsa)((DAORisorse)getInstance().get("daorisorse")).load(supp.getId());
			ris.remove(i);
			ris.add(supp);
		}
		return ris;
	}

	
	/**
	 * ritorno la lista di risorse su un pianeta
	 * ritorno null se il pianeta non esiste, vuoto se la risorsa non e' su nessun pianeta
	 * @author Ivan Capra
	 */
	@Override
	public List<Risorsa> risorsePianeta(String p) {
		List<Entity> pianeti = ((DAOPianeti)getInstance().get("daopianeti")).list();
		Pianeta pia = null;
		for(Entity e : pianeti) {
			if(((Pianeta)e).getNome().equalsIgnoreCase(p)) {
				pia = (Pianeta)e;
				break;
			}
		}
		if(pia == null)
			return null;
		List<Entity> risorsa = ((DAOContiene)getInstance().get("daocontiene")).risorseSuPianeta(pia);
		List<Risorsa> risorse = new ArrayList<Risorsa>();
		for(Entity e : risorsa){
			risorse.add((Risorsa)((DAORisorse)getInstance().get("daorisorse")).load(BigInteger.valueOf(((Contiene)e).getIdrisorse())));
		}
		return risorse;
	}

	/**
	 * ritorno la lista di pianeti con una certa risorsa
	 * ritorno null se la risorsa non esiste, vuota se nessun pianeta ce l'ha
	 * @author Ivan Capra
	 */
	
	@Override
	public List<Pianeta> PianetiConRisorsa(String r) {
		List<Entity> risorse = ((DAORisorse)getInstance().get("daorisorse")).list();
		Risorsa risorsa = null;
		if(risorse == null || risorse.isEmpty())
			return null;
		for(Entity e : risorse) {
			if(((Risorsa)e).getNome().equalsIgnoreCase(r)) {
				risorsa = (Risorsa) e;
				break;
			}
		}
		if(risorsa == null)
			return null;
		List<Entity> contiene = ((DAOContiene)getInstance().get("daocontiene")).pianetiConRisorsa(risorsa);
		List<Pianeta> ris = new ArrayList<Pianeta>();
		if(contiene == null || contiene.isEmpty())
			return null;
		for(Entity e : contiene) {
			ris.add((Pianeta)((DAOPianeti)getInstance().get("daopianeti")).load(BigInteger.valueOf(((Contiene)e).getIdPianeta().longValue())));
		}
		return ris;
	}

	
}
