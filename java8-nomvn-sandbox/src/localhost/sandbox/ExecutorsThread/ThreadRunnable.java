package localhost.sandbox.ExecutorsThread;

/**
 * 
 * <p> Use constructor to pass parameters.
 * 
 * <p>It is also possible, instead of a class which implements interface Runnable,
 * to extend the class Thread and define the method Run.
 *
 */
public class ThreadRunnable implements Runnable {
    
    private String stringFromMain;
    private Long sleepTimeFromMain;
    
    public ThreadRunnable(String stringFromMain, Long sleepTimeFromMain) {
        this.stringFromMain = stringFromMain;
        this.sleepTimeFromMain = sleepTimeFromMain;
    }
    
    public void run (){
        Thread ct = Thread.currentThread();
        System.out.println("thread " + ct.getName() + ", tGroup.name " + ct.getThreadGroup().getName() + " -- task from runnable class -- stringfromMain: '" + stringFromMain + "' -- begin");
        try {
            Thread.sleep(sleepTimeFromMain);
        } catch (Exception e) {
            System.err.println("thread " + ct.getName() + " -- task from runnable class -- error.getMessage(): " + e.getMessage());
        }
        System.out.println("thread " + ct.getName() + ", tGroup.name " + ct.getThreadGroup().getName() + " -- task from runnable class -- stringFromMain: '" + stringFromMain + "' -- end");
    }

    
    


}
