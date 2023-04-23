package prog.ud09.relacion02.ejercicio01.pruebas;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prog.ud09.relacion02.ejercicio01.Centro;
import prog.ud09.relacion02.ejercicio01.ServicioAlmacenamientoEmpresa;
import prog.ud09.relacion02.ejercicio01.ServicioAlmacenamientoException;

class PruebaServicioAlmacenamientoEmpresa {

  private static final String DB_ORIGINAL = "db/empresa_orig.db";
  private static final String DB_TRABAJO = "db/empresa.db";
  private ServicioAlmacenamientoEmpresa servicio;
  
  @BeforeEach
  void setUp() throws Exception {
    // Este método se llama antes de cada prueba para "preparar" el entorno
    // Vamos a copiar la base de datos "original" sobre la de trabajo
    Files.copy(Path.of(DB_ORIGINAL), Path.of(DB_TRABAJO), StandardCopyOption.REPLACE_EXISTING);
    // Y creamos un servicio a la ruta "buena"
    servicio = new ServicioAlmacenamientoEmpresa(DB_TRABAJO);
  }

  @Test
  void testBuscaCentroPorCodigoOk() {
    // Buscamos un centro con un código existente (1 = Malaga)
    Centro centro = servicio.buscaCentroPorCodigo(1);
    // El centro no debe ser null
    assertNotNull(centro);
    // El centro debe tener codigo 1 y nombre Malaga
    assertEquals(1, centro.getNumce());
    assertEquals("Malaga", centro.getNomce());
  }

  @Test
  void testBuscaCentroPorCodigoError() {
    // Creamos un servicio con una ruta incorrecta
    ServicioAlmacenamientoEmpresa servicio2 = new ServicioAlmacenamientoEmpresa("bad.db");
    // Buscamos un centro con un código existente (1 = Malaga) (da igual)
    // Debe lanzar una excepcion al hacer la llamada
    assertThrows(ServicioAlmacenamientoException.class, () -> servicio2.buscaCentroPorCodigo(1));
  }

  @Test
  void testbuscaTodosDesordenado() {
    // Obtenemos la lista con todos los centros
    List<Centro> centros = servicio.buscaTodos(false);

    // La lista debe tener 6 elementos
    assertEquals(6, centros.size());
  }

  @Test
  void testbuscaTodosOrdenado() {
    // Obtenemos la lista con todos los centros
    List<Centro> centros = servicio.buscaTodos(true);

    // La lista debe tener 6 elementos
    assertEquals(6, centros.size());
    
    // El primero debe ser Barbate con codigo 6
    assertEquals(6, centros.get(0).getNumce());
    assertEquals("Barbate", centros.get(0).getNomce());
    // El segundo debe ser Barcelona con codigo 4
    assertEquals(4, centros.get(1).getNumce());
    assertEquals("Barcelona", centros.get(1).getNomce());
    // El tercero debe ser Granada con codigo 5
    assertEquals(5, centros.get(2).getNumce());
    assertEquals("Granada", centros.get(2).getNomce());
    // El cuarto debe ser Madrid con codigo 3
    assertEquals(3, centros.get(3).getNumce());
    assertEquals("Madrid", centros.get(3).getNomce());
    // El quinto debe ser Malaga con codigo 1
    assertEquals(1, centros.get(4).getNumce());
    assertEquals("Malaga", centros.get(4).getNomce());
    // El ultimo debe ser Sevilla con codigo 2
    assertEquals(2, centros.get(5).getNumce());
    assertEquals("Sevilla", centros.get(5).getNomce());
  }

  @Test
  void testbuscaTodosError() {
    // Creamos un servicio con una ruta incorrecta
    ServicioAlmacenamientoEmpresa servicio2 = new ServicioAlmacenamientoEmpresa("bad.db");
    // Debe lanzar una excepcion al hacer la llamada
    assertThrows(ServicioAlmacenamientoException.class, () -> servicio2.buscaTodos(false));
  }

  @Test
  void testInsertaCentroOk() {
    // Insertamos un nuevo centro (Antequera)
    Centro centro = new Centro(0, "Antequera");
    assertDoesNotThrow(() -> servicio.insertaCentro(centro));
    // Busca el centro y debe encontrarlo
    Centro centro2 = servicio.buscaCentroPorCodigo(centro.getNumce());
    assertEquals(centro.getNumce(), centro2.getNumce());
    assertEquals(centro.getNomce(), centro2.getNomce());
  }

  @Test
  void testInsertaCentroError() {
    // Creamos un servicio con una ruta incorrecta
    ServicioAlmacenamientoEmpresa servicio2 = new ServicioAlmacenamientoEmpresa("bad.db");
    // Insertamos un nuevo centro (Antequera) (da igual)
    Centro centro = new Centro(0, "Antequera");
    // Debe lanzar una excepcion al hacer la llamada
    assertThrows(ServicioAlmacenamientoException.class, () -> servicio2.insertaCentro(centro));
  }


  @Test
  void testActualizaCentroOk() {
    // Actualizamos el centro de Malaga a Malaga Sur
    Centro centro = new Centro(1, "Malaga Sur");
    assertDoesNotThrow(() -> servicio.actualizaCentro(centro));
    // Busca el centro y debe encontrarlo
    Centro centro2 = servicio.buscaCentroPorCodigo(centro.getNumce());
    // Sus campos deben ser iguales a los del actualizado
    assertEquals(centro.getNumce(), centro2.getNumce());
    assertEquals(centro.getNomce(), centro2.getNomce());
  }

  @Test
  void testActualizaCentroNoExiste() {
    // Modificamos un centro inexistente
    Centro centro = new Centro(10000, "Malaga Norte");
    // Debe lanzar una excepcion al hacer la llamada
    assertThrows(ServicioAlmacenamientoException.class, () -> servicio.actualizaCentro(centro));
  }

  @Test
  void testActualizaCentroError() {
    // Creamos un servicio con una ruta incorrecta
    ServicioAlmacenamientoEmpresa servicio2 = new ServicioAlmacenamientoEmpresa("bad.db");
    // Actualizamos cualquier centro (da igual)
    Centro centro = new Centro(0, "Antequera");
    // Debe lanzar una excepcion al hacer la llamada
    assertThrows(ServicioAlmacenamientoException.class, () -> servicio2.actualizaCentro(centro));
  }

  @Test
  void testEliminaCentroOk() {
    // Eliminamos el centro de Malaga (1)
    assertDoesNotThrow(() -> servicio.eliminaCentroPorCodigo(1));
    
    // Intentamos acceder a ese centro (nos tiene que dar null porque no existe)
    // Actualizamos el centro de Malaga a Malaga Sur
    Centro centro = servicio.buscaCentroPorCodigo(1);
    assertNull(centro);
  }

  @Test
  void testEliminaCentroError() {
    // Creamos un servicio con una ruta incorrecta
    ServicioAlmacenamientoEmpresa servicio2 = new ServicioAlmacenamientoEmpresa("bad.db");
    // Debe lanzar una excepcion al hacer la llamada
    assertThrows(ServicioAlmacenamientoException.class, () -> servicio2.eliminaCentroPorCodigo(1));
  }

}
