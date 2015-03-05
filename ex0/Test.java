//comment
public class Test {

	static final Customer customerList[] = new Customer[10];
	static final Store storeList[] = new Store[10];
	static final ProductType productList[] = new ProductType[10];

	public static void main(String[] args) {
		init();
		test1();
		test2();
		test3();
	}

	public static void init(){
		for (int i = 0; i < customerList.length; i++) {
			String tempName = "customer" + i;
			customerList[i] = new Customer(tempName, tempName + "address" + i,
					((i + 10) * 200));
		}

		for (int i = 0; i < productList.length; i++) {
			String tempName = "product" + i;
			productList[i] = new ProductType(tempName, ((i + 1) * 10),
					(int) Math.pow(((i + 1) * 10), 2));
		}

		for (int i = 0; i < storeList.length; i++) {
			storeList[i] = new Store();
			for (int j=i; j<productList.length; j++){
				boolean result = storeList[i].addProductType(productList[j]);
				if (j - i >= 5){
					if (result){
						System.out.println("Store init failed, expected false. " + i +", " + j);
					}
				} else if (!result) {
					System.out.println("Store init failed, expected true. " + i +", " + j);
				}
			}
		}
	}

	public static void test1(){
		// Test string representations for customers
		String[] expected = {
				"[customer0,customer0address0,2000]",
				"[customer1,customer1address1,2200]",
				"[customer2,customer2address2,2400]",
				"[customer3,customer3address3,2600]",
				"[customer4,customer4address4,2800]",
				"[customer5,customer5address5,3000]",
				"[customer6,customer6address6,3200]",
				"[customer7,customer7address7,3400]",
				"[customer8,customer8address8,3600]",
				"[customer9,customer9address9,3800]"
		};
		for (int i = 0; i < customerList.length; i++){
			String actual = customerList[i].stringRepresentation();
			if (!actual.equals(expected[i])){
				System.out.println("Missmatch. expected: " + expected[i] +", actual: " + actual);
			}
		}
	}


	public static void test2(){
		// Test string representations for customers
		String[] expected = {
				"[product0,10,100]",
				"[product1,20,400]",
				"[product2,30,900]",
				"[product3,40,1600]",
				"[product4,50,2500]",
				"[product5,60,3600]",
				"[product6,70,4900]",
				"[product7,80,6400]",
				"[product8,90,8100]",
				"[product9,100,10000]"
		};
		for (int i = 0; i < productList.length; i++){
			String actual = productList[i].stringRepresentation();
			if (!actual.equals(expected[i])){
				System.out.println("Missmatch. expected: " + expected[i] +", actual: " + actual);
			}
		}
	}

	public static void test3(){
		// Test string representations for customers
		String[] expected = {
				"Store has a balance of 0, and the following products:\n" +
						"[product0,10,100]\n" +
						"[product1,20,400]\n" +
						"[product2,30,900]\n" +
						"[product3,40,1600]\n" +
						"[product4,50,2500]",

				"Store has a balance of 0, and the following products:\n" +
						"[product1,20,400]\n" +
						"[product2,30,900]\n" +
						"[product3,40,1600]\n" +
						"[product4,50,2500]\n" +
						"[product5,60,3600]",

				"Store has a balance of 0, and the following products:\n" +
						"[product2,30,900]\n" +
						"[product3,40,1600]\n" +
						"[product4,50,2500]\n" +
						"[product5,60,3600]\n" +
						"[product6,70,4900]",

				"Store has a balance of 0, and the following products:\n" +
						"[product3,40,1600]\n" +
						"[product4,50,2500]\n" +
						"[product5,60,3600]\n" +
						"[product6,70,4900]\n" +
						"[product7,80,6400]",

				"Store has a balance of 0, and the following products:\n" +
						"[product4,50,2500]\n" +
						"[product5,60,3600]\n" +
						"[product6,70,4900]\n" +
						"[product7,80,6400]\n" +
						"[product8,90,8100]",

				"Store has a balance of 0, and the following products:\n" +
						"[product5,60,3600]\n" +
						"[product6,70,4900]\n" +
						"[product7,80,6400]\n" +
						"[product8,90,8100]\n" +
						"[product9,100,10000]",

				"Store has a balance of 0, and the following products:\n" +
						"[product6,70,4900]\n" +
						"[product7,80,6400]\n" +
						"[product8,90,8100]\n" +
						"[product9,100,10000]",

				"Store has a balance of 0, and the following products:\n" +
						"[product7,80,6400]\n" +
						"[product8,90,8100]\n" +
						"[product9,100,10000]",

				"Store has a balance of 0, and the following products:\n" +
						"[product8,90,8100]\n" +
						"[product9,100,10000]",

				"Store has a balance of 0, and the following products:\n" +
						"[product9,100,10000]"

		};
		for (int i = 0; i < storeList.length; i++){
			String actual = storeList[i].stringRepresentation();
			if (!actual.equals(expected[i])){
				System.out.println("Missmatch. expected:\n" + expected[i] +"actual:\n" + actual);
			}
		}
	}
}
