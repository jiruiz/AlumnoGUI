/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exceptions.PersonaException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persona.Alumno;
import persona.Persona;

/**
 *
 * @author g.guzman
 */
public class AlumnoDAOTxt extends DAO<Alumno, Integer> {

    private RandomAccessFile raf;

    AlumnoDAOTxt(String fullpath) throws DAOException {
        try {
            raf = new RandomAccessFile(fullpath, "rws");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error de E/S (" + ex.getMessage() + ")");
        }
    }

    @Override
    public void create(Alumno alu) throws DAOException {
        if (exist(alu.getDni())) {
            throw new DAOException("El alumno con DNI " + alu.getDni() + " ya existe");
        }

        try {
            raf.seek(raf.length()); // Se posicion al final del archivo
            raf.writeBytes(alu.toString() + System.lineSeparator());

        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al intentar crear el alumno (" + ex.getMessage() + ")");

        }
    }

    @Override
    public Alumno read(Integer dni) throws DAOException {
        try {
            raf.seek(0);
            String lineaAlu;
            String[] camposAlu;
            while ((lineaAlu = raf.readLine()) != null) {
                camposAlu = lineaAlu.split(Persona.DELIM);
                if (Integer.valueOf(camposAlu[0]).equals(dni)) {
                    return Alumno.str2Alu(camposAlu);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error de E/S (" + ex.getMessage() + ")");
        } catch (PersonaException ex) {
            Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al instanciar el Alumno (" + ex.getMessage() + ")");
        }

        return null;
    }

    @Override
    public void update(Alumno alu) throws DAOException {
        try {
            raf.seek(0);
            String lineaAlu;
            String[] camposAlu;
            long posInicio;

            while ((lineaAlu = raf.readLine()) != null) {
                posInicio = raf.getFilePointer() - (lineaAlu.length() + System.lineSeparator().length());
                camposAlu = lineaAlu.split(Persona.DELIM);

                if (Integer.valueOf(camposAlu[0].trim()).equals(alu.getDni())) {
                    alu.setEstado('M');
                    raf.seek(posInicio);
                    raf.writeBytes(alu.toString());

                    return;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error de E/S (" + ex.getMessage() + ")");
        } catch (PersonaException ex) {
            Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(Integer dni) throws DAOException {
        try {
            raf.seek(0);
            String lineaAlu;
            long posInicio;

            while ((lineaAlu = raf.readLine()) != null) {
                posInicio = raf.getFilePointer() - (lineaAlu.length() + System.lineSeparator().length());

                String[] camposAlu = lineaAlu.split(Persona.DELIM);
                if (Integer.valueOf(camposAlu[0].trim()).equals(dni)) {
                    // Posicionar al comienzo de la línea
                    raf.seek(posInicio);

                    // Cortamos los últimos 2 caracteres (el estado y fin de línea)
                    String nuevaLinea = lineaAlu.substring(0, lineaAlu.length() - 1) + "B";

                    // Completar con newline
                    raf.writeBytes(nuevaLinea + System.lineSeparator());
                    return;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error de E/S (" + ex.getMessage() + ")");
        }
    }

    @Override
    public boolean exist(Integer dni) throws DAOException {

        try {
            raf.seek(0);
            String lineaAlu;
            String[] camposAlu;
            while ((lineaAlu = raf.readLine()) != null) {
                camposAlu = lineaAlu.split(Persona.DELIM);
                if (Integer.valueOf(camposAlu[0]).equals(dni)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error de E/S (" + ex.getMessage() + ")");
        }

        return false;
    }

    @Override
    public List<Alumno> findAll(boolean includeDeleted) throws DAOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Alumno> alumnos = new ArrayList<>();
        try {
            raf.seek(0);
            String lineaAlu;
            String[] camposAlu;
            while ((lineaAlu = raf.readLine()) != null) {
                camposAlu = lineaAlu.split(Persona.DELIM);

                // aca hacemos una validacion porque cuando inicie la fila tenia menos campos
                if (camposAlu.length < 4) {
                    System.err.println("Línea inválida o incompleta: " + lineaAlu);
                    continue;
                }

                
                if (!includeDeleted && camposAlu[7].trim().equalsIgnoreCase("B")) {
                    continue; 
                }

                Alumno alu = new Alumno();
                alu.setDni(Integer.parseInt(camposAlu[0].trim()));
                alu.setNombre(camposAlu[1].trim());
                alu.setApellido(camposAlu[2].trim()); // ACÁ AGREGÚE AL APELLIDO 
                alu.setPromedio(Double.parseDouble(camposAlu[4].trim().replace(",", ".")));
                alu.setCantMatAprob(Integer.parseInt(camposAlu[5].trim().replace(",", ".")));
                alu.setFecIng(LocalDate.parse(camposAlu[6].trim(), formatter));
                
                
                
                if (!camposAlu[3].trim().isEmpty()) {
                    alu.setFecNac(LocalDate.parse(camposAlu[3].trim(), formatter));
                }

                // VALIDAMOS EL CAMPO ESTADO: 
                if (camposAlu.length >= 8 && !camposAlu[7].trim().isEmpty()) {
                    alu.setEstado(camposAlu[7].trim().charAt(0));
                } else {
                    alu.setEstado('N'); 
                }

                alumnos.add(alu);
            }

        } catch (IOException | PersonaException ex) {
            Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alumnos;
    }

    @Override
    public void close() throws DAOException {
        if (raf != null) {
            try {
                raf.close(); // Cierra el archivo .txt que abrimos con RandomAccessFile
                System.out.println("RandomAccessFile cerrado correctamente");
            } catch (IOException ex) {
                Logger.getLogger(AlumnoDAOTxt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
