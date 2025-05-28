import sympy as sp

# Definir variables
x1, x2 = sp.symbols('x1 x2', real=True, positive=True)

# Densidad conjunta
f = 4*x1*x2

# Medias
E1 = sp.integrate(x1 * f, (x1, 0, 1), (x2, 0, 1))
E2 = sp.integrate(x2 * f, (x1, 0, 1), (x2, 0, 1))

# Segundos momentos
E11 = sp.integrate(x1**2 * f, (x1, 0, 1), (x2, 0, 1))
E22 = sp.integrate(x2**2 * f, (x1, 0, 1), (x2, 0, 1))
E12 = sp.integrate(x1*x2 * f, (x1, 0, 1), (x2, 0, 1))

# Varianzas y covarianza
Var1 = sp.simplify(E11 - E1**2)
Var2 = sp.simplify(E22 - E2**2)
Cov12 = sp.simplify(E12 - E1*E2)

# Mostrar resultados
print("E[X1] =", E1)         # 2/3
print("E[X2] =", E2)         # 2/3
print("Var(X1) =", Var1)     # 1/18
print("Var(X2) =", Var2)     # 1/18
print("Cov(X1,X2) =", Cov12) # 0

# Vector de medias y matriz de covarianza
mu = sp.Matrix([E1, E2])
Sigma = sp.Matrix([[Var1, Cov12],
                   [Cov12, Var2]])

print("\nVector de medias μ:")
sp.pprint(mu)

print("\nMatriz de covarianza Σ:")
sp.pprint(Sigma)
