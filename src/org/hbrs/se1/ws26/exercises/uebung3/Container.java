package org.hbrs.se1.ws26.exercises.uebung3;

import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.List;

public class Container {
    private List<Member> membersListe;
    private static Container instance;
    private PersistenceStrategy<Member> strategy;
    private Container() {

        membersListe = new ArrayList<>();
    }
    public static Container getInstance() {
        if (instance == null) {
            instance = new Container();
        }
        return instance;
    }
    public void addMember(Member member) throws ContainerException {
        for (Member m : membersListe) {
            if (m.getID().equals(member.getID())) {
                throw new ContainerException("Das Member-Objekt mit der ID " + member.getID() + " ist bereits vorhanden!");
            }

        }
        membersListe.add(member);
    }

    public String deleteMember(int ID) {
        for (Member m : membersListe) {
            if (m.getID().equals(ID)) {
                membersListe.remove(m);
                return "Member mit der ID: " + ID + " wurde erfolgreich gelöscht.";
            }

        }
        return "Fehler: Kein Member mit der ID " + ID + " vorhanden.";
    }

        public int size () {
            return membersListe.size();
        }

    public void setStrategy(PersistenceStrategy<Member> strategy) {
        this.strategy = strategy;
    }

    public void store() throws PersistenceException {
        if (strategy == null) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"Es ist keine Strategie gesetzt.");
        }
        strategy.save(membersListe);
    }

    public void load() throws PersistenceException {
        if (strategy == null) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"Es ist keine Strategie gesetzt.");
        }
        List<Member> loadedMembers = strategy.load();
        if (loadedMembers != null) {
            this.membersListe = loadedMembers;
        }
    }
    public List<Member> getCurrentList() {
        return this.membersListe;
    }
    public void deleteAllMembers() {
        this.membersListe.clear();
    }
    }
