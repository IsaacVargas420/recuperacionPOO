/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Vehiculo;

public class VehiculoControlador {

    // Método para crear un nuevo vehículo
    public void crearVehiculo(Vehiculo vehiculo) throws SQLException {
        String query = "INSERT INTO Vehiculos (Placa, Tipo, Marca, PropId) VALUES (?, ?, ?, ?)";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getTipo());
            ps.setString(3, vehiculo.getMarca());
            ps.setInt(4, vehiculo.getPropId());
            ps.executeUpdate();
        }
    }

    // Método para obtener un vehículo por su placa
    public Vehiculo obtenerVehiculoPorPlaca(String placa) throws SQLException {
        String query = "SELECT * FROM Vehiculos WHERE Placa = ?";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Vehiculo vehiculo = new Vehiculo();
                    vehiculo.setVehId(rs.getInt("VehId"));
                    vehiculo.setPlaca(rs.getString("Placa"));
                    vehiculo.setTipo(rs.getString("Tipo"));
                    vehiculo.setMarca(rs.getString("Marca"));
                    vehiculo.setPropId(rs.getInt("PropId"));
                    return vehiculo;
                }
            }
        }
        return null;
    }

    // Método para obtener todos los vehículos
    public List<Vehiculo> obtenerVehiculos() throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM Vehiculos";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
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
        return vehiculos;
    }

    // Método para actualizar un vehículo
   public void actualizarVehiculo(Vehiculo vehiculo) throws SQLException {
    String queryVehiculo = "UPDATE Vehiculos SET Tipo = ?, Marca = ?, PropId = ? WHERE Placa = ?";
    String queryVerificarPropietario = "SELECT COUNT(*) FROM Propietarios WHERE PropId = ?";
    
    try (Connection con = new ConexionBDD().conectar();
         PreparedStatement psVerificarPropietario = con.prepareStatement(queryVerificarPropietario);
         PreparedStatement psActualizarVehiculo = con.prepareStatement(queryVehiculo)) {

        // Verificar si el PropId es válido
        psVerificarPropietario.setInt(1, vehiculo.getPropId());
        try (ResultSet rs = psVerificarPropietario.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                throw new SQLException("No existe el PropId proporcionado: " + vehiculo.getPropId());
            }
        }

        // Actualizar en la tabla Vehiculos
        psActualizarVehiculo.setString(1, vehiculo.getTipo());
        psActualizarVehiculo.setString(2, vehiculo.getMarca());
        psActualizarVehiculo.setInt(3, vehiculo.getPropId());
        psActualizarVehiculo.setString(4, vehiculo.getPlaca());
        psActualizarVehiculo.executeUpdate();
    }
}


    // Método para eliminar un vehículo
    public void eliminarVehiculo(String placa) throws SQLException {
        String query = "DELETE FROM Vehiculos WHERE Placa = ?";
        try (Connection con = new ConexionBDD().conectar();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, placa);
            ps.executeUpdate();
        }
    }
}
