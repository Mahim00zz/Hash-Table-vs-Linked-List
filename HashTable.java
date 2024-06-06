import java.util.Iterator;
import java.util.Vector;


public class HashTable<E extends KeyedElementInterface<K>, K> implements HashTableInterface<E, K> {

    public static final int NUMBER_OF_BUCKETS = 1_069;
    private LinkedList<E, K>[] buckets;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public HashTable(LinkedList<E, K>[] buckets) throws InstantiationException {
        if (buckets.length != NUMBER_OF_BUCKETS) {
            throw new InstantiationException("The passed array is not of the correct size");
        }

        this.buckets = (LinkedList<E, K>[]) buckets.clone();
    }




    @Override
    public Iterator<E> iterator() {
        Vector<E> elements = new Vector<>();
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            if (buckets[i] != null) {
                Iterator<E> bucketIterator = buckets[i].iterator();
                while (bucketIterator.hasNext()) {
                    elements.add(bucketIterator.next());
                }
            }
        }
        return new ElementIterator<>(elements);
    }






    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public HashTableInterface<E, K> copy() throws InstantiationException {
        LinkedList<E, K>[] copiedBuckets = new LinkedList[NUMBER_OF_BUCKETS];
        int copiedSize = 0;

        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            copiedBuckets[i] = (LinkedList<E, K>) buckets[i].copy();
            copiedSize += copiedBuckets[i].size();
        }

        HashTableInterface<E, K> copiedTable = new HashTable<>(copiedBuckets);
        ((HashTable<E, K>) copiedTable).setSize(copiedSize);
        return copiedTable;
    }

    public E insert(E element) {
        if (element != null) {
            K key = element.getKey();
            int bucketNumber = Math.abs(key.hashCode() % NUMBER_OF_BUCKETS);
            E removedElement = buckets[bucketNumber].add(element);
            size++;
            return removedElement;
        }
        return null;
    }







    private int hashFunction(K key) {
        return 0;
    }

    public E get(K key) {
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            E retrievedElement = buckets[i].get(key);
            if (retrievedElement != null) {
                return retrievedElement;
            }
        }
        return null;
    }


    public E remove(K key) {
        for (int i = 0; i < NUMBER_OF_BUCKETS; i++) {
            E removedElement = buckets[i].remove(key);
            if (removedElement != null) {
                size--;
                return removedElement;
            }
        }
        return null;
    }





    @Override
    public void removeAll() {
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                buckets[i].removeAll();
                buckets[i] = null;
            }
        }
        size = 0;
    }

    @Override
    public int getSizeOfLargestBucket() {
        int maxBucketSize = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                int currentBucketSize = buckets[i].size();
                if (currentBucketSize > maxBucketSize) {
                    maxBucketSize = currentBucketSize;
                }
            }
        }
        return maxBucketSize;
    }

    @Override
    public double getAverageBucketSize() {
        int nonEmptyBuckets = 0;
        int totalBucketSize = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                nonEmptyBuckets++;
                totalBucketSize += buckets[i].size();
            }
        }
        return nonEmptyBuckets == 0 ? 0 : (double) totalBucketSize / nonEmptyBuckets;
    }

    @Override
    public Object[] getBuckets() {
        return buckets;
    }

    // Method to set the size of the hash table
    private void setSize(int size) {
        this.size = size;
    }
}