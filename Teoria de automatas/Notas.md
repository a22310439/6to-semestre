# Parcial 1
## ¿Qué es un autómata?
Es una máquina que puede interpretar una palabra o serie de palabras y detectar que pertenece a un lenguaje específico.

## ¿Qué es un lenguaje?
Un conjunto de palabras.

**Conjunto de palabras**

## ¿Qué es la gramática?
Un conjunto de reglas que determinan cómo combinar símbolos para formar palabras.

## Relación
El lenguaje tiene las palabras que después la gramática le da el orden o la estructura para que las palabras puedan ser interpretadas correctamente. El Autómata identifica la palabra o secuencia de palabras y determina si es válida la oración. El autómata también retroalimenta al lenguaje.

## ¿Qué es una cadena o palabra?
Una secuencia finita de símbolos juxtapuestos.

$W_0 = 110001$

$W_1 = abba$

## ¿Qué es un alfabeto? ($\epsilon$)
Un conjunto finito de signos.

$\Sigma _0 = \{0, 1\}$

$\Sigma _1 = \{a, b\}$

## Operaciones
### Cardinalidad
$|W_0| = 6$

$|W_1| = 4$

$|\Sigma _0| = 2$

$|\Sigma _1| = 2$

### Cadena vacía
$|\epsilon| = 0$

### Combinaciones del alfabeto
$\Sigma ^* = \{\epsilon,a ,b aa, ab, ba, bb, aaa, aab, ...\}$

#### Cardinalidad de la combinación:
$|\Sigma ^*| = \infty$

## Concatenación
$w = ab \quad v = ba \quad x = aaa$

$wvx = abbaaa$

$(wv)x = w(vx) = wvx = abbaaa$

Es asociativa, pero no conmutativa

### Elemento neutro
$w\epsilon = \epsilon w = w$

## Modular
$|wvx| = |w| + |v| + |x|$

## Potencias
$A^0 = \{\epsilon\}$

$A^{n+1} = A^nA$

$A^2 = A^1A = A^0AA$

$ A = \{a\} \rightarrow A^2 = \{\epsilon\}\{a\}\{a\} = \{aa\}$

##

$w = ab$

$w^2 = abab$

$w^3 = ababab$

$w^4 = abababab$

En realidad la potencia es una concatenación

## Inversa
$w = ab$

$w^I = ba$

## Lenguajes simples
### {}, {$\epsilon$}, {a}, {b}

## Unión de lenguajes ($\cup$)
$a \cup B = \{w \in \Sigma *| w \in A \vee w \in B\}$

$\{aa,ab\} \cup \{ab, ba\} = \{aa, bb, ab, ba\}$

## Intersección de lenguajes ($\cap$)
$A \cap B = \{w \in \Sigma * | w \in A \wedge w \in B\}$

$\{aa,ab\} \cap \{ab, ba\} = \{ab\}$

## Concatenación de lenguajes
$AB = \{wv \in \Sigma ^* | w \in A \wedge v \in B\}$

$\{aa,ab\} \{ab, ba\} = \{aaab, aaba, abab, abba\}$

## Cerradura de KLEENE (*)
$L^* = \cup L^n | n \in N0 = L^0L^1L^2L^3L^4....$

$\{a\}^* = \{\epsilon,a , aa, aaa, aaaa, ....\}$

$\{\epsilon\}^* = \{\epsilon\}$

## Cerradura Positiva
$L^+ = LL^*$

$L^+ = L^0L^1L^2L^3L^4....$

## Prefijo
$v$ es prefijo de $w$ si existe una x $\in \Sigma * | vx = w$

Ejemplo: Alexa

$\epsilon \quad$ Alexa

A $\quad$ lexa

Al $\quad$ exa

Ale $\quad$ xa

Alex $\quad$ A

Alexa $\quad \epsilon$

## Ejecricios
<table>
  <thead>
    <tr>
      <th>A, |A|, B, |B|</th>
      <th>Unión</th>
      <th>Intersección</th>
      <th>~A</th>
      <th>A - B</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Máximo</td>
      <td>|A| + |B|</td>
      <td>min(|A|,|B|)$</td>
      <td>|U| - |A|</td>
      <td>|A|</td>
    </tr>
    <tr>
      <td>Mínimo</td>
      <td>max(|A|,|B|)</td>
      <td>0</td>
      <td>U</td>
      <td>|A| - min(|A|,|B|)</td>
    </tr>
  </tbody>
</table>

##
$L1 = \{\epsilon, a, aa\}$

$L2 = \{bb, ab\}$

$L3 = \{\epsilon, a, ab, bb\}$

$L4 = \{aa, ab, ba, bb\}$

Calcular: $L3^3, L2^3, L3^3L2^3, (L4L1)^2, L4^*, L3^*, L4^+, ((L4L1)^3(L3^2L2^4))^0$

##
$L3^3 = L3^0L3L3L3 = \{\epsilon, a, ab, bb\}\{\epsilon, a, ab, bb\}\{\epsilon, a, ab, bb\} = \{\epsilon, a, ab, bb\}\{\epsilon, a, aa, ab, bb, aab, aba, abb, bba, abab, abbb, bbab, bbbb\} = \{\epsilon, a,ab,bb,aa,aab,abb,aba,abab,abbb,bba,bbab,bbbb, aaa,aaab,aabb,aaba,aabab,aabbb,abba,abbab,abbbb, abaa,abaab,ababb,ababa,ababab,ababbb,abbba,abbbab,abbbbb, bbaa,bbaab,bbaabb,bbaba,bbabab,bbabbb,bbbba,bbbbab,bbbbbb\}$
​
$L2^3 = L2^0L2L2L2 = \{bb, ab\}\{bb, ab\}\{bb, ab\} = \{bb, ab\}\{bbbb, bbab, abbb, abab\} = \{bbbbbb, bbbbab, bbabbb, bbabab, abbbbb, abbbab, ababbb, ababab\}$

$L3^3L2^3 = \{\epsilon, a,ab,bb,aa,aab,abb,aba,abab,abbb,bba,bbab,bbbb, aaa,aaab,aabb,aaba,aabab,aabbb,abba,abbab,abbbb, abaa,abaab,ababb,ababa,ababab,ababbb,abbba,abbbab,abbbbb, bbaa,bbaab,bbaabb,bbaba,bbabab,bbabbb,bbbba,bbbbab,bbbbbb\}\{bbbbbb, bbbbab, bbabbb, bbabab, abbbbb, abbbab, ababbb, ababab\} = \{uv | u \in L3^3, v \in L2^3\}$

$(L4L1)^2 = (aa, ab, ba, bb, aaa, aab, aba, abb, aaaa, aaab, aaba, aabb)^2 = \{uv | u,v \in L4L1\}$

$L4^* = L4^0L4^1l4^2L4^3... = \{\cup L4^n | n \in N0\} = \{\epsilon\} \cup \{aa, ab, ba, bb\} \cup \{xy | x, y \in \{aa, ab, ba, bb\}\} \cup \{xyz | x, y, z \in \{aa, ab, ba, bb\}\} \cup ...$

$L3^* = L3^0L3^1L3^2L3^3... = \{\cup L3^n | n \in N0\} = \{\epsilon\} \cup \{\epsilon, a, ab, bb\} \cup \{xy | x, y \in \{\epsilon, a, ab, bb\}\} \cup \{xyz | x, y, z \in \{\epsilon, a, ab, bb\}\} \cup ...$

$L4^+ = L4^1L4^2L4^3... = \{aa, ab, ba, bb\} \cup \{xy | x, y \in \{aa, ab, ba, bb\}\} \cup \{xyz | x, y, z \in \{aa, ab, ba, bb\}\} \cup ...$

$((L4L1)^3(L3^2L2^4))^0 = \text{Todo valor elevado a la 0 es } \{\epsilon\}$

## Gramáticas
$\Epsilon = \{a, b, c\}$

$S \rightarrow aS$ (Regla de producción)

$S \rightarrow bS$

$S \rightarrow cS$

$S \rightarrow \epsilon$

Las reglas de producción sólo se pueden usar con los símbolos del alfabeto

Primeras 10 palabras con las reglas de producción junto con su 

$\epsilon = S \Rightarrow \epsilon, \quad a = S \Rightarrow aS \Rightarrow a \epsilon, \quad b = S \Rightarrow bS \Rightarrow b \epsilon, \quad c = S \Rightarrow cS \Rightarrow c \epsilon$ 

$aa = S \Rightarrow aS \Rightarrow aaS \Rightarrow aa \epsilon, \quad ab = S \Rightarrow aS \Rightarrow abS \Rightarrow ab \epsilon, \quad ac = aS \Rightarrow acS \Rightarrow ac \epsilon$

$ba = S \Rightarrow bS \Rightarrow baS \Rightarrow ba \epsilon, \quad bb = S \Rightarrow bS \Rightarrow bbS \Rightarrow bb \epsilon, \quad bc = S \Rightarrow bS \Rightarrow bcS \Rightarrow bc \epsilon$

$\{\epsilon, a, b, c, aa, ab, ac, ba, bb, bc\}$

## Definicón formal de gramática
$G = \{N, \Sigma, S, P\}$

$N \cap \Sigma = 0$

N = Símbolos no terminales

$\Sigma$ = Alfabeto conocido

S = Símbolo no terminal especial que nos indica el inicio para todas las producciones (Simbolo inicial)

P = Conjunto de reglas de producción

Siguiendo el ejemplo anterior, tenemos todo lo necesario para definir completamente a G:

$G = (\{S\}, \{a, b, c\}, S, \{S \rightarrow aS|bS|cS|\epsilon\})$

$\{S \rightarrow aS|bS|cS|\epsilon\} \Rightarrow$  notación de Backus Naur

### Ejercicio
$\Sigma = \{a, b\}$

$S \rightarrow AB$

$A \rightarrow aA | \epsilon$

$B \rightarrow bB | \epsilon$

Primeras 10 palabras = $\{\epsilon, a, b, aa, ab, bb, aaa, aab, abb, bbb\}$

$\epsilon = S \Rightarrow AB, AB \Rightarrow \epsilon \epsilon = \epsilon$

$a = S \Rightarrow AB, AB \Rightarrow aA\epsilon \Rightarrow a\epsilon \epsilon = a$

$b = S \Rightarrow AB, AB \Rightarrow \epsilon bB \Rightarrow \epsilon b \epsilon = b$

$aa = S \Rightarrow AB, AB \Rightarrow aA \epsilon \Rightarrow aaA \epsilon \Rightarrow, aa \epsilon \epsilon = aa$

$ab = S \Rightarrow AB, AB \Rightarrow aAbB \Rightarrow a \epsilon b \epsilon = ab$

$bb = S \Rightarrow AB, AB \Rightarrow \epsilon bB \Rightarrow \epsilon bbB \Rightarrow, \epsilon bb \epsilon = bb$

$aaa = S \Rightarrow AB, AB \Rightarrow aA \epsilon \Rightarrow aaA \epsilon \Rightarrow aaaA \epsilon \Rightarrow aaa \epsilon \epsilon = aaa$

$aab = S \Rightarrow AB, AB \Rightarrow aAbB \Rightarrow aaAb \epsilon \Rightarrow aa \epsilon b \epsilon = aab$

$abb = S \Rightarrow AB, AB \Rightarrow aAbB \Rightarrow a \epsilon bbB \Rightarrow a \epsilon bb \epsilon = abb$

$bbb = S \Rightarrow AB, AB \Rightarrow \epsilon bB \Rightarrow \epsilon bbB \Rightarrow \epsilon bbbB \Rightarrow \epsilon bbb \epsilon = bbb$