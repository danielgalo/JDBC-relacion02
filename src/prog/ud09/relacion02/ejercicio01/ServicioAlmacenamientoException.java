package prog.ud09.relacion02.ejercicio01;

public class ServicioAlmacenamientoException extends Exception {

  private String msg;
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public ServicioAlmacenamientoException(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
