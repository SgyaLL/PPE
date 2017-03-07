package presist;
import inscriptions.*;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class BDD implements Serializable
{
	String url = "jdbc:mysql://localhost/ppejava?autoReconnect=true&useSSL=false";
	String login = "root";
	String password = "";
//	Connection cn = null;
//	Statement st = null;
	private static final long serialVersionUID = -60L;
	
	public void selectPersonne(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From personnes p,candidats c WHERE p.id_personne = c.id_personne";
			ResultSet result;
			result = st.executeQuery(requete);
			while ( result.next() ) {
			    Personne personne = inscription.createPersonne(result.getString( "nom" ),result.getString( "prenom" ), result.getString( "mail" ),false);
			    personne.setId(result.getInt("id_candidat"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void selectEquipe(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From equipes e,candidats c WHERE e.id_equipe = c.id_equipe";
			ResultSet result;
			result = st.executeQuery(requete);
			while ( result.next() ) {
			    Equipe equipe = inscription.createEquipe(result.getString( "nom"),false);
			    equipe.setId(result.getInt("id_candidat"));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void selectCompetitions(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From competitions";
			ResultSet result;
			result = st.executeQuery(requete);
			while ( result.next() ) {
				LocalDate date = LocalDate.now().plusMonths((long) 2.0);
			    inscription.createCompetition(result.getString( "nom" ),date, (result.getInt("enequipe") == 1),false);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void selectAttrEquipe(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From attrequipe a,candidats c WHERE a.id_equipe= c.id_candidat";
			ResultSet result;
			result = st.executeQuery(requete);
			while ( result.next() )
			{
				for(Equipe e : inscription.getEquipes())
				{
					if(e.getId()==result.getInt("id_equipe"))
					{
						for(Personne p : inscription.getPersonnes())
						{
							if(p.getId()==result.getInt("id_personne"))
							{
									e.add(p,false);
							}
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void selectAttrCompetition(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From attcompetition a,candidats c WHERE a.id_candidat= c.id_candidat";
			ResultSet result;
			result = st.executeQuery(requete);
			while ( result.next() )
			{
				for(Competition c: inscription.getCompetitions())
				{
					if(c.getId()==result.getInt("id_competition"))
					{
						for(Candidat ca : inscription.getCandidats())
						{
							if(ca.getId()==result.getInt("id_candidat"))
							{
									ca.add(c,false);
							}
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void save(Personne personne) 
	{	
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection cn = DriverManager.getConnection(url, login,password);
				Statement st = cn.createStatement();	
				String requete ="Insert into personnes(prenom,mail) values ('"+personne.getPrenom()+"','"+personne.getMail()+"')";
				st.executeUpdate(requete);	
				String requete2 ="Select id_personne From personnes Where prenom ='" + personne.getPrenom() + "' And mail = '" + personne.getMail() + "' And deleted_at IS NULL";
				ResultSet result = st.executeQuery(requete2);
				int idUser2 = 0;
				while (result.next()) {
				    idUser2 = result.getInt( "id_personne" );
				}
				String requete3 ="Insert into candidats(id_personne,nom) values ('"+idUser2+"','"+personne.getNom()+"')";
				st.executeUpdate(requete3);
				
				int idCandidat=0;
				String requete4="SELECT id_candidat FROM candidats";
				ResultSet result2= st.executeQuery(requete4);
				while (result2.next()) 
				{
				    idCandidat = result2.getInt( "id_candidat" );
				}
				personne.setId(idCandidat);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
	}
	public void save(Equipe equipe) 
	{	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Insert into equipes(id_equipe) values (NULL)";
			st.executeUpdate(requete);	
			String requete2 ="Select id_equipe From equipes";
			ResultSet result = st.executeQuery(requete2);
			int idequipe = 0;
			while (result.next()) {
			    idequipe = result.getInt( "id_equipe" );
			}
			String requete3 ="Insert into candidats(id_equipe,nom) values ('"+idequipe+"','"+equipe.getNom()+"')";
			st.executeUpdate(requete3);
			int idCandidat=0;
			String requete4="SELECT id_candidat FROM candidats";
			ResultSet result2= st.executeQuery(requete4);
			while (result2.next()) 
			{
			    idCandidat = result2.getInt( "id_candidat" );
			}
			equipe.setId(idCandidat);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
	public void save(Competition competition) 
	{	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			
			int equipe = competition.estEnEquipe() ? 1 : 0;
			
			String requete ="Insert into competitions(date,nom,enequipe) values ('"+competition.getDateCloture()+"','"+competition.getNom()+"','"+equipe+"')";
			st.executeUpdate(requete);
			
			int idCompetition=0;
			String requete4="SELECT id_competition FROM competitions";
			ResultSet result2= st.executeQuery(requete4);
			while (result2.next()) 
			{
			    idCompetition = result2.getInt( "id_competition" );
			}
			competition.setId(idCompetition);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
	public void save(Personne personne,Equipe equipe) 
	{	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();		
			String requete ="Insert into attrequipe(id_personne,id_equipe) values ('"+personne.getId()+"','"+equipe.getId()+"')";
			st.executeUpdate(requete);		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
	public void save(Candidat candidat,Competition competition) 
	{	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();		
			String requete ="Insert into attrcompetition(id_candidat,id_competition) values ("+candidat.getId()+","+competition.getId()+")";
			st.executeUpdate(requete);		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	
	public void delete(Personne personne)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="UPDATE personnes SET deleted_at = NOW() WHERE id_candidat = "+personne.getId();
			st.executeUpdate(requete);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void delete(Equipe equipe)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="UPDATE equipes SET deleted_at = NOW() WHERE id_candidat = "+equipe.getId();
			st.executeUpdate(requete);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void delete(Candidat candidat)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="UPDATE candidats SET deleted_at = NOW() WHERE id_candidat = "+candidat.getId();
			st.executeUpdate(requete);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void delete(Competition competition)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="UPDATE competitions SET deleted_at = NOW() WHERE id_candidat = "+competition.getId();
			st.executeUpdate(requete);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Personne personne, Equipe equipe)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="DELETE FROM attrequipe WHERE id_personne="+personne.getId()+" AND id_equipe="+equipe.getId();
			st.executeUpdate(requete);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}