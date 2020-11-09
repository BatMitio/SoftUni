package entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "car")
public class Car extends PassengerVehicle {
    private final static String type = "CAR";

    public Car(int noOfPassengers) {
        super(type, noOfPassengers);
    }


    public Car() {

    }
}
