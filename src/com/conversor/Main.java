package com.conversor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Inicializamos todas las tasas una sola vez
        ExchangeService.inicializarTasas();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║                                          ║");
            System.out.println("║    BIENVENIDO AL CONVERSOR DE MONEDA     ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.println("         1) Dólar ⇒ Peso argentino");
            System.out.println("         2) Peso argentino ⇒ Dólar");
            System.out.println("         3) Dólar ⇒ Real brasileño");
            System.out.println("         4) Real brasileño ⇒ Dólar");
            System.out.println("         5) Dólar ⇒ Peso colombiano");
            System.out.println("         6) Peso colombiano ⇒ Dólar");
            System.out.println("         7) Salir");
            System.out.print("           Elija una opción válida: ");

            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.print("Ingrese el monto a convertir: ");
                double monto = scanner.nextDouble();
                convertirMoneda(opcion, monto);
            } else if (opcion == 7) {
                System.out.println("Gracias por usar el conversor. ¡Hasta luego!");
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }

            System.out.println();
        } while (opcion != 7);

        scanner.close();
    }

    public static void convertirMoneda(int opcion, double monto) {
        String base = "";
        String destino = "";

        switch (opcion) {
            case 1 -> { base = "USD"; destino = "ARS"; }
            case 2 -> { base = "ARS"; destino = "USD"; }
            case 3 -> { base = "USD"; destino = "BRL"; }
            case 4 -> { base = "BRL"; destino = "USD"; }
            case 5 -> { base = "USD"; destino = "COP"; }
            case 6 -> { base = "COP"; destino = "USD"; }
        }

        double tasa = ExchangeService.obtenerTasa(base, destino);

        if (tasa != 0) {
            double resultado = monto * tasa;
            System.out.printf("Tasa actual: %.4f\n", tasa);
            System.out.printf("Resultado de la conversión: %.2f\n", resultado);
        } else {
            System.out.println("No se pudo obtener la tasa de cambio.");
        }
    }
}