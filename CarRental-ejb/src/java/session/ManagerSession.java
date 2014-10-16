/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.util.HashSet;
import java.util.Set;
import javax.ejb.Stateless;
import rental.CarRentalCompany;
import rental.CarType;
import rental.RentalStore;

/**
 *
 * @author Gertjan
 */
@Stateless
public class ManagerSession implements ManagerSessionRemote {

    @Override
    public Set<String> getCarTypes(String companyName) {
        Set<String> carTypes = new HashSet<String>();
        CarRentalCompany company = RentalStore.getRentals().get(companyName);
        for(CarType carType : company.getAllTypes())
            carTypes.add(carType.getName());
        return carTypes;
    }
    
    @Override
    public int getNbOfReservationsOfCarType(String companyName, String carTypeName) {
        CarRentalCompany company = RentalStore.getRentals().get(companyName);
        CarType carType = company.getType(carTypeName);
        return company.getNbOfReservationsOfCarType(carType);
    }
    
    @Override
    public int getNbOfReservationsOfClient(String client) {
        int nbOfReservations = 0;
        for (CarRentalCompany company : RentalStore.getRentals().values()) {
            nbOfReservations += company.getNbOfReservationsOfClient(client);
        }
        return nbOfReservations;
    }
}
