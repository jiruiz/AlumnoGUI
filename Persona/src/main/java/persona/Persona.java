/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persona;

import exceptions.ApellidoNullException;
import exceptions.ApellidoVacioException;
import exceptions.NombreNullException;
import exceptions.NombreVacioException;
import exceptions.PersonaException;
import java.time.LocalDate;

/**
 *
 * @author g.guzman
 */
public class Persona {

    public final static String DELIM = "\t";

    private Integer dni;
    private String nombre;
    private String apellido;
    private LocalDate fecNac;

    public Persona() {
        nombre = "";
    }

    public Persona(Integer dni) throws PersonaException {
        setDni(dni);
    }

    public Persona(Integer dni, String nombre, String apellido) throws IllegalArgumentException, PersonaException {
        this.dni = dni;
        setNombre(nombre);
        setApellido(apellido);

    }

    public Integer getDni() {

        return dni;
    }

    public void setDni(Integer dni) throws PersonaException {
        if (dni == null || dni <= 0) {
            throw new PersonaException("El DNI " + dni + " es inválido");
        }
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    //public void setNombre(String nombre) throws NombreNullException, NombreVacioException {
    public void setNombre(String nombre) throws PersonaException {
        if (nombre == null) {
            throw new NombreNullException("El nombre es nulo");
        }
        String nombreTrim = nombre.trim();
        if (nombreTrim.length() == 0) {
            throw new NombreVacioException("El nombre no tiene contenido");
        }
        this.nombre = nombreTrim;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) throws ApellidoNullException, ApellidoVacioException {
        if (apellido == null) {
            throw new ApellidoNullException("El apellido es nulo");
        }
        String apellidoTrim = apellido.trim();
        if (apellido.trim().length() == 0) {
            throw new ApellidoVacioException("El apellido no tiene contenido");
        }
        this.apellido = apellidoTrim;
    }

    public String getFullName() {
        return nombre + " " + apellido;
    }

    public LocalDate getFecNac() {
        return fecNac;
    }

    public void setFecNac(LocalDate fecNac) {
        this.fecNac = fecNac;
    }

    public String getFecNacStr() {
        if (fecNac == null) {
            return "01/01/0009";
        }
        return String.format("%02d/%02d/%04d", fecNac.getDayOfMonth(), fecNac.getMonthValue(), fecNac.getYear());
    }

    @Override
    public String toString() {
        String nombreTrunc = nombre.length() > 20 ? nombre.substring(0, 20) : nombre;
        return String.format("%08d%s%20s%s%20s%s%10s", dni, DELIM, nombreTrunc, DELIM, apellido,
                DELIM, getFecNacStr());
    }

}
