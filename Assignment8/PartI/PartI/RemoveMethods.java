package PartI;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RemoveMethods {

	public static void remAllStack(Stack<Object> stack, Object item) {
		Stack<Object> tmp_stack = new Stack<>();
		while(!(stack.isEmpty())){
			Object tmp = stack.pop();
			if (!(item.equals(tmp))){
				tmp_stack.push(tmp);
			}
		}
		while(!(tmp_stack.isEmpty())){
			stack.push(tmp_stack.pop());
		}
	}
	
	public static void remAllQueue(Queue<Object> queue, Object item) {
		LinkedList<Object> tmp_queue = new LinkedList<>();
		while(!(queue.isEmpty())){
			Object tmp = queue.remove();
			if (!(item.equals(tmp))){
				tmp_queue.add(tmp);
			}
		}
		while(!(tmp_queue.isEmpty())){
			queue.add(tmp_queue.remove());
		}
	}
	
	public static void main(String[] args) {
		Stack<Object> stk = new Stack<Object>();
		stk.push(new Integer(24));
		stk.push(new Integer(2));
		stk.push(new Integer(9));
		stk.push(new Integer(2));
		stk.push(new Integer(7));
		stk.push(new Integer(10));
		stk.push(new Integer(16));
		System.out.println("begin: stk is " + stk);
		RemoveMethods.remAllStack(stk, new Integer(2));
		System.out.println("end: stk is " + stk);
		RemoveMethods.remAllStack(stk, new Integer(24));
		System.out.println("end: stk is " + stk);
		
//		Queue<Object> q = new Queue<Object>(); // you should probably find a concrete class for this
		Queue<Object> q = new LinkedList<>();
		q.offer(new Integer(24));
		q.offer(new Integer(2));
		q.offer(new Integer(9));
		q.offer(new Integer(2));
		q.offer(new Integer(7));
		q.offer(new Integer(10));
		q.offer(new Integer(16));
		System.out.println("begin: q is " + q);
		RemoveMethods.remAllQueue(q, new Integer(2));
		System.out.println("end: q is " + q);
		RemoveMethods.remAllQueue(q, new Integer(24));
		System.out.println("end: q is " + q);

	}
}
