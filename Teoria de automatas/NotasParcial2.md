# Parcial 2
## Lenguajes regulares
Un tipo de lenguaje que tiene muchas restricciones. Hay lenguajes que son más generales en su expresividad, pero los lenguajes regulares son particularmente útiles a la hora de llevarlos a las computadoras.

Si se quiere resolver un problema de lenguaje en una computadora, utilizando los lenguajes regulares voy a obtener una solución muy eficiente.

En este tipo de lenguajes se utilizan:

* Autómatas Finitos
* Gramáticas Regulares
* Expresiones regulares

## Operaciones
Cerraduras (*, +)
Unión
Concatenación

### Ejercicio
Obtener la forma general del lenguaje, las reglas de producción de cada lenguaje y representar los lenguajes con operaciones de lenguajes, si es posible realizar todo, el lenguaje es regular.

a) $\{a, aa, aaa, aaaa, aaaaa, ...\}$

b) $\{\epsilon, a, b, aa, bb, aaa, ...\}$

c) $\{a, ab, ba, bab, babb, bbab, bbabb, bbabbb, ...\}$

d) $\{b, aba, aabaa, aaabaaa, aaaabaaaa, ...\}$

e) $\{aba, abba, abbba, abbbba, ...\}$

f) $\{aaa, aba, abba, baba\}$

### Resoluciones
a) $\{a^n | n > 0\}$

$S \rightarrow aS | a$

$\{a\}^+ \Rightarrow \text{Es regular}$

#
b) $\{a^n \cup b^m | n,m >= 0\}$

$S \rightarrow \epsilon | A | B$

$A \rightarrow aA | a$

$B \rightarrow bB | b$

$\{a\}^* \cup \{b\}^* \Rightarrow \text{Es regular}$

#
c) $\{b^nab^m | n,m >= 0\}$

$S \rightarrow AaB$

$A \rightarrow \epsilon | bA$

$B \rightarrow \epsilon | bB$

$\{b\}^*\{a\}\{b\}^* \Rightarrow \text{Es regular}$

#
d) $\{a^nba^n | n >= 0\}$

$S \rightarrow aSa | b$

No se puede producir con operaciones de lenguajes, no es regular

#
e) $\{ab^na | n > 0\}$

$S \rightarrow aAa$

$A \rightarrow bA | b$

$\{a\}\{b\}^+\{a\} \Rightarrow \text{Es regular}$

#
f) $\{aaa, aba, abba, baba\}$

$S \rightarrow aaa | aba | abba | baba$

$\{a\}\{a\}\{a\}\cup\{a\}\{b\}\{a\}\cup\{a\}\{b\}\{b\}\{a\}\cup\{b\}\{a\}\{b\}\{a\} \Rightarrow \text{Es regular}$

## Expresiones Regulares (RegEx)


$Lenguaje \quad Expresion Regular$

$\quad \{\} \quad \quad \quad \quad \quad \quad \quad 0$

$\quad \{\epsilon\}\quad \quad \quad \quad \quad \quad \quad \epsilon$

$\quad \{a\} \quad \quad \quad \quad \quad \quad \quad a$

Aplican las mismas operaciones: 

Si "x" y "y" son ER, entonces:

$Unión \quad (x+y)$

$Concatenación \quad (xy)$

$Cerradura \quad (x*)$

### Ejemplo
$L((a+b)) = L(a) \cup L(b)$

$L((ab)) = L(a)L(b) = \{ab\}$

$L((a*)) = L(a)^0 \cup L(a)^1 \cup L(a)^2 \cup ... = \{\epsilon, a, aa, aaa, ...\}$

$L((a+b)(ab)) = L((a+b))L((ab)) = \{a, b\}\{ab\} = \{aab, bab\}$

### Ejercicio: escribir las expresiones regulares de los lenguajes anteriores
a) $\{a, aa, aaa, aaaa, aaaaa, ...\}$

$L(((a)(a^*)))$

##
b) $\{\epsilon, a, b, aa, bb, aaa, ...\}$

$L(((a^*) + (b^*)))$

##
c) $\{a, ab, ba, bab, babb, bbab, bbabb, bbabbb, ...\}$

$L(((b^*a)(b^*)))$

##
d) $\{aba, abba, abbba, abbbba, ...\}$

$L((a(b(b^*)))a)$

##
e) $\{aaa, aba, abba, baba\}$

$L(((((aa)a) + ((ab)a)) + ((ab)(ba)) + ((ba)(ba))))$

