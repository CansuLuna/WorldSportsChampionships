import java.util.*;
/**
 * This is the SortedArrayList class. It barrows from library comparable and arraylist. It sorts the arraylists
 * 
 * @author Cansu Karaboga
 * @version 1.0 12 Dec 2017
 */
public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E>//barrows from library comparable and arraylist so can use their methods 
{
    public boolean add(E e)//overwrites the regular add() for arraylists 
    {       
        if(isEmpty())//if the list is empty, go on and add
        {
            super.add(e);
            return true;
        }
        else
        {
        for (int i=0; i < size(); i++)// find where e should be fitting and add 0:right where it belongs, -1towards left. 1: towards right
        {
            if(e.compareTo(get(i)) < 0)
            {
                super.add(i,e); 
                return true;
            }
        }
    }
        super.add(e);// just add it to the end
        return true;            
                
    }
}

