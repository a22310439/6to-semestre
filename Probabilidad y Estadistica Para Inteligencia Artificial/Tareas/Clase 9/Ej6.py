import math
from scipy.stats import norm

def calcular_tamano_muestra(p0, delta, probabilidad_deseada):
    """
    Calcula el tamaño de muestra n necesario para que la proporción muestral
    se encuentre dentro de p0 ± delta con probabilidad al menos probabilidad_deseada.
    """
    # Nivel de confianza bilateral
    alpha = 1 - probabilidad_deseada
    # Cuantil z para P(|Z| < z) = probabilidad_deseada
    z = norm.ppf(1 - alpha/2)
    
    # Fórmula para tamaño de muestra en proporciones
    n = (z**2 * p0 * (1 - p0)) / (delta**2)
    return math.ceil(n)

def main():
    p0 = 0.2               # proporción verdadera (2 de cada 10)
    delta = 0.04           # margen: ±4%
    probabilidad = 0.8     # probabilidad mínima de estar dentro del margen

    n = calcular_tamano_muestra(p0, delta, probabilidad)
    print(f"Número mínimo de pruebas ciegas requeridas: {n}")

if __name__ == "__main__":
    main()
