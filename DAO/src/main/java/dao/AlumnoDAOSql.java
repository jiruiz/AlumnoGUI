/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exceptions.PersonaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persona.Alumno;
import utils.DateUtils;

/**
 *
 * @author g.guzman
 */
public class AlumnoDAOSql extends DAO<Alumno, Integer> {

    private Connection conn;
    private PreparedStatement prepareStatementCreate;
    private PreparedStatement prepareStatementRead;
    private PreparedStatement prepareStatementAll;

    AlumnoDAOSql(String url, String user, String pwd) throws DAOException {
        try {
            conn = DriverManager.getConnection(url, user, pwd);

            String sqlCreate = "INSERT INTO alumnos\n"
                    + "(DNI,\n"
                    + "NOMBRE,\n"
                    + "APELLIDO,\n"
                    + "FEC_NAC,\n"
                    + "PROMEDIO,\n"
                    + "CANTMATAPROB,\n"
                    + "FECING,\n"
                    + "ESTADO)\n" 
                    + "VALUES\n"
                    + "(?, ?, ?, ?, ?, ?, ?, ?);"; 
            prepareStatementCreate = conn.prepareStatement(sqlCreate);

            String sqlRead = "SELECT * FROM alumnos WHERE DNI = ?";
            prepareStatementRead = conn.prepareStatement(sqlRead);

            String sqlAll = "SELECT * FROM alumnos";
            prepareStatementAll = conn.prepareStatement(sqlAll);
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSql.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error de SQL (" + ex.getMessage() + ")");
        }
    }

    @Override
    public void create(Alumno alu) throws DAOException {
        try {
            int index = 0;
            prepareStatementCreate.setInt(++index, alu.getDni());
            prepareStatementCreate.setString(++index, alu.getNombre());
            prepareStatementCreate.setString(++index, alu.getApellido());
            prepareStatementCreate.setDate(++index, DateUtils.localeDate2SqlDate(alu.getFecNac()));
            prepareStatementCreate.setDouble(++index, alu.getPromedio()); // agregado
            prepareStatementCreate.setInt(++index, alu.getCantMatAprob()); // agregado
            prepareStatementCreate.setDate(++index, DateUtils.localeDate2SqlDate(alu.getFecIng())); // agregado
            prepareStatementCreate.setString(++index, String.valueOf(alu.getEstado()));

            prepareStatementCreate.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSql.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error de SQL (" + ex.getMessage() + ")");
        }
    }

    @Override
    public Alumno read(Integer dni) throws DAOException {
        try {
            prepareStatementRead.setInt(1, dni);
            ResultSet rs = prepareStatementRead.executeQuery();
            if (rs.next()) {
                return buildAlumnoFromDB(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSql.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error de SQL (" + ex.getMessage() + ")");
        } catch (PersonaException ex) {
            Logger.getLogger(AlumnoDAOSql.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al setear datos del alumno (" + ex.getMessage() + ")");
        }

        return null;
    }

    private Alumno buildAlumnoFromDB(ResultSet rs) throws PersonaException, SQLException {
        Alumno alu = new Alumno();
        alu.setDni(rs.getInt("DNI"));
        alu.setNombre(rs.getString("NOMBRE"));
        alu.setApellido(rs.getString("APELLIDO"));
        alu.setFecNac(rs.getDate("FEC_NAC").toLocalDate());
        alu.setPromedio(rs.getDouble("PROMEDIO"));          // agrego promedio
        alu.setCantMatAprob(rs.getInt("CANTMATAPROB"));      // agrego cantidad de materias aprobadas
        alu.setFecIng(rs.getDate("FECING").toLocalDate());   // agrego fecha de ingreso
        alu.setEstado(rs.getString("Estado").charAt(0));

        return alu;
    }

    @Override
    public void update(Alumno alumno) throws DAOException {
        String sqlUpdate = "UPDATE alumnos SET "
                + "NOMBRE = ?, "
                + "APELLIDO = ?, "
                + "FEC_NAC = ?, "
                + "PROMEDIO = ?, "
                + "CANTMATAPROB = ?, "
                + "FECING = ?, "
                + "ESTADO = ? "
                + "WHERE DNI = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
            int index = 0;
            ps.setString(++index, alumno.getNombre());
            ps.setString(++index, alumno.getApellido());
            ps.setDate(++index, DateUtils.localeDate2SqlDate(alumno.getFecNac()));
            ps.setDouble(++index, alumno.getPromedio());
            ps.setInt(++index, alumno.getCantMatAprob());
            ps.setDate(++index, DateUtils.localeDate2SqlDate(alumno.getFecIng()));
            ps.setString(++index, "M");
            ps.setInt(++index, alumno.getDni());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("No se encontró el alumno con DNI: " + alumno.getDni());
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSql.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error de SQL (" + ex.getMessage() + ")");
        }
    }

    @Override
    public void delete(Integer id) throws DAOException {
        String sqlDelete = "UPDATE alumnos SET ESTADO = ? WHERE DNI = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
            ps.setString(1, "B");
            ps.setInt(2, id);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                throw new DAOException("No se encontró el alumno con DNI: " + id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDAOSql.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al realizar la baja lógica (" + ex.getMessage() + ")");
        }
    }

    @Override
    public boolean exist(Integer id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        // alu.setNombre(rs.getString("APELIIDO"));
    }

    @Override
    public List<Alumno> findAll(boolean includeDeleted) throws DAOException {
        List<Alumno> alumnos = new ArrayList<>();
        String sqlCkeckInludeDelete;

        if (includeDeleted) {
            sqlCkeckInludeDelete = "SELECT * FROM alumnos";
        } else {
            sqlCkeckInludeDelete = "SELECT * FROM alumnos WHERE ESTADO <> 'B'";
        }

        try (PreparedStatement ps = conn.prepareStatement(sqlCkeckInludeDelete)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                alumnos.add(buildAlumnoFromDB(rs));
            }
        } catch (SQLException | PersonaException ex) {
            Logger.getLogger(AlumnoDAOSql.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Error al obtener alumnos (" + ex.getMessage() + ")");
        }

        return alumnos;
    }

    @Override
    public void close() throws DAOException {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión a la base de datos cerrada correctamente.");
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDAOSql.class.getName()).log(Level.SEVERE, null, ex);
                throw new DAOException("Error al cerrar la conexión (" + ex.getMessage() + ")");
            }
        }
    }

}
