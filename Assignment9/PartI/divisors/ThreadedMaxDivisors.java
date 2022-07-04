package divisors;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ThreadedMaxDivisors implements Runnable {
	
	private long min;
	private long max;
	private long Num;
	private long divisorNum;
	public ThreadedMaxDivisors(long min, long max) {
		this.min = min;
		this.max = max;
	}
	public long getNum(){return this.Num;}
	public long getDivisorNum(){return this.divisorNum;}
	@Override
	public void run() {
		Entry<Long,Long> result = CountDivisors.maxDivisors(this.min, this.max);
		this.Num = result.getKey();
		this.divisorNum = result.getValue();
	}
	

	public static void main(String[] args) {
		
		long min = 100_000;
		long max = 200_000;

		long interval = 1000;
		Set<Thread> threadSet = new HashSet<Thread>();
		Set<ThreadedMaxDivisors> divisorsSet = new HashSet<ThreadedMaxDivisors>();
		long startTime = System.currentTimeMillis();
		for (long i = min; i < max; i += interval) {
			ThreadedMaxDivisors tmd = new ThreadedMaxDivisors(i, i + interval - 1);
			Thread thread = new Thread(tmd);
			threadSet.add(thread);
			divisorsSet.add(tmd);
			thread.start();
		}
		/* join() tells a thread to wait until it's complete before the rest of the code and proceed.
		 * if we do that for all the threads, then then we can get the results of the threads once
		 * all of them are done
		 */
		for (Thread t: threadSet) {
			try {
				t.join();
//				System.out.print("Done");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// at this point, all threads have been completed, since we
		// called the "join()" method on all the threads we created,
		// which forces the code to wait until the thread is finished
		// before we continue
		long number1=0;long numDivisors1=0;
		for (ThreadedMaxDivisors tmd : divisorsSet) {
			// presumably you've recorded the results of
			// each ThreadedMaxDivisors run. Pick
			// the largest number with the largest number of
			// divisors
			if(tmd.getDivisorNum()>numDivisors1){
				numDivisors1 = tmd.getDivisorNum();
				number1 = tmd.getNum();
			}
			else if (tmd.getDivisorNum()==numDivisors1){
				if(tmd.getNum()>number1)
					number1 = tmd.getNum();
			}
			
		}
		System.out.println("\n" + number1 + ": " + numDivisors1);
		long endTime = System.currentTimeMillis();
		System.out.println("Threaded elapsed time: " + (endTime - startTime));
		startTime = System.currentTimeMillis();
		Entry<Long,Long> e = CountDivisors.maxDivisors(min, max);
		
		long number = e.getKey();
		long numDivisors = e.getValue();
		
		System.out.println("\n" + number + ": " + numDivisors);
		endTime = System.currentTimeMillis();
		
		System.out.println("Non-threaded elapsed time: " + (endTime - startTime));
		
		
		
		
	}
}
