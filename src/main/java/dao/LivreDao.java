package dao;

import org.hibernate.Transaction;

import entity.Livre;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

import javax.faces.bean.ManagedBean;
import javax.persistence.Query;
import util.HibernateUtil;

@ManagedBean(name="livreDao")
public class LivreDao {
	public static List<Livre> listeLivre = all();
	private static Livre livre;
	
	public static Livre recupererLivre() {
		return livre;
	}

	public static void persist(Livre livre){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.persist(livre);
			transaction.commit();
		}
		catch(Exception e) {
			System.out.print("Erreur lors de persist : " + e.getMessage());
			transaction.rollback();
		}
		session.close();
		listeLivre = all();
	}
	
	public static void update(Livre livre){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(livre);
			transaction.commit();
		}
		catch(Exception e) {
			System.out.print("Erreur lors de update : " + e.getMessage());
			transaction.rollback();
		}
		session.close();
		listeLivre = all();
	}
	
	public static void get(int id){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		Livre l = null;
		try {
			l = session.get(Livre.class, id);
			transaction.commit();
		}
		catch(Exception e) {
			System.out.print("Erreur lors de get : " + e.getMessage());
			transaction.rollback();
		}
		session.close();
		livre = l;
	}
	
	public static void delete(Livre livre){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(livre);
			transaction.commit();
		}
		catch(Exception e) {
			System.out.print("Erreur lors de delete : " + e.getMessage());
			transaction.rollback();
		}
		session.close();
		listeLivre = all();
	}
	
	public static List<Livre> all(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		List<Livre> listeLivre = null;
		try {
			Query query = session.createQuery("SELECT l FROM livre l");
			listeLivre = query.getResultList();
			transaction.commit();
		}
		catch(Exception e) {
			System.out.print("Erreur lors de getAll : " + e.getMessage());
			transaction.rollback();
		}
		session.close();
		return listeLivre;
	}
	
	public static HashMap<String,Integer> formatList(){
		HashMap<String,Integer> mapLivre = new HashMap<String,Integer>();
		for(Livre livre:listeLivre) {
			mapLivre.put(livre.getTitre(),livre.getId());
		}
		return mapLivre;
	}
}
