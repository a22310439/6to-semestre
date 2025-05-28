from itertools import product

def probabilidad_conjunta():
    total_combinaciones = 36
    combinaciones = list(product(range(1, 7), repeat=2))

    print("Función de probabilidad conjunta (x, y): P(x,y) = 1/36")

    # Calcular la probabilidad de que la suma sea 10
    suma_diez = [(x, y) for x, y in combinaciones if x + y == 10]
    prob_suma_10 = len(suma_diez) / total_combinaciones

    print("\nCombinaciones que suman 10:")
    for x, y in suma_diez:
        print(f"({x}, {y})")

    print(f"\nProbabilidad de que la suma sea 10: {len(suma_diez)}/36 = {prob_suma_10:.4f}")

if __name__ == "__main__":
    probabilidad_conjunta()
