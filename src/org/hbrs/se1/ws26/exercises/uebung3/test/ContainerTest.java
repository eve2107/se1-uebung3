package org.hbrs.se1.ws26.exercises.uebung3.test;


import org.hbrs.se1.ws26.exercises.uebung3.ConcreteMember;
import org.hbrs.se1.ws26.exercises.uebung3.Container;
import org.hbrs.se1.ws26.exercises.uebung3.ContainerException;
import org.hbrs.se1.ws26.exercises.uebung3.Member;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyMongoDB;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContainerTest {
    private Container container;
    @BeforeEach
    void setUp() {
        container = Container.getInstance();
        container.deleteAllMembers();
    }

    @Test
    void testMongoDBNotImplementedSolution() {
        container.setStrategy(new PersistenceStrategyMongoDB<Member>());
        assertThrows(UnsupportedOperationException.class, () -> container.store());
    }

    @Test
    void testNoStrategeySet() {
        container.setStrategy(null);
        PersistenceException exception = assertThrows(PersistenceException.class, () -> {
            container.store();
        });
        assertEquals("Es ist keine Strategie gesetzt.", exception.getMessage());
    }

    @Test
    void testWrongLocationOfFile() {
        PersistenceStrategyStream<Member> streamStrategy = new PersistenceStrategyStream<>();
        streamStrategy.setLocation("/invalid/path/that/is/a/directory/");
        container.setStrategy(streamStrategy);

        assertThrows(PersistenceException.class, () -> container.store());
    }

    @Test
    void testStoreDeleteAndLoad() throws PersistenceException, ContainerException {
        container.deleteAllMembers();
        assertEquals(0, container.getCurrentList().size(), "Test 1: Liste muss nach deleteAllMembers() leer sein (Größe 0).");

        PersistenceStrategyStream<Member> strategy = new PersistenceStrategyStream<>();
        // Optional: Eine separate Datei für diesen Test setzen, damit die echten Daten nicht überschrieben werden
        strategy.setLocation("test-roundtrip.ser");
        container.setStrategy(strategy);

        Member testMember = new ConcreteMember(1);
        container.addMember(testMember);
        assertEquals(1, container.getCurrentList().size(), "Test 2: Liste muss nach Hinzufügen genau 1 Element enthalten.");

        container.store();
        assertEquals(1, container.getCurrentList().size(), "Test 3: Liste muss nach dem Speichern weiterhin 1 Element enthalten.");

        container.deleteAllMembers();
        assertEquals(0, container.getCurrentList().size(), "Test 4: Liste muss nach dem erneuten Löschen wieder leer sein (Größe 0).");


        container.load();
        assertEquals(1, container.getCurrentList().size(), "Test 5: Liste muss nach dem Laden wieder genau 1 Element aus der Datei enthalten.");
    }

}