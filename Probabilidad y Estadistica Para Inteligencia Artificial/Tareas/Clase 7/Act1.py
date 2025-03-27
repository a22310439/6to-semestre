# Definimos los intervalos y frecuencias
intervals = [(10, 15), (15, 20), (20, 25), (25, 30), (30, 35)]
frequencies = [3, 5, 7, 4, 2]

# Puntos medios
midpoints = [(a + b) / 2 for (a, b) in intervals]

# Suma de frecuencias
N = sum(frequencies)

# Cálculo de la media
sum_xf = sum(m * f for m, f in zip(midpoints, frequencies))
mean = sum_xf / N

# Cálculo de E[X^2]
sum_x2f = sum((m**2) * f for m, f in zip(midpoints, frequencies))
mean_of_squares = sum_x2f / N

# Varianza (poblacional)
variance = mean_of_squares - mean**2

print("Media =", mean)
print("Varianza =", variance)
