package stu.napls.nabootauth;

import org.junit.jupiter.api.Test;
import stu.napls.nabootauth.core.dictionary.StatusCode;
import stu.napls.nabootauth.core.exception.SystemException;

import java.util.Calendar;
import java.util.Date;

public class SimpleTest {

    @Test
    public void test() {
        int a = StatusCode.Identity.NORMAL.getValue();
        System.out.println(a);
    }
}
