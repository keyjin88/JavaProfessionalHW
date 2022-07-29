package ru.vavtech;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
public class Summator {
    private final List<Data> listValues = new ArrayList<>();
    private int counter = 0;
    @Getter
    private int sum = 0;
    @Getter
    private int prevValue = 0;
    @Getter
    private int prevPrevValue = 0;
    @Getter
    private int sumLastThreeValues = 0;
    @Getter
    private int someValue = 0;

    //!!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        counter++;
        if (counter == 6_600_000) {
            counter = 0;
        }
        var currentValue = data.value();
        sum += currentValue;

        sumLastThreeValues = currentValue + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = currentValue;

        for (var idx = 0; idx < 3; idx++) {
            someValue += (sumLastThreeValues * sumLastThreeValues / (currentValue + 1) - sum);
            someValue = Math.abs(someValue) + currentValue;
        }
    }
}
