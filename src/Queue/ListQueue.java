package Queue;
import java.util.Collection;
public class ListQueue<T>{

	/**
	 * the list to store items
	 */
	private List<T> theList;

	/**
	 * stores the number of items currently inserted
	 */
	private int currentSize;

	/**
	 * Construct the queue.
	 */
	public ListQueue(){
		theList = new List<>();
		currentSize = 0;
	}

	/** Construct the queue using items from another collection
	 *
	 */
	public ListQueue(Collection<T> list){
		theList = new List<>();
		currentSize = 0;
		for(T obj: list){
			this.push(obj);
		}
	}

	public int size(){
		return currentSize;
	}

	/**
	* Test if the queue is logically empty.
	* @return true if empty, false otherwise.
	*/
	public boolean isEmpty(){
		return currentSize == 0;
	}

	/**
	 * Return the data segment of the queue's first item without removing it.
	 * @return data of type T of the first item.
	 */
	public T peek(){
		if(isEmpty()) throw new IllegalStateException("ListQueue peek() error. Queue is empty");
		return (T)theList.peek().getData();
	}

	/**
	* Return and remove the least recently inserted item
	* from the queue.
	* @return the least recently inserted item in the queue.
	* @throws IllegalStateException if the queue is empty.
	*/
	public T get(){
		if(isEmpty()) throw new IllegalStateException("ListQueue dequeue error. Queue is Empty.");
		T returnValue = theList.removeFromFront();
        currentSize--;
        return returnValue;
	}

	/**
	* Insert a new item into the queue.
	* @param obj the item to insert.
	*/
	public void push(T obj){
		theList.insertAtBack(obj);
		currentSize++;
	}

	/** Print the data stored in the queue.
	 */
	public void print(){
		if(isEmpty()){
			System.out.printf("Empty queue\n");
			return;
		}

		System.out.printf("The queue is: ");
		ListNode current = theList.peek();
		while(!isEmpty()){
			System.out.print(current.getData());
			current = current.getNext();
		}
	}
}

