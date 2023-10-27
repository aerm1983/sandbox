package localhost.sandbox.Thread;

import localhost.helper.ThreadHelper;

public class ThreadMain {
	
	public static void main() {
		System.out.println("hello from ThreadMain!");
		test00ExtendedThread();
	}

	
	private static void test00ExtendedThread () {
		

		BulkJobThread bjt = new BulkJobThread( 
				() -> {
					try {
						Thread.sleep(10L * 1000L);
						System.out.println("done with sleeping into bjt!");
					} catch (Exception e) {
						
					};
				}, 
				"bjThread");
		bjt.setMarketplace("ebay");
		bjt.setStoreId("do-commerce");
		bjt.setJob("stock");
		bjt.setItems(15);
		
		bjt.start();
		
		Thread[] threadArray = ThreadHelper.searchByNameRegex("bjThread");
		
		BulkJobThread bjt2 = (BulkJobThread) threadArray[0];
		
		return;
		
		
		
	}

	
}
