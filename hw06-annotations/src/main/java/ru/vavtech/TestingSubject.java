package ru.vavtech;

import lombok.extern.slf4j.Slf4j;
import ru.vavtech.annotations.After;
import ru.vavtech.annotations.Before;
import ru.vavtech.annotations.Test;

@Slf4j
public class TestingSubject {
    @Before
    public void beforeTest() {
        log.info("Before");
    }

    @Test
    public void test1() {
        log.info("Test1");
    }

    @Test
    public void test2() {
        log.info("Test2");
    }

    @Test
    public void test3() {
        log.info("Test3");
        int i = 12 / 0;
    }

    @After
    public void afterTest() {
        log.info("After");
    }

}
