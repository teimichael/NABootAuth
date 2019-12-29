package stu.napls.nabootauth;

import org.junit.jupiter.api.Test;
import stu.napls.nabootauth.core.exception.SystemException;

import java.util.Calendar;
import java.util.Date;

public class SimpleTest {

    @Test
    public void test() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        System.out.println(date);
        System.out.println(calendar.getTime());
    }
}
