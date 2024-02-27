import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello, World!");


        // Create a new instance of the class
        // and call the method

        String uri = "mongodb://localhost:27017";
        try (MongoClient client = MongoClients.create(uri)) {

            System.out.println("Connected to MongoDB!");

            for (String dbName : client.listDatabaseNames()) {
                System.out.println(dbName);
            }

            MongoDatabase mongoDatabase = client.getDatabase("prueba");

            System.out.println("Connected to MongoDB!" + mongoDatabase.getName());

            for (var collectionName : mongoDatabase.listCollections()) {
                System.out.println(collectionName.toJson());
            }

            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("micoleccion");


            for (Document document : mongoCollection.find()) {
                System.out.println(document.toJson());
            }

            Bson filter = new Document("nombre", "pedrito").append("edad", new Document("$gt", 20));


            System.out.println("Filtrando por nombre");

            for (var document : mongoCollection.find(filter)) {
                System.out.println(document.toJson());
            }


            Document document = new Document("nombre", "pepe").append("edad", 10).append("salario", 100.10f).append("proyectos", Arrays.asList("1", "2")).append("departamento", new Document("numero", 1).append("nombre", "finanzas").append("localizacion", "burgos"));

            mongoCollection.insertOne(document);
            
            
        
            

        }


    }

}
