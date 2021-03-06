package inscriptions;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import bdd.Connect;;

/**
 * Repr�sente une Equipe. C'est-�-dire un ensemble de personnes pouvant 
 * s'inscrire � une comp�tition.
 * 
 */

public class Equipe extends Candidat
{
	private static final long serialVersionUID = 4147819927233466035L;
	private SortedSet<Personne> membres = new TreeSet<>();
	private int id;

	Connect bdd = new Connect();
	boolean isDelete;
	
	public Equipe(Inscriptions inscriptions, String nom,boolean save)
	{
		super(inscriptions, nom);
		if(save)
		{
			bdd.save(this);
		}
	}
	
	/**
	 * Retourne la valeur de suppression de Equipe.
	 */
	
	public boolean getIsDelete() {
		return isDelete;
	}

	/**
	 * Modifie la valeur de suppression de Equipe.
	 */
	
	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * Retourne l'id de l'�quipe.
	 */
	
	/**
	 * Modifie l'id de l'�quipe.
	 */

	
	/**
	 * Retourne l'ensemble des personnes formant l'�quipe.
	 */
	
	public SortedSet<Personne> getMembres()
	{
		return Collections.unmodifiableSortedSet(membres);
	}
	
	/**
	 * Ajoute une personne dans l'�quipe.
	 * @param membre
	 * @return
	 */

	public boolean add(Personne membre,boolean save)
	{
		membre.add(this);
		if(save)
		{
			bdd.save(membre,this);
		}
		return membres.add(membre);
	}

	/**
	 * Supprime une personne de l'�quipe. 
	 * @param membre
	 * @return
	 */
	
	public boolean remove(Personne membre,boolean save)
	{
		membre.remove(this);
		if(save)
		{
			bdd.delete(membre, this);
		}
		return membres.remove(membre);
	}

	@Override
	public void delete()
	{
		super.delete();

		Connect.deleteE(this);
		
		
			
		for(Personne m : membres)
		{
			m.remove(this);
		}
	}
	
	@Override
	public String toString()
	{
		return "Equipe " + super.toString();
	}
}