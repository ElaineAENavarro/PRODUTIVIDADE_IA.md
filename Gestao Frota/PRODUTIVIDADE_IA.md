# PRODUTIVIDADE_IA.md
## Atividade — O Tradutor de Eras | Java 7 → Java 21

---

## 1. Código Legado (Java 7 — Insumo)

```java
List<Motorista> motoristas = getMotoristaDaEmpresa();

List<Motorista> habilitados = new ArrayList<>();

// Filtrando motoristas com CNH ativa
for (Motorista m : motoristas) {
    if (m.isCnhAtiva()) {
        habilitados.add(m);
    }
}

// Ordenando por tempo de casa (anos) usando Comparator anônimo
Collections.sort(habilitados, new Comparator<Motorista>() {
    @Override
    public int compare(Motorista m1, Motorista m2) {
        return Integer.compare(m1.getAnosEmpresa(), m2.getAnosEmpresa());
    }
});
```

---

## 2. Código Modernizado (Java 21)

```java
List<Motorista> habilitados = getMotoristaDaEmpresa().stream()
    .filter(Motorista::isCnhAtiva)                                      // (1) Method Reference no lugar do for + if
    .sorted(Comparator.comparingInt(Motorista::getAnosEmpresa))         // (2) Comparator declarativo
    .collect(Collectors.toList());                                       // (3) coleta o resultado
```

Ou, se apenas quiser imprimir sem coletar:

```java
getMotoristaDaEmpresa().stream()
    .filter(Motorista::isCnhAtiva)
    .sorted(Comparator.comparingInt(Motorista::getAnosEmpresa))
    .forEach(System.out::println);                                       // (4) Method Reference no println
```

### O que mudou e por quê

| # | Java 7 (legado) | Java 21 (moderno) | Por que é melhor |
|---|---|---|---|
| 1 | `new ArrayList<>()` + `for` + `if (m.isCnhAtiva())` | `.filter(Motorista::isCnhAtiva)` | Elimina lista temporária; intenção expressada em uma palavra |
| 2 | `new Comparator<Motorista>() { @Override public int compare... }` | `Comparator.comparingInt(Motorista::getAnosEmpresa)` | Sem classe anônima de 5 linhas; legível como texto natural |
| 3 | Lista intermediária `habilitados` criada manualmente | Pipeline encadeado sem variáveis temporárias | Menos variáveis = menos superfície para bugs |
| 4 | Necessita `Collections.sort()` separado | `.sorted()` já faz parte do pipeline | Tudo numa cadeia contínua e coesa |

---

## 3. Relato do Aprendizado

O principal insight obtido foi compreender que a Stream API altera a forma de pensar o código, mudando de um paradigma imperativo para um paradigma mais declarativo. No Java 7, a lógica é construída descrevendo como executar cada etapa do processo, como criar listas, percorrer elementos, aplicar validações, adicionar itens e realizar ordenações. Já no Java 21, com o uso da Stream API, o foco passa a ser o que se deseja obter como resultado, por exemplo: selecionar motoristas com CNH ativa e ordená-los pelo tempo de empresa.

Outro ponto importante foi perceber como o uso de Method References contribui para a legibilidade e manutenção do código. Expressões como Motorista::isCnhAtiva tornam a intenção da regra de negócio mais clara, tornando o código praticamente autodocumentado. Dessa forma, comentários explicativos, como // Filtrando motoristas com CNH ativa, tornam-se menos necessários, pois a própria implementação já comunica de forma objetiva o que está sendo realizado.

---

## 4. Prompt do Tradutor de Eras (Passo 1)

```
Atue como um engenheiro de software sênior especializado em Java.
Tenho um código legado que usa Java 7 (for-each e Comparator anônimo)
para filtrar motoristas com CNH ativa e ordená-los por tempo de casa
usando getAnosEmpresa().

Reescreva o código para Java 21, utilizando Stream API e Method References.
Explique cada mudança realizada e por que a nova versão é mais segura
e mais fácil de manter.
```

---

## 5. Prompt do Desafio Socrático (Passo 2)

```
Agora, explique-me: se eu quisesse ordenar os motoristas primeiro pelo tempo de empresa e, em caso de empate, pelo nome em ordem alfabética, como essa lógica deveria ser estruturada? Não me dê o código, explique apenas o raciocínio.
```

### Resposta da IA (lógica explicada)

---
Para ordenar os motoristas primeiro pelo tempo de empresa e, em caso de empate, pelo nome em ordem alfabética, a lógica da ordenação precisaria considerar dois critérios sequenciais.

O primeiro critério continuaria sendo o tempo de empresa, garantindo que os motoristas sejam organizados conforme a quantidade de anos na empresa. Entretanto, quando dois ou mais motoristas possuírem o mesmo tempo de empresa, seria necessário aplicar um segundo critério de desempate: a ordenação pelo nome em ordem alfabética.

Em termos de raciocínio, a ordenação deixaria de analisar apenas um único atributo e passaria a funcionar de forma hierárquica. Primeiro, o sistema verificaria o tempo de empresa; se houver diferença entre os valores, essa informação definiria a posição do motorista na lista. Caso os valores sejam iguais, a lógica seguiria para a próxima comparação, utilizando o nome como critério secundário.

Essa abordagem torna a ordenação mais consistente e previsível, além de facilitar a manutenção do código, pois permite incluir novos critérios futuramente sem alterar a lógica principal do processamento.

---

