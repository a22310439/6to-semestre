import math

def poisson_pmf(k, lam):
    """Calcula la probabilidad de masa de Poisson P(X=k) con parámetro lam."""
    return math.exp(-lam) * lam**k / math.factorial(k)

def poisson_cdf(k, lam):
    """Calcula la función de distribución acumulada P(X ≤ k) para Poisson."""
    return sum(poisson_pmf(i, lam) for i in range(k + 1))

# Parámetros del problema
tasa_promedio = 8 / 100  # fallos por hora
# Intervalos de tiempo
horas_a = 25
horas_b = 50
horas_c = 125

# Parámetros λ para cada intervalo
lam_a = tasa_promedio * horas_a
lam_b = tasa_promedio * horas_b
lam_c = tasa_promedio * horas_c

# a) P(X=1 en 25 horas)
p_a = poisson_pmf(1, lam_a)

# b) P(X<2 en 50 horas) = P(0) + P(1)
p_b = poisson_cdf(1, lam_b)

# c) P(X≥3 en 125 horas) = 1 - P(X≤2)
p_c = 1 - poisson_cdf(2, lam_c)

# Mostrar resultados
print(f"a) P(X = 1 en {horas_a} h)        = {p_a:.6f}")
print(f"b) P(X < 2 en {horas_b} h)        = {p_b:.6f}")
print(f"c) P(X ≥ 3 en {horas_c} h)        = {p_c:.6f}")
