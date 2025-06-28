/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persona;

import exceptions.PersonaException;
import java.time.LocalDate;

/**
 *
 * @author g.guzman
 */
public class Alumno extends Persona {

    public final static String DELIM = "\t";
    private Double promedio;
    private Integer cantMatAprob;
    private LocalDate fecIng;
    private char estado; // A - M - B

    public Alumno() {
        super();
        promedio = 0.0;
    }

    public Alumno(Double promedio, Integer dni) throws PersonaException {
        super(dni);
        this.promedio = promedio;
    }

    public Alumno(Double promedio, Integer cantMatAprob, LocalDate fecIng, char estado) {
        this.promedio = promedio;
        this.cantMatAprob = cantMatAprob;
        this.fecIng = fecIng;
        this.estado = estado;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) throws PersonaException {
        if (estado <= 0) {
            throw new PersonaException("El estado no puede estar vacio");
        }
        this.estado = estado;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public Integer getCantMatAprob() {
        if (cantMatAprob == null) {
            return 0;
        }
        return cantMatAprob;
    }

    public void setCantMatAprob(Integer cantMatAprob) {
        this.cantMatAprob = cantMatAprob;
    }

    public LocalDate getFecIng() {
        
        return fecIng;
    }

    public void setFecIng(LocalDate fecIng) throws NullPointerException {
        this.fecIng = fecIng;
    }

    public String getFecIngStr() {
        if (fecIng == null) {
            return "01/01/0009"; // o "" o alguna cadena que indique que no hay fecha
        }
        return String.format("%02d/%02d/%04d", fecIng.getDayOfMonth(), fecIng.getMonthValue(), fecIng.getYear());
    }

    @Override
    public String toString() {
        return super.toString() + DELIM
                + String.format("%.2f%s%d%s%s%s%c", promedio, DELIM, cantMatAprob, DELIM, getFecIngStr(), DELIM, estado);
    }

    public static Alumno str2Alu(String[] camposAlu) throws PersonaException {
        int index = 0;
        Alumno alumno = new Alumno();

        alumno.setDni(Integer.valueOf(camposAlu[index++]));
        alumno.setNombre(camposAlu[index++]);
        alumno.setApellido(camposAlu[index++]);

        
        String[] fecNacStr = camposAlu[index++].split("/");
        int yearNac = Integer.valueOf(fecNacStr[2]);
        int monthNac = Integer.valueOf(fecNacStr[1]);
        int dayNac = Integer.valueOf(fecNacStr[0]);
        alumno.setFecNac(LocalDate.of(yearNac, monthNac, dayNac));

        
        String promedioStr = camposAlu[index++].replace(",", ".");
        alumno.setPromedio(Double.valueOf(promedioStr));

        
        alumno.setCantMatAprob(Integer.valueOf(camposAlu[index++]));

        
        String[] fecIngStr = camposAlu[index++].split("/");
        int yearIng = Integer.valueOf(fecIngStr[2]);
        int monthIng = Integer.valueOf(fecIngStr[1]);
        int dayIng = Integer.valueOf(fecIngStr[0]);
        alumno.setFecIng(LocalDate.of(yearIng, monthIng, dayIng));

        
        alumno.setEstado(camposAlu[index++].charAt(0));

        return alumno;
    }

}
