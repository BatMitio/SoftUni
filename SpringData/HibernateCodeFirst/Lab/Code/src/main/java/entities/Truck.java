package entities;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "truck")
public class Truck extends TransportationVehicle {
    private final static String type = "TRUCK";
    private int noOfContainers;

    public Truck(){

    }

    public Truck(int loadCapacity, int noOfContainers) {
        super(type, loadCapacity);
        this.noOfContainers = noOfContainers;
    }


}
