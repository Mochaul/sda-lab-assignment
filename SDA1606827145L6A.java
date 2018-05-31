/*
 * @author : Mochamad Aulia Akbar Praditomo (1606827145)
 * SDA-A
 * Lab 6A
 */

// import needed packages
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SDA1606827145L6A { // main class

    public static void main(String[] args) throws IOException { // main method
        // creating needed object
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BinaryMinHeap minHeap = new BinaryMinHeap();
        String line;

        while ((line = in.readLine()) != null) { // main loop
            String[] input = line.split(" ");
            String command = input[0];

            if (command.equalsIgnoreCase("INSERT")){ // INSERT command
                int value = Integer.parseInt(input[1]);
                minHeap.add(value);
            }
            else if (command.equalsIgnoreCase("REMOVE")){ // REMOVE command
                minHeap.poll();
            }
            else if (command.equalsIgnoreCase("NUM_PERCOLATE_UP")){ // NUM_PERCONALTE_UP command
                int value = Integer.parseInt(input[1]);
                minHeap.getNumPercolateUp(value);
            }
            else if (command.equalsIgnoreCase("NUM_PERCOLATE_DOWN")){ // NUM_PERCOLATE_DOWN command
                int value = Integer.parseInt(input[1]);
                minHeap.getNumPercolateDown(value);
            }
            else if (command.equalsIgnoreCase("NUM_ELEMENT")){ // NUM_ELEMENT command
                minHeap.getNumElement();
            }

        }
    }
}

class BinaryMinHeap { // binary min heap class

    // creating instance variables
    private ArrayList<Integer> arrayOfNodes;
    private int[] numPercolateUp;
    private int[] numPercolateDown;

    public BinaryMinHeap() { // class constructor
        this.arrayOfNodes = new ArrayList<Integer>();
        this.numPercolateUp = new int[100001];
        this.numPercolateDown = new int[100001];
    }

    public void add(int value) { // add method
        System.out.println("elemen dengan nilai "+ value +" telah ditambahkan");
        arrayOfNodes.add(value);
        if (arrayOfNodes.size() > 1){
            percolateUp(arrayOfNodes.size()-1);
        }
    }

    public int peek() { // peek method
        return arrayOfNodes.get(0);
    }

    public void poll() { // poll method
        if (arrayOfNodes.size() == 0) {
            System.out.println("min heap kosong");
        }
        else {
            int minNode = arrayOfNodes.get(0);
            swap(0, arrayOfNodes.size()-1);
            arrayOfNodes.remove(arrayOfNodes.size()-1);
            System.out.println(minNode + " dihapus dari heap");

            if (arrayOfNodes.size() > 0){
                percolateDown(0);
            }
        }
    }

    private void percolateUp(int nodeIndex) { // percolateUp method
        if (nodeIndex != 0) {
            int parentIndex = getParentIndex(nodeIndex);
            if (arrayOfNodes.get(parentIndex) > arrayOfNodes.get(nodeIndex)) {
                numPercolateUp[arrayOfNodes.get(nodeIndex)]++;
                swap(nodeIndex, parentIndex);
                percolateUp(parentIndex);
            }
        }
    }

    private void percolateDown(int nodeIndex) { // percolateDown method
        int leftChildIndex = getLeftChildIndex(nodeIndex);
        int rightChildIndex = getRightChildIndex(nodeIndex);
        int minIndex;

        if (rightChildIndex >= arrayOfNodes.size()) {
            if (leftChildIndex >= arrayOfNodes.size()) {
                return;
            }
            else {
                minIndex = leftChildIndex;
            }
        }

        else {
            if (arrayOfNodes.get(leftChildIndex) <= arrayOfNodes.get(rightChildIndex)) {
                minIndex = leftChildIndex;
            }
            else {
                minIndex = rightChildIndex;
            }
        }

        if (arrayOfNodes.get(nodeIndex) > arrayOfNodes.get(minIndex)) {
            numPercolateDown[arrayOfNodes.get(nodeIndex)]++;
            swap(nodeIndex, minIndex);
            percolateDown(minIndex);
        }

    }

    private void swap(int nodeIndex, int parentIndex){ // swap method
        int temp = arrayOfNodes.get(parentIndex);
        arrayOfNodes.set(parentIndex, arrayOfNodes.get(nodeIndex));
        arrayOfNodes.set(nodeIndex, temp);
    }

    public void getNumPercolateUp(int value) {
        System.out.println("percolate up " + numPercolateUp[value]);
    }

    public void getNumPercolateDown(int value) {
        System.out.println("percolate down " + numPercolateDown[value]);
    }

    public void getNumElement() {
        System.out.println("element " + arrayOfNodes.size());
    }

    private int getLeftChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 1;
    }

    private int getRightChildIndex(int nodeIndex) {
        return 2 * (nodeIndex + 1);
    }

    private int getParentIndex(int nodeIndex) {
        return (nodeIndex - 1) / 2;
    }

    @Override
    public String toString() {
        return arrayOfNodes.toString();
    }
}
