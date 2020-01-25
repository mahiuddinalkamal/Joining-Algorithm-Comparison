import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortMergeJoin implements Join {

	@Override
	public String getName() {
		return "Sort Merge Join";
	}

	@Override
	public List<Triple> join(List<Tuple> input1, List<Tuple> input2) {

		// put your implementation in here
		List<Triple> result = new ArrayList<Triple>();

		Collections.sort(input1, new SortbyID());
		Collections.sort(input2, new SortbyID());

		int mark = -1, index1 = 0, index2 = 0;
		int leftToCheck = input1.size() - 1;

		/*
		 * for (int i = 0; i < input1.size(); i++) {
		 * System.out.println(input1.get(i).getID() + "---" + input2.get(i).getID()); }
		 */

		while (index1 <= leftToCheck || index2 <= leftToCheck) {
			if (mark == -1) {
				while (input1.get(index1).getID() < input2.get(index2).getID()) {
					if (index1 != leftToCheck)
						index1++;
					else
						break;
				}
				while (input1.get(index1).getID() > input2.get(index2).getID()) {
					if (index2 != leftToCheck)
						index2++;
					else
						break;
				}
				mark = index2;
			}

			int a = input1.get(index1).getID();
			int b = input2.get(index2).getID();

			if (a == b) {
				result.add(new Triple(a, input1.get(index1).getValue(), input2.get(index2).getValue()));
				if (index1 == leftToCheck && index2 == leftToCheck)
					break;
				if (index2 != leftToCheck)
					index2++;
				else {
					if (index1 != leftToCheck)
						index1++;
					index2 = mark;
					mark = -1;
				}
			} else {
				if (index1 == leftToCheck && index2 == leftToCheck)
					break;

				index2 = mark;
				mark = -1;
				if (index1 != leftToCheck)
					index1++;
				else {
					if (index2 != leftToCheck)
						index2++;
				}
			}
		}
		return result;
	}
}

class SortbyID implements Comparator<Tuple> {
	// Used for sorting in ascending order of ID
	public int compare(Tuple a, Tuple b) {
		return a.getID() - b.getID();
	}
}
