public class DocumentIndisponibleException extends MediathequeException {
    public DocumentIndisponibleException(String titre){
        super("le document est emprunté : " + titre);
    }
    public DocumentIndisponibleException(String titre , String motif){
        super("le document est emprunté : " + "(" + motif + ")" + " " + titre);
    }  
}
