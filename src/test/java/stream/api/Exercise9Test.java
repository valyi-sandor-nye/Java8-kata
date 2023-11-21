package stream.api;

import common.test.tool.annotation.Difficult;
import common.test.tool.annotation.Easy;
import common.test.tool.dataset.ClassicOnlineStore;
import common.test.tool.entity.Customer;
import common.test.tool.entity.Item;
import common.test.tool.util.CollectorImpl;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class Exercise9Test extends ClassicOnlineStore {

    @Easy
    @Test
    public void simplestStringJoin() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Implement a {@link Collector} which can create a String with comma separated names shown in the assertion.
         * The collector will be used by serial stream.
         */
        Supplier<Object> supplier = () -> new StringJoiner(",");
        BiConsumer<Object, String> accumulator =
                (textCollectedTillNow, newString) ->
                        ((StringJoiner) textCollectedTillNow).add(newString);
        BinaryOperator<Object> combiner = (textCollectedTillNow, newString) ->
                ((StringJoiner) textCollectedTillNow).add(newString.toString());
        ;
        Function<Object, String> finisher = Object::toString;

        Collector<String, ?, String> toCsv =
                new CollectorImpl<>(supplier, accumulator, combiner, finisher, Collections.emptySet());
        String nameAsCsv = customerList.stream().map(Customer::getName).collect(toCsv);
        assertThat(nameAsCsv, is("Joe,Steven,Patrick,Diana,Chris,Kathy,Alice,Andrew,Martin,Amy"));
    }

    @Difficult
    @Test
    public void mapKeyedByItems() {
        List<Customer> customerList = this.mall.getCustomerList();

        /**
         * Implement a {@link Collector} which can create a {@link Map} with keys as item and
         * values as {@link Set} of customers who are wanting to buy that item.
         * The collector will be used by parallel stream.
         */

        Supplier<Object> supplier = () -> new HashMap<String, HashSet<String>>();
        BiConsumer<Object, Customer> accumulator =
                (obj, cust) ->
                {
                    HashMap<String, HashSet<String>> hmap = (HashMap<String, HashSet<String>>) obj;
                    for (Item item : cust.getWantToBuy()) {
                        if (hmap.containsKey(item.getName())) {
                            hmap.get(item.getName()).add(cust.getName());
                        } else {
                            HashSet<String> toAdd = (new HashSet<>());
                            toAdd.add(cust.getName());
                            hmap.put(
                                    item.getName(), toAdd);
                        }
                    }
                };
        BinaryOperator<Object> combiner =
                (Object obj1, Object obj2) ->
                {
                    HashMap<String, HashSet<String>> hmap1 = (HashMap<String, HashSet<String>>) obj1;
                    HashMap<String, HashSet<String>> hmap2 = (HashMap<String, HashSet<String>>) obj2;
                    for (String itemName : hmap2.keySet()) {
                        if (hmap1.containsKey(itemName)) {
                            hmap1.get(itemName).addAll(hmap2.get(itemName));
                        } else {
                            HashSet<String> toAdd = hmap2.get(itemName);
                            hmap1.put(
                                    itemName, toAdd);
                        }

                    }
                    return hmap1;
                };
        Function<Object, Map<String, Set<String>>> finisher =
                obj -> (Map<String, Set<String>>) obj;

        Collector<Customer, ?, Map<String, Set<String>>> toItemAsKey =
                new CollectorImpl<>(supplier, accumulator, combiner, finisher, EnumSet.of(
                        Collector.Characteristics.CONCURRENT,
                        Collector.Characteristics.IDENTITY_FINISH));
        Map<String, Set<String>> itemMap = customerList.stream().parallel().collect(toItemAsKey);


        assertThat(itemMap.get("plane"), containsInAnyOrder("Chris"));
        assertThat(itemMap.get("onion"), containsInAnyOrder("Patrick", "Amy"));
        assertThat(itemMap.get("ice cream"), containsInAnyOrder("Patrick", "Steven"));
        assertThat(itemMap.get("earphone"), containsInAnyOrder("Steven"));
        assertThat(itemMap.get("plate"), containsInAnyOrder("Joe", "Martin"));
        assertThat(itemMap.get("fork"), containsInAnyOrder("Joe", "Martin"));
        assertThat(itemMap.get("cable"), containsInAnyOrder("Diana", "Steven"));
        assertThat(itemMap.get("desk"), containsInAnyOrder("Alice"));
    }


//    @Difficult @Test
//    public void bitList2BitString() {
//        String bitList = "22-24,9,42-44,11,4,46,14-17,5,2,38-40,33,50,48";
//        class Interval implements Comparable<Interval> {
//            int from;
//            int to;
//             public Interval(int from, int to) {
//                this.from = from;
//                this.to = to;
//            }
//            @Override
//            public int compareTo(Interval t) {
//              return Integer.compare(to, t.to);
//            }
//
//            public int getFrom() {
//                return from;
//            }
//
//            public int getTo() {
//                return to;
//            }
//
//
//        }
//        String[] pieces = bitList.split(",");
//        Function<String,Interval> transform2Interval =
//                piece -> {
//                    if (piece.contains("-")) {
//                       String[] subpieces = piece.split("-");
//                       return new Interval(Integer.parseInt(subpieces[0]),Integer.parseInt(subpieces[1]));
//                    } else {
//                       int n = Integer.parseInt(piece);
//                       return new Interval(n,n);
//                    }
//                };
//        Stream<Interval> intervalStream = Arrays.asList(pieces).stream().map(transform2Interval);
//        int maxbit = Arrays.asList((Interval[])(intervalStream.toArray())).stream()
//                .max(Comparator.naturalOrder())
//                .orElse(new Interval(0,0))
//                .getTo();
//        /**
//         * Create a {@link String} of "n"th bit ON.
//         * for example
//         * "3" will be "001"
//         * "1,3,5" will be "10101"
//         * "1-3" will be "111"
//         * "7,1-3,5" will be "1110101"
//         */
//        Supplier<String> supplier =
////                () ->
////                {Ch
////                for (int i=0;i<maxbit;i++)
//                () -> "";
//
//
//
//        Collector<Interval, ?, String> toBitString = null;
//        toBitString = new CollectorImpl<>(supplier, accumulator, combiner, finisher, EnumSet.of(
//                Collector.Characteristics.CONCURRENT,
//                Collector.Characteristics.IDENTITY_FINISH));
//
//
//
//
//
//        String bitString = Arrays.stream(bitList.split(",")).collect(toBitString);
//        assertThat(bitString, is("01011000101001111000011100000000100001110111010101")
//
//        );
//
//}
}
