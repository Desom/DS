package session;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import rental.CarType;
import rental.Quote;
import rental.ReservationConstraints;
import rental.ReservationException;

@Remote
public interface CarRentalSessionRemote {

    Set<String> getAllRentalCompanies();
    
    void createQuote(ReservationConstraints constraints, String guest, String companyName) throws ReservationException;
    
    Set<Quote> getCurrentQuotes();
    
    void confirmQuotes(String clientName) throws ReservationException;
    
    Set<CarType> getAvailableCarTypes(Date start, Date end);
}
