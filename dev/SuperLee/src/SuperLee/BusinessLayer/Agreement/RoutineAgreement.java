package SuperLee.BusinessLayer.Agreement;

import SuperLee.BusinessLayer.AgreementItem;

import java.util.*;

public class RoutineAgreement extends Agreement {

    private List<Integer> daysOfDelivery;

    // days should be in the format "x1 x2 x3 ...", xi in {1, 2, 3, 4, 5, 6, 7}
    // THE STRING MUST NOT BE EMPTY!
    public RoutineAgreement(String days){
        super();
        daysOfDelivery = daysStringToDay(days);
    }

    private List<Integer> daysStringToDay(String days){
        List<Integer> list = new LinkedList<>();
        int d = 0;

        for(int i=0; i<days.length(); i++){

            while(i<days.length() && days.charAt(i) == ' '){
                i++;
            }

            d = days.charAt(i)-'0';

            // if the given number is 0 or more than 7 we ignore it
            if(d>=1 && d<=7){
                list.add(d);
            }

            i++;
        }

        Collections.sort(list);

        return list;
    }

    @Override
    public boolean isTransporting() {
        return true;
    }

    @Override
    public int daysToDelivery() {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int closestDelivery = daysOfDelivery.get(0);

        for(Integer i : daysOfDelivery){
            if(i > currentDay){
                closestDelivery = i;
                break;
            }
        }

        if(closestDelivery <= currentDay){
            return 7-currentDay+closestDelivery; // 7 days in a week, minus the current day
        }
        else{
            return closestDelivery-currentDay+1; // subtract the current day from the closest delivery day and add one to count the current day
        }
    }

    public List<Integer> getDaysOfDelivery(){
        return new ArrayList<>(daysOfDelivery);
    }

    public void setDaysOfDelivery(String days) throws Exception {
        List<Integer> list = daysStringToDay(days);

        if(list.isEmpty()){
            throw new Exception("Given string does not contain days of the week!");
        }

        daysOfDelivery = list;
    }

    public void addDaysOfDelivery(String days) throws Exception {
        List<Integer> list = daysStringToDay(days);

        if(list.isEmpty()){
            throw new Exception("Given string does not contain days of the week!");
        }

        for(Integer i : list){
            if(!daysOfDelivery.contains(i)){
                daysOfDelivery.add(i);
            }
        }

        Collections.sort(daysOfDelivery);
    }

    public void removeDayOfDelivery(Integer day){
        daysOfDelivery.remove(day); // Supposed to remove the element day and not the index day
    }
}
