package M;


public class CompteE extends Compte{


  private long taux;

  CompteE(long numC, double solde,long taux){
    super(numC,solde);
    this.taux = taux ;
  }

  /*
  public long getNumC() {
    return numC;
  }


  public void setNumC(long numC) {
    this.numC = numC;
  }
  */


  public long getTaux() {
    return taux;
  }

  public void setTaux(long taux) {
    this.taux = taux;
  }

}
