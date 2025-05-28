import math

def prob_max_defectuosos(n, p, k_max):
    """
    Calcula la probabilidad de que en un lote de n unidades
    con probabilidad p de defecto, haya como máximo k_max defectuosos.
    """
    return sum(math.comb(n, k) * p**k * (1 - p)**(n - k) for k in range(k_max + 1))

# Parámetros del problema
n = 25       # tamaño del lote
p = 0.02     # probabilidad de defecto
k_max = 2    # máximo defectuosos

# Cálculo de la probabilidad
prob = prob_max_defectuosos(n, p, k_max)
print(f"P(X ≤ {k_max}) = {prob:.6f}")
