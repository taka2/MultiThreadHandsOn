package handson;

public class MultiProcessor_1 {

	// ŒvZ‰ñ”
	private static final int NUM_CALCULATION = 10;

	public static void main(String[] args) {
		for(int i=0; i<NUM_CALCULATION; i++) {
			long sum = 0;
			for(int j=0; j<1000000000; j++) {
				sum += j;
			}
			System.out.println(sum);
		}
	}
}
