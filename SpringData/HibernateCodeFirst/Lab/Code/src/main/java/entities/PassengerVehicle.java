package entities;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PassengerVehicle extends Vehicle {
    private int noOfPassengers;

    public PassengerVehicle() { }

    public PassengerVehicle(String type, int noOfPassengers){
        super(type);
        this.noOfPassengers = noOfPassengers;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }
}
