/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author g.guzman
 */
public class NombreVacioException extends PersonaException {

    public NombreVacioException() {
        super();
    }

    public NombreVacioException(String message) {
        super(message);
    }
    
}
