import math

def normal_cdf(x, mu=0, sigma=1):
    """Función de distribución acumulada de la normal N(mu, sigma)."""
    return 0.5 * (1 + math.erf((x - mu) / (sigma * math.sqrt(2))))

# Parámetros de la distribución
mu = 400      # media
sigma = 10    # desviación estándar

# a) Probabilidad de que una lata pese menos de 380 g
p_less_380 = normal_cdf(380, mu, sigma)

# b) Probabilidad de que una lata pese entre 385 y 410 g
p_between_385_410 = normal_cdf(410, mu, sigma) - normal_cdf(385, mu, sigma)

# Resultados
print(f"P(X < 380) = {p_less_380:.6f}")
print(f"P(385 < X < 410) = {p_between_385_410:.6f}")
