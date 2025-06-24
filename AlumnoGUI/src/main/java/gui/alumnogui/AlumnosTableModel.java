/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.alumnogui;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import persona.Alumno;

/**
 *
 * @author g.guzman
 */
public class AlumnosTableModel extends AbstractTableModel {

    private static final int DNI_COL = 0;
    private static final int NAME_COL = 1;
    private static final int FEC_NAC_COL = 3;
    private static final int ESTADO_COL = 4;
    private static final int LAST_NAME_COL = 2;//es la segunda posicion del Table
    private static final int PROMEDIO = 5;
    private static final int CANT_MAT_APROB = 6;
    private static final int FEC_ING = 7;
    
    
    
    private static final String[] CABECERAS = {"DNI", "Nombre", "Apellido", "Fecha Nac.", "Estado", "Promedio", "Mat aprobadas", "Fecha Ingreso" };

    private List<Alumno> alumnos;

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public int getRowCount() {
        if (alumnos == null) {
            return 0;
        }
        return alumnos.size();
    }

    @Override
    public int getColumnCount() {

        return CABECERAS.length;
    }

    @Override
    public String getColumnName(int column) {
        return CABECERAS[column];
    }

    public void limpiar() {
        if (alumnos != null) {
            alumnos.clear();
        }
        fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int fila, int col) {//dni - nombre - apellido - fecNac - promedio - cantMatAprob - fecIng - estado
        Alumno alu = alumnos.get(fila);
        switch (col) {
            case DNI_COL:
                return alu.getDni();
            case NAME_COL:
                return alu.getNombre();
            case LAST_NAME_COL:
                return alu.getApellido();
            case FEC_NAC_COL:
                return alu.getFecNacStr();
            case ESTADO_COL:
                return alu.getEstado();
            case PROMEDIO:
                return alu.getPromedio(); 
            case CANT_MAT_APROB:
                return alu.getCantMatAprob();
            case FEC_ING:
                return alu.getFecIngStr();    
            default:
                break;
        }

        return null;
    }

}
