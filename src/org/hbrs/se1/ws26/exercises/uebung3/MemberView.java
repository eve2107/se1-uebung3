package org.hbrs.se1.ws26.exercises.uebung3;

import java.util.List;
public class MemberView {

    public void dump(List<Member> membersListe) {
        for (Member m : membersListe) {
            System.out.println(m.toString());
        }
    }
}
