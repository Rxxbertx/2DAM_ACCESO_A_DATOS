import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {

	public static void main(String[] args) {
	
		
		String persistenceUnitName = "persistencia";
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        //Person person = new Person("alex");
        //Team team = new Team("Los teleros");
        
        Person person = em.find(Person.class, 1);
        System.out.println(person.getSuperHero().name);

        
        em.getTransaction().commit();
        
        
        em.clear();
        em.close();
        emf.close();
		
		
	}

}
