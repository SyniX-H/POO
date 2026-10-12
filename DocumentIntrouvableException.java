public class DocumentIntrouvableException extends MediathequeException{
    public DocumentIntrouvableException(String titre){
        super("le document est introuvable : " + titre);
    }
}

