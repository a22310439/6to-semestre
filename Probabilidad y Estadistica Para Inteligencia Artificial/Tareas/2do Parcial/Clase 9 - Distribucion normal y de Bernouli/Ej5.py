import numpy as np
from scipy.stats import norm

# Parámetros del problema
n = 15          # número de manzanas
mu = 150        # media (g) de cada manzana
sigma = 30      # desviación típica (g) de cada manzana

# Distribución de la suma
mu_sum = n * mu
sigma_sum = np.sqrt(n) * sigma

# Umbral de 2000 g (2 kg)
threshold = 2000

# Probabilidad de que el peso total sea < 2000 g
p_total_less_2000 = norm.cdf(threshold, loc=mu_sum, scale=sigma_sum)

print(f"P(peso total < {threshold} g) = {p_total_less_2000:.6f}")
