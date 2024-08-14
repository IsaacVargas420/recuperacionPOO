/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Vehiculo {
    private int vehId;
    private String placa;
    private String tipo;
    private String marca;
    private int propId;

    // Constructor vacío
    public Vehiculo() {}

    // Constructor con parámetros
    public Vehiculo(int vehId, String placa, String tipo, String marca, int propId) {
        this.vehId = vehId;
        this.placa = placa;
        this.tipo = tipo;
        this.marca = marca;
        this.propId = propId;
    }

    // Getters y Setters
    public int getVehId() {
        return vehId;
    }

    public void setVehId(int vehId) {
        this.vehId = vehId;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

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
           
           placa='""" + placa + '\'' + '\n' +
            "tipo='" + tipo + '\'' + '\n' +
            "marca='" + marca + '\'' + '\n';
}

}
