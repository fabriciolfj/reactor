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
- utilizado para emitir condicionalmente um sinal.
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

- Alguns existem apenas no mono, como por exemplo doOnsucess().

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
```
    public static void main(String[] args) {
        delay().timeout(Duration.ofSeconds(2), fallback())
                .subscribe(Util.subscriber());
        
        Util.sleepSeconds(60);
    }
```
#### default if empty
- Emite um evento caso o publisher não tenha eventos, diante a uma condição por exemplo,.
```
    public static void main(String[] args) {
        Flux.range(1,100)
                .filter(i -> i > 101)
                .defaultIfEmpty(-100)
                .subscribe(Util.subscriber());
    }
```    
#### switchifempty
- a diferença deste como default if empty, que este aceita um fallback de eventos e não apenas um valor.
```
    public static void main(String[] args) {
        getOrderNumbers()
                .filter(i -> i > 12)
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber());
    }
```    

#### transform
- utilizado para aplicar operadores dinamicamente.
- utiliza-se uma function (java8) para retornar outro evento, exemplo: gostaria de enviar um flux e receber outro flux modificado, posso usar o transform.

#### switchOnFirst
- usa um condicional para desviar o fluxo a uma function, que pode realizar algum processo ou não. (alternativa ao transform)
```
    public static void main(String[] args) {
        getPerson()
                .switchOnFirst((signal, person) ->
                    signal.isOnNext() && signal.get().getAge() > 0 ? modify().apply(person) : person)
                .subscribe(Util.subscriber());
    }

    private static Function<Flux<Person>, Flux<Person>> modify() {
        return flux -> flux.filter(p -> p.getAge() > 10)
                    .doOnNext(p -> p.getName().toUpperCase())
                    .doOnDiscard(Person.class, d -> System.out.println("discarted : " + d));
    }

    private static Flux<Person> getPerson() {
        return Flux.range(1, 30)
                .map(p -> new Person());
    }
```    

#### flatmap
- cria um novo fluxo a partir de cada evento upstream (um-para-muitos mapeamentos, pega-se num fluxo upstream, e cria-se um fluxo a partir dele que pode ter múltiplos eventos)

#### next()
- retorna o primeiro evento apenas no fluxo.

### doOnNext()
- Este operador por ser declarado várias vezes em vários momentos, por exemplo:
  - Apos o flux.create:  vai emitir o evento antes do onNext declaro anterior ao subscriber (caso aja).
  - antes do subscribe: vai emitir um evento antes do inscrito receber o mesmo.
- O comportamento segue a questão da procedencia, ou seja, se declaro na criação do evento, esse será emitido antes do onNext definido na inscrição.  

### Cold Publisher
- precisa que alguem se inscreva no mesmo, para emitir eventos.
- quando um stream inicia sua emissão, o mesmo envia os eventos para todos os inscritos.

### Hot Publisher
- não necessita de inscritos para emitir eventos.

#### share
- converte um cold publisher em um hot publisher

#### publish refCount
- exige um número de inscritos para iniciar a emissão dos eventos, e são enviados apenas para estes.

#### autoConnect()
- define a quantidade de inscritos que receberam os eventos. Exemplo: caso informe 1, e tenha 2 inscritos, ambos receberam os eventos (primeiro se inscreve, o segundo ao se inscriver ele é reconectado aos eventos, por isso recebe também).
```
        Flux<String> movieStream = getMovies()
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .autoConnect(2);
```             
#### cache
- guarda o evento em memória, para os outros inscritos consumirem.
- No exemplo abaixo, o ultimo evento (no caso 4) é guardado na memória.
```
    public static void main(String[] args) {
        Flux<Object> flux = Flux.create(fluxSink -> {
            System.out.println("created");
            for (int i = 0; i < 5; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
        })
                .cache(1);
        System.out.println("primeiro");
        flux.subscribe(System.out::println);
        System.out.println("segundo");
        flux.subscribe(System.out::println);
        System.out.println("terceiro");
        flux.subscribe(System.out::println);
    }
```    

### Schedulers
- immedieate -> sua a thread corrente
- single -> utiliza outra thread  (não a principal), para efetuar a operação
- boundedElastic -> utiliza um pool de threads e reaproveita as mesmas que não estão sendo mais utilizadas (indicado caso queria realizar um block no fluxo)
- parallel -> usa um pool de threads

### Operadores usam scheduling
- subscribeOn (upstream) -> consome os eventos utilizando outra thread
- publishOn (downstream) -> publica os eventos utilizando outra thread

#### Combinando subscribeOn e publisherOn
- Quando combina-se o subscribeOn e publishOn, os eventos são criados no pool vinculado ao subscribeOn e consumidos no pool do puslisheOn

#### Parallel-execution
- Executa a operação de forma paralela (outra estratégia de processamento assíncrono), dividindo em diversas threads.

#### Diferença entre parallel-execution com operadores utilizando scheduling
- parallel -> quebra o processamento em diversas threads, mesmo com 1 inscrito no fluxo.
- operadores -> cada inscrito é executado em uma thread diferente.

#### Observação importante
- caso vincule subscribeOn a um flux.create, por exemplo, todo o processo  (map no caso abaixo) é executado no pool vinculado a este e não no pool vinculado ao inscrito.
```
        Flux<Object> flux = Flux.create(fluxSink -> {
            for (int i = 0; i < 5; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
        })
        .subscribeOn(Schedulers.boundedElastic());
        
        flux.subscribeOn(Schedulers.parallel())
            .map(i -> i + "a") -> é exeutado boundedElastic, ou seja, o primeiro subscribeOn que prevalece
            .subscribe(Util.subscriber()); -> é exeutado boundedElastic
```

### Backpressure
- Estratégia para linmitar a quantidade de eventos que o inscrito recebe, afim de não sobrecarrega-lo.
- Deve ser declarado antes do publishOn

#### Buffer
- limita o volume de dados a serem consumidos em memoria.

#### Drop
- cria uma fila, e enquanto esta estiver cheia, os novos itens são descartadas (usa a propriedade System.setProperty("reactor.bufferSize.small", "16"))

#### latest
- quandoa fila estiver cheia, mantém apenas o ultimo item, quando o próximo chegar, este é descardado.

#### error
- quando a fila encher, emite um error.

### Batching
- Quando se possui muitos eventos e queira quebra-los em lotes, utilizando o batching e este nos fornece 3 estratégias demonstradas abaixo:

#### buffer
- Armazena em memoria o uma quantidade armazenada, que será emitida
```

    public static void main(String[] args) {
        Flux.range(1, 10)
                .buffer(2)
                .subscribe(Util.subscriber());
    }
```

#### window
- Quebra em subfluxos, na quantidade configurada
```
    public static void main(String[] args) {
        Flux.range(1, 10)
                .window(2)
                .flatMap(e -> save(e))
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> save(Flux<Integer> flux) {
        return flux.doOnNext(e-> System.out.println("saving " + e))
                .doOnComplete(() -> {
                    System.out.println("saved this batch");
                });
    }
```

#### grouping
- Mediante um predicate (uma função que retorna um booleano),  divide o fluxo em 2 subfluxos, e estes possuem uma chave, simular ao hasmap.
```
    public static void main(String[] args) {
        Flux.range(1, 10)
                .groupBy(p -> p % 2)
                .subscribe(value -> print(value, value.key()));
    }

    private static void print(GroupedFlux<Integer, Integer> value, Integer key) {
        value.subscribe(p -> System.out.println(p + " key: " + key));
    }
```    

### THEN
- caso queira emitir um outro evento após o flux.
```
    public static void main(String[] args) {
        Flux.just(1, 10)
                .doOnNext(i -> System.out.println(i))
                .then(Mono.just("teste"))
                .subscribe(Util.subscriber());
    }
```    
### Repeat e Retry
- repeat -> repete a emissão do evento mediante configuração

```
    public static void main(String[] args) {
        random()
                .repeat(2).subscribe(Util.subscriber());
    }

    private static Flux<Integer> random() {
        return Flux.range(5, 5)
                .doOnComplete(() -> System.out.println("Completed"));
    }
```
- retry -> tenta consumir o evento novamente, diante a uma falha
```
    public static void main(String[] args) {
        random().retryWhen(
                //Retry.fixedDelay(2, Duration.ofSeconds(2))
                Retry.max(2)
        )
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> random() {
        return Flux.range(5, 10)
                .map(i -> {
                    var valor = Util.faker().random().nextInt(10);
                    System.out.println("Random: " + valor);
                    return i / valor;
                })
                .doOnSubscribe(s -> System.out.println("subscribe"))
                .doOnComplete(() -> System.out.println("--completed"))
                .doOnNext(value -> System.out.println("Next: " + value))
                .doOnError(value -> System.out.println("Error: " + value));
    }
```    

### SINK
- é um processador
- emite eventos (ou sinais) manualmente ou programaticamente
- Exemplo de uso no caso do create ou generated do flux.

### Sink one
- emite um mono, empty ou error.
- monto a emissão do evento de forma programática.
- pode possuir vários inscritos
```
    public static void main(String[] args) {
        var sink = Sinks.one();
        var mono = sink.asMono();

        sink.tryEmitValue("ola");
        mono.subscribe(Util.subscriber());
    }
```    

### Sink unicast
- Emite um ou vários eventos
- pode possuir apenas 1 inscrito
```
    public static void main(String[] args) {
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber());

        sink.tryEmitNext(1);
        sink.tryEmitNext(2);
        sink.tryEmitNext(3);

        //vai dar erro, pois permite apenas 1 inscrito
        flux.subscribe(Util.subscriber());

    }
```    

### Sink multicast
- Emite um ou vários eventos
- pode possuir vários inscritos
- Os inscritos após a emissão, não receberá os eventos
```
    public static void main(String[] args) {
        //buffer
        //var sink = Sinks.many().multicast().onBackpressureBuffer();
        //no history
        var sink = Sinks.many().multicast().directAllOrNothing();

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("ONE"));
        flux.subscribe(Util.subscriber("TWO"));

        sink.tryEmitNext(1);
        sink.tryEmitNext(2);
        sink.tryEmitNext(3);

        //não vai receber, pois se inscreveu após a emissão
        flux.subscribe(Util.subscriber("THREE"));

    }
```    

### Sink replay
- Emite um ou vários eventos
- pode possuir vários inscritos
- Os inscritos após a emissão, receberão os eventos
```
    public static void main(String[] args) {
        var sink = Sinks.many().replay().all(2);

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("ONE"));
        flux.subscribe(Util.subscriber("TWO"));

        sink.tryEmitNext(1);
        sink.tryEmitNext(2);
        sink.tryEmitNext(3);

        //vai receber os eventos, mesmo após a emissão
        flux.subscribe(Util.subscriber("THREE"));

    }
```

## CONTEXTS
- imutável
- ao inserir algum dado no contexto, o mesmo fica acessivel na app como um todo

```
    public static void main(String[] args) {
        getWelcome().contextWrite(Context.of("user", "fabricio"))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getWelcome() {
        return Mono.deferContextual(ctx -> {
            if(ctx.hasKey("user")) {
                return Mono.just("Welcome " + ctx.get("user"));
            }

            return Mono.error(new RuntimeException("unauthenticated"));
        });
    }
```
