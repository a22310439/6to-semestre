import numpy as np
from scipy.stats import norm

# Parámetros de la distribución
mu = 4.5      # media (% de uso)
sigma = 0.5   # desviación típica

# a) P(X > 5%)
p_a = 1 - norm.cdf(5, loc=mu, scale=sigma)

# b) P(X < 3.75%)
p_b = norm.cdf(3.75, loc=mu, scale=sigma)

# c) Umbral para el 20% más alto de la población
umbral_c = norm.ppf(0.80, loc=mu, scale=sigma)

# d) Umbral para el 10% más bajo de la población
umbral_d = norm.ppf(0.10, loc=mu, scale=sigma)

# e) Intervalo central que contiene al 80% más próximo a la media
limite_inferior_e = norm.ppf(0.10, loc=mu, scale=sigma)
limite_superior_e = norm.ppf(0.90, loc=mu, scale=sigma)

# Mostrar resultados
print(f"a) P(uso > 5%)       = {p_a:.6f}")
print(f"b) P(uso < 3.75%)    = {p_b:.6f}")
print(f"c) Límite 20% alto   = {umbral_c:.2f}%")
print(f"d) Límite 10% bajo   = {umbral_d:.2f}%")
print(f"e) Intervalo central 80%: [{limite_inferior_e:.2f}%, {limite_superior_e:.2f}%]")
