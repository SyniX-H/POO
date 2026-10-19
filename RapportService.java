public  interface FormatRapport {
 String formater ( List < Document > docs );
}
public interface SortieRapport {
 void ecrire(String contenu) throws IOException ;
}
public interface Notificateur {
   void notifier(String message) ; 
}
    

public class FormatCsv implements FormatRapport {
 @Override
 public String formater(List<Document> docs) {
 StringBuilder sb = new StringBuilder();
 for (Document d : docs) {
 sb.append(d.getTitre()).append(';').append(d.getId()).append('\n');
 }
 return sb.toString();
 }
}
public class FormatTxt implements FormatRapport {
 @Override
 public String formater(List<Document> docs) {
 StringBuilder sb = new StringBuilder();
 for (Document d : docs) {
 sb.append("- ").append(d.descriptionCourte()).append('\n');
 }
 return sb.toString();
 }
}
public class RapportService {
 private final FormatRapport format;
 private final SortieRapport sortie;
 private final Notificateur notificateur;
 public RapportService(FormatRapport f, SortieRapport s, Notificateur n) {
 this.format = f;
 this.sortie = s;
 this.notificateur = n;
 }
 public void generer(List<Document> docs) throws IOException {
 String contenu = format.formater(docs);
 sortie.ecrire(contenu); // si ça échoue : exception, PAS de mail notificateur.notifier("Rapport généré"); // seulement après succès
 }
}
public class SortieFichier implements SortieRapport {
 private final Path chemin;
 public SortieFichier(Path chemin) { this.chemin = chemin; }
 @Override
 public void ecrire(String contenu) throws IOException {
 Files.writeString(chemin, contenu);
 }
}
public class NotificateurSmtp implements Notificateur {
 private final ServeurSmtp smtp;
 private final String destinataire;
 public NotificateurSmtp(ServeurSmtp smtp, String destinataire) {
 this.smtp = smtp;
 this.destinataire = destinataire;
 }
 @Override
 public void notifier(String message) {
 smtp.envoyer(destinataire, message);
 }
}
class RapportServiceTest {
 @Test
 void genereEtNotifie() throws IOException {
 StringBuilder disque = new StringBuilder();
 List<String> notifs = new ArrayList<>();
 RapportService service = new RapportService(
 docs -> "contenu-teste", // faux format (lambda)
 contenu -> disque.append(contenu), // fausse sortie
 message -> notifs.add(message)); // faux notificateur
 service.generer(List.of());
 assertEquals("contenu-teste", disque.toString());
 assertEquals(1, notifs.size());
 }
 @Test
 void pasDeNotificationSiEcritureEchoue() {
 List<String> notifs = new ArrayList<>();
 RapportService service = new RapportService(
 docs -> "x",
 contenu -> { throw new IOException("disque plein"); },
 message -> notifs.add(message));
 assertThrows(IOException.class, () -> service.generer(List.of()));
 assertTrue(notifs.isEmpty()); // le bug de l'exception avalée est corrigé
 }
}

    
