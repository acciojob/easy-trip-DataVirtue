package com.driver.Services;

import com.driver.Respositories.AirportRepository;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class AirportService {

    @Autowired
    AirportRepository airportRepository = new AirportRepository();

    public String addAirport(Airport airport) {

       return  airportRepository.addAirport(airport);


    }

    public String getLargestAirportName() {

       return airportRepository.getLargestAirportName();
    }

    public String addFlight(Flight flight) {
        return airportRepository.addFlight(flight);

    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {

        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {

        return airportRepository.bookATicket(flightId,passengerId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {

        return airportRepository.cancelATicket(flightId,passengerId);
    }

    public String getAirportNameFromFlightId(Integer flightId) {

       return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {

        Set<Integer> passengers = airportRepository.getFlightPassengers(flightId);

        if(passengers == null || passengers.size()==0)
            return 0;

        int n = passengers.size()-1; // pricing changes from second passenger

        return  3000 + ((n*(n+1))/2) * 50; // sum of natural numbers * size to account for adjustable pricing
    }

    public String addPassenger(Passenger passenger) {

        return  airportRepository.addPassenger(passenger);

    }

    public int calculateFlightFare(Integer flightId) {
        Set<Integer> passengers = airportRepository.getFlightPassengers(flightId);

        if(passengers == null || passengers.size()==0)
            return 3000;


        return 3000  + passengers.size()*50;
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {

        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {

        return airportRepository.getNumberOfPeopleOn(date,airportName);
    }
}
