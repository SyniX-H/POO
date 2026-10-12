import java.util.Optional;
public class Bibliothecaire {
 private final String nom;
 private final Catalogue<Document> catalogue; // COMPOSITION : possede un catalogue
 public Bibliothecaire(String nom, Catalogue<Document> catalogue) {
 this.nom = nom;
 this.catalogue = catalogue;
 }
 public void accueillir() { System.out.println("Bonjour, " + nom); }
 // DELEGATION : on n'expose que ce qui a un sens pour un bibliothecaire
 public Optional<Document> chercher(String titre) {
 return catalogue.rechercherParTitre(titre);
 }
}