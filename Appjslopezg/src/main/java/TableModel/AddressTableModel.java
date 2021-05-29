/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel;

import Model.Address;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 * La clase AddressTableModel es un modelo personalizado, que hereda de
 * AbstractTableModel y sobreescribe muchos de sus métodos para su correcto
 * funcionamiento, al crear este modelo personalizado para nuestra tabla de
 * teléfonos nos apoyamos en el codigo de la clase DefaultTableModel, ya que
 * esta tambien hereda de AbstractTableModel y sobre todo de la documentacion de
 * Java que nos proporciona codigo de ejemplo para realizar un modelo
 * personalizado.
 *
 * @see
 * https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#data
 *
 * En la API de Java se nos dice que cuando creamos un modelo personalizado, es
 * sumamente importante hacer la llamada a los siguientes métodos cada vez que
 * realizamos una operacion con el modelo o los datos de nuestra tabla, ya que
 * de lo contrario, si estos métodos no son llamados no veremos los cambios en
 * nuestra JTable, los métodos son los siguientes:
 *
 * fireTableCellUpdated	- Se llama cuando se actualiza una celda en especifico
 * fireTableRowsUpdated	- Se llama cuando se actualiza una fila
 * fireTableDataChanged	- Se llama cuando se actualizo todo el contenido de la
 * tabla fireTableRowsInserted - Se llama cuando se inserto una nueva fila
 * fireTableRowsDeleted	- Se llama cuando se elimino una fila
 * fireTableStructureChanged - Se llama cuando se cambio todo el contenido de la
 * tabla incluyendo el numero de columnas y es necesario que vuelva a dibujarse.
 *
 * Finalmente este modelo implementa la interfaz Serializable, pero en este caso
 * NO se esta haciendo uso de esta interfaz, solo se implemento para dar un
 * ejemplo mas completo de un modelo personalizado, ya que como se dijo
 * anteriormente, para crear este modelo nos apoyamos en la documentacion de
 * Java y en el codigo de la clase DefaultTableModel la cual implementa la
 * interfaz Serializable, remover esta implementacion no afectaria en nada.
 *
 * @author wong
 * @author willj
 */
public class AddressTableModel extends AbstractTableModel implements Serializable {

    /**
     * Matriz que contiene los datos de la tabla
     */
    private Object rowData[][];

    /**
     * Arreglo que contiene el nombre de cada columna
     */
    private String columnNames[];

    /**
     * Variable contador para ir asignando un id diferente
     */
    private static int productId = 1;

    /**
     * En el constructor por defecto, cargamos el arreglo que contiene el nombre
     * de las columnas e inicializamos la matriz con cero de espacio
     */
    public AddressTableModel() {
        loadColumnNames();
        rowData = new Object[0][0];
    }

    /**
     * En este constructor podemos pasarle el arreglo de teléfonos que queremos
     * mostrar en la tabla y tambien cargamos el arreglo que contiene el nombre
     * de las columnas
     *
     * @param addresses
     */
    public AddressTableModel(Address[] addresses) {
        loadColumnNames();
        loadDataModel(addresses);
    }

    /**
     * Este método nos permite pasarle un arreglo de teléfonos y transformarlo a
     * matriz para poder mostrar su contenido en la tabla
     *
     * @param arreglo de Address
     */
    public void setDataModel(Address[] addresses) {
        loadDataModel(addresses);
    }

    /**
     * Este método nos devuleve todo el contenido de la tabla en un arreglo de
     * teléfonos
     *
     * @return un arreglo de Address
     */
    public Address[] getDataModel() {
        Address[] addresses = new Address[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            Object[] row = rowData[i];
            addresses[i] = convertToAddress(row);
        }
        return addresses;
    }

    /**
     * Este método convierte cada fila en un objeto de tipo Address, cada campo
     * o atributo del objeto Teléfono que este método retorna, es obtenido de la
     * fila que se le pasa por parametro, en orden desde la posicion 0 a la 3 se
     * van obteniendo los valores y asignando al objeto de Teléfono
     *
     * @param row
     * @return un objeto de tipo Teléfono
     */
    private Address convertToAddress(Object[] row) {
        Address temp = new Address();
        temp.setType(String.valueOf(row[0]));
        temp.setAddress(String.valueOf(row[1]));
        return temp;
    }

    /**
     * Devuelve el arreglo que contiene el nombre de las columnas
     *
     * @return un arreglo de String
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    /**
     * Este método nos devuelve un arreglo, en el que en cada posicion del
     * arreglo contiene el valor o contenido de cada atributo de la instancia de
     * Teléfono y despues este arreglo es introducido al final de la matriz,
     * pasando a formar una nueva fila
     *
     * @return un arreglo de Object
     */
    public static Object[] getDefaultRowData() {
        Address p = new Address();
        p.setType("Tipo ");
        p.setAddress("00000000");
        productId++;

        return new Object[]{p.getType(), p.getAddress()};
    }

    /**
     * Devuelve el numero total de filas usando el Operador ternario. El
     * operador ternario funciona como un if-else pero esta hecho para
     * validaciones mas cortas o de una linea como en este caso, lo que quiere
     * decir es lo siguiente:
     *
     * si rowData que es nuestra matriz de datos es igual a null (rowData ==
     * null) si es asi entonces retorname 0, esto lo hacemos con el signo de
     * interrogacion asi: ? 0 Y si no es igual a null entonces devuelvenos el
     * numero de filas de este, eso lo hacemos con los dos puntos, :
     * rowData.length
     *
     * OPERADOR TERNARIO = condicion ? si es verdad retorna.. : si no es verdad
     * retorna...
     *
     * Seria lo mismo tener:
     *
     * if (rowData == null) { return 0; } else { return rowData.length; }
     *
     * @return un entero
     */
    @Override
    public int getRowCount() {
        return rowData == null ? 0 : rowData.length;
    }

    /**
     * Devuelve el numero total de columnas de la tabla de teléfonos
     *
     * @return un entero
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Devuelve el valor de la fila y columna que le indiquemos, al crear un
     * modelo desde cero es importante sobreescribir este método.
     *
     * @param indice de la fila
     * @param indice de la columna
     * @return el objeto que se encuentra en la fila y columna seleccionada
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > rowData.length) {
            return null;
        } else if (columnIndex < 0 || columnIndex > columnNames.length) {
            return null;
        }
        return rowData[rowIndex][columnIndex];
    }

    /**
     * Establece el nuevo valor en la fila y columna que le indiquemos, al crear
     * un modelo desde cero, tambien es importante sobreescribir este método ya
     * que este es el que nos permite modificar los valores que estamos
     * mostrando e internamente este método se manda a llamar para poder editar
     * celdas
     *
     * NOTA: Recordemos siempre que se actualice un dato en una celda llamar al
     * método fireTableCellUpdated para que se reflejen los cambios en la tabla.
     *
     * @param el nuevo valor
     * @param indice de la fila
     * @param indice de la columna
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > rowData.length) {
            return;
        } else if (columnIndex < 0 || columnIndex > columnNames.length) {
            return;
        }
        rowData[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    /**
     * Este método nos retorna true si se puede editar la celda o false si no se
     * puede, este método funciona de la mano con el anterior internamente.
     *
     * @param indice de la fila
     * @param indice de la columna
     * @return true para cualquier fila y columna en nuestro caso
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * Devuelve la clase a la que pertenece el valor que se encuentra en la
     * columna que le indiquemos como parametro, para esto se apoya en el método
     * que sobreescribimos anteriormente getValueAt
     *
     * @param indice de la columna
     * @return la clase a la que pertence
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    /**
     * Devuelve el nombre de la columna, en base al indice de la columna que le
     * pasemos por parametro
     *
     * @param el indice de la columna
     * @return una String que representa el nombre de la columna
     */
    @Override
    public String getColumnName(int column) {
        if (column < 0 || column > columnNames.length) {
            return null;
        }
        return columnNames[column];
    }

    /**
     * Este método agrega una nueva fila a la matriz de teléfonos.
     *
     * Lo que hacemos primero es verificar si la matriz es diferente de null y
     * si es diferente de null tenemos que verificar si contiene datos, porque
     * puede ser diferente de null pero puede tener 0 registros o posiciones
     * entonces tenemos que validar que sea mayor que 0.
     *
     * Despues lo que hacemos es agregar una nueva fila o posicion a la matriz
     * para esto nos apoyamos en la clase Arrays y el método copyOf, lo que
     * hacemos es decir que:
     *
     * rowData = Arrays.copyOf(rowData, rowData.length + 1);
     *
     * Este método recibe como parametros un arreglo o matriz y el tamaño que
     * esta va a tener, en este caso le pasamos nuestra matriz de datos y su
     * nuevo tamaño sera el tamaño anterior + 1.
     *
     * En otras palabras, este método nos devuelve la matriz que ya tenemos MAS
     * una fila al final, en esta fila agregaremos el nuevo registro o arreglo.
     * Internamente lo que hace es copiar todo el contenido del arreglo o matriz
     * que le pasamos y nos devuelve una nueva matriz o arreglo con los mismos
     * datos pero con el nuevo tamaño que le indicamos, esto podria hacerse
     * usando bucles para copiar todos los registros que ya tenemos en una nueva
     * matriz y agregarle una posicion mas para el nuevo registro, pero esta
     * forma es mas rapida y facil.
     *
     * Despues lo que hacemos es situarnos en la ultima posicion que agregamos y
     * añadir el nuevo registro:
     *
     * rowData[rowData.length - 1] = defaultRowData;
     *
     * Y finalmente ES IMPORTANTE llamar al método fireTableRowsInserted() para
     * que los cambios se refelejen en la tabla, de lo contrario no se
     * actualizaran los datos y seguiremos viendo todo igual, es como para
     * notificar que hubo un cambio, a este método le pasamos la ultima fila que
     * insertamos que seria rowData.length - 1
     *
     * @param un arreglo de Object, la nueva fila
     */
    public void addRow(Object[] defaultRowData) {
        if (defaultRowData != null && defaultRowData.length > 0) {
            rowData = Arrays.copyOf(rowData, rowData.length + 1);
            rowData[rowData.length - 1] = defaultRowData;
            fireTableRowsInserted(rowData.length - 1, rowData.length - 1);
        }
    }

    /**
     * Este método lo que hace es remover la fila que le pasamos por parametro
     * primero verificamos si la fila esta entre 0 y rowData.length, si es asi
     * entonces procedemos a crear una nueva matriz con el mismo tamaño de la
     * anterior, luego con un bucle for iremos recorriendo cada fila y con un
     * condicional iremos verificando si el numero de fila que nos encontramos
     * es diferente de la que se quiere eliminar, si es diferente entonces
     * copiamos su contenido a la nueva matriz, de esta forma copiamos las filas
     * que no queremos eliminar a la nueva matriz.
     *
     * Finalmente lo que hacemos es igualar el contenido de la matriz temporal
     * que creamos con la matriz rowData;
     *
     * y nuevamente ES IMPORTANTE llamar al método fireTableRowsDeleted() para
     * notificar de que se elimino una fila en el modelo de la tabla, a este
     * método le pasamos la fila que eliminamos.
     *
     * @param fila seleccionada
     */
    public void removeRow(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < rowData.length) {
            final int length = rowData.length;
            Object[][] tempMatrix = new Object[length - 1][rowData[0].length];
            for (int i = 0, j = 0; i < length; i++) {
                if (i != selectedRow) {
                    System.arraycopy(rowData[i], 0, tempMatrix[j], 0, rowData[i].length);
                    j++;
                }
            }
            rowData = tempMatrix;
            fireTableRowsDeleted(selectedRow, selectedRow);
        }
    }

    /**
     * Este método obtiene el nombre de todos los campos de la clase Address y
     * almacena cada uno de ellos en el arreglo columnNames.
     *
     * Según la API el método fireTableStructureChanged() debe de llamarse
     * cuando la estructura de la tabla a cambiado y tenga que volverse a
     * dibujar de otra forma, como en este caso cuando se agregan las columnas
     *
     */
    private void loadColumnNames() {
        Field fields[] = Address.class.getDeclaredFields();
        columnNames = new String[fields.length];
        int index = 0;
        for (Field field : fields) {
            columnNames[index++] = field.getName();
        }
        fireTableStructureChanged();
    }

    /**
     * Este método se encarga de convertir el arreglo de teléfonos en filas de
     * la matriz rowData, para ello hacemos las validaciones correspondientes y
     * convertimos cada Teléfono del arreglo en una fila y lo vamos agregando.
     *
     * Según la API se debe de llamar el método fireTableDataChanged() cuando
     * todo el contenido de la tabla ha cambiado.
     *
     * @param el arreglo de Teléfonos a mostrar en la tabla
     */
    private void loadDataModel(Address[] addresses) {
        if (addresses != null && addresses.length > 0) {
            rowData = new Object[addresses.length][columnNames.length];
            int index = 0;
            for (Address a : addresses) {
                rowData[index++] = convertToArray(a);
            }
            fireTableDataChanged();
        } else {
            rowData = new Object[0][columnNames.length];
            fireTableDataChanged();
        }
    }

    /**
     * Este método se encarga de convertir una instancia de Teléfono en un
     * arreglo de Object[] el cual contiene en cada posicion el valor de cada
     * atributo de la instancia.
     *
     * Lo que hacemos es obtener el numero total de campos o atributos de la
     * clase Address en un arreglo de tipo Field[] despues vamos recorriendo
     * cada posicion de este arreglo y obtenemos el contenido de cada campo o
     * atributo para posteriormente almacenarlo en el arreglo de Object[].
     *
     * NOTA: ES IMPORTANTE LA LINEA f.setAccessible(true);
     *
     * Ya que si no la ponemos no nos dejara obtener al contenido de ese
     * atributo y nos lanzara una excepcion.
     *
     * Finalmente vamos obteniendo el contenido de cada campo y lo agregamos en
     * una nueva posicion del arreglo Object[] para retornarlo
     *
     * @param un objeto de tipo Teléfono
     * @return un arreglo de Object, con todos los datos del Teléfono
     */
    private Object[] convertToArray(Address product) {
        Field fields[] = Address.class.getDeclaredFields();
        Object[] row = new Object[fields.length];
        int index = 0;
        for (Field f : fields) {
            f.setAccessible(true); // para acceder al contenido de cada atributo
            try {
                row[index++] = f.get(product); // obtenemos su contenido y lo agregamos
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(AddressTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return row;
    }

}
