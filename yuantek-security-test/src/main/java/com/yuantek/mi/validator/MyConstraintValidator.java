package com.yuantek.mi.validator;

import com.yuantek.mi.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// 此处不用显示的声明从Spring的Bean
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {
    @Autowired
    private HelloService helloService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("my Validator init");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println(value);
        System.out.println(helloService.get());
        return false;
    }
}
