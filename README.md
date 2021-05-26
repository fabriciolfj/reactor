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
