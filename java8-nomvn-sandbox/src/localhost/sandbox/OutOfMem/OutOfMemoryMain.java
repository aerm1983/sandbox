package localhost.sandbox.OutOfMem;

/*

If an integer is 4b, 500*10^3 integers wourld require a memory of 2*10^6b, or closely 2mb.

Minimum heap allowed is -Xms1024k or -Xms1m.

With -Xmx2m:
- Program does not terminate, throws "Out of Memory" Exception.

With -Xmx4m:
- VisualVM connects slowly.
- Execution prone to "Out of Memory" Exception.
- VisualVM connection takes 2mb of the heap (first "sleep" execution, array empty)
- Used memory close to maximum heap size during execution.

With -Xmx8m
- Program starts and ends correctly.
- VisualVM connects fast, connection takes 2mb of the heap.
- Used memory oscillates between 4mb and 6mb.

*/

public class OutOfMemoryMain {

	public static void main() {
		
		System.out.println("Hello from MemoryLeak!");
		
		
		
		
		int i = 0;
		int maxInt1 = 101*1000;
		int maxInt2 = 501*1000;
		int[] intArray = new int[maxInt2 + 1];


		
		try {
			System.out.println("sleeping...");
			Thread.sleep(25000);	
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " --- e.getCause():" + e.getCause());
		}
		
		for (i=0 ; i < maxInt1 ; i++ ) {
			intArray[i] = i;
			if ( (i % (20*1000)) == 0) {
				System.out.println("i: " + i);
			}
		}
		
		System.out.println("done first part! i:" + i);

		
		
		try {
			System.out.println("sleeping...");
			Thread.sleep(20000);	
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " --- e.getCause():" + e.getCause());
		}
		
		for (i = maxInt1 ; i < maxInt2 ; i++ ) {
			intArray[i] = i;
			if ( (i % (20*1000)) == 0) {
				System.out.println("i: " + i);
			}
		}
		
		System.out.println("done second part! i:" + i);
		
		
		
		
		try {
			System.out.println("sleeping...");
			Thread.sleep(25000);	
		} catch (Exception e) {
			System.err.println("e.getMessage(): " + e.getMessage() + " --- e.getLocalizedMessage(): " + e.getLocalizedMessage() + " --- e.getCause():" + e.getCause());
		}

		System.out.println("done!");
		
	}

}
