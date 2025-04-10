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

## Gramáticas Regulares
Hay 2 tipos de gramáticas regulares:

* Lineal izquierda
* Lineal derecha

##
Ejemplo gramática derecha:
$A \rightarrow \epsilon$

$A \rightarrow w$

$A \rightarrow wB$

$A \rightarrow B$

##
Ejemplo gramática izquierda
$A \rightarrow \epsilon$

$A \rightarrow w$

$A \rightarrow Bw$

$A \rightarrow B$
##

NO se permite:

* Uso de dos símbolos no terminales: $A \rightarrow BC$
* Uso de reglas de producción izquierda y derecha: $A \rightarrow wB$ y $A \rightarrow Bw$
* Uso de símbolos no terminales "en medio": $A \rightarrow wBv$
##

### Ejercicio
$S \rightarrow AB$

$A \rightarrow \epsilon | aA$

$B \rightarrow \epsilon | bB$

Esta gramática no es regular, pero sabemos que las palabras que puede formar esta gramática es $a^*b^*$, quiere decir que es un lenguaje regular. Escribe la gramática de tal manera que sea regular.

$S \rightarrow aS | A$

$A \rightarrow \epsilon | bA$

## Autómatas Finitos
Se les conoce como máquinas de estados y transiciones.

* Estados: Los "estados" del sistema

* Transiciones: Estímulo que hace el cambio

Los estados se representan con círculos y las transiciones con flechas. Los estados pueden ser círculos "simples" que significan "trabajando" y un círculo doble que significa "fin". 

### Ejercicio
Escribir 10 palabras para un autómata finito (excepto "aaa") que sea definido de la siguiente manera:
El estado inicial tiene una transición hacia si mismo con la letra "b" y una transición al siguiente estado con la letra "a". El segundo estado tiene una transición hacis si mismo con la letra "b" y una transición al siguiente estado con la letra "a". El tercer estado tiene una transición hacis si mismo con la letra "b" y una transición al estado final con la letra "a".

Palabras: aaba, abaa, baaa, ababa, baaba, bababa, aabba, ababba, baabba, bababba, abbabba...

Este autómata pertenece a una subcategoría llamada Autómata Finito no Determinístico. Esto es porque llega a un punto en el que no sabe qué hacer ante cierta trancisión. En este caso, al llegar al estado final, si recibe otra letra, el autómata no sabe qué hacer porque no hay un estado que defina la siguiente instrucción.

En los autómatas deterministas, siempre se sabe qué hacer en todo momento en presencia de todas las transiciones.

### La definición formal de un autómata
$M = (Q, \Sigma, \delta, q0, F)$

Donde:

Q = El conjunto de estados

$\Sigma$ = El conjunto de símbolos del lenguaje

$\delta$ = El conjunto de transiciones

q0 = El estado inicial

F = El conjunto de etados finales

<table>
  <thead>
    <tr>
      <th></th>
      <th>a</th>
      <th>b</th>
      </tr>
  </thead>
  <tbody>
    <tr>
      <td>1</td>
      <td>2</td>
      <td>1</td>
    </tr>
    <tr>
      <td>2</td>
      <td>3</td>
      <td>2</td>
    </tr>
    <tr>
      <td>3</td>
      <td>4</td>
      <td>3</td>
    </tr>
    <tr>
      <td>4</td>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <td>5</td>
      <td>5</td>
      <td>5</td>
    </tr>
  </tbody>
</table>

Para representar cóḿo trabaja un autómata, se utiliza lo siguiente:

$\delta = (q, wa) = \delta (\delta(q, w), a)$

q = estado que va a transicionar

wa = la transición que se va a tomar

## Ejercicio
Encontrar la representación y la expresión regular del siguiente autómata:

El estado 1 (inicial) tiene la transición 'a' al estado 2 y la transición 'b' a si mismo. El estado 2 tiene la transición 'a' al estado 3 y la transición 'b' al estado 1. El estado 3 tiene la transición 'a' al estado 4 (final) y la transición 'b' al estado 1. El estado 4 y final tiene las transiciones 'a' y 'b' hacia si mismo.

<table>
  <thead>
    <tr>
      <th></th>
      <th>a</th>
      <th>b</th>
      </tr>
  </thead>
  <tbody>
    <tr>
      <td>1</td>
      <td>2</td>
      <td>1</td>
    </tr>
    <tr>
      <td>2</td>
      <td>3</td>
      <td>1</td>
    </tr>
    <tr>
      <td>3</td>
      <td>4</td>
      <td>1</td>
    </tr>
    <tr>
      <td>4</td>
      <td>4</td>
      <td>4</td>
    </tr>
  </tbody>
</table>

RegEx: $(b^*+(ab)^*+(aab)^*)^*aaa(a+b)^*$

## Ejercicio
El estado 0 (inicial) tiene teansición 'a' al estado 1 y la transición 'b' al estado 4. El estado 1 tiene la transición 'a' al estado 5 y la transición 'b' al estado 2. El estado 2 tiene la transición 'a' al estado 3 (final) y la transición 'b' a si mismo. El estado 3 (final) tiene la transición 'a' y 'b' a si mismo. El estado 4 tiene la transición 'a' al estado 5 y la transición 'b' al estado 6 (final). El estado 5 tiene la transición 'a' a si mismo y la transición 'b' al estado 3 (final). El estado 6 (final) tiene las transiciones 'a' y 'b' a si mismo.

Encontrar su tabla, la expresión regular y su gramática.