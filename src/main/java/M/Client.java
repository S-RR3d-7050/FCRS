package M;


public class Client {

  private long num;
  private String nom;
  private String prenom;
  private String email;
  private String pass;
  private long compteRef;

  public Client(int id, String nom, String prenom, String email, String pass, long compteRef) {
    this.num = id;
    this.nom = nom;
    this.prenom = prenom;
    this.email = email;
    this.pass = pass;
    this.compteRef = compteRef;
  }
  //private Compte Compte;


  public long getNum() {
    return num;
  }

  public void setNum(long num) {
    this.num = num;
  }


  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }


  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  /*
  public long getNumC() {
    return numC;
  }

  public void setNumC(long numC) {
    this.numC = numC;
  }
*/

  @Override
  public String toString() {
    return "Client{" +
            "num=" + num +
            ", nom='" + nom + '\'' +
            ", prenom='" + prenom + '\'' +
            ", email='" + email + '\'' +
            ", pass='" + pass + '\'' +
            '}';
  }
}
