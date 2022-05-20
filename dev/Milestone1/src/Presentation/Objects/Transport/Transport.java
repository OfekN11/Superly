package Presentation.Objects.Transport;

import Globals.Enums.ShiftTypes;
import Globals.Enums.TransportStatus;
import Globals.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transport {
    private int transportID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String driverID;
    private  int truckNumber;
    private  int truckWeight;
    private TransportStatus status;
    private Pair<LocalDate, ShiftTypes> shift;

    public Transport(int transportID, LocalDateTime startTime, LocalDateTime endTime, String driverID, int truckNumber, int truckWeight, Pair<LocalDate, ShiftTypes> shift) {
        this.transportID = transportID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.driverID = driverID;
        this.truckNumber = truckNumber;
        this.truckWeight = truckWeight;
        //this.shift = shift;
    }

    public void display()
    {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Transport ID: " + transportID);
        printDates();
        printCarrier();
        printTruckDetails();
    }
    private void printCarrier()
    {
        if(driverID.equals(""))
        {
            System.out.println("Carrier ID: The carrier has not been assigned yet!");
        }
        else
        {
            System.out.println("Carrier ID: " + driverID);
        }

    }

    private void printTruckDetails()
    {
        if(truckNumber == -1)
        {
            System.out.println("Truck License Number:  The truck has not been installed yet!");
        }
        else
        {
            System.out.println("Truck License Number: " + truckNumber +
                    "\nTruck Weight: " + truckWeight);
        }

    }

    private void printDates()
    {
        if(startTime == null)
        {
            System.out.println("Start Time: " + startTime);
        }
        else {
        System.out.println("Start Time: The transport has not left yet!");
    }
        if(endTime == null)
        {
            System.out.println("End Time: " + endTime);
        }
        else {
            System.out.println("End Time: The Transport has not been completed yet!");
        }
    }

}
