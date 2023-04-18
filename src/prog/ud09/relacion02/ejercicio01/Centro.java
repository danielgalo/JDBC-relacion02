package prog.ud09.relacion02.ejercicio01;

public class Centro {

  private int numce;
  private String nomce;
  
  public Centro(int numce, String nomce) {
    this.numce = numce;
    this.nomce = nomce;
  }

  public int getNumce() {
    return numce;
  }

  public void setNumce(int numce) {
    this.numce = numce;
  }

  public String getNomce() {
    return nomce;
  }

  public void setNomce(String nomce) {
    this.nomce = nomce;
  }
  
  public String toString() {
    return "[Centro = (numce = " + getNumce() + ", nomce = " + getNomce() + ")].";
  }
  
}
