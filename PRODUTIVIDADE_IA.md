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

O insight mais importante foi perceber que a **Stream API muda o paradigma de raciocínio**:
no Java 7 o código descreve *como* fazer (crie lista, percorra, verifique, adicione, ordene);
no Java 21 o código descreve *o que* se quer (motoristas com CNH ativa, ordenados por anos
na empresa). Além disso, a IA explicou que Method References como `Motorista::isCnhAtiva`
tornam o código **autodocumentado** — o comentário `// Filtrando motoristas com CNH ativa`
do código legado se torna desnecessário porque o próprio código já diz isso com clareza.

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
Agora, explique-me: se eu quisesse que esse filtro também removesse
motoristas que não possuem um Optional de seguro ativo, como eu
alteraria essa Stream? Não me dê o código, explique-me a lógica.
```

### Resposta da IA (lógica explicada)

A IA explicou que bastaria encadear um segundo `.filter()` no pipeline,
logo após o `.filter(Motorista::isCnhAtiva)`. Dentro desse segundo filtro,
usaria-se `m.getSeguro().isPresent()` — onde `getSeguro()` retorna um
`Optional<Seguro>`. A lógica é: `Optional.isPresent()` retorna `true`
apenas quando o valor não é nulo, então motoristas sem seguro cadastrado
são descartados automaticamente, sem risco de `NullPointerException`.
O pipeline ficaria: filtrar CNH ativa → filtrar seguro presente → ordenar
→ coletar. Cada filtro tem uma responsabilidade clara e pode ser lido
como uma frase em português.

---

