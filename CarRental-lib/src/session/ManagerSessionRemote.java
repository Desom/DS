/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.Set;
import javax.ejb.Remote;

/**
 *
 * @author Gertjan
 */
@Remote
public interface ManagerSessionRemote {
    
    Set<String> getCarTypes(String companyName);
    
    int getNbOfReservationsOfCarType(String companyName, String carTypeName);
    
    int getNbOfReservationsOfClient(String client);
}
