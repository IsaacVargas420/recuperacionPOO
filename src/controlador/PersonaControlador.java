package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Persona;

public class PersonaControlador {

    // Método para crear una nueva persona
    public void crearPersona(Persona persona) throws SQLException {
        String query = "INSERT INTO Persona (Nombre, Apellido, Cedula, Telefono, Direccion) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setString(3, persona.getCedula());
            ps.setString(4, persona.getTelefono());
            ps.setString(5, persona.getDireccion());
            ps.executeUpdate();
        }
    }

    // Método para obtener una persona por su cédula
    public Persona obtenerPersonaPorCedula(String cedula) throws SQLException {
        String query = "SELECT * FROM Persona WHERE Cedula = ?";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona persona = new Persona();
                    persona.setPersonaId(rs.getInt("PersonaId"));
                    persona.setNombre(rs.getString("Nombre"));
                    persona.setApellido(rs.getString("Apellido"));
                    persona.setCedula(rs.getString("Cedula"));
                    persona.setTelefono(rs.getString("Telefono"));
                    persona.setDireccion(rs.getString("Direccion"));
                    return persona;
                }
            }
        }
        return null;
    }

    // Método para obtener todas las personas
    public List<Persona> obtenerPersonas() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String query = "SELECT * FROM Persona";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setPersonaId(rs.getInt("PersonaId"));
                persona.setNombre(rs.getString("Nombre"));
                persona.setApellido(rs.getString("Apellido"));
                persona.setCedula(rs.getString("Cedula"));
                persona.setTelefono(rs.getString("Telefono"));
                persona.setDireccion(rs.getString("Direccion"));
                personas.add(persona);
            }
        }
        return personas;
    }

    // Método para actualizar una persona por su cédula
    public void actualizarPersona(Persona persona) throws SQLException {
        String query = "UPDATE Persona SET Nombre = ?, Apellido = ?, Telefono = ?, Direccion = ? WHERE Cedula = ?";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setString(3, persona.getTelefono());
            ps.setString(4, persona.getDireccion());
            ps.setString(5, persona.getCedula());
            ps.executeUpdate();
        }
    }

    // Método para eliminar una persona por su cédula
    public void eliminarPersona(String cedula) throws SQLException {
        String query = "DELETE FROM Persona WHERE Cedula = ?";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, cedula);
            ps.executeUpdate();
        }
    }
}
