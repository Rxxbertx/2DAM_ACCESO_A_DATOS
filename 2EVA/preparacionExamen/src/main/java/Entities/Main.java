package Entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.*;
import java.util.Map;

public class Main {


    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {
        menu();
    }

    private static void menu() throws IOException {

        System.out.println("que deseas hacer ?  1 buscar 2 update 3 delete 4 crear");
        String userinput;
        while ((userinput = reader.readLine()) != null) {

            switch (userinput) {

                case "1":
                    buscar();
                    break;
                case "2":
                    update();
                    break;
                case "3":
                    delete();
                    break;
                case "4":
                    create();
                    break;
                default:
                    System.err.println("introduce una opcion correcta");
                    System.err.println("que deseas hacer ?  1 buscar 2 update 3 delete 4 crear");

            }
        }
        ;


    }

    private static void create() {


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        System.out.println("introduce el nombre del usuario");
        String name = null;
        try {
            name = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("introduce el nombre del rol");
        String rol = null;
        try {
            rol = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = new User(name, new Rol(rol));

        entityManager.persist(user);
        entityTransaction.commit();
        entityManagerFactory.close();


    }

    private static EntityTransaction obtenerTrans() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        return entityTransaction;
    }


    static void update() {

        EntityTransaction entityTransaction = obtenerTrans();

        entityTransaction.begin();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("introduce el nombre del usuario a buscar");
        String input;
        try {
            while (!(input = reader.readLine()).equals("salir")) {

                User user = entityManager.find(User.class, input);
                if (user != null) {
                    System.out.println("introduce el nuevo nombre del usuario");
                    String newName = reader.readLine();
                    user.setName(newName);
                    entityManager.persist(user);
                    entityTransaction.commit();
                    System.out.println("usuario actualizado");
                } else {
                    System.out.println("usuario no encontrado");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void buscar() throws IOException {

        EntityTransaction entityTransaction = obtenerTrans();

        entityTransaction.begin();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("introduce el nombre del usuario a buscar");
        String input;
        while (!(input = reader.readLine()).equals("salir")) {


            User user = entityManager.find(User.class, input);

            if (user != null) {
                System.out.println("usuario encontrado");
                System.out.println("nombre: " + user.getName());
                System.out.println("rol: " + user.getRol().getNombre());
            } else {
                System.out.println("usuario no encontrado");
            }


        }
        
        
        entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class).setParameter("name",input).getResultList().forEach(System.out::println);
        
        
        entityManagerFactory.close();


    }

    static void delete() {

   

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        entityTransaction.begin();


        System.out.println("introduce el nombre del usuario a buscar");
        String input;
        try {
            while (!(input = reader.readLine()).equals("salir")) {

                User user = entityManager.find(User.class, input);
                if (user != null) {
                    entityManager.remove(user);
                    entityTransaction.commit();
                    System.out.println("usuario eliminado");
                } else {
                    System.out.println("usuario no encontrado");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        entityManagerFactory.close();

    }

}
