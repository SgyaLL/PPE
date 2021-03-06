package bdd;
import inscriptions.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Connect implements Serializable
{
	private static final long serialVersionUID = -6035399822298694746L;

//	static Connection cn = null;
//	Statement st = null;
	

	//	public Connect()
	//		{
	//			try
	//			{
	//				Class.forName("com.mysql.jdbc.Driver");
	//				Connection connexion = DriverManager.getConnection("jdbc:mysql://localhost/m2ljava?autoReconnect=true&useSSL=false", "root","");
	//				Connect.cn = connexion;
					
	//			}
	//			catch(Exception e)
	//			{	
	//				 System.out.println( "Erreur lors de la connexion : " + e.getMessage() );
	//			} 
	//			
	//		}
	
	
	public void selectPersonne(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From personne";
			ResultSet result;
			result = st.executeQuery(requete);
			while ( result.next() ) {
			    Personne personne = inscription.createPersonne(result.getString( "nom" ),result.getString( "prenom" ), result.getString( "mail" ),false);
			    personne.setId(result.getInt("id_personne"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
		public static HashMap<String, Integer> selectPersonne()
			{
				HashMap<String, Integer> listeP =  new LinkedHashMap<String, Integer>();
				try {
					Statement st = null;
					String requete ="SELECT * FROM candidat, personne where id_candidat = id_personne";
					String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
					String login = "root";
					String password = "";
					Connection cn = DriverManager.getConnection(url, login,password);
					st = cn.createStatement();	
					ResultSet result;
					result = st.executeQuery(requete);
					while ( result.next() ) {
					  String nom = result.getString( "nom" );
						int idP = result.getInt("id_personne");
						listeP.put(nom,idP);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}	
				return listeP;
			}
	
	public static void afficheP(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From personne where personne.id_personne = id_personne";
			ResultSet result;
			result = st.executeQuery(requete);
			while ( result.next() ) {
			    Personne personne = inscription.createPersonne(result.getString( "nom" ),result.getString( "prenom" ), result.getString( "mail" ),false);
			    personne.setId(result.getInt("id_personne"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	
	public static void afficheC (Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From competition where competition.id_competition = id_competition";
			ResultSet result;
			result = st.executeQuery(requete);
			while ( result.next() ) {
			    Personne personne = inscription.createPersonne(result.getString( "nom" ),result.getString( "prenom" ), result.getString( "mail" ),false);
			    personne.setId(result.getInt("id_personne"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void selectEquipe(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From equipe e,candidat c WHERE e.id_equipe = c.id_candidat";
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
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From competition";
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
	
	public void selecttoEquipe(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From toequipe a,candidat c WHERE a.id_equipe= c.id_candidat";
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
	
	public void selecttoCompetition(Inscriptions inscription)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="Select * From attcompetition a,candidat c WHERE a.id_candidat= c.id_candidat";
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
				String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
				String login = "root";
				String password = "";
				Connection cn = DriverManager.getConnection(url, login,password);
				Statement st = cn.createStatement();	
				String requete ="Insert into personne(prenom,mail,nom) values ('"+personne.getPrenom()+"','"+personne.getMail()+"','"+personne.getNom()+"')";
				
				 int idp = st.executeUpdate(requete , Statement.RETURN_GENERATED_KEYS);
				 ResultSet rs= st.getGeneratedKeys();
		            if (rs.next()) 
		            {
		              System.out.println("Last Inserted ID = "+rs.getLong(1));
		            }    
 
		           
				String requete3 ="Insert into candidat(id_candidat,nom_candidat) values ('"+rs.getLong(1)+"','"+personne.getNom()+"')";
				st.executeUpdate(requete3);
				

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
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	


			String requete3 ="Insert into candidat(nom_candidat) values ('"+equipe.getNom()+"')";

			
			  long idequipe = st.executeUpdate(requete3 , Statement.RETURN_GENERATED_KEYS);
	            ResultSet rs= st.getGeneratedKeys();
	            if (rs.next()) 
	            {
	              System.out.println("Last Inserted ID = "+rs.getLong(1));
	            }    
	            
			String requete ="Insert into equipe(id_equipe,nom) values ('"+rs.getLong(1)+"','"+equipe.getNom()+"')";
			

			st.executeUpdate(requete);
			int idCandidat=0;
			String requete4="SELECT id_candidat FROM candidat";
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
	public static void updateE(Equipe equipe) 
	{	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	


			String requete3 ="UPDATE candidat set nom_candidat = '"+equipe.getNom()+"' WHERE id_candidat = '"+equipe.getId()+"'";

			st.executeUpdate(requete3);	
			
			  long idequipe = st.executeUpdate(requete3 , Statement.RETURN_GENERATED_KEYS);
	            ResultSet rs= st.getGeneratedKeys();
	            if (rs.next()) 
	            {
	              System.out.println("Last Inserted ID = "+rs.getLong(1));
	            }    
	            
			String requete ="UPDATE equipe set nom = '"+equipe.getNom()+"' WHERE id_equipe = '"+equipe.getId()+"'";
			

			st.executeUpdate(requete);
			int idCandidat=0;
			String requete4="SELECT id_candidat FROM candidat";
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
	
	
	public static void updateP(Personne personne) 
	{	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	


			String requete3 ="UPDATE candidat set nom_candidat = '"+personne.getNom()+"' WHERE id_candidat = '"+personne.getId()+"'";

			st.executeUpdate(requete3);	
			
			  long idpersonne = st.executeUpdate(requete3 , Statement.RETURN_GENERATED_KEYS);
	            ResultSet rs= st.getGeneratedKeys();
	            if (rs.next()) 
	            {
	              System.out.println("Last Inserted ID = "+rs.getLong(1));
	            }    
	            
			String requete ="UPDATE personne set nom = '"+personne.getNom()+"',prenom = '"+personne.getPrenom()+"',mail = '"+personne.getMail()+"' WHERE id_personne = '"+personne.getId()+"'";
			

			st.executeUpdate(requete);
			int idCandidat=0;
			String requete4="SELECT id_candidat FROM candidat";
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
	public void save(Competition competition) 
	{	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			
			int equipe = competition.estEnEquipe() ? 1 : 0;
			
			String requete ="Insert into competition(date_debut,nom_competition,enEquipe) values ('"+competition.getDateCloture()+"','"+competition.getNom()+"','"+equipe+"')";
			st.executeUpdate(requete);
			
			int idCompetition=0;
			String requete4="SELECT id_competition FROM competition";
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
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();		
			String requete ="Insert into toequipe(id_personne,id_equipe) values ('"+personne.getId()+"','"+equipe.getId()+"')";
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
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();		
			String requete ="Insert into tocompetition(id_candidat,id_competition) values ("+candidat.getId()+","+competition.getId()+")";
			st.executeUpdate(requete);		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	
	public void deleteP(Personne personne)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");	
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="UPDATE personne SET deleted_at = NOW() WHERE id_candidat = "+personne.getId();
			st.executeUpdate(requete);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void deleteE(Equipe equipe)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="DELETE from equipe WHERE id_equipe = "+equipe.getId();
			st.executeUpdate(requete);	
			Connect.deleteC(equipe);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static void deleteC(Candidat candidat)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");	
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="DELETE from candidat WHERE id_candidat = "+candidat.getId();
			st.executeUpdate(requete);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("");
		}
		
	}
	
	public void delete(Competition competition)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");	
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="UPDATE competition SET deleted_at = NOW() WHERE id_candidat = "+competition.getId();
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
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	
			String requete ="DELETE FROM toequipe WHERE id_personne="+personne.getId()+" AND id_equipe="+equipe.getId();
			st.executeUpdate(requete);	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void updateC(Competition competition) 
	{	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/competition?autoReconnect=true&useSSL=false";
			String login = "root";
			String password = "";
			Connection cn = DriverManager.getConnection(url, login,password);
			Statement st = cn.createStatement();	


			String requete3 ="UPDATE competition set nom_competition = '"+competition.getNom()+"' WHERE id_competition = '"+competition.getId()+"'";

			st.executeUpdate(requete3);	
			
			  
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
}