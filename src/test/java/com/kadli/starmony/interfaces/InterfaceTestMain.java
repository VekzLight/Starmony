package com.kadli.starmony.interfaces;

import com.kadli.starmony.StarmonyApplication;
import org.aspectj.util.Reflection;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.ServiceLoader;

@SpringBootTest(classes = StarmonyApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InterfaceTestMain {

    @Test
    public void testInterfaceImp(){
        InterfaceTest1 interfaceTest1 = new InterfaceTest1Imp();
        System.out.println(interfaceTest1.getHola("jose"));

        Inter
    }

}
