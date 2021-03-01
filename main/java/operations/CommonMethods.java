package operations;

import java.util.Iterator;
import java.util.List;

public class CommonMethods {
    public void printList(List l){
        Iterator itr = l.iterator();
        while (itr.hasNext()){
            System.out.println(itr.next().toString());
        }
    }
}
