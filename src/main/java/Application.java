import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        /**
         *  wraps Array into an Object that implements List Interface (Arrays.asList)
         */
        List<Device> devicesList =
                Arrays.asList(
                    new Device("iphone6", "phone", "Apple", 450.50f),
                    new Device("Xiaomi Mi 5", "phone", "Xiaomi", 250f),
                    new Device("Dell Inspiron", "laptop", "Dell", 950f),
                    new Device("iWatch", "wearable", "Apple", 350.50f),
                    new Device("Samsung Galaxy Tab 4", "tablet", "Samsung", 200.50f),
                    new Device("LG monitor 29'", "monitor", "LG", 300f));

        Map<String, List<Device>> devicesByBrand =
                devicesList
                        .stream()
                        .collect(Collectors.groupingBy(Device::getBrand));
        System.out.println(devicesByBrand);

        /**
         *  1) how we iterate a map
         *  2) devicesList.add will not work since it is not implemented by the Array wrapper, and also array cannot be
         *  modified
         */
        for (Map.Entry<String, List<Device>> entry : devicesByBrand.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }

        //devicesList.add(new Device("new Device","tablet", "LMS", 40f));
        ListIterator<Device> i = devicesList.listIterator();
        while (i.hasNext()){
            System.out.println(i.next().toString());
        }

        /**
        * Building a special collector that will create combinations of different device types
         * as "bundles"
        */
        Collector<Device, ArrayList<ArrayList<Device>>, ArrayList<ArrayList<Device>>> typeCombinatorCollector =
                Collector.of(
                        () -> new ArrayList<ArrayList<Device>>(),  // supplier
                        (container, device) -> {                   // accumulator
                            //try to put the device on an existing list
                            System.out.println(device);
                            ListIterator<ArrayList<Device>> containerIterator = container.listIterator();
                            boolean deviceAdded = false;
                            while (containerIterator.hasNext()){
                                ArrayList<Device> bundleList = containerIterator.next();
                                // if the device type does not exist in a List, add the device there
                                if (! bundleList.stream().filter(o -> o.getType().equals(device.getType())).findFirst().isPresent()) {
                                    bundleList.add(device);
                                    deviceAdded = true;
                                }
                            }
                            //if the device is not added, create a new list and put the device
                            if (!deviceAdded) {
                                ArrayList<Device> newBundleList = new ArrayList<Device>();
                                newBundleList.add(device);
                                container.add(newBundleList);
                            }
                        },
                        (j1, j2) -> { //combiner, no finisher
                            j1.addAll(j2);
                            return j1;
                        });

        ArrayList<ArrayList<Device>> bundles =
                devicesList
                    .stream()
                    .collect(typeCombinatorCollector);

        System.out.println(bundles);

        /**
         * TODO: filter by price and print
         */

    }
}
