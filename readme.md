# Analisador de Expressões Matemáticas · Kotlin + Regex

Pequeno projeto‐laboratório feito para praticar **Kotlin**, expressões regulares e recursão.
O programa recebe qualquer string contendo números decimais, parênteses e os quatro operadores básicos (`+ − * /`) e devolve o resultado **já corrigindo**:

1. Parênteses desbalanceados
2. Sequências de sinais repetidos (`-- → +`, `+- → −` etc.)
3. Espaços em branco ou quebras de linha

---

## ✅ Funcionalidades

| Recurso                          | Detalhes                                                            |
| -------------------------------- | ------------------------------------------------------------------- |
| **Suporte a operadores**         | Soma, subtração, multiplicação e divisão                            |
| **Prioridade correta**           | Parênteses ➜ multiplicação/divisão ➜ soma/subtração                 |
| **Números negativos e decimais** | Qualquer combinação de `-`/`+` na frente, `123`, `-4.5`, `.8`, `6.` |
| **Correção de parênteses**       | Adiciona os que faltam no início ou fim da expressão                |
| **“Jogo de sinais”**             | Simplifica `--`, `++`, `+-`, `-+` durante o parsing                 |
| **Histórico de resoluções**      | Cada simplificação é armazenada em `historic` (conjunto de strings) |
| **API simples**                  | Função pública `getResult(expr: String): Double`                    |

---

## 🗂️ Estrutura

```
Analisador-de-expressao/
├── src/main/kotlin/
│   └── main.kt            # Toda a lógica (≈ 250 linhas)
├── readme.md              # (este arquivo)
└── out/…                  # Saída compilada pelo IntelliJ
```

**Pontos‐chave do código**

| Local                                     | O que faz                                                                                                                  |
| ----------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- |
| `object RegexExp`                         | Declara todas as regex necessárias: números, operadores, parênteses, etc.                                                  |
| `searchExpressions()`                     | Função recursiva central: identifica o próximo padrão a resolver e delega para `calculateMulDiv()` ou `calculateAddSub()`. |
| `calculateMulDiv()` / `calculateAddSub()` | Convertem os dois operandos em `Double`, fazem a operação e substituem o trecho pela resposta.                             |
| `fixParentheses()`                        | Conta `(` e `)`; injeta os que faltam na ponta correta antes de começar a recursão.                                        |
| `sinalGame()`                             | Resolve dois sinais seguidos conforme regra matemática.                                                                    |
| `getResult()`                             | Função pública; recebe string bruta, normaliza, devolve `Double`.                                                          |
| `main()`                                  | Exemplo simples: define `expression`, imprime resultado e o histórico.                                                     |

---

## ▶️ Como executar

### Usando o Kotlin CLI

```bash
# Compila
kotlinc src/main/kotlin/main.kt -include-runtime -d analisador.jar

# Roda o exemplo do main()
java -jar analisador.jar
```

Para usar como biblioteca em outro código Kotlin:

```kotlin
import RegexExp   // já no default package
fun main() {
    val exp = "2*(-3+8)/2"
    println(getResult(exp))  // → 5.0
}
```

### Via IntelliJ IDEA

1. **File ▸ Open** e aponte para o diretório do projeto.
2. Marque **src/** como *Sources Root* caso não esteja.
3. Clique em **Run MainKt**.

---

## 🔎 Exemplos rápidos

| Expressão de entrada | Resultado | Observações                                 |
| -------------------- | --------- | ------------------------------------------- |
| `-6/)(-2*(2+1)`      | `1.0`     | Corrige parênteses extra `)` e faltante `(` |
| `5+-+4*--2`          | `13.0`    | Simplifica sinais e aplica precedência      |
| `(.5 + .5)*2`        | `2.0`     | Suporte a números sem dígito inteiro        |

---

## ⚠️ Limitações

* Apenas os quatro operadores básicos – sem potência, resto ou funções.
* Trabalha com `Double`; números muito grandes podem perder precisão.
* Não há tratamento para divisão por zero (será lançada exceção).

---

## 📄 Licença

Escolha livre; recomendo [MIT License](https://opensource.org/licenses/MIT) para facilitar estudo e forks.

---

> Projeto acadêmico/experimental – sugestões e *pull requests* são muito bem‐vindos!
