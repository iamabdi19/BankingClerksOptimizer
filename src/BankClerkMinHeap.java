public class BankClerkMinHeap implements Cloneable {
   private BankClerk[] heap; // array of heap entries
   private int size; 

   public BankClerkMinHeap(int capacity) {
       heap = new BankClerk[capacity];
       size = 0;
   }

   // checks if heap is empty
   public boolean isEmpty() {
       return size == 0;
   }

   // returns the top of the heap
   public BankClerk top() {
       if (isEmpty()) {
           throw new IllegalStateException("Heap is empty");
       }
       return heap[0];
   }

   // adds a clerk to the heap
   public void add(BankClerk clerk) {
       if (size == heap.length) {
           expandHeap();
       }
       heap[size] = clerk;
       int current = size++;
       while (current != 0 && heap[current].getAvailableTime().isBefore(heap[parent(current)].getAvailableTime())) {
           swap(current, parent(current));
           current = parent(current);
       }
   }

   // expands heap size
   private void expandHeap() {
       BankClerk[] newHeap = new BankClerk[heap.length * 2];
       System.arraycopy(heap, 0, newHeap, 0, heap.length);
       heap = newHeap;
   }

   // removes and returns the top of the heap
   public BankClerk removeTop() {
       if (isEmpty()) {
           throw new IllegalStateException("Heap is empty");
       }
       BankClerk min = heap[0];
       heap[0] = heap[--size];
       heapify(0);
       return min;
   }

   // heapify-down process
   private void heapify(int index) {
       int left = left(index);
       int right = right(index);
       int smallest = index;
       if (left < size && heap[left].getAvailableTime().isBefore(heap[index].getAvailableTime())) {
           smallest = left;
       }
       if (right < size && heap[right].getAvailableTime().isBefore(heap[smallest].getAvailableTime())) {
           smallest = right;
       }
       if (smallest != index) {
           swap(index, smallest);
           heapify(smallest);
       }
   }

   private int parent(int index) {
       return (index - 1) / 2;
   }

   private int left(int index) {
       return 2 * index + 1;
   }

   private int right(int index) {
       return 2 * index + 2;
   }

   private void swap(int i, int j) {
       BankClerk temp = heap[i];
       heap[i] = heap[j];
       heap[j] = temp;
   }

   // returns the size of the heap
   public int size() {
       return size;
   }
}
