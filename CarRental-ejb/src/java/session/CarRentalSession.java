package session;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import rental.CarRentalCompany;
import rental.CarType;
import rental.Quote;
import rental.RentalStore;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Stateful
public class CarRentalSession implements CarRentalSessionRemote {
    
    private Set<Quote> quotes;
    
    @Override
    public Set<String> getAllRentalCompanies() {
        return new HashSet<String>(RentalStore.getRentals().keySet());
    }
    
    @PostConstruct
    private void createEmptyQuoteList() {
        this.quotes = new HashSet<Quote>();
    }
    
    @Override
    public void createQuote(ReservationConstraints constraints, String guest, String companyName) throws ReservationException {
        CarRentalCompany company = RentalStore.getRentals().get(companyName);
        CarType carType = company.getType(constraints.getCarType());
        Quote newQuote = company.createQuote(constraints, guest);
        this.quotes.add(newQuote);
        //Quote newQuote = new Quote(guest, constraints.getStartDate(), constraints.getEndDate(), companyName, constraints.getCarType(), carType.getRentalPricePerDay());
    }
    
    @Override
    public Set<Quote> getCurrentQuotes() {
        return this.quotes;
    }
    
    @Override
    public void confirmQuotes(String clientName) throws ReservationException {
        Set<Reservation> newReservations = new HashSet<Reservation>();
        try {
            for (Quote quote : this.quotes) {
                CarRentalCompany company = RentalStore.getRentals().get(quote.getRentalCompany());
                if (clientName.equals(quote.getCarRenter())) {
                    newReservations.add(company.confirmQuote(quote));
                }
            }
        } catch (ReservationException e) {
            for (Reservation reservation : newReservations) {
                CarRentalCompany company = RentalStore.getRentals().get(reservation.getRentalCompany());
                company.cancelReservation(reservation);
            }
            throw e;
        }
    }
    
    @Override
    public Set<CarType> getAvailableCarTypes(Date start, Date end) {
        Set<CarType> availableCarTypes = new HashSet<CarType>();
        for (CarRentalCompany company : RentalStore.getRentals().values()) {
            availableCarTypes.addAll(company.getAvailableCarTypes(start, end));
        }
        return availableCarTypes;
    }
}
