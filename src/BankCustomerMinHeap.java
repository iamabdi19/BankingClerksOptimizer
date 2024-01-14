public class BankCustomerMinHeap implements Cloneable {
    private BankCustomer[] heap;
    int size;

    public BankCustomerMinHeap(int capacity) {
        heap = new BankCustomer[capacity];
        size = 0;
    }

    // checks if heap is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // returns the top of the heap
    public BankCustomer top() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[0];
    }

    // adds a customer to the heap
    public void insert(BankCustomer customer) {
        if (size == heap.length) {
            expandHeap();
        }
        heap[size] = customer;
        int current = size++;
        while (current != 0 && shouldSwap(current, parent(current))) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    // expands heap size
    private void expandHeap() {
        BankCustomer[] newHeap = new BankCustomer[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    // Determines if two elements should be swapped based on their arrival and processing times
    private boolean shouldSwap(int i, int j) {
        return heap[i].getArrival().compareTo(heap[j].getArrival()) < 0 ||
               (heap[i].getArrival().equals(heap[j].getArrival()) && heap[i].getProcessingTime() < heap[j].getProcessingTime());
    }

    // removes and returns the top of the heap
    public BankCustomer removeMin() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        BankCustomer min = heap[0];
        heap[0] = heap[--size];
        heapify(0);
        return min;
    }

    // heapify-down process
    private void heapify(int index) {
        int left = left(index);
        int right = right(index);
        int smallest = index;
        if (left < size && shouldSwap(left, index)) {
            smallest = left;
        }
        if (right < size && shouldSwap(right, smallest)) {
            smallest = right;
        }
        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest);
        }
    }

    // Helper methods to get parent, left, and right indices
    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int left(int index) {
        return 2 * index + 1;
    }

    private int right(int index) {
        return 2 * index + 2;
    }

    // swaps two elements in the heap
    private void swap(int i, int j) {
        BankCustomer temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // returns the size of the heap
    public int size() {
        return size;
    }
}
