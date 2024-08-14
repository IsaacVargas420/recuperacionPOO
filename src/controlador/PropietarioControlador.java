package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Propietario;
import modelo.Vehiculo;

public class PropietarioControlador {

    // Método para crear un nuevo propietario
    public void crearPropietario(Propietario propietario) throws SQLException {
        String queryPersona = "INSERT INTO Persona (Nombre, Apellido, Cedula, Telefono, Direccion) VALUES (?, ?, ?, ?, ?)";
        String queryPropietario = "INSERT INTO Propietarios (PersonaId) VALUES (?)";

        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement psPersona = con.prepareStatement(queryPersona, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement psPropietario = con.prepareStatement(queryPropietario)) {
            
            // Insertar en la tabla Persona
            psPersona.setString(1, propietario.getNombre());
            psPersona.setString(2, propietario.getApellido());
            psPersona.setString(3, propietario.getCedula());
            psPersona.setString(4, propietario.getTelefono());
            psPersona.setString(5, propietario.getDireccion());
            psPersona.executeUpdate();

            // Obtener el ID generado para la persona
            try (ResultSet rs = psPersona.getGeneratedKeys()) {
                if (rs.next()) {
                    int personaId = rs.getInt(1);

                    // Insertar en la tabla Propietarios
                    psPropietario.setInt(1, personaId);
                    psPropietario.executeUpdate();
                }
            }
        }
    }

    // Método para obtener un propietario por su cédula
    public Propietario obtenerPropietarioPorCedula(String cedula) throws SQLException {
        String query = "SELECT p.*, pe.Nombre, pe.Apellido, pe.Cedula, pe.Telefono, pe.Direccion " +
                       "FROM Propietarios p " +
                       "JOIN Persona pe ON p.PersonaId = pe.PersonaId " +
                       "WHERE pe.Cedula = ?";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Propietario propietario = new Propietario();
                    propietario.setPropId(rs.getInt("PropId"));
                    propietario.setNombre(rs.getString("Nombre"));
                    propietario.setApellido(rs.getString("Apellido"));
                    propietario.setCedula(rs.getString("Cedula"));
                    propietario.setTelefono(rs.getString("Telefono"));
                    propietario.setDireccion(rs.getString("Direccion"));
                    return propietario;
                }
            }
        }
        return null;
    }

    // Método para listar vehículos por cédula de propietario
    public List<Vehiculo> listarVehiculosPorPropietario(String cedula) throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT v.* FROM Vehiculos v " +
                       "JOIN Propietarios p ON v.PropId = p.PropId " +
                       "JOIN Persona pe ON p.PersonaId = pe.PersonaId " +
                       "WHERE pe.Cedula = ?";
        
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Vehiculo vehiculo = new Vehiculo();
                    vehiculo.setVehId(rs.getInt("VehId"));
                    vehiculo.setPlaca(rs.getString("Placa"));
                    vehiculo.setTipo(rs.getString("Tipo"));
                    vehiculo.setMarca(rs.getString("Marca"));
                    vehiculo.setPropId(rs.getInt("PropId"));
                    vehiculos.add(vehiculo);
                }
            }
        }
        return vehiculos;
    }

public void actualizarPropietario(Propietario propietario) throws SQLException {
    // Primero, obtener el PersonaId a partir de la cédula
    String queryPersonaId = "SELECT PersonaId FROM Persona WHERE Cedula = ?";
    String queryActualizarPersona = "UPDATE Persona SET Nombre = ?, Apellido = ?, Telefono = ?, Direccion = ? WHERE Cedula = ?";
    
    try (Connection con = new ConexionBDD().conectar();
         PreparedStatement psPersonaId = con.prepareStatement(queryPersonaId);
         PreparedStatement psActualizarPersona = con.prepareStatement(queryActualizarPersona)) {

        // Obtener PersonaId
        psPersonaId.setString(1, propietario.getCedula());
        int personaId;
        try (ResultSet rs = psPersonaId.executeQuery()) {
            if (rs.next()) {
                personaId = rs.getInt("PersonaId");
            } else {
                throw new SQLException("No se encontró Persona con cédula: " + propietario.getCedula());
            }
        }

        // Actualizar en la tabla Persona
        psActualizarPersona.setString(1, propietario.getNombre());
        psActualizarPersona.setString(2, propietario.getApellido());
        psActualizarPersona.setString(3, propietario.getTelefono());
        psActualizarPersona.setString(4, propietario.getDireccion());
        psActualizarPersona.setString(5, propietario.getCedula());
        psActualizarPersona.executeUpdate();
    }
}


public void eliminarPropietario(String cedula) throws SQLException {
    // SQL queries para eliminar vehículos y luego propietario basado en la cédula
    String queryVehiculos = "DELETE FROM Vehiculos WHERE PropId = (SELECT PropId FROM Propietarios WHERE PersonaId = (SELECT PersonaId FROM Persona WHERE Cedula = ?))";
    String queryPropietario = "DELETE FROM Propietarios WHERE PersonaId = (SELECT PersonaId FROM Persona WHERE Cedula = ?)";
    String queryPersona = "DELETE FROM Persona WHERE Cedula = ?";

    try (Connection con = new ConexionBDD().conectar();
         PreparedStatement psVehiculos = con.prepareStatement(queryVehiculos);
         PreparedStatement psPropietario = con.prepareStatement(queryPropietario);
         PreparedStatement psPersona = con.prepareStatement(queryPersona)) {

        con.setAutoCommit(false); // Iniciar transacción

        // Primero, eliminar en la tabla Vehiculos
        psVehiculos.setString(1, cedula);
        psVehiculos.executeUpdate();

        // Luego, eliminar en la tabla Propietarios
        psPropietario.setString(1, cedula);
        psPropietario.executeUpdate();

        // Finalmente, eliminar en la tabla Persona
        psPersona.setString(1, cedula);
        psPersona.executeUpdate();

        con.commit(); // Confirmar transacción
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}
}
