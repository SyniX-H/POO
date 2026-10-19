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

    
