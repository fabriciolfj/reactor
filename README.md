# Reactor
- Este repositório tem por objetivo explanar os conceitos envolvemente programação reativa com reactor.

## Programação reativa
- Programação reativa possui como pilar, os seguintes atores:
  - Publisher: dono dos dados, que produz as informações
  - Processor: que envia para os assinantes os dados
  - Subscriber: quem assina o publicador
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
- Formas de emissão:
  - Flux.fromStream(passa um stream)
  - Flux.range(inicio, quantidade)
  - Flux.fromIterable(List.of(1,1,2))
  - Flux.create -> personalizao a forma de emitir, dar error e completar o fluxo.
  - Flux.generate -> similar ao create, mas posso apenas emitir um evento por vez
  - Flux.puhs -> não é thread safe.
