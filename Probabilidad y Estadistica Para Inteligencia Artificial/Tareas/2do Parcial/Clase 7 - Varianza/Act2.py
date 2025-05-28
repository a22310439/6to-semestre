import numpy as np
import matplotlib.pyplot as plt
from statistics import mean, median, mode, pvariance, pstdev

# 100 datos enteros aleatorios entre 1 y 100 como ejemplo
data = np.random.randint(1, 101, size=100).tolist()

# Medidas de tendencia central
media = mean(data)
mediana = median(data)

# En caso de que existan valores con frecuencia repetida
try:
    moda = mode(data)
except:
    moda = None

# Medidas de dispersión
rango_datos = max(data) - min(data)
varianza = pvariance(data)  # varianza poblacional
desviacion_estandar = pstdev(data)  # desviación estándar poblacional

# Rango intercuartílico (IQR)
q1 = np.percentile(data, 25)
q3 = np.percentile(data, 75)
iqr = q3 - q1

# -----------------------------
# 3. Impresión de resultados
# -----------------------------
print("RESULTADOS ESTADÍSTICOS:")
print(f" - Media:                 {media:.2f}")
print(f" - Mediana:               {mediana:.2f}")
print(f" - Moda:                  {moda if moda is not None else 'No definida'}")
print(f" - Rango:                 {rango_datos}")
print(f" - Varianza:              {varianza:.2f}")
print(f" - Desviación Estándar:   {desviacion_estandar:.2f}")
print(f" - Cuartil 1 (Q1):        {q1:.2f}")
print(f" - Cuartil 3 (Q3):        {q3:.2f}")
print(f" - Rango Intercuartílico: {iqr:.2f}")

# Gráfica de datos
plt.figure(figsize=(8, 5))
plt.hist(data, bins=10, edgecolor='black', alpha=0.7)

# Trazamos líneas verticales para la media, mediana y moda
plt.axvline(media, color='red', linestyle='dashed', linewidth=2, label=f'Media = {media:.2f}')
plt.axvline(mediana, color='green', linestyle='dashed', linewidth=2, label=f'Mediana = {mediana:.2f}')
if moda is not None:
    plt.axvline(moda, color='blue', linestyle='dashed', linewidth=2, label=f'Moda = {moda}')

plt.title('Distribución de Datos (100 valores)')
plt.xlabel('Valor')
plt.ylabel('Frecuencia')
plt.legend()
plt.tight_layout()
plt.show()
