package org.hbrs.se1.ws26.exercises.uebung3;

public class ConcreteMember implements Member, java.io.Serializable {
    private int ID;

    public ConcreteMember(int ID) {
        this.ID = ID;
    }

    @Override
    public Integer getID() {
        return this.ID;
    }

    @Override
    public String toString() {
        return "Member (ID = " + this.ID + ")";
    }
}