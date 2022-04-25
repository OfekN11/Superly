package Globals.util;

import Domain.Service.Objects.Employee;

import java.util.Comparator;

public class EmployeeComparator implements Comparator<Employee> {
    private JobTitleComparator jobTitleComparator = new JobTitleComparator();
    @Override
    public int compare(Employee o1, Employee o2) {
        return jobTitleComparator.compare(o1.getType(), o2.getType());
    }
}
