package client;

import java.util.Date;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import rental.CarType;
import rental.ReservationConstraints;
import session.CarRentalSessionRemote;
import session.ManagerSessionRemote;

public class Main extends AbstractScriptedSimpleTripTest<CarRentalSessionRemote, ManagerSessionRemote> {
    
    @EJB
    static CarRentalSessionRemote session;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println("found rental companies: "+session.getAllRentalCompanies());
        Main client = new Main("simpleTrips");
        client.run();
    }

    public Main(String scriptFile) throws Exception {
        super(scriptFile);
    }

    @Override
    protected CarRentalSessionRemote getNewReservationSession(String name) throws Exception {
        InitialContext context = new InitialContext();
        return (CarRentalSessionRemote) context.lookup(CarRentalSessionRemote.class.getName());
    }

    @Override
    protected ManagerSessionRemote getNewManagerSession(String name, String carRentalName) throws Exception {
        InitialContext context = new InitialContext();
        return (ManagerSessionRemote) context.lookup(ManagerSessionRemote.class.getName());
    }

    @Override
    protected void checkForAvailableCarTypes(CarRentalSessionRemote session, Date start, Date end) throws Exception {
        for (CarType carType : session.getAvailableCarTypes(start, end))
            System.out.println(carType.getName());       
    }

    @Override
    protected void addQuoteToSession(CarRentalSessionRemote session, String name, Date start, Date end, String carType, String carRentalName) throws Exception {
        ReservationConstraints constraints = new ReservationConstraints(start, end, carType);
        session.createQuote(constraints, name, carRentalName);
    }

    @Override
    protected void confirmQuotes(CarRentalSessionRemote session, String name) throws Exception {
        session.confirmQuotes(name);
    }

    @Override
    protected int getNumberOfReservationsBy(ManagerSessionRemote ms, String clientName) throws Exception {
        return ms.getNbOfReservationsOfClient(clientName);
    }

    @Override
    protected int getNumberOfReservationsForCarType(ManagerSessionRemote ms, String carRentalName, String carType) throws Exception {
        return ms.getNbOfReservationsOfCarType(carRentalName, carType);
    }
}
