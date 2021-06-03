# Reactor
- Este repositório tem por objetivo explanar os conceitos envolvemente programação reativa com reactor.

## Programação reativa
- Programação reativa possui como pilar, os seguintes atores:
  - Publisher: dono dos dados, que produz as informações
  - Processor: que envia para os assinantes os dados
  - Subscriber: quem assina o publicador, que solicita os dados através da inscrição (subscription). Nesse ponto que podemos utilizar o backpressure.
  - Subscription: assinatura, responsável em solicitar o processo dado ou cancelar ao publicador.

- Fluxo:
```
publisher -> processor -> subscribe
subscrive -> subscription -> publisher
```
#### Subscribe
- É composto por:
  - onNext -> próximo evento recebido
  - onComplete -> emitido quando acaba-se os eventos
  - onError -> quando ocorre algum problema ao consumir o evento

#### Subscription
- Composta por:
  - request -> pega pedi o próximo evento
  - cancel -> cancela  inscrição e não recebe mais nenhum envento.

#### Publisher
- Hot -> emitie eventos, sem ninguem se inscrever
- cold -> emite eventos, quando um subscriber se inscrever.

### Mono
- Emite um único evento
- Formas de emissão:
  - Mono.fromSupplier -> usa um fornecedor para emitir um evento
  - Mono.fromCallable -> usa um fornecedor para emitir um evento ou lança uma exceção caso não consiga.
   
#### Mono async
- Uma forma de se subscrever em um evento, utiliza-se:
```
subscribeOn(Schedulers.boundedElastic()).subscribe()
```

### Flux
- Emite um ou mais eventos.

#### Flux - formas de criações
- Flux.fromStream(passa um stream)
- Flux.range(inicio, quantidade)
- Flux.fromIterable(List.of(1,1,2))
- Flux.create -> personalizao a forma de emitir, dar error e completar o fluxo.
- Flux.generate -> similar ao create, mas posso apenas emitir um evento por vez
- Flux.puhs -> não é thread safe.
- Flux.interval -> gerando dados dia um time.
- Flux.array -> gera eventos diante um array

#### Flux - formas de combinação
- mergeWith -> agrupa 2 flux em 1
- zip -> combinado 2 flux em 1, e neste com dados mesclados ou em tuples

### Operadores
##### handle
- permite manipular o evento, de forma sincrona. Exemplo:
```
    public static void main(String[] args) {
        Flux.range(1,10)
                .handle((integer, sync) -> {
                    if(integer == 7) {
                        sync.complete();
                    } else {
                        sync.next(integer);
                    }
        }).subscribe(Util.subscriber());
    }
```
##### do (callback)
- retorno da chamada em momentos diferentes como: cada evento, após completar o flux, após o erro e etc.
- Exemplo:

```
    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            System.out.println("created");
            for(int i =0; i < 5; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
            System.out.println("complete");
        }).doOnComplete(() -> System.out.println("doOnComplete")) //chama no fim do fluxo, seja concluido pelo consumo ou cancelada a inscrição
                .doFirst(() -> System.out.println("doFirst 1")) //primeiro evento
                .doOnNext(o -> System.out.println("doOnNext: " + o)) //retorna a cada evento
                .doOnSubscribe(s -> System.out.println("doOnSubscribe " + s)) //momento da inscrição no publisher
                .doOnError(err -> System.out.println("doOnError: " + err.getMessage()))
                .doOnTerminate(() -> System.out.println("doOnTerminate")) // chama apos o fim do complete
                .doOnCancel(() -> System.out.println("doOnCancel")) //retorna no momento do cancelamento da inscrição no publisher
                .doFinally(signalType -> System.out.println("doFinally: " + signalType)) // chama apos o terminate
                .doOnDiscard(Object.class, o -> System.out.println("doOndiscard: " + o)) // são eventos emtidos após o cancelamento da inscrição no publisher
                .doFirst(() -> System.out.println("doFirst 2")) //primeiro evento, nao importa o local
                .subscribe(Util.subscriber());
    }
```  

##### limitRate
- Restringe uma quantidade de eventos, no request dá subscripiton
- Diferente do take, a inscrição não é cancelada.
- Podemos também controlar o volume de requisição, no exemplo abaixo o primeiro request será de 100 e depois será em 99% (sobre 100), até que os eventos termine.
```
    public static void main(String[] args) {
        Flux.range(1,1000)
                .log()
                .limitRate(100, 99)
                .subscribe(Util.subscriber());
    }
```
##### delayElements
- atrasar, por um tempo configurado, o tempo de produção dos eventos.

##### onError
- Mecanismo para deixar a pipeline reativa mais resiliente.
- Exemplos: onErrorReturn, onErrorMap, onErrorResume e etc.
```
    public static void main(String[] args) {
        Flux.range(1, 10)
                .log()
                .map(i -> 10 / (5 - i))
                //.onErrorReturn(-1)
                .onErrorResume(throwable -> fallback())
                .subscribe(Util.subscriber());
    }

    private static Mono<Integer> fallback() {
        return Mono.fromSupplier(() -> Util.faker().random().nextInt(100, 200));
    }
```    
#### timeout
- Espera o evento diante um time esperado, caso passe, chama um fallback (também configurado)
