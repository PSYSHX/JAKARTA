package com.javaee.exercises.validations;

import java.util.Date;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tester {

    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    @Test
    public void createUser() {
        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(3, violations.size());
    }

    @Test
    public void createUserWithSetterCalls() {
        User user = new User();
        user.setFirstName("Howard Phillips");
        user.setLastName("L");
        user.setBirthday(new Date(-2504563200000L));
        user.setCustomerNumber("//1XX");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(1, violations.size());

        ConstraintViolation<User> cv = violations.iterator().next();
        Assert.assertTrue(cv.getMessage().contains("\"^[a-zA-Z]{2}-\\d+$\""));    }

    @Test
    public void createUserWithoutViolations() {
        User user = new User();
        user.setFirstName("Howard Phillips");
        user.setLastName("Lovecraft");
        user.setBirthday(new Date(-2504563200000L));
        user.setCustomerNumber("CL-1234");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assert.assertEquals(0, violations.size());
    }


}
