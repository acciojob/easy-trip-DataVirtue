package com.driver.Respositories;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {
    Map<Integer, Passenger> passengerMap;
    Map<String, Airport> airportMap;
    Map<Integer, Flight> flightMap;


    Map<Integer,Integer> passengerFlightMap;

    Map<Integer, Set<Integer>> flightPassengerMap; // flight id and list of passengers


    public AirportRepository() {
        this.passengerMap = new HashMap<>();
        this.airportMap = new HashMap<>();
        this.flightMap = new HashMap<>();
        this.passengerFlightMap = new HashMap<>();
        this.flightPassengerMap = new HashMap<>();

    }

    public String addAirport(Airport airport) {

        airportMap.put(airport.getAirportName(),airport);

        return "SUCCESS";
    }

    public  String getLargestAirportName() {

        Airport dummyport = new Airport();
        dummyport.setNoOfTerminals(0);
        Airport max = dummyport;

        for(Airport airport: airportMap.values()){
            if(max.getNoOfTerminals()<airport.getNoOfTerminals()){
                max = airport;
            }else if(max.getNoOfTerminals()==airport.getNoOfTerminals()){
                if(max.getAirportName().compareTo(airport.getAirportName())==1){
                    max = airport;
                }
            }
        }
        return max.getAirportName();


    }

    public String addFlight(Flight flight) {

        flightMap.put(flight.getFlightId(),flight);

        return "SUCCESS";
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {

        double min = Double.MAX_VALUE;


        for (Flight flight: flightMap.values()){
            if(String.valueOf(flight.getToCity()).equals(String.valueOf(toCity)) &&
                    String.valueOf(flight.getFromCity()).equals(String.valueOf(fromCity))){

                if(min>flight.getDuration()){
                    min = flight.getDuration();
                }

            }
        }
        return min;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {

        if(passengerFlightMap.containsKey(passengerId)){
            return "FAILURE"; // passenger already booked a flight
        }
        if (flightMap.get(flightId).getMaxCapacity()==flightPassengerMap.get(flightId).size()){
            return "FAILURE"; // capacity full
        }
        Set<Integer> passengerList = flightPassengerMap.getOrDefault(flightId,new HashSet<>());
        passengerList.add(passengerId);

        flightPassengerMap.put(flightId,passengerList);

        passengerFlightMap.put(passengerId,flightId);


        return "SUCCESS";
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        if(!passengerFlightMap.containsKey(passengerId)){
            return "FAILURE"; // No flight booked
        }
        if(!flightPassengerMap.containsKey(flightId) || flightPassengerMap.get(flightId)==null || !flightPassengerMap.get(flightId).contains(passengerId)){
            return "FAILURE"; //invalid flight id or wrong flight id
        }

        passengerFlightMap.remove(passengerId);

        flightPassengerMap.get(flightId).remove(passengerId);

        return "SUCCESS";
    }

    public String getAirportNameFromFlightId(Integer flightId) {

        if(!flightMap.containsKey(flightId)){
            return "FAILURE";
        }

        for(Airport airport: airportMap.values()){
            if(String.valueOf(airport.getCity()).equals(String.valueOf(flightMap.get(flightId).getFromCity())))
                return airport.getAirportName();
        }
        return "FAILURE";

    }

//    public int calculateRevenueOfAFlight(Integer flightId) {
//        if(flightPassengerMap.get(flightId)==null)
//            return 0;
//
//        return  flightPassengerMap.get(flightId).size();
//    }

    public String addPassenger(Passenger passenger) {

        passengerMap.put(passenger.getPassengerId(), passenger);

        return "SUCCESS";
    }

    public Set<Integer> getFlightPassengers(Integer flightId) {
       return flightPassengerMap.get(flightId);
    }
}