package assignment.reactivejointwostring;

import org.springframework.aop.scope.ScopedProxyUtils;
import reactor.core.publisher.Flux;

public class MergeAndTransformStrings {
    public static void main(String[] args) {
        Flux<String> flux1 = Flux.just("Hello,", "Welcome To");
        Flux<String> flux2 = Flux.just("A", "new World!");


        System.out.println("Merge With:");
        Flux<String> mergedAndTransformed = Flux.merge(flux1, flux2)
                .map(String::toUpperCase);

        String result = mergedAndTransformed
                .collectList()
                .map(strings -> String.join(" ", strings))
                .block();

        System.out.println(result);


        System.out.println("Zip With: ");
        Flux<String> zip = flux1.zipWith(flux2, (s1, s2)-> s1+s2)
                .map(String::toUpperCase);

        result = zip
                .collectList()
                .map(strings -> String.join(" ", strings))
                .block();

        System.out.println(result);


        System.out.println("Concat With:");
        Flux<String> concat = flux1.concatWith(flux2)
                .map(String::toUpperCase);

        result = concat
                .collectList()
                .map(strings -> String.join(" ", strings))
                .block();

        System.out.println(result);
    }
}

