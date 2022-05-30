package Presentation.Objects.Transport;

import Globals.Enums.ShiftTypes;
import Globals.Enums.TransportStatus;
import Globals.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transport {
    private int transportID;
    private String startTime;
    private String endTime;
    private String driverID;
    private  int truckNumber;
    private  int truckWeight;

    public Transport(Domain.Service.Objects.Transport transport) {
        this.transportID = transport.getTransportID();
        this.startTime = transport.getStartTime();
        this.endTime = transport.getEndTime();
        this.driverID = transport.getDriverID();
        this.truckNumber = transport.getTruckNumber();
        this.truckWeight = transport.getTruckWeight();
    }

    public Transport(int transportID, String startTime, String endTime, String driverID, int truckNumber, int truckWeight) {
        this.transportID = transportID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.driverID = driverID;
        this.truckNumber = truckNumber;
        this.truckWeight = truckWeight;
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
        if(!startTime.equals(""))
        {
            System.out.println("Start Time: " + startTime);
        }
        else {
        System.out.println("Start Time: The transport has not left yet!");
    }
        if(!endTime.equals(""))
        {
            System.out.println("End Time: " + endTime);
        }
        else {
            System.out.println("End Time: The Transport has not been completed yet!");
        }
    }

}
