package com.solvd.laba.lab2.reflection;

import com.solvd.laba.lab2.Account;
import com.solvd.laba.lab2.CreditCard;
import com.solvd.laba.lab2.Customer;
import com.solvd.laba.lab2.enums.AccountType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class Reflection {
    //LOGGER
    private static final Logger LOGGER = LogManager.getLogger(Reflection.class);

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //getting class
        Class<?> c = CreditCard.class;

        /*Reflection Fields*/
        LOGGER.info("------Fields------");
        //getting fields
        Field[] fields = c.getDeclaredFields();
        Stream.of(fields)
                .forEach(field -> {
                    System.out.format("Field name: %-20s   Modifier: %-22s Field type: %s%n",
                            field.getName(), Modifier.toString(field.getModifiers()), field.getType().getName());
                });

        /*Reflection methods*/
        LOGGER.info("\n-----Constructors-----");
        //getting constructor
        Constructor<?>[] constructors = c.getConstructors();
        Stream.of(constructors)
                .forEach(constructor -> {
                    LOGGER.info("Constructor name: " + constructor.getName());
                    LOGGER.info("Constructor modifier: " + Modifier.toString(constructor.getModifiers()));
                    LOGGER.info("Constructor return type: " + Arrays.toString(constructor.getTypeParameters()));
                    LOGGER.info("Constructor parameter type:  " + Arrays.toString(constructor.getParameterTypes()));
                });

        /*Reflection methods*/
        System.out.println("\n-----Methods-----");
        //getting methods
        Method[] methods = c.getDeclaredMethods();
        Stream.of(methods)
                .forEach(method -> {
                    LOGGER.info("Method name: " + method.getName());
                    LOGGER.info("Method modifier: " + Modifier.toString(method.getModifiers()));
                    LOGGER.info("Method return type: " + method.getReturnType());
                    LOGGER.info("Method parameter type:  " + Arrays.toString(method.getParameterTypes()) + "\n");
                });

        /*Create object and call method using reflection*/
        LOGGER.info("-----Create Instance-----");
        Class<?> accountClass = Account.class;
        Constructor<?> constructor = accountClass.getConstructor(Customer.class, double.class);

        //initialize constructor using reflection
        Account account = (Account) constructor.newInstance(new Customer("Skirmish", "01-17-1981", 600), 3000);
        
        //Invoke method using reflection
        Method accMethod1 = accountClass.getMethod("getBalance");
        LOGGER.info(accMethod1.invoke(account));

        Method accMethod2 = accountClass.getMethod("setAccountType", AccountType.class);
        accMethod2.invoke(account, AccountType.CHECKING);

        Method accMethod3 = accountClass.getMethod("deposit", double.class);
        LOGGER.info(accMethod3.invoke(account, 1000));

        LOGGER.info(accMethod1.invoke(account));
        System.out.println(account);
    }
}

