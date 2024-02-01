import org.hibernate.Hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class test {

    public static void main(String[] args) {
        // Nombre de la unidad de persistencia debe coincidir con el definido en persistence.xml
        String persistenceUnitName = "persistencia";
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emf.createEntityManager();

        try {
            // Realiza operaciones de persistencia aquí
            em.getTransaction().begin();


            DeportistasID deportistasID = new DeportistasID();
            
            deportistasID.nombre="pepa";
            
            Deportistas deportistas = new Deportistas();
            
            deportistas.setId(deportistasID);
            
            
            em.persist(deportistas);
            

            em.getTransaction().commit();
            
            
            
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
