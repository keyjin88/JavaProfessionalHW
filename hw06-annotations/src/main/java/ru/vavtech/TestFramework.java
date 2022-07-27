package ru.vavtech;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.vavtech.annotations.After;
import ru.vavtech.annotations.Before;
import ru.vavtech.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class TestFramework {
    public void run(String className) {
        try {
            var testClass = Class.forName(className);

            var methodsAll = testClass.getDeclaredMethods();
            var beforeMethod = Arrays.stream(methodsAll)
                    .filter(method -> method.isAnnotationPresent(Before.class))
                    .findFirst()
                    .orElse(null);
            var afterMethod = Arrays.stream(methodsAll)
                    .filter(method -> method.isAnnotationPresent(After.class))
                    .findFirst()
                    .orElse(null);
            var testMethods = Arrays.stream(methodsAll)
                    .filter(method -> method.isAnnotationPresent(Test.class))
                    .collect(Collectors.toList());

            run(testClass, testMethods, beforeMethod, afterMethod);
        } catch (Exception e) {
            log.error("Error during test process.", e);
        }
    }

    private void run(Class<?> testClass, List<Method> testMethods, Method beforeMethod, Method afterMethod) throws Exception {
        int errorCounter = 0;
        for (Method testMethod : testMethods) {
            var testClassInstance = testClass.getConstructor().newInstance();
            var result = run(testClassInstance, testMethod, beforeMethod, afterMethod);
            if (!result) errorCounter++;
        }
        log.info("Statistics: {} test completed, {} test failed", testMethods.size(), errorCounter);
    }

    private boolean run(Object testClassInstance, Method testMethod, Method beforeMethod, Method afterMethod) throws Exception {
        boolean result;
        if (beforeMethod != null) {
            beforeMethod.invoke(testClassInstance);
        }
        try {
            testMethod.invoke(testClassInstance);
            log.info("Test {} passed successfully", testMethod.getName());
            result = true;
        } catch (Exception e) {
            result = false;
            log.error("Test {} failed", testMethod.getName());
        }
        if (afterMethod != null) {
            afterMethod.invoke(testClassInstance);
        }
        return result;
    }


}
