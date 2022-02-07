package M;


public class Compte {

  private long numC;
  private double solde;

  public Compte(long numC, double solde) {
      this.numC = numC;
      this.solde = solde;
  }


  public long getNumC() {
    return numC;
  }

  public void setNumC(long numC) {
    this.numC = numC;
  }


  public double getSolde() {
    return solde;
  }

  public void setSolde(double solde) {
    this.solde = solde;
  }



}
