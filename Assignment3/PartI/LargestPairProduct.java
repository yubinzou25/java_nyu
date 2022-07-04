
public class LargestPairProduct {

	public static void main(String[] args) {
		
		int[] intArray = {17, 1, 3, 12, 39, 4, 76, 4, 31, 87};
		int product = -1;
		int new_product;
		for (int i = 0;i<intArray.length;i++){
			for (int j = i+1; j<intArray.length;j++){
				new_product = intArray[i]*intArray[j];
				if(product < new_product){
					product = new_product;
				}
			}
		}

		System.out.println("Maximum product of all pairs in the array: " +
							product);
	}
}
