package org.hbrs.se1.ws26.exercises.uebung3;

public class Client {

   public void execute() throws ContainerException {
       Container container = Container.getInstance();

       container.addMember(new ConcreteMember(1));
       container.addMember(new ConcreteMember(2));

       MemberView view = new MemberView();
       view.dump(container.getCurrentList());
   }

}
