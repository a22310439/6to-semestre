# Parcial 1
## ¿Qué es un autómata?
Es una máquina que puede interpretar una palabra o serie de palabras y detectar que pertenece a un lenguaje específico.

## ¿Qué es un lenguaje?
Un sistema de signos que es utilizado para comunicar e interpretar un mensaje.

**Conjunto de palabras**

## ¿Qué es la gramática?
Un conjunto de reglas que forman la estructura de un lenguaje para que pueda ser interpretado correctamente.

## Relación
El lenguaje tiene las palabras que después la gramática le da el orden o la estructura para que las palabras puedan ser interpretadas correctamente. El Autómata identifica la palabra o secuencia de palabras y determina si es válida la oración. El autómata también retroalimenta al lenguaje.

## ¿Qué es una cadena o palabra?
Una secuencia finita de címbolos juxtapuestos.

$W_0 = 110001$

$W_1 = abba$

## ¿Qué es un alfabeto? ($\Epsilon$)
Un conjunto de signos.

$\Sigma _0 = \{0, 1\}$

$\Sigma _1 = \{a, b\}$

## Operaciones
### Cardinalidad
$|W_0| = 6$

$|W_1| = 4$

$|\Sigma _0| = 2$

$|\Sigma _1| = 2$

### Cadena vacía
$|\Epsilon| = 0$

### Combinaciones del alfabeto
$\Sigma ^* = \{\Epsilon,a ,b aa, ab, ba, bb, aaa, aab, ...\}$

#### Cardinalidad de la combinación:
$|\Sigma ^*| = \infty$

## Concatenación
$w = ab \quad v = ba \quad x = aaa$

$wvx = abbaaa$

$(wv)x = w(vx) = wvx = abbaaa$

Es asociativa, pero no conmutativa

### Elemento neutro
$w\Epsilon = \Epsilon w = w$

## Modular
$|wvx| = |w| + |v| + |x|$

## Potencias
$w = ab$

$w^2 = abab$

$w^3 = ababab$

$w^4 = abababab$

En realidad la potencia es una concatenación

## Inversa
$w = ab$

$w^I = ba$

## Lenguajes simples
### {}, {$\Epsilon$}, {a}, {b}

## Unión de lenguajes ($\cup$)
$a \cup B = \{w \in \Sigma *| w \in A \vee w \in B\}$

$\{aa,ab\} \cup \{ab, ba\} = \{aa, bb, ab, ba\}$

## Intersección de lenguajes ($\cap$)
$A \cap B = \{w \in \Sigma * | w \in A \wedge w \in B\}$

$\{aa,ab\} \cap \{ab, ba\} = \{ab\}$

## Concatenación de lenguajes
$AB = \{wv \in \Sigma ^* | w \in A \wedge v \in B\}$

$\{aa,ab\} \{ab, ba\} = \{aaab, aaba, abab, abba\}$

## Prefijo
$v$ es prefijo de $w$ si existe una x $\in \Sigma * | vx = w$

Ejemplo: Alexa

$\Epsilon \quad$ Alexa

A $\quad$ lexa

Al $\quad$ exa

Ale $\quad$ xa

Alex $\quad$ A

Alexa $\quad \Epsilon$

