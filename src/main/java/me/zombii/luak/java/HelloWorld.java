package me.zombii.luak.java;

import java.util.Map;

public class HelloWorld {

    public static void im_a_function_no_args_no_return() {
        System.out.println("Hello");
    }

    public static void im_a_function_one_arg_no_return(String thing) {
        System.out.println(thing);
    }

    public static void im_a_function_two_arg_no_return(String thing, Integer a) {
        for (int i = 0; i < a; i++) {
            System.out.println(thing);
        }
    }

    public static void im_a_function_vararg_no_return(Map<Double, String> things) {
        System.out.println(things);
        for (Map.Entry<Double, String> objectObjectEntry : things.entrySet()) {
            System.out.println(objectObjectEntry.getKey() + " " + objectObjectEntry.getValue());
        }
    }

}
