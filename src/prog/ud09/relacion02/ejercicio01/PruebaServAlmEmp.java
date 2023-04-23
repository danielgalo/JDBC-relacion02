package prog.ud09.relacion02.ejercicio01;

import java.util.List;

public class PruebaServAlmEmp {
  
  private static final String URL_BD = "jdbc:sqlite:db/empresa.db";
  
  public static void main(String[] args) throws ServicioAlmacenamientoException {
    ServicioAlmacenamientoEmpresa servicio = new ServicioAlmacenamientoEmpresa(URL_BD);
//    Centro centro7 = new Centro(7, "Siete");
//    Centro centro8 = new Centro(8, "Ocho");
//    Centro centro9 = new Centro(9, "Nueve");
//    Centro centro = servicio.buscaCentroPorCodigo(1);
//    
//    System.out.println("Prueba Buscar por Codigo");
//    System.out.println(centro);
//    
//    
//    System.out.println("Prueba buscar todos");
//    List<Centro> listaCentros = servicio.buscaTodos(true);
//    for (Centro c: listaCentros) {
//      System.out.println(c);
//    }
//    
//    System.out.println("Prueba insertar centro");
//    try {
//      servicio.insertaCentro(centro7);
//    } catch (ServicioAlmacenamientoException e) {
//      e.printStackTrace();
//    }
//    System.out.println("Eliminar centro");
//    servicio.eliminaCentroPorCodigo(7);
//    List<Centro> listaCentrosDesupesEliminar = servicio.buscaTodos(true);
//    for (Centro c: listaCentrosDesupesEliminar) {
//      System.out.println(c);
//    }
//    servicio.eliminaCentroPorCodigo(9);
    
    Departamento dep = servicio.buscaDepartamentoPorCodigo(102);
    
    System.out.println(dep);
    
    System.out.println(servicio.buscaTodosDepartamentos(true));
    
    Departamento dep1 = new Departamento(1, 10, "Calle zuloaga", 0, 0, 0, "Departamento agarramela pa dentro");
    Departamento dep2 = new Departamento(12312313, 10, "Calle zuloaga", 0, 0, 0, "Departamento agarramela pa dentro");
    
    servicio.actualizaDepartamento(dep2.getNumde(), "NASHEE");
    
    
    
  }

}
