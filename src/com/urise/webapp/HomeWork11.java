package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;

public class HomeWork11 {
    public static void main(String[] args) {
        int[] storage = new int[]{1, 2, 3, 3, 2, 3};
        HomeWork11 hw = new HomeWork11();
        int result = hw.minValue(storage);
        System.out.println(result);

        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(7);
        list.add(11);
        list.add(24);
        list.add(33);
        list.add(58);
        list.add(94);
        list.add(96);

        List<Integer> resultList = hw.oddOrEven(list);
        System.out.println(resultList);
    }

    /**
     * Реализовать метод через стрим int minValue(int[] values).
     * Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число, составленное из этих уникальных цифр.
     * Не использовать преобразование в строку и обратно. Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89
     */

    private int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce((x, y) -> x * 10 + y).getAsInt();
    }

    /**
     * Реализовать метод List<Integer> oddOrEven(List<Integer> integers) если сумма всех чисел нечетная - удалить все нечетные,
     * если четная - удалить все четные.
     * Сложность алгоритма должна быть O(N). Optional - решение в один стрим.
     */

    private List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> maps = integers.stream().collect(Collectors.partitioningBy(x -> x % 2 != 0));
        return maps.get(maps.get(true).size() % 2 == 0);
    }
}
