import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class HashJoin implements Join {

	@Override
	public String getName() {
		return "Hash Join";
	}

	@Override
	public List<Triple> join(List<Tuple> input1, List<Tuple> input2) {

		// put your implementation in here
		List<Triple> result = new ArrayList<Triple>();
		Multimap<Integer, Integer> map = ArrayListMultimap.create();
		for (int i = 0; i < input1.size(); i++) {
			map.put(input1.get(i).getID(), input1.get(i).getValue());
		}
		for (int i = 0; i < input1.size(); i++) {
			int key = input2.get(i).getID();
			if (map.containsKey(key)) {
				for (Integer value : map.get(key)) {
					result.add(new Triple(input2.get(i).getID(), value, input2.get(i).getValue()));
				}
			}
		}
		return result;
	}
}
