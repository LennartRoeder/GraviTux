/**
 * @author Knut Hartmann <BR>
 * Flensburg University of Applied Sciences <BR>
 * Knut.Hartmann@FH-Flensburg.DE
 * 
 * @version October 14, 2012
 */

import java.util.LinkedList;

/**
 * LinkedList is a generic container class implements a number of Interfaces:
 * Collection and Iterable. The Iterable interface allows an easy access to the
 * members of the collections in a foreach loop. The collection Interface
 * provides consistent access methods. Just to list a few of them: <OL>
 * <LI> add new  members to a collection 
 * <LI> remove members from a collection.
 * </OL>
 */
public class CollectionDemo {

	public static void main(String[] args) {
		LinkedList<String> stringCollection = new LinkedList<String>();
		stringCollection.add("Knut");
		stringCollection.add("Hartmann");
		stringCollection.add("Flensburg");
		stringCollection.remove("Flensburg");

		for (String s : stringCollection) {
			System.out.println(s);
		}

		boolean isContained = stringCollection.contains("Flensburg");
		System.out.println("Ist Knut in Flensburg? " + isContained);
	}
}
