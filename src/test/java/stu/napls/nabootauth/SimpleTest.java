package stu.napls.nabootauth;

import org.junit.jupiter.api.Test;
import stu.napls.nabootauth.core.dictionary.StatusConst;

public class SimpleTest {

    @Test
    public void test() {
        int a = StatusConst.Identity.NORMAL.getValue();
        System.out.println(a);
    }
}
