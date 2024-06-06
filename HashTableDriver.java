public class HashTableDriver implements HashTableDriverInterface {

    @Override
    public Person<Integer>[] createPersons(int numPeople) {
    	@SuppressWarnings("unchecked")
    	Person<Integer>[] people = (Person<Integer>[]) new Person<?>[numPeople];

        for (int i = 0; i < numPeople; i++) {
            people[i] = new Person<>(i); // Assuming integer keys for simplicity
        }
        return people;
    }

    @Override
    public LinkedList<Person<Integer>, Integer>[] createBuckets(int numBuckets) {
        // Create an array of LinkedLists to serve as buckets
    	LinkedList<Person<Integer>, Integer>[] buckets = new LinkedList[numBuckets];

        for (int i = 0; i < numBuckets; i++) {
            buckets[i] = new LinkedList<>();
        }
        return buckets;
    }

    @Override
    public HashTable<Person<Integer>, Integer> createHashTable(TestType testType, int numPeople) {
        LinkedList<Person<Integer>, Integer>[] buckets = createBuckets(HashTable.NUMBER_OF_BUCKETS);
        HashTable<Person<Integer>, Integer> hashTable = null;
        try {
            hashTable = new HashTable<>(buckets);
            if (testType == TestType.Get || testType == TestType.Remove) {
                // Populate the hash table with numPeople Person objects
                Person<Integer>[] people = createPersons(numPeople);
                for (Person<Integer> person : people) {
                    hashTable.insert(person);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return hashTable;
    }


    @Override
    public Object[] runTestCase(TestType testType, int numPeople, int numberOfTimes) {
        // Check if numberOfTimes is positive
        if (numberOfTimes <= 0) {
            System.out.println("Error: numberOfTimes must be a positive integer.");
            return null; // or throw an exception
        }
        
        // Create an array to store the results
        Object[] results = new Object[numberOfTimes * 2];
        
        System.out.println("Results array length: " + results.length);
        System.out.println("numberOfTimes: " + numberOfTimes);
        for (int i = 0; i < numberOfTimes; i++) {
            System.out.println("Loop iteration: " + i);
            // Create an initial HashTable based on the test type and number of people
            HashTable<Person<Integer>, Integer> initialTable = createHashTable(testType, numPeople);

            // Make a copy of the initial table
            HashTable<Person<Integer>, Integer> copiedTable = null;
            try {
                copiedTable = (HashTable<Person<Integer>, Integer>) initialTable.copy();
            } catch (InstantiationException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }

            // Perform the test based on the test type
            switch (testType) {
                case Insert:
                    // No specific action for Insert test
                    break;
                case Get:
                    // Get all Person objects inserted in the initialization
                    for (int j = 0; j < numPeople; j++) {
                        copiedTable.get(j);
                    }
                    break;
                case Remove:
                    // Remove each Person object inserted in the initialization
                    for (int j = 0; j < numPeople; j++) {
                        copiedTable.remove(j);
                    }
                    break;
            }

            // Store the initial and modified tables in the results array
            int initialIndex = i * 2;
            int modifiedIndex = i * 2 + 1;
            if (initialIndex < results.length) {
                results[initialIndex] = initialTable;
                System.out.println("Stored initial table for test " + i + " at index " + initialIndex);
            } else {
                System.out.println("Error: Initial index out of bounds: " + initialIndex);
            }
            if (modifiedIndex < results.length) {
                results[modifiedIndex] = copiedTable;
                System.out.println("Stored modified table for test " + i + " at index " + modifiedIndex);
            } else {
                System.out.println("Error: Modified index out of bounds: " + modifiedIndex);
            }
        }

        return results;
    }





}
