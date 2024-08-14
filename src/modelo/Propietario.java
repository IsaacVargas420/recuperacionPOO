/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


public class Propietario extends Persona {
    private int propId;

    // Constructor vacío
    public Propietario() {}

    // Constructor con parámetros
    public Propietario(int personaId, String nombre, String apellido, String cedula, String telefono, String direccion, int propId) {
        super(personaId, nombre, apellido, cedula, telefono, direccion);
        this.propId = propId;
    }

    // Getters y Setters
    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    // Método toString
    @Override
             public String toString() {
                return """
             PROPIETARIO
                       
           NOMBRE='""" + getNombre() + '\'' + '\n' +
            "APELLIDO='" + getApellido() + '\'' + '\n' +
            "CEDULA='" + getCedula() + '\'' + '\n' +
            "TELEFONO='" + getTelefono() + '\'' + '\n' +
            "DIRECCION='" + getDireccion() + '\'' + '\n';
}

}
