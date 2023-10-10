package assignment.reactivejointwostring;

import reactor.core.publisher.Flux;


public class ShoppingCartExample {

    public static void main(String[] args) {
        Flux<Item> shoppingCart = Flux.just(
                new Item("Laptop", 1000),
                new Item("Phone", 500),
                new Item("Headphones", 100)
        );

        Flux<Item> processedCart = shoppingCart.flatMap(item ->
                applyDiscount(item)
                        .flatMap(ShoppingCartExample::checkAvailability)
                        .map(ShoppingCartExample::calculateFinalPrice)
        );

        processedCart.subscribe(item -> System.out.println("Processed: " + item));
    }

    private static Flux<Item> applyDiscount(Item item) {
        return Flux.just(new Item(item.getName(), item.getPrice() * 0.9));
    }

    private static Flux<Item> checkAvailability(Item item) {
        return Flux.just(item);
    }

    private static Item calculateFinalPrice(Item item) {
        return new Item(item.getName(), item.getPrice());
    }

    static class Item {
        private final String name;
        private final double price;

        public Item(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}