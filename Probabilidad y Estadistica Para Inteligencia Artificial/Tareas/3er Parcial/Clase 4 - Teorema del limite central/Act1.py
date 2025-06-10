import math

def norm_cdf(z: float) -> float:
    return 0.5 * (1 + math.erf(z / math.sqrt(2)))

def main():
    mu = 500.0        # media individual (en gramos)
    sigma = 35.0      # desviación típica individual (en gramos)
    n = 100           # tamaño de la muestra (bolsas por caja)

    # 1) Probabilidad de que la media de 100 bolsas sea < 495 g
    mu_mean = mu                      # Esperanza de X_n
    sigma_mean = sigma / math.sqrt(n) # Desviación típica de X_n
    límite = 495.0

    z1 = (límite - mu_mean) / sigma_mean
    p1 = norm_cdf(z1)

    # 2) Probabilidad de que el total de 100 bolsas supere 51 kg = 51 000 g
    mu_sum = n * mu
    sigma_sum = sigma * math.sqrt(n)
    límite_sum = 51_000.0  # en gramos

    z2 = (límite_sum - mu_sum) / sigma_sum
    # Queremos P(S > límite_sum) = 1 - Φ(z2)
    p2 = 1.0 - norm_cdf(z2)

    # Resultados
    print(f"1) P( X_100 < 495 g ) = P(Z < {z1:.4f}) = {p1:.6f}")
    print(f"2) P( S_100 > 51000 g ) = 1 - Φ({z2:.4f}) = {p2:.6f}")

if __name__ == "__main__":
    main()  
