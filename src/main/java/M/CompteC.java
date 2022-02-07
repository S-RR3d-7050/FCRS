package M;


public class CompteC extends Compte{


  private long seuil;

  CompteC(long numC, double solde,long seuil){
      super(numC,solde);
      this.seuil = seuil ;
  }


  /*
  public long getNumC() {
    return numC;
  }

  public void setNumC(long numC) {
    this.numC = numC;
  }
  */

  public long getSeuil() {
    return seuil;
  }

  public void setSeuil(long seuil) {
    this.seuil = seuil;
  }

}
