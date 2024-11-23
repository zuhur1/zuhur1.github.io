import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

/**
 * JUnit Tests for the LinkedList portion of Project 3 CSCI 1933 Spring 2021.
 * Written by Noah Park on 03.03.2021.
 */
@FixMethodOrder(NAME_ASCENDING)
public class LinkedListTest {

    private static final ScoringTestRule SCORING_TEST_RULE = new ScoringTestRule();

    @Rule
    public ScoringTestRule scoringTestRule = SCORING_TEST_RULE;

    @Rule
    public Timeout globalTimeOut = Timeout.seconds(15);


    @AfterClass
    public static void printScore() {
        System.out.println();
        System.out.println("LinkedList: " + SCORING_TEST_RULE.getPoints() + " / " + SCORING_TEST_RULE.getTotal() + " points");
        System.out.println("Note that 3 points are NOT included in these tests:");
        System.out.println("3 points for increasing the efficiency of the indicated methods based on if the method is sorted (indexOf, sort, equalTo).");
        System.out.println();
    }

    @Test
    @WorthPoints(points = 2)
    public void addTest() {
        // run 1000 tests
        for (int it = 0; it < 1000; it++) {
            List<String> list = new LinkedList<>();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter",
                    "The Promised Neverland", "Solo Leveling", "The Breaker", "One Punch Man", "Dragon Ball Z", "JoJo's Bizarre Adventure", "Yuri!!! on ICE"};
            Random r = new Random();
            String[] arr = new String[255]; // comparison array
            int ptr = 0;

            assertFalse(list.add(null)); // can't add null object

            // add element
            for (int i = 0; i < 250; i++) {
                int idx = r.nextInt(test.length);
                assertTrue(list.add(test[idx]));
                arr[ptr++] = test[idx];
            }

            // added correctly
            for (int i = 0; i < 50; i++) {
                int idx = r.nextInt(250);
                assertEquals(arr[idx], list.get(idx));
            }
        }
    }

    @Test
    @WorthPoints(points = 3)
    public void addIndexTest(){
        List<String> list = new LinkedList<> ();
        String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter",
                "The Promised Neverland", "Solo Leveling", "The Breaker", "One Punch Man", "Dragon Ball Z", "JoJo's Bizarre Adventure", "Yuri!!! on ICE"};
        Random r = new Random();
        String[] arr = new String[255]; // comparison array
        int ptr = 0;

        // add elements to list, sets up list for accurate indexing
        for (int i = 0; i < 250; i++) {
            int idx = r.nextInt(test.length);
            list.add(test[idx]);
            arr[ptr++] = test[idx];
        }

        // add element to index
        for (int i = 0; i < 5; i++) {
            int idx = r.nextInt(list.size()), toAdd = r.nextInt(test.length);
            assertTrue(list.add(idx, test[toAdd]));
            String temp = arr[idx], next;
            for (int j = idx + 1; j < arr.length; j++) { // annoying array shifting
                next = arr[j];
                arr[j] = temp;
                temp = next;
                if (temp == null) break;
            }
            arr[idx] = test[toAdd];

            for (int j = 0; j < arr.length; j++) assertEquals(arr[j], list.get(j)); // all elements should match
        }

        // add out of bounds
        for (int i = 0; i < 10; i++)
            assertFalse(list.add(list.size() + r.nextInt(list.size()), test[r.nextInt(test.length)]));
    }

    @Test
    @WorthPoints(points = 2)
    public void getTest() {
        // run 1000 tests
        for (int it = 0; it < 1000; it++) {
            List<String> list = new LinkedList<>();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter",
                    "The Promised Neverland", "Solo Leveling", "The Breaker", "One Punch Man", "Dragon Ball Z", "JoJo's Bizarre Adventure", "Yuri!!! on ICE"};
            Random r = new Random();
            int size = r.nextInt(250) + 1, ptr = 0;
            String[] arr = new String[size]; // comparison

            // add to list
            for (int i = 0; i < size; i++) {
                int idx = r.nextInt(test.length);
                list.add(test[idx]);
                arr[ptr++] = test[idx];
            }

            // can't get negative indices
            for (int i = -10; i < 0; i++) assertNull(list.get(i));

            // can't get index one beyond maximum
            assertNull(list.get(list.size()));

            // ensure positions all match and out of bounds indices are invalid
            for (int i = 0; i < 25; i++) {
                int idx = r.nextInt(size), fail = idx + 1000;
                assertEquals(arr[idx], list.get(idx));
                assertNull(list.get(fail));
            }
        }
    }

    @Test
    @WorthPoints(points = 2)
    public void indexOfTest() {
        // run 1000 tests
        for (int it = 0; it < 1000; it++) {
            List<String> list = new LinkedList<>();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter",
                    "The Promised Neverland", "Solo Leveling", "The Breaker", "One Punch Man", "Dragon Ball Z", "JoJo's Bizarre Adventure", "Yuri!!! on ICE"};
            Random r = new Random();
            HashMap<String, Integer> map = new HashMap<>(); // map indices

            // can't find null
            assertEquals(-1, list.indexOf(null));

            // ensure matching starting index is the same from function call
            for (int i = 0; i < 100; i++) {
                int idx = r.nextInt(test.length);

                assertEquals((int) map.getOrDefault(test[idx], -1), list.indexOf(test[idx]));

                list.add(test[idx]);
                if (!map.containsKey(test[idx])) map.put(test[idx], list.size() - 1);

                // clear map/list every 10 iterations
                if (i % 10 == 0) {
                    list.clear();
                    map = new HashMap<>();
                }
            }
        }
    }

    @Test
    @WorthPoints(points = 1)
    public void emptyTest() {
        // run 1000 tests
        for (int it = 0; it < 1000; it++) {
            List<String> list = new LinkedList<>();
            assertTrue(list.isEmpty());
            list.add("notEmpty");
            assertFalse(list.isEmpty());
        }
    }

    @Test
    @WorthPoints(points = 3)
    public void sizeAndClearTest() {
        // run 1000 tests
        for (int it = 0; it < 1000; it++) {
            List<String> list = new LinkedList<>();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter",
                    "The Promised Neverland", "Solo Leveling", "The Breaker", "One Punch Man", "Dragon Ball Z", "JoJo's Bizarre Adventure", "Yuri!!! on ICE"};
            Random r = new Random();

            // size update in add element
            for (int i = 0; i < 25; i++) {
                int size = r.nextInt(25);
                for (int j = 0; j < size; j++) list.add(test[r.nextInt(test.length)]);
                assertEquals(size, list.size());
                list.clear();
                assertEquals(0, list.size());
            }

            list.add(test[r.nextInt(test.length)]);
            int size = 1;

            // size update in add to index
            for (int i = 0; i < 25; i++) {
                list.add(0, test[r.nextInt(test.length)]);
                assertEquals(++size, list.size());
            }

            // size update in remove
            for (int i = 0; i < 25; i++) {
                list.remove(0);
                assertEquals(--size, list.size());
            }
        }
    }

    @Test
    @WorthPoints(points = 2)
    public void sortTest() {
        // run 1000 tests
        for (int it = 0; it < 1000; it++) {
            List<String> list = new LinkedList<>();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter",
                    "The Promised Neverland", "Solo Leveling", "The Breaker", "One Punch Man", "Dragon Ball Z", "JoJo's Bizarre Adventure", "Yuri!!! on ICE"};
            Random r = new Random();

            for (int i = 0; i < 250; i++) list.add(test[r.nextInt(test.length)]);

            list.sort();
            for (int i = 1; i < 250; i++)
                assertTrue(list.get(i).compareTo(list.get(i - 1)) >= 0); // ensure list is sorted
        }
    }

    @Test
    @WorthPoints(points = 4)
    public void removeTest() {
        // run 1000 tests
        for (int it = 0; it < 1000; it++) {
            List<String> list = new LinkedList<>();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter"};
            Random r = new Random();
            String[] arr = new String[250]; // comparison
            int ptr = 0;

            // create
            for (int i = 0; i < 250; i++) {
                int idx = r.nextInt(test.length);
                list.add(test[idx]);
                arr[ptr++] = test[idx];
            }

            // can't remove negative indices
            for (int i = -10; i < 0; i++) assertNull(list.remove(i));

            for (int i = 0; i < 50; i++) {
                int remove = r.nextInt(list.size()), fail = remove + 250;
                assertNull(list.remove(fail)); // can't remove out of bound index
                assertEquals(arr[remove], list.remove(remove));
                System.arraycopy(arr, remove + 1, arr, remove, 249 - remove);
                arr[--ptr] = null;
            }
        }
    }

    @Test
    @WorthPoints(points = 3)
    public void equalToTest() {
        // run 1000 tests
        for (int it = 0; it < 1000; it++) {
            List<String> list = new LinkedList<>();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter"};
            Random r = new Random();
            int size = 0;

            // add elements and count the number of Haikyuu!! occurrences
            for (int i = 0; i < 250; i++) list.add(test[r.nextInt(test.length)]);
            for (int i = 0; i < 250; i++) if (list.get(i).equals("Haikyuu!!")) size++;

            // ensure the size is updated and that all elements in the list were updated accordingly
            list.equalTo("Haikyuu!!");
            assertEquals(size, list.size());
            for (int i = 0; i < size; i++) assertEquals("Haikyuu!!", list.get(i));
        }
    }

    @Test
    @WorthPoints(points = 2)
    public void reverseTest() {
        // run 1000 tests
        for (int it = 0; it < 1000; it++) {
            List<String> list = new LinkedList<>(), reversed = new LinkedList<>();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter"};
            Random r = new Random();

            // add elements to normal list and reverse list in reverse order
            for (int i = 0; i < 250; i++) list.add(test[r.nextInt(test.length)]);
            for (int i = 249; i >= 0; i--) reversed.add(list.get(i));

            list.reverse();
            for (int i = 0; i < 250; i++) assertEquals(list.get(i), reversed.get(i)); // lists should match
        }
    }

    @Test
    @WorthPoints(points = 4)
    public void intersectTest() {
        // run 1000 tests
        for (int i = 0; i < 1000; i++) {
            List<String> list1 = new LinkedList<>();
            List<String> list2 = new LinkedList<>();
            Random random = new Random();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter",
                    "The Promised Neverland", "Solo Leveling", "The Breaker", "One Punch Man", "Dragon Ball Z", "JoJo's Bizarre Adventure", "Yuri!!! on ICE"};
            Arrays.sort(test);

            // declare locals, generate random range values, set low->high range for each list, set intersection range
            int a, b, c, d, low1, high1, low2, high2, intersectLow, intersectHigh;
            a = random.nextInt(test.length); b = random.nextInt(test.length); c = random.nextInt(test.length); d = random.nextInt(test.length);
            low1 = Math.min(a, b); high1 = Math.max(a, b); low2 = Math.min(c, d); high2 = Math.max(c, d);

            intersectLow = Math.max(low1, low2); intersectHigh = Math.min(high1, high2);

            for (int j = low1; j <= high1; j++) { list1.add(test[j]); }
            for (int j = low2; j <= high2; j++) { list2.add(test[j]); }

            list1.intersect(list2);

            for (int j = intersectLow; j <= intersectHigh; j++) {
                String strExpected = test[j];
                String strActual = list1.remove(0);
                assertEquals(strExpected, strActual);
            }
        }
    }

    @Test
    @WorthPoints(points = 3)
    public void getMinTest(){
        final int RANGE = 300;
        LinkedList<Integer> list = new LinkedList<>();
        //null check
        assertNull(list.getMin());
        Random r = new Random();

        //run 100 tests
        for(int i = 0; i < 100; i++){
            list = new LinkedList<>();

            int minRandomNumber = r.nextInt(RANGE);
            list.add(minRandomNumber);
            //add 49 other random integers in RANGE
            for(int j = 0; j < 49; j++){
                int newNumber = r.nextInt(RANGE);
                list.add(newNumber);
                if(newNumber < minRandomNumber){
                    minRandomNumber = newNumber;
                }
            }
            int resultingMin = list.getMin();
            assertEquals(minRandomNumber, resultingMin);
        }
    }

    @Test
    @WorthPoints(points = 3)
    public void getMaxTest() {
        final int RANGE = 300;
        LinkedList<Integer> list = new LinkedList<>();
        //null check
        assertNull(list.getMax());
        Random r = new Random();

        //run 100 tests
        for (int i = 0; i < 100; i++) {
            list = new LinkedList<>();

            int maxRandomNumber = r.nextInt(RANGE);
            list.add(maxRandomNumber);
            //add 49 other random integers in RANGE
            for (int j = 0; j < 49; j++) {
                int newNumber = r.nextInt(RANGE);
                list.add(newNumber);
                if (newNumber > maxRandomNumber) {
                    maxRandomNumber = newNumber;
                }
            }
            int resultingMax = list.getMax();
            assertEquals(maxRandomNumber, resultingMax);
        }
    }

    @Test
    @WorthPoints(points = 3)
    public void isSortedTest() {
        // run 50 tests
        for (int it = 0; it < 50; it++) {
            List<String> list = new LinkedList<>();
            String[] test = new String[]{"One Piece", "Fullmetal Alchemist", "Attack On Titan", "Tokyo Ghoul", "Haikyuu!!", "Mob Psycho", "Hunter x Hunter",
                    "The Promised Neverland", "Solo Leveling", "The Breaker", "One Punch Man", "Dragon Ball Z", "JoJo's Bizarre Adventure", "Yuri!!! on ICE"};
            String[] sorted = new String[]{"Jojo's Bizarre Adventure", "Naruto", "One Piece", "Solo Leveling", "The Breaker"};
            Random r = new Random();

            // add element
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    list.add(test[r.nextInt(test.length)]);
                    boolean check = true;
                    for (int k = 1; k < list.size(); k++) check &= list.get(k).compareTo(list.get(k - 1)) >= 0;
                    assertEquals(check, list.isSorted());
                }
                list.clear();
            }

            // add at index
            list.add(test[r.nextInt(test.length)]);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    list.add(0, test[r.nextInt(test.length)]);
                    boolean check = true;
                    for (int k = 1; k < list.size(); k++) check &= list.get(k).compareTo(list.get(k - 1)) >= 0;
                    assertEquals(check, list.isSorted());
                }
                list.clear();
            }

            // clear
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 50; j++) list.add(test[r.nextInt(test.length)]);
                list.clear();
                assertTrue(list.isSorted());
            }

            // sort
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 250; j++) list.add(test[r.nextInt(test.length)]);
                list.sort();
                assertTrue(list.isSorted());
            }
            list.clear();

            // remove
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 50; j++) list.add(test[r.nextInt(test.length)]);
                for (int j = 0; j < 50; j++) {
                    list.remove(0);
                    boolean check = true;
                    for (int k = 1; k < list.size(); k++) check &= list.get(k).compareTo(list.get(k - 1)) >= 0;
                    assertEquals(check, list.isSorted());
                }
            }

            list.clear();
            for (int i = 0; i < 5; i++) {
                list.add(sorted[i]);
                assertTrue(list.isSorted());
            }

            // equalTo
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 250; j++) list.add(test[r.nextInt(test.length)]);
                list.equalTo(sorted[r.nextInt(sorted.length)]);
                assertTrue(list.isSorted());
                list.clear();
            }

            for (int i = 4; i >= 0; i--)
                for (int j = 0; j < 100; j++) list.add(sorted[i]);
            list.reverse();
            assertTrue(list.isSorted());

        }
    }
}