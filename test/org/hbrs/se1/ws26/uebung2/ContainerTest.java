package org.hbrs.se1.ws26.uebung2;

import org.hbrs.se1.ws26.exercises.uebung3.Member;
import org.hbrs.se1.ws26.exercises.uebung3.ConcreteMember;
import org.hbrs.se1.ws26.exercises.uebung3.Container;
import org.hbrs.se1.ws26.exercises.uebung3.ContainerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    @Test
            void ContainerTest() {
    Container container = new Container();
    Member m1 = new ConcreteMember(1);
    Member m2 = new ConcreteMember(2);

    //1.test
    assertEquals(0,container.size(), "Container sollte leer sein");
    try

    {
        //2.test
        container.addMember(m1);
        assertEquals(1, container.size(), "Container sollte ein Element haben.");
        //3.test
        container.addMember(m2);
        assertEquals(2, container.size(), "Container sollte zwei Elemente haben.");
        //4.test
        assertThrows(ContainerException.class, () -> {
            container.addMember(m1);
        });
        //5.test
        assertEquals(2, container.size(), "Größe muss nach Exception bei 2 bleiben.");

        //6.test
        String res1 = container.deleteMember(1);
        assertEquals("Member mit der ID: " + m1.getID() + " wurde erfolgreich gelöscht.", res1);

        //7.test
        assertEquals(1, container.size(), "Container sollte nur noch 1 Element haben.");

        //8.test
        String resFehler = container.deleteMember(55);
        assertNotNull(resFehler, "Es sollte eine Fehlermeldung als String zurückkommen.");

        //9.test
        Member mMax = new ConcreteMember(2147483647);
        container.addMember(mMax);
        assertEquals(2, container.size(), "Container sollte wieder 2 Elemente haben.");

        //10.test
        container.deleteMember(2);
        container.deleteMember(2147483647);
        assertEquals(0, container.size(), "Container sollte am Ende wieder komplett leer sein.");
        container.dump();
    } catch(
    ContainerException e)

    {
        fail("Exception wurde geworfen, wo keine sein sollte.");
    }
}
}