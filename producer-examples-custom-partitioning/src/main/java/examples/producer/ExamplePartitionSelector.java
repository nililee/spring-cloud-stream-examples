package examples.producer;

import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;

public class ExamplePartitionSelector implements PartitionSelectorStrategy {

	@Override
	public int selectPartition(Object key, int partitionCount) {
		
		int parition = 0;
		char firstChar = String.valueOf(key).toUpperCase().charAt(0);
		
		if(firstChar >= 65 && firstChar < 70) {
			parition = 0;
		} else if(firstChar >= 70 && firstChar < 75) {
			parition = 1;
		} else if(firstChar >= 75 && firstChar < 80) {
			parition = 2;
		} else if(firstChar >= 80 && firstChar < 85) {
			parition = 3;
		} else {
			parition = 4;
		}
		
		return parition;
	}

}
