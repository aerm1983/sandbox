package localhost.sandbox.jse8.Thread;

import localhost.sandbox.jse8.A0Helper.ThreadHelper;

public class Test00_ExtendedThread {

	public static void test00ExtendedThread () {


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



	public static class BulkJobThread extends Thread {

		private String storeId;

		private String marketplace;

		private String merchant;

		private String job;

		private long items;


		// constructor

		public BulkJobThread(Runnable target, String name) {
			super(target, name);
		}


		// to String

		public String toString() {
			return null;
		}



		// getters and setters

		public String getStoreId() {
			return storeId;
		}

		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}

		public String getMarketplace() {
			return marketplace;
		}

		public void setMarketplace(String marketplace) {
			this.marketplace = marketplace;
		}

		public String getMerchant() {
			return merchant;
		}

		public void setMerchant(String merchant) {
			this.merchant = merchant;
		}

		public String getJob() {
			return job;
		}

		public void setJob(String job) {
			this.job = job;
		}

		public long getItems() {
			return items;
		}

		public void setItems(long items) {
			this.items = items;
		}

	}
}
