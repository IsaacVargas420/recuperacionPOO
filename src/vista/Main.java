package vista;

import controlador.ConexionBDD;
import controlador.PropietarioControlador;
import controlador.VehiculoControlador;
import modelo.Propietario;
import modelo.Vehiculo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear la instancia de ConexionBDD y establecer la conexión
        ConexionBDD conexionBDD = new ConexionBDD();
        Connection conexion = conexionBDD.conectar();

        // Verificar si la conexión fue exitosa
        if (conexion == null) {
            System.out.println("No se pudo establecer la conexión. Salida.");
            return;
        }

        PropietarioControlador propietarioControlador = new PropietarioControlador();
        VehiculoControlador vehiculoControlador = new VehiculoControlador();

        while (true) {
                                   System.out.println("===== Menú Principal =====");
                                   System.out.println("1. Agregar Propietario");
                                   System.out.println("2. Buscar Propietario por Cédula");
                                   System.out.println("3. Actualizar Propietario");
                                   System.out.println("4. Eliminar Propietario");
                                   System.out.println("5. Agregar Vehículo");
                                   System.out.println("6. Actualizar Vehículo");
                                   System.out.println("7. Eliminar Vehículo");
                                   System.out.println("8. Mostrar Vehículos de Propietario");
                                   System.out.println("9. Salir");
                                   System.out.print("Elija una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            // Manejar operaciones según la opción seleccionada
            switch (opcion) {
                case 1:
                    try {
                        agregarPropietario(scanner, propietarioControlador);
                    } catch (SQLException e) {
                                   System.out.println("Error al agregar propietario: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Ingrese la cédula del propietario: ");
                    String cedula = scanner.nextLine();
                    try {
                        buscarPropietarioPorCedula(cedula, propietarioControlador);
                    } catch (SQLException e) {
                        System.out.println("Error al buscar propietario: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        actualizarPropietario(scanner, propietarioControlador);
                    } catch (SQLException e) {
                        System.out.println("Error al actualizar propietario: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Ingrese la cédula del propietario a eliminar: ");
                    String cedulaEliminar = scanner.nextLine();
                    try {
                        eliminarPropietario(cedulaEliminar, propietarioControlador);
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar propietario: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        agregarVehiculo(scanner, propietarioControlador, vehiculoControlador);
                    } catch (SQLException e) {
                        System.out.println("Error al agregar vehículo: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        actualizarVehiculo(scanner, vehiculoControlador);
                    } catch (SQLException e) {
                        System.out.println("Error al actualizar vehículo: " + e.getMessage());
                    }
                    break;
                case 7:
                    System.out.print("Ingrese la placa del vehículo a eliminar: ");
                    String placaEliminar = scanner.nextLine();
                    try {
                        eliminarVehiculo(placaEliminar, vehiculoControlador);
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar vehículo: " + e.getMessage());
                    }
                    break;
                case 8:
                    System.out.print("Ingrese la cédula del propietario: ");
                    String cedulaPropietario = scanner.nextLine();
                    try {
                        mostrarVehiculosDePropietario(cedulaPropietario, propietarioControlador);
                    } catch (SQLException e) {
                        System.out.println("Error al mostrar vehículos del propietario: " + e.getMessage());
                    }
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción del menú.");
            }
        }
    }
    private static void agregarPropietario(Scanner scanner, PropietarioControlador controlador) throws SQLException {
        System.out.print("Ingrese el nombre del propietario: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el apellido del propietario: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese la cédula del propietario: ");
        String cedula = scanner.nextLine();
        System.out.print("Ingrese el teléfono del propietario: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese la dirección del propietario: ");
        String direccion = scanner.nextLine();

        Propietario propietario = new Propietario();
        propietario.setNombre(nombre);
        propietario.setApellido(apellido);
        propietario.setCedula(cedula);
        propietario.setTelefono(telefono);
        propietario.setDireccion(direccion);

        controlador.crearPropietario(propietario);
        System.out.println("Propietario agregado exitosamente.");
    }

    private static void buscarPropietarioPorCedula(String cedula, PropietarioControlador controlador) throws SQLException {
        Propietario propietario = controlador.obtenerPropietarioPorCedula(cedula);
        if (propietario != null) {
            System.out.println("Propietario encontrado: " + propietario);
        } else {
            System.out.println("No se encontró el propietario con cédula: " + cedula);
        }
    }

    private static void actualizarPropietario(Scanner scanner, PropietarioControlador controlador) throws SQLException {
        System.out.print("Ingrese la cédula del propietario a actualizar: ");
        String cedula = scanner.nextLine();
        Propietario propietario = controlador.obtenerPropietarioPorCedula(cedula);

        if (propietario != null) {
            System.out.print("Ingrese el nuevo nombre del propietario: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el nuevo apellido del propietario: ");
            String apellido = scanner.nextLine();
            System.out.print("Ingrese el nuevo teléfono del propietario: ");
            String telefono = scanner.nextLine();
            System.out.print("Ingrese la nueva dirección del propietario: ");
            String direccion = scanner.nextLine();

            propietario.setNombre(nombre);
            propietario.setApellido(apellido);
            propietario.setTelefono(telefono);
            propietario.setDireccion(direccion);

            controlador.actualizarPropietario(propietario);
            System.out.println("Propietario actualizado exitosamente.");
        } else {
            System.out.println("No se encontró el propietario con cédula: " + cedula);
        }
    }

private static void eliminarPropietario(String cedula, PropietarioControlador controlador) throws SQLException {
    try {
        controlador.eliminarPropietario(cedula);
        System.out.println("Propietario eliminado exitosamente.");
    } catch (SQLException e) {
        System.out.println("Error al eliminar propietario: " + e.getMessage());
    }
}


    private static void agregarVehiculo(Scanner scanner, PropietarioControlador propietarioControlador, VehiculoControlador vehiculoControlador) throws SQLException {
        System.out.print("Ingrese la placa del vehículo: ");
        String placa = scanner.nextLine();
        System.out.print("Ingrese el tipo del vehículo: ");
        String tipo = scanner.nextLine();
        System.out.print("Ingrese la marca del vehículo: ");
        String marca = scanner.nextLine();
        System.out.print("Ingrese la cédula del propietario del vehículo: ");
        String cedulaPropietario = scanner.nextLine();

        Propietario propietario = propietarioControlador.obtenerPropietarioPorCedula(cedulaPropietario);
        if (propietario == null) {
            System.out.println("No se encontró el propietario con cédula: " + cedulaPropietario);
            return;
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(placa);
        vehiculo.setTipo(tipo);
        vehiculo.setMarca(marca);
        vehiculo.setPropId(propietario.getPropId());

        vehiculoControlador.crearVehiculo(vehiculo);
        System.out.println("Vehículo agregado exitosamente.");
    }

    private static void actualizarVehiculo(Scanner scanner, VehiculoControlador controlador) throws SQLException {
        System.out.print("Ingrese la placa del vehículo a actualizar: ");
        String placa = scanner.nextLine();
        Vehiculo vehiculo = controlador.obtenerVehiculoPorPlaca(placa);

        if (vehiculo != null) {
            System.out.print("Ingrese el nuevo tipo del vehículo: ");
            String tipo = scanner.nextLine();
            System.out.print("Ingrese la nueva marca del vehículo: ");
            String marca = scanner.nextLine();

            vehiculo.setTipo(tipo);
            vehiculo.setMarca(marca);

            controlador.actualizarVehiculo(vehiculo);
            System.out.println("Vehículo actualizado exitosamente.");
        } else {
            System.out.println("No se encontró el vehículo con placa: " + placa);
        }
    }

    private static void eliminarVehiculo(String placa, VehiculoControlador controlador) throws SQLException {
        controlador.eliminarVehiculo(placa);
        System.out.println("Vehículo eliminado exitosamente.");
    }

    private static void mostrarVehiculosDePropietario(String cedula, PropietarioControlador controlador) throws SQLException {
        List<Vehiculo> vehiculos = controlador.listarVehiculosPorPropietario(cedula);
        if (vehiculos.isEmpty()) {
            System.out.println("El propietario con cédula " + cedula + " no tiene vehículos.");
        } else {
            System.out.println("Vehículos del propietario con cédula " + cedula + ":");
            for (Vehiculo v : vehiculos) {
                System.out.println("  Vehículo: " + v);
            }
        }
    }
}
