package ru.vavtech;


import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> map = new TreeMap<>((Comparator.comparingLong(Customer::getScores)));

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return copyEntry(map.firstEntry()); // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copyEntry(map.higherEntry(customer)); // это "заглушка, чтобы скомилировать"
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> copyEntry(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        }
        Customer key = new Customer(
                entry.getKey().getId(),
                entry.getKey().getName(),
                entry.getKey().getScores()
        );
        String value = entry.getValue();
        return Map.entry(key, value);
    }
}
