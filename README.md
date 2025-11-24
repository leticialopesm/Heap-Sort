# Heap Sort – Análise de Complexidade de Tempo

Projeto da disciplina **Teoria da Computação** (CESAR School) com foco no estudo teórico e experimental do algoritmo **Heap Sort**, incluindo implementação em duas linguagens, análise de desempenho e relação com classes de complexidade.

---

## Objetivos do Projeto

- Descrever o algoritmo Heap Sort, sua ideia geral e estrutura de dados (heap binário).
- Apresentar o pseudocódigo e a lógica de construção e manutenção do heap.
- Classificar assintoticamente o tempo de execução com Big-O, Big-Ω e Big-Θ.
- Discutir a aplicabilidade prática: quando o Heap Sort é adequado ou não.
- Executar experimentos variando o tamanho das entradas e o número de execuções.
- Coletar métricas (tempo, comparações, trocas) e organizar em tabelas/gráficos.
- Comparar resultados teóricos e práticos, incluindo comparação entre linguagens.
- Relacionar o problema de ordenação com classes de complexidade (P, NP, NP-completo).

---

## Visão Geral do Algoritmo Heap Sort

O **Heap Sort** é um algoritmo de ordenação por comparação que utiliza um **max-heap** construído sobre o próprio array:

1. Construção do heap (heapify) a partir do array.
2. Repetidamente:
   - Troca do maior elemento (raiz) com a última posição do array.
   - Redução do tamanho lógico do heap.
   - Restauração da propriedade de heap.

**Complexidade de tempo:**

- Melhor caso: `O(n log n)`
- Caso médio: `O(n log n)`
- Pior caso: `O(n log n)`

**Complexidade de espaço:**

- Espaço auxiliar: `O(1)` (algoritmo in-place, sem uso significativo de memória extra).

---

## Implementações

O Heap Sort foi implementado em duas linguagens para comparação de desempenho:

- **Python**
  - Interpretada, alto nível, tipagem dinâmica.
  - Foco em legibilidade e rapidez de desenvolvimento.

- **Java**
  - Compilada, orientada a objetos, tipagem estática.
  - Foco em desempenho e robustez.

Essa combinação permite comparar diferenças entre ambientes interpretados e compilados, bem como o impacto da tipagem e da VM no tempo de execução.

---

## Estrutura do Repositório

```text
Heap-Sort/
├── java/        # Implementação do Heap Sort em Java e scripts de benchmark
├── python/      # Implementação do Heap Sort em Python e scripts de benchmark
└── results/     # Resultados de execução, CSVs e gráficos

## Como Executar

### Versão em Python

Pré-requisitos: Python 3.x instalado.

```bash
cd python
python3 main.py   # Ajustar para o nome real do arquivo principal
```

Em geral, o script:

- Gera vetores aleatórios de diferentes tamanhos.
- Executa o Heap Sort várias vezes por tamanho.
- Mede o tempo e, quando implementado, o número de comparações e trocas.
- Exporta os resultados para arquivos em `results/`.

### Versão em Java

Pré-requisitos: JDK instalado (recomendado 17+).

```bash
cd java
javac Main.java  
java Main
```

O comportamento é análogo à versão em Python: geração de dados, execução repetida do algoritmo e registro de tempos/estatísticas, com saída em arquivos de resultados.

---

## Resultados Principais (Resumo)

Com base nos experimentos descritos no relatório:

- O crescimento do tempo de execução segue o comportamento esperado de `O(n log n)`.
- Há boa aderência entre os dados medidos e funções do tipo `T(n) = c · n log n + k`.
- A implementação em **Java** geralmente apresenta tempo de execução menor que a versão em **Python**, refletindo a vantagem de um ambiente compilado/otimizado.
- Diferenças entre melhor, médio e pior caso são relativamente pequenas, evidenciando a robustez do Heap Sort quanto a variações na entrada.

---

## Aspectos de Teoria da Complexidade

- O problema de ordenação resolvido pelo Heap Sort é tratável em **tempo polinomial**, logo pertence à classe **P**.
- O algoritmo é determinístico e tanto sua execução quanto a verificação da ordenação podem ser feitas em tempo polinomial.
- O problema de ordenar uma sequência não é NP-completo, embora existam problemas relacionados à ordenação (por exemplo, problemas de escalonamento ou alocação) que são NP-completos.

---

## Quando Utilizar Heap Sort

Recomendado quando:

- Há limitação de memória (algoritmo in-place).
- É importante garantir complexidade de tempo sempre `O(n log n)`.
- O volume de dados é médio ou grande.
- Estabilidade na ordenação não é um requisito.

Menos indicado quando:

- O conjunto de dados é muito pequeno (algoritmos simples podem ser mais rápidos).
- Os dados já estão quase ordenados (algoritmos adaptativos podem ser preferíveis).
- Estabilidade é obrigatória.

---

## Relatório

Uma análise completa teórica e experimental, com:

- Descrição detalhada do algoritmo;
- Pseudocódigo;
- Tabelas de desempenho;
- Gráficos de tempo de execução;
- Discussão dos resultados em função da complexidade assintótica;

está disponível no relatório em PDF incluído no projeto:  
`Relatorio-Heap-Sort.pdf`.

---

## Equipe

- Brandon Hunt – boh@cesar.school  
- Hugo Rocha – har3@cesar.school  
- Leticia Lopes – llm3@cesar.school  
- Lucas Rosati – lrcp@cesar.school  