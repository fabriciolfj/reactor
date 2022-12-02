package com.github.fabriciolfj.reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class CombineTest {

    private int min = 1;
    private int max = 5;

    Flux<Integer> evenNumbers = Flux
            .range(min, max)
            .filter(x -> x % 2 == 0); // i.e. 2, 4

    Flux<Integer> oddNumbers = Flux
            .range(min, max)
            .filter(x -> x % 2 > 0);  // ie. 1, 3, 5

    @Test
    @DisplayName("Similar ao zip, mas aceita apenas 2 publicadores")
    public void givenFluxes_whenZipWithIsInvoked_thenZipWith() {
        Flux<Integer> fluxInteger = evenNumbers.zipWith(oddNumbers, (a, b) -> a + b);

        StepVerifier.create(fluxInteger)
                .expectNext(3)
                .expectNext(7)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Transforma 2 eventos, gerando um novo. Percorre o numero de eventos iguais, caso um seja maior que o outro, as posicoes acima seram ignoradas")
    public void givenFluxes_whenZipIsInvoked_thenZip() {
        Flux<Integer> integerFlux = Flux.zip(evenNumbers, oddNumbers,
                (a, b) -> a + b);

        StepVerifier.create(integerFlux)
                .expectNext(3)
                .expectNext(7)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Embaralha os eventos, emite um erro caso o atraso passe o valor fornecido")
    public void givenFluxes_whenMergeWithDelayedElementsIsInvoked_thenMergeWithDelayedElements() {
        Flux<Integer> fluxOfIntegers = Flux.mergeDelayError(1,
                evenNumbers.delayElements(Duration.ofMillis(900L)),
                oddNumbers.delayElements(Duration.ofMillis(300L)));

        StepVerifier.create(fluxOfIntegers)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(5)
                .expectNext(4)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Os eventos ficaram na ordem de assinatura")
    public void testMergeSequential() {
        Flux<Integer> fluxOfIntegers = Flux.mergeSequential(
                evenNumbers,
                oddNumbers);

        StepVerifier.create(fluxOfIntegers)
                .expectNext(2)
                .expectNext(4)
                .expectNext(1)
                .expectNext(3)
                .expectNext(5)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Embaralha os eventos")
    public void givenFluxes_whenMergeIsInvoked_thenMerge() {
        Flux<Integer> fluxOfIntegers = Flux.merge(evenNumbers, oddNumbers);

        StepVerifier
                .create(fluxOfIntegers)
                .expectNext(2)
                .expectNext(4)
                .expectNext(1)
                .expectNext(3)
                .expectNext(5)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Pega o ultimo elemento publicado pela primeira fonte")
    public void givenFluxes_whenCombineLatestIsInvoked_thenCombineLatest() {
        Flux<Integer> fluxOfIntegers = Flux.combineLatest(evenNumbers, oddNumbers,
                (a, b) -> a + b);

        StepVerifier.create(fluxOfIntegers)
                .expectNext(5)
                .expectNext(7)
                .expectNext(9)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Se inscreve na primeira fonte, quando terminar, se inscreve na próxima")
    public void givenFluxes_whenConcatIsInvoked_thenConcat() {
        Flux<Integer> fluxOfIntegers = Flux.concat(evenNumbers, oddNumbers);

        StepVerifier.create(fluxOfIntegers)
                .expectNext(2)
                .expectNext(4)
                .expectNext(1)
                .expectNext(3)
                .expectNext(5)
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Produz uma fonte que é a junção das 2")
    public void givenFluxes_whenConcatIsInvoked_thenConcatWith() {
        Flux<Integer> fluxOfIntegers = evenNumbers.concatWith(oddNumbers);

        StepVerifier.create(fluxOfIntegers)
                .expectNext(2)
                .expectNext(4)
                .expectNext(1)
                .expectNext(3)
                .expectNext(5)
                .expectComplete()
                .verify();

    }
}
